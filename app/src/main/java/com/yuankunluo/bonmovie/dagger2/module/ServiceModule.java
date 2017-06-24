package com.yuankunluo.bonmovie.dagger2.module;

import android.content.Context;

import com.yuankunluo.bonmovie.services.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.VolleyWebService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-21.
 */

@Module
public class ServiceModule {

    @Provides
    @Singleton
    VolleyWebService provideVolleyWebService(@Named("appcontext")Context context){
        return new VolleyWebService(context);
    }

    @Provides
    @Singleton
    TheMovieApiUriBuilder provideTheMovieApiUriBuilder(@Named("appcontext") Context context){
        return new TheMovieApiUriBuilder(context);
    }


}
