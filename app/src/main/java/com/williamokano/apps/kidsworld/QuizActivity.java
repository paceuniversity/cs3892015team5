package com.williamokano.apps.kidsworld;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.williamokano.apps.kidsworld.bll.CategoryBLL;
import com.williamokano.apps.kidsworld.bll.GameBLL;
import com.williamokano.apps.kidsworld.bll.StatisticsBLL;
import com.williamokano.apps.kidsworld.bll.ThingBLL;
import com.williamokano.apps.kidsworld.models.Category;
import com.williamokano.apps.kidsworld.models.Image;
import com.williamokano.apps.kidsworld.models.Sound;
import com.williamokano.apps.kidsworld.models.Statistic;
import com.williamokano.apps.kidsworld.models.Thing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class QuizActivity extends Activity {

    TextView quiz_top_text = null;
    ImageView quiz_body_img1 = null;
    ImageView quiz_body_img2 = null;
    ImageView quiz_body_img3 = null;
    ImageView quiz_body_img4 = null;

    ArrayList<Thing> images = new ArrayList<>();
    ArrayList<Image> shapeimages = new ArrayList<>();
    ArrayList<Image> colorimages = new ArrayList<>();
    ArrayList<Image> animalimages = new ArrayList<>();

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    private int currStreamId;
    private int actual = 0;
    private int selectedOption = 0;
    private int timesWrong = 0;
    private Category cat = null;

    ArrayList<Sound> ErrorsSound = new ArrayList<>();

    ArrayList<Thing> ActualScreenItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quiz_top_text = (TextView) findViewById(R.id.quiz_top_text);
        quiz_body_img1 = (ImageView) findViewById(R.id.quiz_body_img1);
        quiz_body_img2 = (ImageView) findViewById(R.id.quiz_body_img2);
        quiz_body_img3 = (ImageView) findViewById(R.id.quiz_body_img3);
        quiz_body_img4 = (ImageView) findViewById(R.id.quiz_body_img4);

        quiz_body_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = 0;
                testOption();
            }
        });

        quiz_body_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = 1;
                testOption();
            }
        });

        quiz_body_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = 2;
                testOption();
            }
        });

        quiz_body_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = 3;
                testOption();
            }
        });

        Intent selfIntent = getIntent();
        int v = selfIntent.getIntExtra("Key", 0);

        dbHelper = new DBHelper(this);
        ThingBLL thingHelper = new ThingBLL();

        Category cat = CategoryBLL.GetCategory(dbHelper, v);

        images = thingHelper.GetThings(dbHelper, cat);

        Collections.shuffle(images);

        initSoundPool();

        updateScreen();
    }

    private void testOption() {
        if(ActualScreenItems.get(selectedOption).getImage().getDescription().equals(quiz_top_text.getText())) {

            Statistic s = new Statistic();
            s.setGame(GameBLL.ActualGame);
            s.setThing(ActualScreenItems.get(selectedOption));
            s.setTimesWrong(timesWrong);

            StatisticsBLL.Create(dbHelper, s);

            if(actual + 1 < images.size()) {
                actual++;
                updateScreen();
            }else{
                Intent MainGameIntent = new Intent(QuizActivity.this, ButtonChoose.class);
                startActivity(MainGameIntent);
                finish();
            }
        }else{
            timesWrong++;
            this.play(null);
        }
    }

    private void updateScreen() {
        timesWrong = 0;
        quiz_top_text.setText(images.get(actual).getDescription());
        play(actual + 1, 0);

        Image actualImage = images.get(actual).getImage();
        ArrayList<Thing> RandomList = new ArrayList<>(images);
        RandomList.remove(actual);

        Collections.shuffle(RandomList);

        ArrayList<Thing> ScreenItems = new ArrayList<>();
        ScreenItems.add(images.get(actual));
        ScreenItems.add(RandomList.get(0));
        ScreenItems.add(RandomList.get(1));
        ScreenItems.add(RandomList.get(2));

        Collections.shuffle(ScreenItems);

        quiz_body_img1.setBackgroundResource(ScreenItems.get(0).getImage().getImageAsset());
        quiz_body_img2.setBackgroundResource(ScreenItems.get(1).getImage().getImageAsset());
        quiz_body_img3.setBackgroundResource(ScreenItems.get(2).getImage().getImageAsset());
        quiz_body_img4.setBackgroundResource(ScreenItems.get(3).getImage().getImageAsset());

        ActualScreenItems = new ArrayList<>(ScreenItems);
    }

    @TargetApi(21)
    private void initSoundPool() {
        if ((android.os.Build.VERSION.SDK_INT) == 21) {
            SoundPool.Builder soundPool21 = new SoundPool.Builder();
            soundPool21.setMaxStreams(15);
            soundPool = soundPool21.build();
        } else {
            soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 0);
        }
        for (int i = 0; i< images.size(); i++ )
        {
            hashMap.put(i+1, soundPool.load(getApplicationContext(),images.get(i).getImage().getSoundAsset().getSoundAsset(), 1));
        }
    }

    private void play(int sound, int loop) {
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        float currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = currVolume / maxVolume;
        currStreamId = soundPool.play(hashMap.get(sound), volume, volume, 1, loop, 1.0f);
        System.out.println(currStreamId);
    }

    private void play(Sound sound) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.try_again);
        mp.setLooping(false);
        mp.start();
    }

    private String whichCategory(int id){

        switch (id){
            case 1:
                return "shapes";

            case 2:
                return "colors";

            case 3:
                return "animals";

            default:
                return "";

        }

    }

}
