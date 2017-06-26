package com.yuankunluo.bonmovie.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.view.ui.PopularMovieFragment;
import com.yuankunluo.bonmovie.view.ui.TopRatedMovieFragment;

/**
 * Created by yuank on 2017-06-22.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 2;
    private Context mContext;

    public PagerAdapter(FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PopularMovieFragment();
            case 1:
                return new TopRatedMovieFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.tab_title_popular_movie);
            case 1:
                return mContext.getString(R.string.tab_title_top_rated_movie);
            case 2:
                return mContext.getString(R.string.tab_title_my_favorite);
            default:
                return "UNKNOWN";
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
