package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.view.adapter.ViewPagerAdapter;

/**
 * Created by yuank on 2017-07-03.
 */

public class MoviePaggerFragment extends LifecycleFragment {

    private ViewPager mViewPagger;
    private ViewPagerAdapter mViewPaggerAdaper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies_pagger, container, false);
        mViewPagger = root.findViewById(R.id.viewpagger_movies_pagger);
        mViewPaggerAdaper = new ViewPagerAdapter(getFragmentManager(),getContext());
        mViewPagger.setAdapter(mViewPaggerAdaper);
        return root;
    }
}
