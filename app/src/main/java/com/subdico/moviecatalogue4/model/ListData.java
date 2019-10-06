package com.subdico.moviecatalogue4.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.subdico.moviecatalogue4.db.DbContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.subdico.moviecatalogue4.db.DbContract.getColumnsInt;
import static com.subdico.moviecatalogue4.db.DbContract.getColumnsString;

public class ListData implements Parcelable {


    private int id;
    private String name;
    private String poster_path;
    private String overview;
    private String first_air_date;
    private String vote_average;
    private String type;

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

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ListData(int id, String name, String poster_path, String overview, String first_air_date, String vote_average, String type) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.overview = overview;
        this.first_air_date = first_air_date;
        this.vote_average = vote_average;
        this.type = type;
    }
    public ListData(Cursor cursor){
        this.id = getColumnsInt(cursor, _ID);
        this.name = getColumnsString(cursor, DbContract.NoteColumns.NAME);
        this.poster_path = getColumnsString(cursor, DbContract.NoteColumns.POSTER_PATH);
        this.overview = getColumnsString(cursor, DbContract.NoteColumns.OVERVIEW);
        this.first_air_date = getColumnsString(cursor, DbContract.NoteColumns.FIRST_AIR_DATE);
        this.vote_average = getColumnsString(cursor, DbContract.NoteColumns.VOTE_AVERAGE);
        this.type = getColumnsString(cursor, DbContract.NoteColumns.FAV_TYPE);
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
            int id = jsonObject.getInt("id");
            double vote = jsonObject.getDouble("vote_average");
            String vote_average = String.valueOf(vote);

            String poster_path = path + jsonObject.getString("poster_path");
            String overview = jsonObject.getString("overview");

            this.id = id;
            this.name =  name;
            this.first_air_date = release_date;
            this.vote_average = vote_average;
            this.poster_path = poster_path;
            this.overview = overview;
            this.type = type;
            Log.d("VOTE", "ListData: "+getVote_average());

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public ListData() {
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
        dest.writeString(this.vote_average);
        dest.writeString(this.type);
    }

    protected ListData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.first_air_date = in.readString();
        this.vote_average = in.readString();
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
