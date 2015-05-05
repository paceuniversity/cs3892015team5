package com.williamokano.apps.kidsworld.bll;

import android.database.Cursor;

import com.williamokano.apps.kidsworld.DBHelper;
import com.williamokano.apps.kidsworld.models.Category;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ThanatosVK on 5/3/2015.
 */
public class CategoryBLL {

    public static Category GetCategory(DBHelper helper, Integer id) {
        helper.openDataBase();
        Category cat = null;
        Cursor c = helper.getMyDataBase().query("category", new String[]{"_id", "name"}, "_id = " + id.toString() + "", null, null, null, null);
        c.moveToFirst();
        if (!c.isAfterLast()) {
            int internalId = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            cat = new Category(internalId, name);
        }
        return cat;
    }

    public static ArrayList<Category> GetCategories(DBHelper dbHelper){

        ArrayList<Category> categories = new ArrayList<>();

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dbHelper.openDataBase();

        String[] columns = {"_id", "name"};

        Cursor c = dbHelper.getMyDataBase().query("category", columns, null, null, null, null, null);


        c.moveToFirst();

        while(!c.isAfterLast()){

            categories.add(new Category(c.getInt(c.getColumnIndexOrThrow("_id")), c.getString(c.getColumnIndexOrThrow("name"))));

            c.moveToNext();
        }

        return categories;
    }
}
