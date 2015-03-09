package com.example.android.fragments.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by cgcalatrava on 06/03/2015.
 */
public class MuseumDbHelper extends SQLiteOpenHelper {

    private ContentResolver myCR;
    // On a database schema change, database version must be incremented.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "MuseumInfo.db";
    public static final String COL_URL = "url";

    private static final String TEXT_TYPE_NOT_NULL = " TEXT NOT NULL, ";
    private static final String TEXT_TYPE = " TEXT "; // WITHOUT "," as this is the last item
    private static final String TEXT_TYPE_UNIQUE_NOT_NULL = " TEXT UNIQUE NOT NULL, ";
    private static final String REAL_TYPE_NOT_NULL = " REAL NOT NULL, ";
    private static final String INTEGER_TYPE_NOT_NULL = " INTEGER NOT NULL, ";
    private static final String INTEGER_TYPE_PRIMARY_KEY = " INTEGER PRIMARY KEY, ";
    // Obviously, since it is a PK, it is both unique and not null

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MuseumContract.MuseumInfo.TABLE_NAME + " (" +
                    MuseumContract.MuseumInfo.COLUMN_NUMBER_ART + INTEGER_TYPE_PRIMARY_KEY +
                    MuseumContract.MuseumInfo.COLUMN_NAME_AUTHOR + TEXT_TYPE_NOT_NULL +
                    MuseumContract.MuseumInfo.COLUMN_NAME_ARTWORK + TEXT_TYPE_NOT_NULL +
                    MuseumContract.MuseumInfo.COLUMN_DATE + TEXT_TYPE_NOT_NULL +
                    MuseumContract.MuseumInfo.COLUMN_DESCRIPTION + TEXT_TYPE +
                    " );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MuseumContract.MuseumInfo.TABLE_NAME;

    public MuseumDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //DONE
    public Cursor findArtJob(int art_num) {
        //String[] projection = {MuseumContract.MuseumInfo.COLUMN_NUMBER_ART,
          //      MuseumContract.MuseumInfo.COLUMN_NAME_AUTHOR};

        String selection = String.valueOf(MuseumContract.MuseumInfo.COLUMN_NUMBER_ART) + " = \"" + art_num + "\"";

        Cursor cursor = myCR.query(MuseumProvider.CONTENT_URI,
                /*projection*/ null, selection, null,
                null);
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            Log.i("MUSEM DB", "ART JOB " + art_num + " FOUND");

            return cursor;

        } else {

            Log.i("MUSEUM DB", "TRIED TO FIND " + art_num + ", BUT IT DID NOT EXISTS");
            return null;

        }

    }

    //DONE
    public boolean addArtJob(int art_num, String aut_name, String art_name, String date, String desc) {

        if (findArtJob(art_num) == null) {

            ContentValues values = new ContentValues();
            values.put(MuseumContract.MuseumInfo.COLUMN_NUMBER_ART, art_num);
            values.put(MuseumContract.MuseumInfo.COLUMN_NAME_AUTHOR, aut_name);
            values.put(MuseumContract.MuseumInfo.COLUMN_NAME_ARTWORK, art_name);
            values.put(MuseumContract.MuseumInfo.COLUMN_DATE, date);
            values.put(MuseumContract.MuseumInfo.COLUMN_DESCRIPTION, desc);

            myCR.insert(MuseumProvider.CONTENT_URI, values);

            Log.i("MUSEM DB", "INSERTED ART JOB " + art_num + ": " + art_name);
            return true;
        }

        Log.i("MUSEM DB", "TRIED TO INSERT " + art_num + ": " + art_name + ", BUT IT ALREADY EXISTED");
        return false; //ya existia

    }

    //DONE
    public boolean deleteArtJob(int art_num) {

        String selection = MuseumContract.MuseumInfo.COLUMN_NUMBER_ART +" = \"" + art_num + "\"";

        int rowsDeleted = myCR.delete(MuseumProvider.CONTENT_URI,
                selection, null);

        if (rowsDeleted > 0) {
            Log.i("MUSEUM DB", "DELETED ART JOB " + art_num);
            return true;
        }
        else {
            Log.i("MUSEUM DB", "TRIED TO DELETE " + art_num + ", BUT IT DID NOT EXISTS");
            return false;
        }

    }

    //DONE
    public boolean clear() {

        //directly by DB, not by content provider
        getReadableDatabase().execSQL(SQL_DELETE_ENTRIES);
        return true;

    }

    //DONE
    public String insertFromQR(String text) {

        int iniPos;
        int lastPost;

        //Get ArtJobNumber
        iniPos = text.indexOf(";00; ");
        lastPost = text.indexOf(" ;0;");
        int art_num = Integer.valueOf(text.substring(iniPos + 5, lastPost));


        //Get Author Name

        iniPos = text.indexOf(";01; ");
        lastPost = text.indexOf(" ;1;");
        String aut_name = text.substring(iniPos + 5, lastPost);


        //Get Art Job Name
        iniPos = text.indexOf(";02; ");
        lastPost = text.indexOf(" ;2;");
        String art_name = text.substring(iniPos + 5, lastPost);

        //Get Art Job Date
        iniPos = text.indexOf(";03; ");
        lastPost = text.indexOf(" ;3;");
        String date = text.substring(iniPos + 5, lastPost);

        //Get Art Job Description
        iniPos = text.indexOf(";04; ");
        lastPost = text.indexOf(" ;4;");
        String desc = text.substring(iniPos + 5, lastPost);

        //Check if it already exists

        boolean added = addArtJob(art_num, aut_name, art_name, date, desc);

        if (added) return art_name;
        else return null;

    }

    //DONE
    public Cursor getArtJobs() {
        //String[] projection = {MuseumContract.MuseumInfo.COLUMN_NUMBER_ART,
                //MuseumContract.MuseumInfo.COLUMN_NAME_AUTHOR};

        Cursor cursor = myCR.query(MuseumProvider.CONTENT_URI,
                null, null, null,
                null);
            if (cursor.getCount() > 0) {

                Log.i("MUSEM DB", "ART JOBS FOUND, RETREIVING ALL");

            }
            else{

                Log.i("MUSEUM DB", "TRIED TO FIND ARTJOBS, BUT NO ONE FOUND");
            }

        return cursor;

        }

}

 //INTERESTING BY UNNECESARY STUFF

    /*

    //NOT NECESARY
    public boolean insert(String art_num, String aut_name, String art_name, String date, String desc) {

        /*
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase(); //As we are in the helper, just call the function

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MuseumContract.MuseumInfo._ID, 1);
        values.put(MuseumContract.MuseumInfo.COLUMN_NUMBER_ART, art_num);
        values.put(MuseumContract.MuseumInfo.COLUMN_NAME_AUTHOR, aut_name);
        values.put(MuseumContract.MuseumInfo.COLUMN_NAME_ARTWORK, art_name);
        values.put(MuseumContract.MuseumInfo.COLUMN_DATE, date);
        values.put(MuseumContract.MuseumInfo.COLUMN_DESCRIPTION, desc);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MuseumContract.MuseumInfo.TABLE_NAME,
                null,
                values);

        return newRowId;
        */

       /* Cursor cursor =
                getWritableDatabase().
                        rawQuery("Insert INTO " +
                                        MuseumContract.MuseumInfo.TABLE_NAME +
                                        " VALUES (?, ?, ?, ?, ?);", new String[] {
                                String.valueOf(art_num) , aut_name, art_name, date, desc});*/
