package com.lydia.dicoding.moviecatalogue4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL)",
            DatabaseContract.MovieColumns.TABLE_NAME,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.POSTER_PATH,
            DatabaseContract.MovieColumns.RELEASE_DATE,
            DatabaseContract.MovieColumns.POPULARITY
    );


    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL,",
            DatabaseContract.TvshowColumns.TABLE_NAME,
            DatabaseContract.TvshowColumns._ID,
            DatabaseContract.TvshowColumns.TITLE,
            DatabaseContract.TvshowColumns.OVERVIEW,
            DatabaseContract.TvshowColumns.POSTER_PATH
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MovieColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TvshowColumns.TABLE_NAME);
        onCreate(db);
    }




}