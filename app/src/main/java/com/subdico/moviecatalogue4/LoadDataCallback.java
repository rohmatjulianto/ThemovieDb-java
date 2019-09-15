package com.subdico.moviecatalogue4;

import com.subdico.moviecatalogue4.model.ListData;

import java.util.ArrayList;

public interface LoadDataCallback {
    void preExecute();
    void postExecute(ArrayList<ListData> listData);
}
