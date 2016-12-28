package com.example.eightleaves.comedybox.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.eightleaves.comedybox.data.CBContract.ComedyEntry;

import static com.example.eightleaves.comedybox.data.CBContract.*;

class CBDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=3;
    static final String DATABASE_NAME = "comedy.db";

    public CBDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_SORT_TABLE = "CREATE TABLE " + SortEntry.TABLE_NAME + "(" +
                SortEntry._ID +" INTEGER PRIMARY KEY,"+
                SortEntry.COLUMN_SORT_SETTING + " TEXT NOT NULL" + ")";

        final String SQL_CREATE_COMEDY_TABLE = "CREATE TABLE " + ComedyEntry.TABLE_NAME + "(" +
                ComedyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ComedyEntry.COLUMN_SORT_KEY + " INTEGER NOT NULL, "+
                ComedyEntry.COLUMN_COMEDY_ID + " INTEGER NOT NULL, "+
                ComedyEntry.COLUMN_TITLE +" TEXT NOT NULL, "+
                ComedyEntry.COLUMN_OVERVIEW +" TEXT NOT NULL, "+
                ComedyEntry.COLUMN_POSTER_PATH +" TEXT NOT NULL, "+
                ComedyEntry.COLUMN_RELEASE_DATE +" TEXT NOT NULL, "+

                " FOREIGN KEY (" + ComedyEntry.COLUMN_SORT_KEY + ") REFERENCES " +
                SortEntry.TABLE_NAME + " (" + SortEntry._ID + "), " +

                // To assure the application have just one movie entry per sort key
                //it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + ComedyEntry.COLUMN_COMEDY_ID + ","
                + ComedyEntry.COLUMN_SORT_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_COMEDY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SORT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SortEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ComedyEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
