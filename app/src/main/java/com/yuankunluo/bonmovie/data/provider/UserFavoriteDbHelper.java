package com.yuankunluo.bonmovie.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manage a local database for UserFavorites
 * Created by yuank on 2017-11-05.
 */

public class UserFavoriteDbHelper extends SQLiteOpenHelper{

    /*
     * This is the name of BonMovie's database.
     * The name should be with .db extension.
     */
    public static final String DATABASE_NAME = "user_fav.db";

    /*
     * Up the version number, if database scheme changed.
     */
    private static final int DATABASE_VERSION = 1;

    /*
     * Constructor by calling super's constructor.
     */
    public UserFavoriteDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    /**
     * Create the Database for the first time.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        /*
         * The raw SQL statement to create the user favorite table.
         */
        final String SQL_CREATET_TABLE_USER_FAV =
                "CREATE TABLE " + UserFavoriteContract.UserFavEntry.TABLE_NAME + " (" +
                        "_id INTEGER PRIMARY KEY ASC, " +
                        UserFavoriteContract.UserFavEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                        UserFavoriteContract.UserFavEntry.COLUMN_POSTER_URL  + " TEXT, " +
                        UserFavoriteContract.UserFavEntry.COLUMN_INSERT_TIMESTAMP + " INTEGER ); ";

        sqLiteDatabase.execSQL(SQL_CREATET_TABLE_USER_FAV);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserFavoriteContract.UserFavEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
