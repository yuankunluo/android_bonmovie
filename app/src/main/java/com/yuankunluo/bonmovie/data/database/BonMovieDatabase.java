package com.yuankunluo.bonmovie.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.model.DateConverter;
import com.yuankunluo.bonmovie.data.model.PopularMovie;

/**
 * Created by yuank on 2017-06-21.
 */

@Database(entities = {PopularMovie.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class BonMovieDatabase extends RoomDatabase{
    public abstract PopularMovieDao popularMovieDao();
}
