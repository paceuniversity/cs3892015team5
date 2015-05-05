package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.williamokano.apps.kidsworld.bll.PlayerBLL;
import com.williamokano.apps.kidsworld.models.Player;


public class ProfileCreateActivity extends Activity {

    Button profile_create_save_btn = null;
    EditText profile_create_name_txt = null;
    DBHelper helper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);

        profile_create_save_btn = (Button) findViewById(R.id.profile_create_save_btn);
        profile_create_name_txt = (EditText) findViewById(R.id.profile_create_name_txt);
        helper = new DBHelper(this);

        profile_create_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Player p = PlayerBLL.GetPlayer(helper, profile_create_name_txt.getText().toString());
                try {
                    Player p = PlayerBLL.CreatePlayer(helper, profile_create_name_txt.getText().toString());
                    PlayerBLL.ActualPlayer = p;

                    //Go back to profile screen
                    Intent selectProfile = new Intent(ProfileCreateActivity.this, SelectProfileActivity.class);
                    startActivity(selectProfile);
                    finish(); //Finish this screen to avoid to came back from back button
                }catch(Exception e) {
                    Toast.makeText(ProfileCreateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
