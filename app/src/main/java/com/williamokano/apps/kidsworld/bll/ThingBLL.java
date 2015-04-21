package com.williamokano.apps.kidsworld.bll;

import android.content.Context;
import android.database.Cursor;

import com.williamokano.apps.kidsworld.DBHelper;
import com.williamokano.apps.kidsworld.models.Category;
import com.williamokano.apps.kidsworld.models.Image;
import com.williamokano.apps.kidsworld.models.Thing;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by William on 4/19/2015.
 */
public class ThingBLL {

    //@TODO: Implement this method. Retrieve from database
    public ArrayList<Thing> GetThings(DBHelper dbHelper) {

        ArrayList<Thing> things = new ArrayList<>();

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dbHelper.openDataBase();

        String[] columns = {dbHelper.getImage(), dbHelper.getSound(), dbHelper.getObjDescription(),dbHelper.getCategory()};

        Cursor c = dbHelper.getMyDataBase().query(dbHelper.getTable(), columns, null, null, null, null, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            things.add(new Thing());


        }
        return null;
    }

    //@TODO: Implement this method. Retrieve from database
    public ArrayList<Thing> GetThins(Category category) {
        return null;
    }
}
