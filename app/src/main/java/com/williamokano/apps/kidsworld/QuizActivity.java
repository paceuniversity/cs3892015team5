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

import com.williamokano.apps.kidsworld.models.Image;
import com.williamokano.apps.kidsworld.models.Sound;

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

    ArrayList<Image> images = new ArrayList<>();
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

    ArrayList<Sound> ErrorsSound = new ArrayList<>();

    ArrayList<Image> ActualScreenItems = null;

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

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper.IMAGE, dbHelper.SOUND, dbHelper.OBJ_DESCRIPTION,dbHelper.CATEGORY};
        Cursor c = db.query(dbHelper.TABLE,columns, null, null, null, null, null );

        int v = selfIntent.getIntExtra("Key", 0);

        c.moveToFirst();
        while(!c.isAfterLast()){
            Image img = new Image();
            img.setImageAsset(c.getInt(c.getColumnIndexOrThrow(dbHelper.IMAGE)));
            img.setSoundAsset(new Sound(c.getInt(c.getColumnIndexOrThrow(dbHelper.SOUND))));
            img.setDescription(c.getString(c.getColumnIndexOrThrow(dbHelper.OBJ_DESCRIPTION)));
            img.setCategory(c.getString(c.getColumnIndexOrThrow(dbHelper.CATEGORY)));

            if(c.getString(c.getColumnIndexOrThrow(dbHelper.CATEGORY)).equals("Shapes")) {
                shapeimages.add(img);
            }else if(c.getString(c.getColumnIndexOrThrow(dbHelper.CATEGORY)).equals("Colors")){
                colorimages.add(img);
            }else if(c.getString(c.getColumnIndexOrThrow(dbHelper.CATEGORY)).equals("Animals")){
                animalimages.add(img);
            }
            c.moveToNext();
        }

        switch(v){
            case 1:
                images = shapeimages;
                break;
            case 2:
                images = colorimages;
                break;
            case 3:
                images = animalimages;
                break;
        }

        Collections.shuffle(images);

        initSoundPool();

        updateScreen();
    }

    private void testOption() {
        if(ActualScreenItems.get(selectedOption).getDescription().equals(quiz_top_text.getText())) {
            if(actual + 1 < images.size()) {
                actual++;
                updateScreen();
            }else{
                Intent MainGameIntent = new Intent(QuizActivity.this, ButtonChoose.class);
                startActivity(MainGameIntent);
                finish();
            }
        }else{
            this.play(null);
        }
    }

    private void updateScreen() {
        quiz_top_text.setText(images.get(actual).getDescription());
        play(actual + 1, 0);

        Image actualImage = images.get(actual);
        ArrayList<Image> RandomList = new ArrayList<>(images);
        RandomList.remove(actual);

        Collections.shuffle(RandomList);

        ArrayList<Image> ScreenItems = new ArrayList<>();
        ScreenItems.add(actualImage);
        ScreenItems.add(RandomList.get(0));
        ScreenItems.add(RandomList.get(1));
        ScreenItems.add(RandomList.get(2));

        Collections.shuffle(ScreenItems);

        quiz_body_img1.setBackgroundResource(ScreenItems.get(0).getImageAsset());
        quiz_body_img2.setBackgroundResource(ScreenItems.get(1).getImageAsset());
        quiz_body_img3.setBackgroundResource(ScreenItems.get(2).getImageAsset());
        quiz_body_img4.setBackgroundResource(ScreenItems.get(3).getImageAsset());

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
            hashMap.put(i+1, soundPool.load(getApplicationContext(),images.get(i).getSoundAsset().getSoundAsset(), 1));
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

}
