package com.subdico.moviecatalogue4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.subdico.moviecatalogue4.model.ListData;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.FAV_TYPE;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.FIRST_AIR_DATE;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.NAME;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.OVERVIEW;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.POSTER_PATH;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.VOTE_AVERAGE;
import static com.subdico.moviecatalogue4.db.DbContract.TABLE_NAME;

public class FavHelper {

    private static final String DB_TABLE = TABLE_NAME;
    private static DbHelper dbHelper;
    private static FavHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavHelper(Context context) {
        dbHelper = new DbHelper(context);
    }

    public static FavHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public ArrayList<ListData> getAllFav(String type){
        ArrayList<ListData> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DB_TABLE,
                null,FAV_TYPE + "=?",new String[]{type}, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ListData listData;

        if (cursor.getCount() > 0){
            do {
                listData = new ListData();
                listData.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                listData.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                listData.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                listData.setFirst_air_date(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE)));
                listData.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                listData.setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                arrayList.add(listData);
                cursor.moveToNext();

            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFav(ListData listData){
        ContentValues args = new ContentValues();
        args.put(NAME, listData.getName());
        args.put(POSTER_PATH, listData.getPoster_path());
        args.put(FIRST_AIR_DATE, listData.getFirst_air_date());
        args.put(OVERVIEW, listData.getOverview());
        args.put(VOTE_AVERAGE, listData.getVote_average());
        args.put(FAV_TYPE, listData.getType());

        Log.w("Inserting", "insertFav: "+args);
        return database.insert(DB_TABLE, null, args);

    }

    public Cursor checkFav(String name){
        Cursor cursor =  database.query(DB_TABLE,null,
                NAME + "=?",new String[]{name}, null, null,null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int deleteFav(String name){
        return database.delete(DB_TABLE, NAME + "=?", new String[]{name});
    }
}
