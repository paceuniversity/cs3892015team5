package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.williamokano.apps.kidsworld.bll.CategoryBLL;
import com.williamokano.apps.kidsworld.bll.GameBLL;
import com.williamokano.apps.kidsworld.bll.PlayerBLL;
import com.williamokano.apps.kidsworld.models.Category;
import com.williamokano.apps.kidsworld.models.Game;

import java.util.HashMap;

/**
 * Created by Eva on 2015/4/18.
 */
public class SelectedItems extends Activity{
    int k;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itembutton);

        Button btnTraining = (Button)findViewById(R.id.train);
        Button btnQuiz = (Button)findViewById(R.id.quiz);
        final DBHelper helper = new DBHelper(this);

        Intent i = getIntent();
        k = i.getIntExtra("Key",0);

        btnTraining.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedItems.this,GameMain.class);
                intent.putExtra("Key",k);
                startActivity(intent);
                finish();
            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Category
                Category cat = CategoryBLL.GetCategory(helper, k);

                //Create the game
                Game g = GameBLL.Create(helper, cat, PlayerBLL.ActualPlayer);

                //Set the game on the BLL
                GameBLL.ActualGame = g;

                Intent intent = new Intent(SelectedItems.this,QuizActivity.class);
                intent.putExtra("Key",k);
                startActivity(intent);
                finish();
            }
        });

    }
}
