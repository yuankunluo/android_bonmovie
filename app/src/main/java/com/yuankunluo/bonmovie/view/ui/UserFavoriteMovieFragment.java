package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.data.model.UserFavoriteMovie;
import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.listener.OnSwipeRefreshListener;
import com.yuankunluo.bonmovie.services.receiver.DBBRefreshBroadcastReceiver;
import com.yuankunluo.bonmovie.view.adapter.MoviesRecyclerViewAdapter;
import com.yuankunluo.bonmovie.view.listener.EndlessRecyclerViewScrollListener;
import com.yuankunluo.bonmovie.viewmodel.PopularMovieViewModel;
import com.yuankunluo.bonmovie.viewmodel.UserFavoriteMovieViewModel;

/**
 * Created by yuank on 2017-06-22.
 */

public class UserFavoriteMovieFragment extends LifecycleFragment  {
    final String TAG = UserFavoriteMovieFragment.class.getSimpleName();
    private UserFavoriteMovieViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter<UserFavoriteMovie> movieRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        mViewModel = ViewModelProviders.of(this).get(UserFavoriteMovieViewModel.class);
        BonMovieApp.getAppComponent().inject(mViewModel);
        mViewModel.init();
        movieRecyclerViewAdapter = new MoviesRecyclerViewAdapter<>(getContext());
        mViewModel.getMovies().observe(this, movieRecyclerViewAdapter);
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View root =  inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview_movies);
        int columnNumber = getResources().getInteger(R.integer.grid_column);
        mLayoutManager = new GridLayoutManager(getContext(),columnNumber, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(movieRecyclerViewAdapter);
        return root;
    }


}
