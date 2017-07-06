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
import android.widget.LinearLayout;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.MovieReview;
import com.yuankunluo.bonmovie.view.widget.MovieReviewItemView;
import com.yuankunluo.bonmovie.viewmodel.MovieReviewsViewModel;

import java.util.List;


/**
 * Created by yuank on 2017-07-06.
 */

public class MovieReviewsFragment extends LifecycleFragment {

    private final static String TAG = MovieReviewsFragment.class.getSimpleName();

    private MovieReviewsViewModel mMovieReviewsViewModel;
    private int mMovieId;
    private LinearLayout mRootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieId = getArguments().getInt("movie_id");
        mMovieReviewsViewModel = ViewModelProviders.of(this).get(MovieReviewsViewModel.class);
        BonMovieApp.getAppComponent().inject(mMovieReviewsViewModel);
        mMovieReviewsViewModel.init(mMovieId);
        mMovieReviewsViewModel.getMovieReviews().observe(this, new Observer<List<MovieReview>>() {
            @Override
            public void onChanged(@Nullable List<MovieReview> movieReviews) {
                if(movieReviews!=null) {
                    Log.d(TAG, "onChanged: " + movieReviews.size());
                    for(MovieReview r : movieReviews){
                        MovieReviewItemView reviewItemView = new MovieReviewItemView(getContext());
                        reviewItemView.setMovieReview(r);
                        mRootView.addView(reviewItemView);
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_movie_reviews,container,false);
        mRootView = v.findViewById(R.id.reviews_container);
        return mRootView;
    }
}
