package com.yuankunluo.bonmovie.dagger2.module;

import android.content.Context;

import com.google.gson.Gson;
import com.yuankunluo.bonmovie.utilities.TheMovieApiJsonResultsParser;
import com.yuankunluo.bonmovie.utilities.TheMovieApiUriBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-26.
 */
@Module
public class UtiltyModule {
    @Provides
    @Singleton
    TheMovieApiUriBuilder provideTheMovieApiUriBuilder(@Named("appcontext") Context context){
        return new TheMovieApiUriBuilder(context);
    }

    @Provides
    TheMovieApiJsonResultsParser provideTheMovieApiJsonParser(Gson gson, TheMovieApiUriBuilder builder){
        return new TheMovieApiJsonResultsParser(gson, builder);
    }

}
