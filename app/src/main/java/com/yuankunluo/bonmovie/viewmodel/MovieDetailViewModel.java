package com.yuankunluo.bonmovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.data.model.MovieReview;
import com.yuankunluo.bonmovie.data.model.MovieVideo;
import com.yuankunluo.bonmovie.data.repository.MovieDetailRepository;
import com.yuankunluo.bonmovie.data.repository.UserFavoriteMovieRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-27.
 */

public class MovieDetailViewModel extends ViewModel {
    private static final String TAG = MovieDetailViewModel.class.getSimpleName();
    private LiveData<MovieDetail> mMovieDetail;
    private LiveData<List<MovieVideo>> mMovieVideos;
    private int mMovieId;
    @Inject
    MovieDetailRepository movieDetailRepository;

    public void init(int movieId){
        mMovieId = movieId;
        if(mMovieDetail != null && mMovieVideos != null){
            return;
        }
        mMovieDetail = movieDetailRepository.getMovieDetailByMovieId(movieId);
        mMovieVideos = movieDetailRepository.getMovieVideosByMovieId(movieId);
        Log.d(TAG, "init " + mMovieDetail.toString());
    }

    public LiveData<MovieDetail> getMovieDetail(){
        return mMovieDetail;
    }

    public LiveData<List<MovieVideo>> getMovieVideos(){
        return mMovieVideos;
    }

}

