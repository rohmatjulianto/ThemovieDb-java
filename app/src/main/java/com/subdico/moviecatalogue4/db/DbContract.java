package com.subdico.moviecatalogue4.db;

import android.provider.BaseColumns;

class DbContract {
    static String TABLE_NAME = "favorites";

    static final class NoteColumns implements BaseColumns{
        static String NAME = "name";
        static String POSTER_PATH = "poster_path";
        static String OVERVIEW = "overview";
        static String FIRST_AIR_DATE = "first_air_date";
        static String VOTE_AVERAGE = "vote_average";
        static String FAV_TYPE = "fav_type";
    }

}
