package com.yuankunluo.bonmovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.yuankunluo.bonmovie.data.model.TopRatedMovie;
import com.yuankunluo.bonmovie.data.repository.MovieShortRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-22.
 */

public class TopRatedMovieViewModel extends ViewModel {

    private final String TAG = TopRatedMovieViewModel.class.getSimpleName();
    private LiveData<List<TopRatedMovie>> mMovies;
    @Inject
    MovieShortRepository mRepository;


    public void init(){
        if(mMovies != null){
            return;
        }
        mMovies = mRepository.getTopRatedMovies();
        Log.d(TAG, "init() :" + mMovies.toString());
    }

    public LiveData<List<TopRatedMovie>> getMovies(){
        return mMovies;
    }

    public void loadMoviesAtPage(int page){
        Log.d(TAG, "loadMoviesAtPage " + Integer.toString(page));
        mRepository.refreshTopRatedMovieAtPage(page);
    }

    public void forceRefresh(){
        Log.d(TAG, "forceRefresh");
        mRepository.refreshAllTopRatedMovies();
    }


}
