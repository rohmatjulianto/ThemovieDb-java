package com.subdico.moviecatalogue4.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ListData implements Parcelable {
    private int id;
    private String name;
    private String poster_path;
    private String overview;
    private String first_air_date;
    private Double vote_average;
    private String type;




    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public ListData(JSONObject jsonObject) {

        try {
            String name;
            String release_date;
            String type;
            StringBuilder path = new StringBuilder("https://image.tmdb.org/t/p/w154/");

            if (jsonObject.isNull("name")){
                //movie
                type = "movie";
                name = jsonObject.getString("title");
                release_date = jsonObject.getString("release_date");
            }else{
                //series
                type = "series";
                name = jsonObject.getString("name");
                release_date = jsonObject.getString("first_air_date");

            }

            Double vote_average = jsonObject.getDouble ("vote_average");
            String poster_path = path + jsonObject.getString("poster_path");
            String overview = jsonObject.getString("overview");

            this.name =  name;
            this.first_air_date = release_date;
            this.vote_average = vote_average;
            this.poster_path = poster_path;
            this.overview = overview;
            this.type = type;

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.first_air_date);
        dest.writeValue(this.vote_average);
        dest.writeString(this.type);
    }

    public ListData() {
    }


    protected ListData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.first_air_date = in.readString();
        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Creator<ListData> CREATOR = new Creator<ListData>() {
        @Override
        public ListData createFromParcel(Parcel source) {
            return new ListData(source);
        }

        @Override
        public ListData[] newArray(int size) {
            return new ListData[size];
        }
    };
}
