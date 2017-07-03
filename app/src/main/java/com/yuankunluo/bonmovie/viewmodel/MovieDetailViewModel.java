package com.yuankunluo.bonmovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.data.model.MovieReview;
import com.yuankunluo.bonmovie.data.model.MovieVideo;
import com.yuankunluo.bonmovie.data.repository.MovieDetailRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-27.
 */

public class MovieDetailViewModel extends ViewModel {
    private static final String TAG = MovieDetailViewModel.class.getSimpleName();
    private LiveData<MovieDetail> mMovieDetail;
    private LiveData<List<MovieReview>> mReviews;
    private LiveData<List<MovieVideo>> mVideos;
    @Inject
    MovieDetailRepository movieDetailRepository;

    public void init(int movieId){
        if(mMovieDetail != null){
            return;
        }
        mMovieDetail = movieDetailRepository.getMovieDetailByMovieId(movieId);
    }

    public LiveData<MovieDetail> getMovieDetail(){
        return mMovieDetail;
    }

}

