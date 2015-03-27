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

        main_game_image = (ImageView)findViewById(R.id.main_game_image);
        updateImage();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                swipeDirection = deltaX > 0 ? SwipeDirection.Right : SwipeDirection.Left;

                if(Math.abs(deltaX) > MIN_DISTANCE) {
                    updateImage();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void updateImage() {
        int size = images.size();
        if(swipeDirection == SwipeDirection.Left) {
            actual = (actual + size - 1) % size;
        }else if(swipeDirection == SwipeDirection.Right) {
            actual = (actual + size + 1) % size;
        }
        main_game_image.setBackgroundResource(images.get(actual).getImageAsset());
    }
}
