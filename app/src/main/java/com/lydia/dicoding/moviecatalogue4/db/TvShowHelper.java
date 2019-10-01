package com.lydia.dicoding.moviecatalogue4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lydia.dicoding.moviecatalogue4.entity.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.OVERVIEW;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.POSTER_PATH;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.TABLE_NAME;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.TITLE;

public class TvShowHelper {

    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public TvShowHelper(Context context) {
        this.context = context;
    }

    public TvShowHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<TvShow> query() {
        ArrayList<TvShow> arrayList = new ArrayList();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                tvShow.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(TvShow tvShow) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(_ID, tvShow.getId());
        initialValues.put(TITLE, tvShow.getTitle());
        initialValues.put(OVERVIEW, tvShow.getOverview());
        initialValues.put(POSTER_PATH, tvShow.getPosterPath());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(TITLE, tvShow.getTitle());
        args.put(OVERVIEW, tvShow.getOverview());
        args.put(POSTER_PATH, tvShow.getPosterPath());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + tvShow.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
