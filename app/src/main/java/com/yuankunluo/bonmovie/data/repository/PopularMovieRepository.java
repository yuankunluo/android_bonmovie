package com.yuankunluo.bonmovie.data.repository;

import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.database.BonMovieDatabase;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.services.VolleyWebService;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yuank on 2017-06-22.
 */
@Singleton
public class PopularMovieRepository {
    VolleyWebService mVolleyWebService;
    PopularMovieDao mPopularMovieDao;
    Executor mExecutor;

    @Inject
    public PopularMovieRepository(VolleyWebService webService, PopularMovieDao movieDao, ExecutorService executorService){
        mVolleyWebService = webService;
        mPopularMovieDao = movieDao;
        mExecutor = executorService;
    }




}
