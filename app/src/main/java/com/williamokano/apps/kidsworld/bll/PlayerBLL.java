package com.williamokano.apps.kidsworld.bll;

import android.content.ContentValues;
import android.database.Cursor;

import com.williamokano.apps.kidsworld.DBHelper;
import com.williamokano.apps.kidsworld.models.Player;

import java.util.ArrayList;

/**
 * Created by William on 5/4/2015.
 */
public class PlayerBLL {

    public static Player ActualPlayer = null;

    public static Player CreatePlayer(DBHelper helper, String name) throws Exception {
        helper.openDataBase();
        String[] columns = {"_id", "name"};

        Player p = PlayerBLL.GetPlayer(helper, name);
        if (p != null)
            throw new Exception("Player already exists");

        ContentValues values = new ContentValues();

        values.put("name", name);

        long id = helper.getMyDataBase().insertOrThrow("player", null, values);

        p = new Player();
        p.setIdPlayer((int) id);
        p.setName(name);

        return p;
    }

    public static Player GetPlayer(DBHelper helper, String name) {
        helper.openDataBase();
        Player p = null;
        Cursor c = helper.getMyDataBase().query("player", new String[]{"_id", "name"}, "name = '" + name + "'", null, null, null, null);
        c.moveToFirst();

        if (!c.isAfterLast()) {
            p = new Player();
            p.setName(c.getString(c.getColumnIndex("name")));
            p.setIdPlayer(c.getInt(c.getColumnIndex("_id")));
        }

        return p;
    }

    public static Player GetPlayer(DBHelper helper, Integer _id) {
        helper.openDataBase();
        Player p = null;
        Cursor c = helper.getMyDataBase().query("player", new String[]{"_id", "name"}, "_id = " + _id + "", null, null, null, null);
        c.moveToFirst();

        if (!c.isAfterLast()) {
            p = new Player();
            p.setName(c.getString(c.getColumnIndex("name")));
            p.setIdPlayer(c.getInt(c.getColumnIndex("_id")));
        }

        return p;
    }

    public static ArrayList<Player> GetPlayers(DBHelper helper) {
        ArrayList<Player> players = new ArrayList<>();
        helper.openDataBase();

        String[] columns = {"_id", "name"};
        Cursor c = helper.getMyDataBase().query("player", columns, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));

            players.add(new Player(id, name));
            c.moveToNext();
        }

        return players;
    }

}
