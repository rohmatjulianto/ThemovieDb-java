package com.subdico.moviecatalogue4.model;

import android.util.Log;
import com.subdico.moviecatalogue4.api.ApiClient;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ListData>> listDataItem = new MutableLiveData<>();
    public void getSearchList(final String data, String query){
        final ArrayList<ListData> listItem = new ArrayList<>();
        GetSearchEndpoint getSearchEndpoint = ApiClient.getClient().create(GetSearchEndpoint.class);
        Call<ResponseBody> call = getSearchEndpoint.getAdataSearch(data, ApiClient.API_KEY, "en-US", query);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject resJson = new JSONObject(result);
                    JSONArray list  = resJson.getJSONArray("results");
                    Log.d(TAG, "onResponse: " +list);

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movies = list.getJSONObject(i);
                        ListData listData = new ListData(movies);
                        listItem.add(listData);
                    }
                    listDataItem.postValue(listItem);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
    public void getDataList(final String data){
        final ArrayList<ListData> listItem = new ArrayList<>();
        GetDataEndpoint getDataEndpoint = ApiClient.getClient().create(GetDataEndpoint.class);
        Call<ResponseBody> call = getDataEndpoint.getAdata(data, ApiClient.API_KEY, "en-US");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                        String result = response.body().string();
                        JSONObject resJson = new JSONObject(result);
                        JSONArray list  = resJson.getJSONArray("results");
                        Log.d(TAG, "onResponse: " +list);

                        for (int i = 0; i < list.length(); i++){
                            JSONObject movies = list.getJSONObject(i);
                            ListData listData = new ListData(movies);
                            listItem.add(listData);
                        }
                        listDataItem.postValue(listItem);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<ListData>> getAllData(){ return listDataItem; }

    interface GetDataEndpoint{
        @GET("3/discover/{data}")
        Call<ResponseBody> getAdata(@Path("data") String data, @Query("api_key") String API_KEY, @Query("language") String language);
    }

    interface GetSearchEndpoint{
        @GET("3/search/{data}")
        Call<ResponseBody> getAdataSearch(@Path("data") String data, @Query("api_key") String API_KEY, @Query("language") String language,  @Query("query") String query);
    }
}
