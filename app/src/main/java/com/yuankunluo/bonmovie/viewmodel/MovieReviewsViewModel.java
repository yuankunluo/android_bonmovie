package com.yuankunluo.bonmovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.yuankunluo.bonmovie.data.model.MovieReview;
import com.yuankunluo.bonmovie.data.repository.MovieDetailRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-07-06.
 */

public class MovieReviewsViewModel extends ViewModel {

    private static final String TAG = MovieReviewsViewModel.class.getSimpleName();
    private LiveData<List<MovieReview>> mReviews;
    private int mMovieId;
    @Inject
    MovieDetailRepository movieDetailRepositiory;

    public void init(int movieId){
        mMovieId = movieId;
        if(mReviews != null){
            return;
        }
        mReviews = movieDetailRepositiory.getMovieReviewsAtPageByMovieId(movieId,1);
    }

    public LiveData<List<MovieReview>> getMovieReviews(){
        return mReviews;
    }

    public void loadMovieReivesAtPage(int page){
        Log.d(TAG, "loadMovieReivesAtPage " + Integer.toString(page));
        movieDetailRepositiory.refreshMovieReviewsAtPageByMovieId(mMovieId,page);
    }

}
