package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.R;

/**
 * Created by yuank on 2017-06-27.
 */

public class MovieDetailFragment extends LifecycleFragment {


    final static String TAG = MovieDetailFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_detail, container,false);
        return root;
    }
}
