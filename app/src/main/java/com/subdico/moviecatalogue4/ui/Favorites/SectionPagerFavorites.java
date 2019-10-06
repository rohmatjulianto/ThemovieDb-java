package com.subdico.moviecatalogue4.ui.Favorites;

import android.content.Context;

import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.ui.Favorites.pager_favorites.MovieFavoritesFragment;
import com.subdico.moviecatalogue4.ui.Favorites.pager_favorites.SeriesFavoritesFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SectionPagerFavorites extends FragmentStatePagerAdapter {

    private static final int[] TAB_TITLE = new int[]{R.string.movie, R.string.series};
    private final Context mContext;

    public SectionPagerFavorites(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MovieFavoritesFragment();
                break;
            case 1:
                fragment = new SeriesFavoritesFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_TITLE.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLE[position]);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
