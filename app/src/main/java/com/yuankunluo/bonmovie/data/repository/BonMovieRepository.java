package com.yuankunluo.bonmovie.data.repository;

import android.arch.lifecycle.LiveData;

import android.os.Bundle;
import android.util.Log;


import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.dao.TopRatedMovieDao;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.data.model.TopRatedMovie;
import com.yuankunluo.bonmovie.services.jobs.FetchPopularMoviesFromApiJobService;
import com.yuankunluo.bonmovie.services.jobs.FetchTopRatedMoviesFromApiJobService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yuank on 2017-06-22.
 */
@Singleton
public class BonMovieRepository {

    private final String TAG = BonMovieRepository.class.getSimpleName();

    public enum MoviesType{
        TypePopularMovie("PopularMovie"),
        TypeTopRatedMovie("TopRatedMovie");

        private String name;
        private MoviesType(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    }

    private PopularMovieDao mPopularMovieDao;
    private TopRatedMovieDao mTopRatedMovieDao;
    private Executor mExecutor;
    private FirebaseJobDispatcher mFireBaseDispatcher;


    @Inject
    public BonMovieRepository(PopularMovieDao movieDao,
                              TopRatedMovieDao topRatedMovieDao,
                              ExecutorService executorService,
                              FirebaseJobDispatcher dispatcher
                                  ){
        mPopularMovieDao = movieDao;
        mExecutor = executorService;
        mFireBaseDispatcher = dispatcher;
        mTopRatedMovieDao = topRatedMovieDao;
        Log.d(TAG, "new BonMovieRepository: " + this.toString());
    }


    public LiveData<List<PopularMovie>> getPopularMovies(){
        return mPopularMovieDao.getAllMovies();
    }

    public LiveData<List<TopRatedMovie>> getTopRatedMovies(){
        return mTopRatedMovieDao.getAllMovies();
    }

    public void refreshAllPopularMovies(){
       refreshAllMovies(MoviesType.TypePopularMovie);
    }

    public void refreshAllTopRatedMovies(){
        refreshAllMovies(MoviesType.TypeTopRatedMovie);
    }

    private void refreshAllMovies(final MoviesType type){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                switch (type){
                    case TypePopularMovie:
                        mPopularMovieDao.deleteAll();
                        refreshPopularMovieAtPage(1);
                        break;
                    case TypeTopRatedMovie:
                        mTopRatedMovieDao.deleteAll();
                        refreshTopRatedMovieAtPage(1);
                }
            }
        });
    }



    /**
     * Check if this page already exists in local db and check if should
     * refetch. If fetch needs, start a firebase job to fetch. Use firebase
     * it good for its constraint such as ANY_NETWORK. It saves us from manually
     * check internet connection anywhere.
     * @param page the page number to load
     *
     */
    private void refreshMoviesAtPage(final int page, final MoviesType typeEnum){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                boolean moviesExist = false;
                String typeExtra = typeEnum.getName();
                final Class<? extends JobService> service;
                switch (typeEnum){
                    case TypePopularMovie:
                        moviesExist = mPopularMovieDao.hasMoviesAtPage(page);
                        service = FetchPopularMoviesFromApiJobService.class;
                        break;
                    case TypeTopRatedMovie:
                        moviesExist = mTopRatedMovieDao.hasMoviesAtPage(page);
                        service = FetchTopRatedMoviesFromApiJobService.class;
                        break;
                    default:
                        service = FetchPopularMoviesFromApiJobService.class;
                        Log.e(TAG, "no movie type matched");
                }
                Log.d(TAG, typeExtra + " Page " + page +" exists in DB: " + Boolean.toString(moviesExist));
                if(!moviesExist){
                    Bundle jobBundle = new Bundle();
                    jobBundle.putInt("page", page);
                    jobBundle.putString("type", typeExtra);
                    Job fetchJob = mFireBaseDispatcher.newJobBuilder()
                            .setService(service)
                            .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                            // fetch immediacy
                            .setTrigger(Trigger.executionWindow(0,0))
                            .setRecurring(false)
                            .setReplaceCurrent(true)
                            .setConstraints(Constraint.ON_ANY_NETWORK)
                            .setExtras(jobBundle)
                            .setTag(typeExtra + "-page-"+String.valueOf(page))
                            .build();
                    mFireBaseDispatcher.schedule(fetchJob);
                    Log.d(TAG, "firebase job scheduled for " + typeExtra + " page " + page + " " + fetchJob.toString());
                }
            }
        });
    }

    public void refreshPopularMovieAtPage(final int page){
        refreshMoviesAtPage(page, MoviesType.TypePopularMovie);
    }

    public void refreshTopRatedMovieAtPage(final int page){
        refreshMoviesAtPage(page, MoviesType.TypeTopRatedMovie);
    }



}
