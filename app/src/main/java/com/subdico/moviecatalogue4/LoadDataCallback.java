package com.subdico.moviecatalogue4;

import android.database.Cursor;

import com.subdico.moviecatalogue4.model.ListData;

import java.util.ArrayList;

public interface LoadDataCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
