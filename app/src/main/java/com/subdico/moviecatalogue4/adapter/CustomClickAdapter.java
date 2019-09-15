package com.subdico.moviecatalogue4.adapter;

import android.view.View;

public class CustomClickAdapter implements View.OnClickListener {

    private int position;
    private onCallback onItemCallback;

    CustomClickAdapter(int position ,onCallback onItemCallback) {
        this.onItemCallback = onItemCallback;
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        onItemCallback.onItemClicked(view, position);
    }

    interface onCallback{
        void onItemClicked(View view, int position);
    }
}
