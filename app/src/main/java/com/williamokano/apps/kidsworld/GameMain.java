package com.williamokano.apps.kidsworld;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

enum SwipeDirection {
    Left,
    Right
}

public class GameMain extends Activity {

    private float x1, x2;
    static final int MIN_DISTANCE = 150;
    public int actual = 0;
    SwipeDirection swipeDirection = null;

    private GestureDetector gestureDetector;

    ArrayList<Image> images = new ArrayList<>();
    ImageView main_game_image;

    TextView imageDescription;

    private SoundPool soundPool;
    private HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    private int currStreamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * While no database is used, we define manually
         * the images to test and implement the "slide" functionality
         */

        // Initialize animals

        Image imageDog, imageCat;

        imageDog = new Image();
        imageCat = new Image();

        Sound soundDog = new Sound(R.raw.dog);
        Sound soundCat = new Sound(R.raw.cat);

        imageDog.setImageAsset(R.drawable.dog);
        imageDog.setDescription("DOG");
        imageDog.setSoundAsset(soundDog);
        images.add(imageDog);

        imageCat.setImageAsset(R.drawable.cat);
        imageCat.setDescription("CAT");
        imageCat.setSoundAsset(soundCat);
        images.add(imageCat);


        // Initialize colors

        Image colorYellow, colorRed, colorBlue, colorBlack, colorGreen, colorOrange, colorPink, colorPurple, colorWhite;

        colorBlack = new Image();
        colorBlue = new Image();
        colorGreen = new Image();
        colorOrange = new Image();
        colorPink = new Image();
        colorPurple = new Image();
        colorRed = new Image();
        colorWhite = new Image();
        colorYellow = new Image();

        Sound soundBlack = new Sound(R.raw.black);
        Sound soundBlue = new Sound(R.raw.blue);
        Sound soundGreen = new Sound(R.raw.green);
        Sound soundOrange = new Sound(R.raw.orange);
        Sound soundPink = new Sound(R.raw.pink);
        Sound soundPurple = new Sound(R.raw.purple);
        Sound soundRed = new Sound(R.raw.red);
        Sound soundWhite = new Sound(R.raw.white);
        Sound soundYellow = new Sound(R.raw.yellow);

        colorBlack.setImageAsset(R.drawable.black);
        colorBlack.setDescription("BLACK");
        colorBlack.setSoundAsset(soundBlack);
        images.add(colorBlack);

        colorBlue.setImageAsset(R.drawable.blue);
        colorBlue.setDescription("BLUE");
        colorBlue.setSoundAsset(soundBlue);
        images.add(colorBlue);

        colorGreen.setImageAsset(R.drawable.green);
        colorGreen.setDescription("GREEN");
        colorGreen.setSoundAsset(soundGreen);
        images.add(colorGreen);

        colorOrange.setImageAsset(R.drawable.orange);
        colorOrange.setDescription("ORANGE");
        colorOrange.setSoundAsset(soundOrange);
        images.add(colorOrange);

        colorPink.setImageAsset(R.drawable.pink);
        colorPink.setDescription("PINK");
        colorPink.setSoundAsset(soundPink);
        images.add(colorPink);

        colorPurple.setImageAsset(R.drawable.purple);
        colorPurple.setDescription("PURPLE");
        colorPurple.setSoundAsset(soundPurple);
        images.add(colorPurple);

        colorRed.setImageAsset(R.drawable.red);
        colorRed.setDescription("RED");
        colorRed.setSoundAsset(soundRed);
        images.add(colorRed);

        colorWhite.setImageAsset(R.drawable.white);
        colorWhite.setDescription("WHITE");
        colorWhite.setSoundAsset(soundWhite);
        images.add(colorWhite);

        colorYellow.setImageAsset(R.drawable.yellow);
        colorYellow.setDescription("YELLOW");
        colorYellow.setSoundAsset(soundYellow);
        images.add(colorYellow);

        // Initialize shapes

        Image shapeSquare, shapeRectangle, shapeCircle, shapeTriangle;
        Sound soundCircle = new Sound(R.raw.circle);
        Sound soundRectangle = new Sound(R.raw.rectangle);
        Sound soundSquare = new Sound(R.raw.square);
        Sound soundTriangle = new Sound(R.raw.triangle);

        shapeCircle = new Image();
        shapeRectangle = new Image();
        shapeSquare = new Image();
        shapeTriangle = new Image();

        shapeCircle.setImageAsset(R.drawable.circle);
        shapeCircle.setDescription("CIRCLE");
        shapeCircle.setSoundAsset(soundCircle);
        images.add(shapeCircle);

        shapeRectangle.setImageAsset(R.drawable.rectangle);
        shapeRectangle.setDescription("RECTANGLE");
        shapeRectangle.setSoundAsset(soundRectangle);
        images.add(shapeRectangle);

        shapeSquare.setImageAsset(R.drawable.square);
        shapeSquare.setDescription("SQUARE");
        shapeSquare.setSoundAsset(soundSquare);
        images.add(shapeSquare);

        shapeTriangle.setImageAsset(R.drawable.triangle);
        shapeTriangle.setDescription("TRIANGLE");
        shapeTriangle.setSoundAsset(soundTriangle);
        images.add(shapeTriangle);


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
                 * We save the last position fro mthe screen when
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
                 * updateImage function to update the imagem.
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
