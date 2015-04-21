package com.williamokano.apps.kidsworld;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by JsLucasSf on 4/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    static final String DB_NAME = "base.db";
    static final int DB_VERSION = 1;
    static final String _ID = "_id";
    static final String TABLE = "Objects";
    static final String IMAGE = "image";
    static final String OBJ_DESCRIPTION = "object_description";
    static final String SOUND = "sound";
    static final String CATEGORY = "category";

    Context context;

    public DBHelper(Context context){

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        SQLiteDatabase dbTest = null;

        try {

            dbTest = SQLiteDatabase.openDatabase(DB_NAME, null, SQLiteDatabase.OPEN_READONLY);

        }catch (Exception e){
        }

        if(dbTest == null){

            //These attributes will change over the next sprints, for instance, "category" will become a foreign key.
            String SQLCreate = String.format("CREATE TABLE %s (%s INT PRIMARY KEY, %s INT, %s TEXT, %s INT, %s TEXT);",
                    this.TABLE,
                    this._ID,
                    this.IMAGE,
                    this.OBJ_DESCRIPTION,
                    this.SOUND,
                    this.CATEGORY);

            db.execSQL(SQLCreate);

            initializeDefaultComponents(db);


        }else{
            db = dbTest;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //To be implemented in the next sprints
    }

    public void initializeDefaultComponents(SQLiteDatabase db){

        ContentValues values = new ContentValues();

        values.put(this.IMAGE, R.drawable.bird);
        values.put(this.OBJ_DESCRIPTION, "BIRD");
        values.put(this.SOUND, R.raw.bird);
        values.put(this.CATEGORY, "Animals");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.frog);
        values.put(this.OBJ_DESCRIPTION, "FROG");
        values.put(this.SOUND, R.raw.frog);
        values.put(this.CATEGORY, "Animals");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.horse);
        values.put(this.OBJ_DESCRIPTION, "HORSE");
        values.put(this.SOUND, R.raw.horse);
        values.put(this.CATEGORY, "Animals");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.rabbit);
        values.put(this.OBJ_DESCRIPTION, "rabbit");
        values.put(this.SOUND, R.raw.rabbit);
        values.put(this.CATEGORY, "Animals");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.turtle);
        values.put(this.OBJ_DESCRIPTION, "Turtle");
        values.put(this.SOUND, R.raw.rabbit);
        values.put(this.CATEGORY, "Animals");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.dog);
        values.put(this.OBJ_DESCRIPTION, "DOG");
        values.put(this.SOUND, R.raw.dog);
        values.put(this.CATEGORY, "Animals");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.cat);
        values.put(this.OBJ_DESCRIPTION, "CAT");
        values.put(this.SOUND, R.raw.cat);
        values.put(this.CATEGORY, "Animals");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();


        values.put(this.IMAGE, R.drawable.blue);
        values.put(this.OBJ_DESCRIPTION, "BLUE");
        values.put(this.SOUND, R.raw.blue);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.black);
        values.put(this.OBJ_DESCRIPTION, "BLACK");
        values.put(this.SOUND, R.raw.black);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.green);
        values.put(this.OBJ_DESCRIPTION, "GREEN");
        values.put(this.SOUND, R.raw.green);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.orange);
        values.put(this.OBJ_DESCRIPTION, "ORANGE");
        values.put(this.SOUND, R.raw.orange);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.pink);
        values.put(this.OBJ_DESCRIPTION, "PINK");
        values.put(this.SOUND, R.raw.pink);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.purple);
        values.put(this.OBJ_DESCRIPTION, "PURPLE");
        values.put(this.SOUND, R.raw.purple);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.red);
        values.put(this.OBJ_DESCRIPTION, "RED");
        values.put(this.SOUND, R.raw.red);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.white);
        values.put(this.OBJ_DESCRIPTION, "WHITE");
        values.put(this.SOUND, R.raw.white);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.yellow);
        values.put(this.OBJ_DESCRIPTION, "YELLOW");
        values.put(this.SOUND, R.raw.yellow);
        values.put(this.CATEGORY, "Colors");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.circle);
        values.put(this.OBJ_DESCRIPTION, "CIRCLE");
        values.put(this.SOUND, R.raw.circle);
        values.put(this.CATEGORY, "Shapes");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.rectangle);
        values.put(this.OBJ_DESCRIPTION, "RECTANGLE");
        values.put(this.SOUND, R.raw.rectangle);
        values.put(this.CATEGORY, "Shapes");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.square);
        values.put(this.OBJ_DESCRIPTION, "SQUARE");
        values.put(this.SOUND, R.raw.square);
        values.put(this.CATEGORY, "Shapes");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();

        values.put(this.IMAGE, R.drawable.triangle);
        values.put(this.OBJ_DESCRIPTION, "TRIANGLE");
        values.put(this.SOUND, R.raw.triangle);
        values.put(this.CATEGORY, "Shapes");

        db.insertOrThrow(this.TABLE, null, values);
        values.clear();
    }
}
