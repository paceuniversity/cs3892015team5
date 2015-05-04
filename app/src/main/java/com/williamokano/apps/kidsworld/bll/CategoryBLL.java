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