/*
        SQLiteDatabase db = getWritableDatabase();

                db.
                        execSQL("Insert INTO " +
                                MuseumContract.MuseumInfo.TABLE_NAME +
                                " VALUES (?, ?, ?, ?, ?);", new String[]{art_num, aut_name, art_name, date, desc});

        return true;*/
    //Our app is not going to the following functions

    /*
    public boolean delete(int art_num) {

        // Define 'where' part of query.
        String selection = MuseumContract.MuseumInfo.COLUMN_NUMBER_ART + " LIKE ? ";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(art_num) };
        // Issue SQL statement.
        getWritableDatabase().delete(MuseumContract.MuseumInfo.TABLE_NAME, selection, selectionArgs);

        return true;

    }

    public boolean update(int art_num, String new_desc) {

        //Update operation is not expected in this app, even though, it's implemented
        // (just for practising).
        //The only update is, logically, the description, as the author, name or ID of art
        //is never changed.

        ContentValues values = new ContentValues();
        values.put(MuseumContract.MuseumInfo.COLUMN_DESCRIPTION, new_desc);

        String selection = MuseumContract.MuseumInfo.COLUMN_NUMBER_ART + " LIKE ?";
        String[] selectionArgs = { String.valueOf(art_num) };

        int count = getReadableDatabase().update(
                MuseumContract.MuseumInfo.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return (count == 1);
        //Since COLUMN_NUMBER_ART is unique, there will be 0/1 element.

    }*/
/*
}


/*
    }

    //NOT NECESARY
    public Cursor read(String art_num) {

        /*String[] projection = {
                MuseumContract.MuseumInfo.COLUMN_NUMBER_ART,
                MuseumContract.MuseumInfo.COLUMN_NAME_AUTHOR,
                MuseumContract.MuseumInfo.COLUMN_NAME_ARTWORK,
                MuseumContract.MuseumInfo.COLUMN_DATE,
                MuseumContract.MuseumInfo.COLUMN_DESCRIPTION
        };

        String[] where = new String[1];
        where[0] = art_num;

        Cursor c = getReadableDatabase().query(
                MuseumContract.MuseumInfo.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                MuseumContract.MuseumInfo.COLUMN_NUMBER_ART,// The columns for the WHERE clause
                where,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        return c*/
/*
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.
                rawQuery("Select * FROM " +
                        MuseumContract.MuseumInfo.TABLE_NAME +
                        " WHERE number = ?;", new String[] {art_num});
        return c;

        /*
        //Nos aseguramos de que existe al menos un registro
if (c.moveToFirst()) {
     //Recorremos el cursor hasta que no haya más registros
     do {
          String codigo= c.getString(0);
          String nombre = c.getString(1);
     } while(c.moveToNext());
         */
/*
    }*/