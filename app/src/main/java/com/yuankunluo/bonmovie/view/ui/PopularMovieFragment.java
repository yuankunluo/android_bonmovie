package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.R;

/**
 * Created by yuank on 2017-06-22.
 */

public class PopularMovieFragment extends LifecycleFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.popular_movie_fragment, container, false);
    }
}
