package com.williamokano.apps.kidsworld;

import com.williamokano.apps.kidsworld.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class SplashScreen extends Activity {

    //Splash screen timer
    private static int SPLASH_TIMEOUT = 3000; //3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        /**
         * Make a new handler to open with delay the main intent
         * and then close this to prevent the user to come back
         * because this activity must be shown just one time
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * Create the intent and start the activity
                 */
                Intent MainGameIntent = new Intent(SplashScreen.this, SelectProfileActivity.class);
                startActivity(MainGameIntent);

                /**
                 * Finish this activity to prevent from coming back
                 */
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
