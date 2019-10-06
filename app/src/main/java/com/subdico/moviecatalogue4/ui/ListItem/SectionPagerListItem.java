package com.subdico.moviecatalogue4.ui.ListItem;

import android.content.Context;

import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.ui.ListItem.PagerListItem.MovieFragment;
import com.subdico.moviecatalogue4.ui.ListItem.PagerListItem.SeriesFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SectionPagerListItem extends FragmentStatePagerAdapter {

    private static final int[] TAB_TITLE = new int[]{R.string.movie, R.string.series};
    private final Context mContext;

    public SectionPagerListItem(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new SeriesFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_TITLE.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLE[position]);
    }

}
