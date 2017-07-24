package com.yuankunluo.bonmovie.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.yuankunluo.bonmovie.data.dao.MovieDetailDao;
import com.yuankunluo.bonmovie.data.dao.MovieReviewDao;
import com.yuankunluo.bonmovie.data.dao.MovieVideoDao;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.dao.TopRatedMovieDao;
import com.yuankunluo.bonmovie.data.dao.UserFavoriteMovieDao;
import com.yuankunluo.bonmovie.data.model.UserFavoriteMovie;
import com.yuankunluo.bonmovie.data.tools.DateConverter;
import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.data.model.MovieReview;
import com.yuankunluo.bonmovie.data.model.MovieVideo;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.data.model.TopRatedMovie;

/**
 * Created by yuank on 2017-06-21.
 */

@Database(entities = {PopularMovie.class,
        TopRatedMovie.class , MovieDetail.class, MovieReview.class, MovieVideo.class},
        version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class BonMovieDatabase extends RoomDatabase{
    public abstract PopularMovieDao popularMovieDao();
    public abstract TopRatedMovieDao topRatedMovieDao();
    public abstract MovieDetailDao movieDetailDao();
    public abstract MovieVideoDao movieVideoDao();
    public abstract MovieReviewDao movieReviewDao();
    public abstract UserFavoriteMovieDao userFavoriteMovieDao();
}
