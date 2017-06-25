package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.view.adapter.MovieRecyclerViewAdapter;
import com.yuankunluo.bonmovie.viewmodel.PopularMovieViewModel;

/**
 * Created by yuank on 2017-06-22.
 */

public class PopularMovieFragment extends LifecycleFragment {
    final String TAG = PopularMovieFragment.class.getSimpleName();
    private PopularMovieViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter<PopularMovie> movieRecyclerViewAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        mViewModel = ViewModelProviders.of(this).get(PopularMovieViewModel.class);
        BonMovieApp.getAppComponent().inject(mViewModel);
        mViewModel.init();
        mViewModel.getPopularMovies().observe(this, movieRecyclerViewAdapter);
        mViewModel.loadMoviesAtPage(1);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View root =  inflater.inflate(R.layout.movie_fragment, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview_movies);
        int columnNumber = getResources().getInteger(R.integer.grid_column);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),columnNumber, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter<>();
        mRecyclerView.setAdapter(movieRecyclerViewAdapter);
        return root;
    }


}
