package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.williamokano.apps.kidsworld.bll.PlayerBLL;
import com.williamokano.apps.kidsworld.models.Player;

import java.util.ArrayList;


public class SelectProfileActivity extends Activity {

    Spinner profile_spinner = null;
    Button select_profile_select_profile_btn = null;
    Button select_profile_create_new_profile_btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        profile_spinner = (Spinner)findViewById(R.id.profile_spinner);
        select_profile_select_profile_btn = (Button)findViewById(R.id.select_profile_select_profile_btn);
        select_profile_create_new_profile_btn = (Button)findViewById(R.id.select_profile_create_new_profile_btn);

        DBHelper dbHelper = new DBHelper(this);
        ArrayList<Player> players = PlayerBLL.GetPlayers(dbHelper);

        select_profile_select_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(SelectProfileActivity.this, ButtonChoose.class);
                startActivity(mainMenu);
                finish();
            }
        });

        select_profile_create_new_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createProfile = new Intent(SelectProfileActivity.this, ProfileCreateActivity.class);
                startActivity(createProfile);
                finish();
            }
        });

        /**
         * Spinner select
         */
        profile_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PlayerBLL.ActualPlayer = (Player) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Go to create activity
        if(players.size() == 0) {
            Intent profileCreate = new Intent(SelectProfileActivity.this, ProfileCreateActivity.class);
            startActivity(profileCreate);
            finish(); //Finish to avoid go back here just by the back button.
        }else {
            PlayerBLL.ActualPlayer = players.get(0);
            ArrayAdapter<Player> dataAdapter = new ArrayAdapter<Player>(this,
                    android.R.layout.simple_spinner_item, players);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            profile_spinner.setAdapter(dataAdapter);
        }
    }
}
