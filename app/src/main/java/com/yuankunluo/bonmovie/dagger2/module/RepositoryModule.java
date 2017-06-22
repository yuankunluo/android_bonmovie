package com.yuankunluo.bonmovie.dagger2.module;

import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.repository.PopularMovieRepository;
import com.yuankunluo.bonmovie.services.VolleyWebService;

import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-22.
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    PopularMovieRepository provideMovieRepository(VolleyWebService webService, PopularMovieDao movieDao,
                                                  ExecutorService executorService){
        return new PopularMovieRepository(webService, movieDao, executorService);
    }

}
