package com.yuankunluo.bonmovie.data.repository;

import android.arch.lifecycle.LiveData;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;


import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.services.jobs.FetchPopularMoviesFromApiJobService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yuank on 2017-06-22.
 */
@Singleton
public class PopularMovieRepository {


    final static String TAG = PopularMovieRepository.class.getSimpleName();

    private PopularMovieDao mPopularMovieDao;
    private Executor mExecutor;
    private FirebaseJobDispatcher mFireBaseDispatcher;


    @Inject
    public PopularMovieRepository(PopularMovieDao movieDao,
                                  ExecutorService executorService,
                                  FirebaseJobDispatcher dispatcher
                                  ){
        mPopularMovieDao = movieDao;
        mExecutor = executorService;
        mFireBaseDispatcher = dispatcher;
    }


    public LiveData<List<PopularMovie>> getPopularMovies(){
        return mPopularMovieDao.getAllMovies();
    }

    /**
     * Check if this page already exists in local db and check if should
     * refetch. If fetch needs, start a firebase job to fetch. Use firebase
     * it good for its constraint such as ANY_NETWORK. It saves us from manually
     * check internet connection anywhere.
     * @param page the page number to load
     *
     */
    public void refreshMoviesAtPage(final int page){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                boolean movieExists = mPopularMovieDao.hasMoviesAtPage(page);
                Log.i(TAG, Boolean.toString(movieExists));
                if(!movieExists){
                    Bundle pageExtraBundle = new Bundle();
                    pageExtraBundle.putInt("page", page);
                    Log.i(TAG, "page " + page +" not exists");
                    Job fetchJob = mFireBaseDispatcher.newJobBuilder()
                            .setService(FetchPopularMoviesFromApiJobService.class)
                            .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                            // fetch immediacy
                            .setTrigger(Trigger.executionWindow(0,0))
                            .setRecurring(false)
                            .setConstraints(Constraint.ON_ANY_NETWORK)
                            .setExtras(pageExtraBundle)
                            .setTag("page-"+String.valueOf(page))
                            .build();
                    mFireBaseDispatcher.schedule(fetchJob);
                }
            }
        });
    }




}
