package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.williamokano.apps.kidsworld.bll.GameBLL;
import com.williamokano.apps.kidsworld.bll.PlayerBLL;
import com.williamokano.apps.kidsworld.models.Game;
import com.williamokano.apps.kidsworld.models.Player;

import java.text.ParseException;
import java.util.ArrayList;


public class StatisticsMainActivity extends Activity {

    DBHelper helper;
    ListView statistics_listing = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_main);

        helper = new DBHelper(this);
        statistics_listing = (ListView)findViewById(R.id.statistics_listing);
        ArrayList<Game> games = null;
        try {
            games = GameBLL.GetGames(helper, PlayerBLL.ActualPlayer);



        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
