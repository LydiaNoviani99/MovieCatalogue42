package com.lydia.dicoding.moviecatalogue4.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.lydia.dicoding.moviecatalogue4.db.TvShowHelper;

import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.AUTHORITY;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.CONTENT_URI;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.TABLE_NAME;


public class TvShowProvider extends ContentProvider {

    private static final int TVSHOW = 1;
    private static final int TVSHOW_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, TVSHOW);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                TVSHOW_ID);
    }

    private TvShowHelper tvShowHelper;

    @Override
    public boolean onCreate() {
        tvShowHelper = new TvShowHelper(getContext());
        tvShowHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case TVSHOW:
                cursor = tvShowHelper.queryProvider();
                break;
            case TVSHOW_ID:
                cursor = tvShowHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case TVSHOW:
                added = tvShowHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case TVSHOW_ID:
                updated = tvShowHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case TVSHOW_ID:
                deleted = tvShowHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }
}
