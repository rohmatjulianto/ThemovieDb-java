package com.subdico.moviecatalogue4.ui;



import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private MainAdapter adapter;
    private ProgressBar progressBar;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_layout_recyclerview, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        MainViewModel model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.getAllData().observe(this, getDataObserve);

        showLoading(true);
        model.getDataList("tv");

        adapter = new MainAdapter(getActivity());
        adapter.notifyDataSetChanged();

        RecyclerView rvShow = view.findViewById(R.id.rv_show);
        rvShow.setHasFixedSize(true);
        rvShow.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvShow.setAdapter(adapter);

        return view;
    }

    private Observer<ArrayList<ListData>> getDataObserve = new Observer<ArrayList<ListData>>() {
        @Override
        public void onChanged(ArrayList<ListData> listData) {
            if (listData != null){
                adapter.setmData(listData);
                showLoading(false);
            }
        }
    };

    private void showLoading(boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        } else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
