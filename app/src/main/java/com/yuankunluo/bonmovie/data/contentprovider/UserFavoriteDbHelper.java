package com.yuankunluo.bonmovie.data.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-07-24.
 */

public class UserFavoriteDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "user_favorite.db";
    public static final int DATABASE_VERSION = 1;

    @Inject
    public UserFavoriteDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_USER_FAVORITE_TABLE =
                "CREATE TABLE " + UserFavoriteContract.UserFavoriteEntry.TABLE_NAME + " (" +
                        UserFavoriteContract.UserFavoriteEntry.COLUMN_MOVIEID + " INTEGER PRIMARY KEY, " +
                        UserFavoriteContract.UserFavoriteEntry.COLUMN_POSTER_URL + " TEXT, " +
                        UserFavoriteContract.UserFavoriteEntry.COLUMN_INSERT_TIMESTAMP + " TEXT );";
        sqLiteDatabase.execSQL(SQL_CREATE_USER_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserFavoriteContract.UserFavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
