package com.subdico.moviecatalogue4.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String AUTHORITY ="com.subdico.moviecatalogue4";
    private static final String SCHEME = "content";

    public static String TABLE_NAME = "favorites";
    public static final class NoteColumns implements BaseColumns{
        public static String NAME = "name";
        public static String POSTER_PATH = "poster_path";
        public static String OVERVIEW = "overview";
        public static String FIRST_AIR_DATE = "first_air_date";
        public static String VOTE_AVERAGE = "vote_average";
        public static String FAV_TYPE = "fav_type";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static String getColumnsString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnsInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }


}
