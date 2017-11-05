package com.yuankunluo.bonmovie.data.repository;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;

import com.yuankunluo.bonmovie.data.dao.UserFavoriteMovieDao;
import com.yuankunluo.bonmovie.data.model.UserFavoriteMovie;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-07-07.
 */

public class UserFavoriteMovieRepositoryWithContentProvider {

    private static String TAG = UserFavoriteMovieRepositoryWithContentProvider.class.getSimpleName();

    private ExecutorService mExecutor;

    @Inject
    public UserFavoriteMovieRepositoryWithContentProvider(UserFavoriteMovieDao dao, ExecutorService executorService){
        mExecutor = executorService;
    }

    public LiveData<List<UserFavoriteMovie>> getAllMovies(){
        return new LiveData<List<UserFavoriteMovie>>() {
            @Override
            public void observe(LifecycleOwner owner, Observer<List<UserFavoriteMovie>> observer) {
                super.observe(owner, observer);
            }
        };
    }

    public void insertMovie(final UserFavoriteMovie movie){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                movie.setInsert_timestamp(System.currentTimeMillis());
            }
        });
    }

    public void deleteMovieByMovieId(final int movieId){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    public LiveData<Boolean> hasMovieById(final int movieId){
        return new LiveData<Boolean>() {
            @Override
            public void observe(LifecycleOwner owner, Observer<Boolean> observer) {
                super.observe(owner, observer);
            }
        };
    }


}
