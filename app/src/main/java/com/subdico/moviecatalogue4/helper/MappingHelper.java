package com.subdico.moviecatalogue4.helper;

import android.database.Cursor;

import com.subdico.moviecatalogue4.model.ListData;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.FAV_TYPE;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.FIRST_AIR_DATE;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.NAME;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.OVERVIEW;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.POSTER_PATH;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.VOTE_AVERAGE;

public class MappingHelper {
    public static ArrayList<ListData> mapCursorToArrayList(Cursor favCursor){
        ArrayList<ListData> listData = new ArrayList<>();

        while (favCursor.moveToNext()){
            int id = favCursor.getInt(favCursor.getColumnIndexOrThrow(_ID));
            String name  = favCursor.getString(favCursor.getColumnIndexOrThrow(NAME));
            String poster_path  = favCursor.getString(favCursor.getColumnIndexOrThrow(POSTER_PATH));
            String overview  = favCursor.getString(favCursor.getColumnIndexOrThrow(OVERVIEW));
            String first_air_date  = favCursor.getString(favCursor.getColumnIndexOrThrow(FIRST_AIR_DATE));
            String vote_average  = favCursor.getString(favCursor.getColumnIndexOrThrow(VOTE_AVERAGE));
            String type  = favCursor.getString(favCursor.getColumnIndexOrThrow(FAV_TYPE));
            listData.add(new ListData(id, name, poster_path, overview, first_air_date, vote_average, type));
        }
        return listData;
    }
}
