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
import com.yuankunluo.bonmovie.data.model.TopRatedMovie;
import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.receiver.DBBRefreshBroadcastReceiver;
import com.yuankunluo.bonmovie.view.adapter.MoviesRecyclerViewAdapter;
import com.yuankunluo.bonmovie.services.listener.OnSwipeRefreshListener;
import com.yuankunluo.bonmovie.view.listener.EndlessRecyclerViewScrollListener;
import com.yuankunluo.bonmovie.viewmodel.TopRatedMovieViewModel;

/**
 * Created by yuank on 2017-06-22.
 */

public class TopRatedMovieFragment extends LifecycleFragment implements OnSwipeRefreshListener {
    final String TAG = TopRatedMovieFragment.class.getSimpleName();
    private TopRatedMovieViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter<TopRatedMovie> movieRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipRefreshContainer;
    private BroadcastReceiver mBroadcastReceiver;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        mViewModel = ViewModelProviders.of(this).get(TopRatedMovieViewModel.class);
        BonMovieApp.getAppComponent().inject(mViewModel);
        mViewModel.init();
        mViewModel.getMovies().observe(this, movieRecyclerViewAdapter);
        mViewModel.loadMoviesAtPage(1);

    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View root =  inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview_movies);
        // set layout manager
        int columnNumber = getResources().getInteger(R.integer.grid_column);
        mLayoutManager = new GridLayoutManager(getContext(),columnNumber, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        movieRecyclerViewAdapter = new MoviesRecyclerViewAdapter<>(getContext());
        mRecyclerView.setAdapter(movieRecyclerViewAdapter);
        mScrollListener = new EndlessRecyclerViewScrollListener((GridLayoutManager)mLayoutManager,1) {
            @Override
            public void onLoadMore(int page) {
                mViewModel.loadMoviesAtPage(page);
                Log.d(TAG, "onLoadMore " + page);
            }
        };
        mScrollListener.resetState();
        mRecyclerView.addOnScrollListener(mScrollListener);

        // forceRefresh container
        mSwipRefreshContainer = root.findViewById(R.id.swipeContainer);
        mSwipRefreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollListener.resetState();
                mViewModel.forceRefresh();
            }

        });
        mSwipRefreshContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // register broadcast receiver
        IntentFilter filter = new IntentFilter(BonMovieAction.ACTION_DB_INSERTED_MOVIES);
        mBroadcastReceiver = new DBBRefreshBroadcastReceiver(this);
        getActivity().registerReceiver(mBroadcastReceiver,filter);
        return root;
    }


    @Override
    public void onRefreshOver() {
        if(mSwipRefreshContainer != null){
            mSwipRefreshContainer.setRefreshing(false);
            Log.d(TAG, "onRefreshOver");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }
}
