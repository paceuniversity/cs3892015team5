package com.williamokano.apps.kidsworld.bll;

import android.content.Context;
import android.database.Cursor;

import com.williamokano.apps.kidsworld.DBHelper;
import com.williamokano.apps.kidsworld.models.Category;
import com.williamokano.apps.kidsworld.models.Image;
import com.williamokano.apps.kidsworld.models.Sound;
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

        String[] columns = {dbHelper.getId(), dbHelper.getImage(), dbHelper.getSound(), dbHelper.getObjDescription(),dbHelper.getCategory()};

        Cursor c = dbHelper.getMyDataBase().query(dbHelper.getTable(), columns, null, null, null, null, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            int idThing = c.getInt(c.getColumnIndexOrThrow(dbHelper.getId()));
            int idCategory = c.getInt(c.getColumnIndexOrThrow(dbHelper.getCategory()));
            Category category = new Category(idCategory, whichCategory(idCategory));

            int idImage = dbHelper.getContext().getResources().getIdentifier(c.getString(c.getColumnIndexOrThrow(dbHelper.getImage())),
                    "drawable", dbHelper.getContext().getPackageName());

            int idSound = dbHelper.getContext().getResources().getIdentifier(c.getString(c.getColumnIndexOrThrow(dbHelper.getSound())),
                    "raw", dbHelper.getContext().getPackageName());

            String description = c.getString(c.getColumnIndexOrThrow(dbHelper.getObjDescription()));

            Sound sound = new Sound(idSound);
            Image img = new Image(idImage, idSound, description,
                    whichCategory(c.getInt(c.getColumnIndexOrThrow(dbHelper.getCategory()))));


            things.add(new Thing(idThing, idCategory, category, img, sound, description ));

            c.moveToNext();
        }

        return things;
    }

    private String whichCategory(int id) {

        switch (id){
            case 1:
                return "animals";

            case 2:
                return "shapes";

            case 3:
                return "colors";

            default:
                return "";

        }


    }

    //@TODO: Implement this method. Retrieve from database
    public ArrayList<Thing> GetThings(DBHelper dbHelper, Category category) {

        ArrayList<Thing> things = GetThings(dbHelper);
        ArrayList<Thing> categorizedThings = new ArrayList<>();

        for(Thing i: things){

            if(i.getCategory().getName().equals(category.getName()))
                categorizedThings.add(i);
        }

        return categorizedThings;

    }
}
