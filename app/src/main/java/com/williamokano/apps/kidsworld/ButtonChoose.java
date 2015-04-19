package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Eva on 2015/4/11.
 */
public class ButtonChoose extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        Button button1 = (Button) findViewById(R.id.button_1);
        Button button_2 = (Button) findViewById(R.id.button_2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonChoose.this, GameMain.class);
                startActivity(intent);
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonChoose.this, QuizActivity.class));
            }
        });
    }
}
