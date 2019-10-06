package com.subdico.moviecatalogue4.ui;


import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.subdico.moviecatalogue4.LoadDataCallback;
import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.ui.Favorites.SectionPagerFavorites;
import com.subdico.moviecatalogue4.ui.Favorites.pager_favorites.SeriesFavoritesFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    ViewPager  viewPager;
    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_layout, container, false);
        SectionPagerFavorites sectionPagerAdapter = new SectionPagerFavorites(getContext(), getChildFragmentManager());
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.getAdapter().notifyDataSetChanged();
    }
}
