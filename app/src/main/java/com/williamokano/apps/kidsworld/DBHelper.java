package com.williamokano.apps.kidsworld;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.database.SQLException;
import android.util.Log;

/**
 * Created by JsLucasSf on 4/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "KidsWorldDB";
    static final int DB_VERSION = 1;
    static final String _ID = "_id";
    static final String TABLE = "thing";
    static final String IMAGE = "image";
    static final String OBJ_DESCRIPTION = "description";
    static final String SOUND = "sound";
    static final String CATEGORY = "category_id";

    private Context context;

    private static String DB_PATH = "/data/data/com.williamokano.apps.kidsworld/databases/";
    private SQLiteDatabase myDataBase;

    public static String getDB_PATH() {
        return DB_PATH;
    }

    public static void setDB_PATH(String DB_PATH) {
        DBHelper.DB_PATH = DB_PATH;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getId() {
        return _ID;
    }

    public static String getImage() {
        return IMAGE;
    }

    public static String getTable() {
        return TABLE;
    }

    public static String getObjDescription() {
        return OBJ_DESCRIPTION;
    }

    public static String getSound() {
        return SOUND;
    }

    public static String getCategory() {
        return CATEGORY;
    }

    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    public SQLiteDatabase getMyDataBase() {

        return myDataBase;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*SQLiteDatabase dbTest = null;

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
*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To be implemented in the next sprints
    }

    public void createDataBase() throws IOException {

        boolean dbExist = false; //checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLException e) {
            //database does't exist yet.
        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

/*    public void initializeDefaultComponents(SQLiteDatabase db){

        ContentValues values = new ContentValues();

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
    }*/
}
