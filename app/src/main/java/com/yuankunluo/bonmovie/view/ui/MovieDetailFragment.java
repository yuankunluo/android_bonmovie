package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.viewmodel.MovieDetailViewModel;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-27.
 */

public class MovieDetailFragment extends LifecycleFragment {
    private MovieDetailViewModel mViewModel;
    private NetworkImageView mImageView;
    @Inject
    ImageLoader mImageLoader;



    final static String TAG = MovieDetailFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BonMovieApp.getAppComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        int movieIdExtra = intent.getIntExtra("movie_id", 0);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        BonMovieApp.getAppComponent().inject(mViewModel);
        mViewModel.init(movieIdExtra);
        mViewModel.getMovieDetail().observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(@Nullable MovieDetail movieDetail) {
                Log.d(TAG, "onActivityCreated: onChanged" );

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // initial view elements
        View root = inflater.inflate(R.layout.fragment_movie_detail, container,false);
        mImageView = root.findViewById(R.id.imv_movie_detail_header_poster);

        return root;
    }



}
