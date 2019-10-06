package com.subdico.moviecatalogue4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.FAV_TYPE;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.NAME;
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

    public Cursor checkFav(String name){
        Cursor cursor =  database.query(DB_TABLE,null,
                NAME + "=?",new String[]{name}, null, null,null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    //provider
    public Cursor getFavProvider(String type){
        return database.query(DB_TABLE,
                null,FAV_TYPE + "=?",new String[]{type}, null, null, _ID + " DESC");
    }
    public Cursor getFavProviderAll(){
        return database.query(DB_TABLE,
                null,null,null, null, null, _ID + " DESC");
    }

    public long insertFavProvider(ContentValues values){
        return database.insert(DB_TABLE, null, values);
    }

    public int deleteFavProvider(String name){
        return database.delete(DB_TABLE, NAME + "=?", new String[]{name});
    }
}
