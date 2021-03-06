package com.yuankunluo.bonmovie.dagger2.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuankunluo.bonmovie.data.dao.MovieDetailDao;
import com.yuankunluo.bonmovie.data.dao.MovieReviewDao;
import com.yuankunluo.bonmovie.data.dao.MovieVideoDao;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.dao.TopRatedMovieDao;
import com.yuankunluo.bonmovie.data.database.BonMovieDatabase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-21.
 */
@Module
public class DataBaseModule {

    @Provides
    @Singleton
    BonMovieDatabase provideBonMovieDatabase(@Named("appcontext") Context context){
        BonMovieDatabase db = Room.databaseBuilder(context, BonMovieDatabase.class, "bonmovie.db").build();
        return db;
    }


    @Provides
    PopularMovieDao providePopularMovieDao(BonMovieDatabase db){
        return db.popularMovieDao();
    }

    @Provides
    TopRatedMovieDao provideTopRatedMovieDao(BonMovieDatabase db){
        return db.topRatedMovieDao();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    MovieDetailDao provideMovieDetailDao(BonMovieDatabase db){
        return db.movieDetailDao();
    }

    @Provides
    MovieReviewDao provideMovieReviewDao(BonMovieDatabase db){
        return db.movieReviewDao();
    }

    @Provides
    MovieVideoDao provideMovieVideoDao(BonMovieDatabase db){
        return db.movieVideoDao();
    }

}
