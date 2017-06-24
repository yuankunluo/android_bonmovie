package com.yuankunluo.bonmovie.dagger2.module;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.google.gson.Gson;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.repository.PopularMovieRepository;
import com.yuankunluo.bonmovie.services.tools.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-22.
 */

@Module
public class RepositoryModule {

    @Provides
    ExecutorService provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }



    @Singleton
    @Provides
    PopularMovieRepository provideMovieRepository(PopularMovieDao movieDao,
                                                  ExecutorService executorService, FirebaseJobDispatcher dispatcher){
        return new PopularMovieRepository( movieDao, executorService, dispatcher);
    }

}
