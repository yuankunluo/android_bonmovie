package com.yuankunluo.bonmovie.dagger2.module;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.dao.TopRatedMovieDao;
import com.yuankunluo.bonmovie.data.dao.UserFavoriteMovieDao;
import com.yuankunluo.bonmovie.data.repository.MovieShortRepository;
import com.yuankunluo.bonmovie.data.repository.UserFavoriteMovieRepository;

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
    MovieShortRepository provideMovieRepository(PopularMovieDao popularMovieDao,
                                                TopRatedMovieDao topRatedMovieDao,
                                                ExecutorService executorService, FirebaseJobDispatcher dispatcher){
        return new MovieShortRepository( popularMovieDao, topRatedMovieDao, executorService, dispatcher);
    }

    @Singleton
    @Provides
    UserFavoriteMovieRepository provideUserFavoriteRepository(UserFavoriteMovieDao dao, ExecutorService executorService){
        return new UserFavoriteMovieRepository(dao, executorService);
    }

}
