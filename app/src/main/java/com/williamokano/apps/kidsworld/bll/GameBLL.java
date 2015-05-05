package com.williamokano.apps.kidsworld.bll;

import android.content.ContentValues;
import android.database.Cursor;

import com.williamokano.apps.kidsworld.DBHelper;
import com.williamokano.apps.kidsworld.models.Category;
import com.williamokano.apps.kidsworld.models.Game;
import com.williamokano.apps.kidsworld.models.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static ArrayList<Game> GetGames(DBHelper helper, Player player) throws ParseException {
        ArrayList<Game> games = new ArrayList<>();
        helper.openDataBase();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] columns = {"_id", "category_id", "player_id", "dt_begin"};
        Cursor c = helper.getMyDataBase().query("game", columns, null, null, null, null, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            Game g = new Game();

            Integer Id;
            Integer CategoryId;
            Integer PlayerId;
            String DtBegin;

            Id = c.getInt(c.getColumnIndex("_id"));
            CategoryId = c.getInt(c.getColumnIndex("category_id"));
            PlayerId = c.getInt(c.getColumnIndex("player_id"));
            DtBegin = c.getString(c.getColumnIndex("dt_begin"));

            Category cat = CategoryBLL.GetCategory(helper, CategoryId);
            Player p = PlayerBLL.GetPlayer(helper, PlayerId);

            g.setPlayer(p);
            g.setCategory(cat);
            g.setDtBegin(format.parse(DtBegin));

            c.moveToNext();
        }

        return games;
    }
}
