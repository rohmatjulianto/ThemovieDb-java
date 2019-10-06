package com.subdico.moviecatalogue4.alarm;

import android.content.Context;
import android.util.Log;

import com.subdico.moviecatalogue4.api.ApiClient;
import com.subdico.moviecatalogue4.model.ListData;
import com.subdico.moviecatalogue4.model.MainViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ReleaseApi {

    public ReleaseApi(final Context context) {
        final ArrayList<ListData> todayItem = new ArrayList<>();
        GetReleaseMovie getReleaseMovie = ApiClient.getClient().create(GetReleaseMovie.class);
        Call<ResponseBody> call = getReleaseMovie.getMovie(ApiClient.API_KEY, getCurrentDate(), getCurrentDate());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject resJson = new JSONObject(result);
                    JSONArray list  = resJson.getJSONArray("results");
                    Log.d("XXX", "onResponse: "+ result);
                    for (int i = 0; i < list.length(); i++){
                        JSONObject movies = list.getJSONObject(i);
                        ListData listData = new ListData(movies);
                        todayItem.add(listData);
                    }
                    AlarmReceiver.onReceiveReleaseToday(context, todayItem);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("XXX", "onFailure: " + t.getMessage());
            }
        });
    }
    interface GetReleaseMovie{
        @GET("3/discover/movie")
        Call<ResponseBody> getMovie(@Query("api_key") String API_KEY, @Query("primary_release_date.gte") String release_date_gte, @Query("primary_release_date.lte") String release_date_lte);
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
