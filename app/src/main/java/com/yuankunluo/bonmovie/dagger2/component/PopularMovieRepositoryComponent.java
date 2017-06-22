package com.yuankunluo.bonmovie.dagger2.component;

import com.yuankunluo.bonmovie.dagger2.module.AppModule;
import com.yuankunluo.bonmovie.dagger2.module.DataBaseModule;
import com.yuankunluo.bonmovie.dagger2.module.RepositoryModule;
import com.yuankunluo.bonmovie.dagger2.module.ServiceModule;
import com.yuankunluo.bonmovie.data.repository.PopularMovieRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yuank on 2017-06-22.
 */

@Singleton
@Component(modules = {AppModule.class, DataBaseModule.class, RepositoryModule.class, ServiceModule.class})
public interface PopularMovieRepositoryComponent {
    PopularMovieRepository providePopularMovieRepository();
}
