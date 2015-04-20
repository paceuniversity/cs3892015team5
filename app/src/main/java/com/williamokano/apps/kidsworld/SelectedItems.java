package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.HashMap;

/**
 * Created by Eva on 2015/4/18.
 */
public class SelectedItems extends Activity{
    int k;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itembutton);

        Button button1 = (Button)findViewById(R.id.train);
        Button button2 = (Button)findViewById(R.id.quiz);

        Intent i = getIntent();
        k = i.getIntExtra("Key",0);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedItems.this,GameMain.class);
                intent.putExtra("Key",k);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedItems.this,QuizActivity.class);
                startActivity(intent);
            }
        });

    }
}
