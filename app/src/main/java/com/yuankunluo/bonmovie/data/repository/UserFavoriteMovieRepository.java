package com.yuankunluo.bonmovie.data.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.yuankunluo.bonmovie.data.dao.UserFavoriteMovieDao;
import com.yuankunluo.bonmovie.data.model.UserFavoriteMovie;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-07-07.
 */

public class UserFavoriteMovieRepository {

    private static String TAG = UserFavoriteMovieRepository.class.getSimpleName();

    private UserFavoriteMovieDao mDao;
    private ExecutorService mExecutor;

    @Inject
    public UserFavoriteMovieRepository(UserFavoriteMovieDao dao, ExecutorService executorService){
        mDao = dao;
        mExecutor = executorService;
    }


    public LiveData<List<UserFavoriteMovie>> getAllMovies(){
        return mDao.getAllMovies();
    }

    public void insertMovie(final UserFavoriteMovie movie){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.insertMovies(movie);
            }
        });
    }

    public void deleteMovieByMovieId(final int movieId){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.deleteMovieByMovieId(movieId);
            }
        });
    }

    LiveData<Boolean> hasMovieById(final int movieId){
        return mDao.hasMoviesByMovieId(movieId);
    }


}
