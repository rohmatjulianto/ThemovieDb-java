package com.subdico.moviecatalogue4.ui.Favorites.pager_favorites;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.os.Handler;
import android.os.HandlerThread;
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
import com.subdico.moviecatalogue4.ui.Favorites.SectionPagerFavorites;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.CONTENT_URI;
import static com.subdico.moviecatalogue4.helper.MappingHelper.mapCursorToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoritesFragment extends Fragment implements LoadDataCallback {

   private FavAdapter favAdapter;
//   private FavHelper favHelper;
   private LinearLayout noItem;
   private ProgressBar progressBar;
   private SectionPagerFavorites viewPagerFav;

   private static final String EXRTRA_STATE = "extra_state";

    public MovieFavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.favorites_recyclerview, container, false);

//        favHelper = FavHelper.getInstance(getContext());
//        favHelper.open();

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
            new LoadDataAsync(getContext(), this).execute();
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

    private static class LoadDataAsync extends AsyncTask<Void, Void, Cursor>{
        private final WeakReference<Context> WeakContext;
        private final WeakReference<LoadDataCallback> WeakCallback;

        LoadDataAsync(Context context, LoadDataCallback callback) {
            WeakContext = new WeakReference<>(context);
            WeakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            WeakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = WeakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, "movie", null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            WeakCallback.get().postExecute(cursor);
        }
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(Cursor cursor) {
        progressBar.setVisibility(View.GONE);

        ArrayList<ListData> listData = mapCursorToArrayList(cursor);
        if (listData.size() > 0){
            noItem.setVisibility(View.GONE);
            favAdapter.setmDataFav(listData);
            Log.d("onn", "postExecute: invisible ");
        }else{
            favAdapter.setmDataFav(listData);
            noItem.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadDataAsync(getContext(),this).execute();
    }

}
