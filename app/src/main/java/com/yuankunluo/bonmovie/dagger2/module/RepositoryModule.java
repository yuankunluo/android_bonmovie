package com.yuankunluo.bonmovie.dagger2.module;

import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.repository.BonMovieRepository;
import com.yuankunluo.bonmovie.services.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.VolleyWebService;

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
    BonMovieRepository provideMovieRepository(VolleyWebService webService, PopularMovieDao movieDao,
                                              ExecutorService executorService, TheMovieApiUriBuilder movieApiUriBuilder){
        return new BonMovieRepository(webService, movieDao, executorService, movieApiUriBuilder);
    }

}
