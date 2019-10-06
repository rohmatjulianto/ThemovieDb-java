package com.subdico.moviecatalogue4.ui.ListItem.PagerListItem;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.adapter.MainAdapter;
import com.subdico.moviecatalogue4.model.ListData;
import com.subdico.moviecatalogue4.model.MainViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment{

    private static final String PAGE = "tv";
    private MainAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModel model;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_layout_recyclerview, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        RecyclerView rvShow = view.findViewById(R.id.rv_show);
        rvShow.setHasFixedSize(true);
        rvShow.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MainAdapter(getContext());
        adapter.notifyDataSetChanged();
        rvShow.setAdapter(adapter);

        showLoading(true);
        model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        model.getDataList(PAGE);
        model.getAllData().observe(this, new Observer<ArrayList<ListData>>() {
            @Override
            public void onChanged(ArrayList<ListData> listData) {
                if (listData != null){
                    adapter.setmData(listData);
                    showLoading(false);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    model.getDataList(PAGE);
                }else{
                    model.getSearchList(PAGE, newText);
                }
                showLoading(true);
                return true;
            }});
    }

    private void showLoading(boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        } else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
