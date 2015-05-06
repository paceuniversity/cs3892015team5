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

    public Context getContext() {
        return context;
    }

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
        try {
            this.context = context;
            this.createDataBase();
            myDataBase = this.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB", e.getMessage());
        }

    }

    public SQLiteDatabase getMyDataBase() {

        return myDataBase;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To be implemented in the next sprints
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        SQLiteDatabase db_Read = null;

        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            db_Read = this.getReadableDatabase();
            db_Read.close();
            copyDataBase();
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

    private void copyDataBase() {

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }
}
