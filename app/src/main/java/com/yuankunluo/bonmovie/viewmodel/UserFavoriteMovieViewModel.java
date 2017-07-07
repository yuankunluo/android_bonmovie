package com.yuankunluo.bonmovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yuankunluo.bonmovie.data.model.UserFavoriteMovie;
import com.yuankunluo.bonmovie.data.repository.UserFavoriteMovieRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-07-07.
 */

public class UserFavoriteMovieViewModel extends ViewModel {
    @Inject
    UserFavoriteMovieRepository mRepository;
    private LiveData<List<UserFavoriteMovie>> mMovies;


    public void init(){
        if(mMovies != null){
            return;
        }
        mMovies = mRepository.getAllMovies();
    }


    public LiveData<List<UserFavoriteMovie>> getMovies(){
        return mMovies;
    }

    public void insertMovie(UserFavoriteMovie movie){
        mRepository.insertMovie(movie);
    }

    public void deleteMovieById(int id){
        mRepository.deleteMovieByMovieId(id);
    }

    public LiveData<Boolean> hasMovieByID(int id){
        return mRepository.hasMovieById(id);
    }


    public void forceRefresh(){
        mMovies = mRepository.getAllMovies();
    }
}
