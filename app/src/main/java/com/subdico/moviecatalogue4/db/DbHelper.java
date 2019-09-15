package com.subdico.moviecatalogue4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static String DB_NAME ="db_favorites";
    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_TABLE = String.format(
                    "CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s DOUBLE NOT NULL,"+
                    " %s TEXT NOT NULL)",
            DbContract.TABLE_NAME,
            DbContract.NoteColumns._ID,
            DbContract.NoteColumns.NAME,
            DbContract.NoteColumns.POSTER_PATH,
            DbContract.NoteColumns.OVERVIEW,
            DbContract.NoteColumns.FIRST_AIR_DATE,
            DbContract.NoteColumns.VOTE_AVERAGE,
            DbContract.NoteColumns.FAV_TYPE
    );

    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
