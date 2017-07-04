package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.databinding.FragmentMovieDetailBinding;
import com.yuankunluo.bonmovie.viewmodel.MovieDetailViewModel;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-27.
 */

public class MovieDetailFragment extends LifecycleFragment{
    private MovieDetailViewModel mViewModel;
    private NetworkImageView mImageView;
    private FragmentMovieDetailBinding mBinding;
    private ProgressBar mProgressBar;


    private int mMovieId;
    @Inject
    ImageLoader mImageLoader;



    final static String TAG = MovieDetailFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BonMovieApp.getAppComponent().inject(this);
        mMovieId = getArguments().getInt("movie_id");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        BonMovieApp.getAppComponent().inject(mViewModel);
        mViewModel.init(mMovieId);
        mViewModel.getMovieDetail().observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(@Nullable MovieDetail movieDetail) {

                if (movieDetail != null){
                    Log.d(TAG, "onActivityCreated: onChanged" );
                    Log.d(TAG, movieDetail.toString());
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mImageView.setImageUrl(movieDetail.getPosterImageUrl(), mImageLoader);
                    mBinding.setMovieDetail(movieDetail);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // initial view elements
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        View root = mBinding.getRoot();
        mImageView = root.findViewById(R.id.imv_movie_detail_header_poster);
        mProgressBar = root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        return root;
    }



}
