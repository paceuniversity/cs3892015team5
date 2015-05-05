package com.williamokano.apps.kidsworld.bll;

import android.content.ContentValues;

import com.williamokano.apps.kidsworld.DBHelper;
import com.williamokano.apps.kidsworld.models.Statistic;

/**
 * Created by William on 5/5/2015.
 */
public class StatisticsBLL {
    public static void Create(DBHelper helper, Statistic s) {
        helper.openDataBase();
        ContentValues values = new ContentValues();

        values.put("game_id", s.getGame().getIdGame());
        values.put("thing_id", s.getThing().getIdThing());
        values.put("times_wrong", s.getTimesWrong());

        int id = (int)helper.getMyDataBase().insertOrThrow("statistics", null, values);
        s.setId(id);
    }
}
