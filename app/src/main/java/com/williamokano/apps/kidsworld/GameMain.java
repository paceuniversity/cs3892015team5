package com.williamokano.apps.kidsworld;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.williamokano.apps.kidsworld.models.Image;
import com.williamokano.apps.kidsworld.models.Sound;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import android.database.Cursor;

enum SwipeDirection {
    Left,
    Right
}

public class GameMain extends Activity {

    private float x1, x2;
    static final int MIN_DISTANCE = 150;
    public int actual = 0;
    SwipeDirection swipeDirection = null;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private GestureDetector gestureDetector;

    ArrayList<Image> images = new ArrayList<>();
    ImageView main_game_image;

    TextView imageDescription;

    private SoundPool soundPool;
    private HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    private int currStreamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbHelper = new DBHelper(this);

        db = dbHelper.getReadableDatabase();

        String[] columns = {dbHelper.IMAGE, dbHelper.SOUND, dbHelper.OBJ_DESCRIPTION};

        Cursor c = db.query(dbHelper.TABLE,columns, null, null, null, null, null );

        c.moveToFirst();

        while(!c.isAfterLast()){

            images.add(new Image(c.getInt(c.getColumnIndexOrThrow(dbHelper.IMAGE)),
                    c.getInt(c.getColumnIndexOrThrow(dbHelper.SOUND)),
                    c.getString(c.getColumnIndexOrThrow(dbHelper.OBJ_DESCRIPTION))));

            c.moveToNext();

        }

        Collections.shuffle(images);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        /**
         * Find the instance of the imageview and set the first
         * image from the array, which is the dog!
         */
        main_game_image = (ImageView) findViewById(R.id.main_game_image);
        updateImage();

        initSoundPool();
    }

    /**
     * This method was overrided to implement a simple swipe
     * detection. The GestureDetection class wasn't needed
     * to avoid unnecessary waste of resources.
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            /**
             * This case is triggered when the user touch the screen
             */
            case MotionEvent.ACTION_DOWN:

                /**
                 * We save the initial position from the screen when the
                 * screen was touched
                 */
                x1 = event.getX();
                break;

            /**
             * This case is triggered when the user untouch the screen
             */
            case MotionEvent.ACTION_UP:

                /**
                 * We save the last position fro the screen when
                 * the screen was untouched.
                 */
                x2 = event.getX();

                /**
                 * DeltaX is the difference from the
                 * initial "x" position and the final "x" position
                 */
                float deltaX = x2 - x1;

                /**
                 * We define if the swipe was from left to right
                 * or if the swipe was right to left
                 */
                swipeDirection = deltaX > 0 ? SwipeDirection.Right : SwipeDirection.Left;

                /**
                 * Check if the swipe action must be triggered.
                 * Only triggers when the delta distance (the distance travelled
                 * from the user finger on the touch action). If it absolute value
                 * is bigger than the necessary distance to trigger, we call the
                 * updateImage function to update the image.
                 */
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    updateImage();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), images.get(actual).getDescription(), Toast.LENGTH_SHORT);
                    toast.show();
                    play(actual+1, 0);
                }
                break;
        }
        return false;
        //return super.onTouchEvent(event);
    }

    /**
     * This method is responsible for updating the mainActivity
     * image depending on the swipe.
     */
    public void updateImage() {
        int size = images.size();

        /**
         * The way that I implemented the actual counter relies on modulus.
         * I added size to the actual because when the mod is calculated
         * it not change the result and I try to keep actual positive.
         * Doing this I prevent from the user keep swiping left and I
         * have to calculate the modulus of a negative number, which was
         * triggering run-time errors on the application.
         */
        if (swipeDirection == SwipeDirection.Left) {
            this.actual = (actual + size - 1) % size;
        } else if (swipeDirection == SwipeDirection.Right) {
            this.actual = (actual + size + 1) % size;
        }

        /**
         * Just update programmatically the ImageView from the layout.
         */
        main_game_image.setBackgroundResource(images.get(this.actual).getImageAsset());
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
}
