package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.williamokano.apps.kidsworld.models.Image;

import java.util.ArrayList;

enum SwipeDirection {
    Left,
    Right
}

public class GameMain extends Activity {

    private float x1, x2;
    static final int MIN_DISTANCE = 150;
    public int actual = 0;
    SwipeDirection swipeDirection = null;

    ArrayList<Image> images = new ArrayList<>();
    ImageView main_game_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * While no database is used, we define manually
         * the images to test and implement the "slide" functionality
         */
        Image img1, img2;
        img1 = new Image();
        img2 = new Image();

        img1.setImageAsset(R.drawable.dog);
        img1.setDescription("DOG");

        img2.setImageAsset(R.drawable.cat);
        img2.setDescription("CAT");

        images.add(img1);
        images.add(img2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        /**
         * Find the instance of the imageview and set the first
         * image from the array, which is the dog!
         */
        main_game_image = (ImageView)findViewById(R.id.main_game_image);
        updateImage();
    }

    /**
     * This method was overrided to implement a simple swipe
     * detection. The GestureDetection class wasn't needed
     * to avoid unnecessary waste of resources.
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
                if(Math.abs(deltaX) > MIN_DISTANCE) {
                    updateImage();
                }
                break;
        }
        return super.onTouchEvent(event);
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
        if(swipeDirection == SwipeDirection.Left) {
            actual = (actual + size - 1) % size;
        }else if(swipeDirection == SwipeDirection.Right) {
            actual = (actual + size + 1) % size;
        }

        /**
         * Just update programmatically the ImageView from the layout.
         */
        main_game_image.setBackgroundResource(images.get(actual).getImageAsset());
    }
}
