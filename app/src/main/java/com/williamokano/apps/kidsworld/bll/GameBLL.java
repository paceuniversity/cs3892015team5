package com.williamokano.apps.kidsworld.bll;

import android.content.ContentValues;

import com.williamokano.apps.kidsworld.DBHelper;
import com.williamokano.apps.kidsworld.models.Category;
import com.williamokano.apps.kidsworld.models.Game;
import com.williamokano.apps.kidsworld.models.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by William on 5/5/2015.
 */
public class GameBLL {

    public static Game ActualGame = null;

    public static Game Create(DBHelper helper, Category category, Player player) {
        Game g = new Game();
        g.setCategory(category);
        g.setPlayer(player);
        g.setDtBegin(new Date());

        helper.openDataBase();

        ContentValues values = new ContentValues();
        values.put("category_id", category.getIdCategoria());
        values.put("player_id", player.getIdPlayer());
        values.put("dt_begin", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(g.getDtBegin()));

        long id = helper.getMyDataBase().insertOrThrow("game", null, values);
        g.setIdGame(new Integer((int) id));

        return g;
    }
}
