package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.viewmodel.PopularMovieViewModel;

import java.util.List;

/**
 * Created by yuank on 2017-06-22.
 */

public class PopularMovieFragment extends LifecycleFragment {
    final String TAG = PopularMovieFragment.class.getSimpleName();
    PopularMovieViewModel mViewModel;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PopularMovieViewModel.class);
        BonMovieApp.getAppComponent().inject(mViewModel);
        mViewModel.init();
        mViewModel.getPopularMovies().observe(this, new Observer<List<PopularMovie>>() {
            @Override
            public void onChanged(@Nullable List<PopularMovie> popularMovies) {
                Log.i(TAG, "onActivityCreated: data changed");
                Log.i(TAG, "movies: " + popularMovies.size());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.popular_movie_fragment, container, false);
        Button button = (Button) root.findViewById(R.id.next_page);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.loadNextPage();
            }
        });
        return root;
    }


}
