package com.subdico.moviecatalogue4.model;

import android.graphics.Bitmap;

public class WidgetItems {

    private String name;
    private Bitmap imgPoster;

    public WidgetItems(String name, Bitmap imgPoster) {
        this.name = name;
        this.imgPoster = imgPoster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(Bitmap imgPoster) {
        this.imgPoster = imgPoster;
    }
}
