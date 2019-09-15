package com.subdico.moviecatalogue4.ui.Favorites.pager;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.subdico.moviecatalogue4.LoadDataCallback;
import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.adapter.FavAdapter;
import com.subdico.moviecatalogue4.db.FavHelper;
import com.subdico.moviecatalogue4.model.ListData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoritesFragment extends Fragment implements LoadDataCallback {

   private FavAdapter favAdapter;
   private FavHelper favHelper;
   private LinearLayout noItem;
   private ProgressBar progressBar;

   private static final String EXRTRA_STATE = "extra_state";

    public MovieFavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.favorites_recyclerview, container, false);

        favHelper = FavHelper.getInstance(getContext());
        favHelper.open();

        favAdapter = new FavAdapter(getActivity());
        favAdapter.notifyDataSetChanged();

        noItem = rootView.findViewById(R.id.no_item_layout);
        progressBar = rootView.findViewById(R.id.progress_bar);

        RecyclerView rvFav = rootView.findViewById(R.id.rv_show_fav);
        rvFav.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFav.setHasFixedSize(true);
        rvFav.setAdapter(favAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null){
            new LoadDataAsync(favHelper, this).execute();
        }else{
            ArrayList<ListData> list = savedInstanceState.getParcelableArrayList(EXRTRA_STATE);
            if (list != null){
                favAdapter.setmDataFav(list);
            }
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXRTRA_STATE, favAdapter.getmDataFav());
    }

    private static class LoadDataAsync extends AsyncTask<Void, Void, ArrayList<ListData>>{
        private final WeakReference<FavHelper> WeakHelper;
        private final WeakReference<LoadDataCallback> WeakCallback;

        LoadDataAsync(FavHelper favHelper, LoadDataCallback callback) {
            WeakHelper = new WeakReference<>(favHelper);
            WeakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            WeakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<ListData> doInBackground(Void... voids) {
            return WeakHelper.get().getAllFav("movie");
        }

        @Override
        protected void onPostExecute(ArrayList<ListData> listData) {
            super.onPostExecute(listData);
            WeakCallback.get().postExecute(listData);
        }
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<ListData> listData) {
        progressBar.setVisibility(View.GONE);
        favAdapter.setmDataFav(listData);

        if (listData.size() > 0){
            noItem.setVisibility(View.GONE);
            Log.d("onn", "postExecute: invisible ");
        }else{
            noItem.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        new LoadDataAsync(favHelper, this).execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favHelper.close();
    }

}
