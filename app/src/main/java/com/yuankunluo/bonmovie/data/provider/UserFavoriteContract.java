package com.yuankunluo.bonmovie.data.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Define table and columns names.
 * Created by yuank on 2017-11-05.
 */

public class UserFavoriteContract {


    /*
     * The symbolic name for this content provider,
     */
    public static final String CONTENT_AUTHORITY = "com.yuankunluo.bonmovie";

    /*
     * The base URI, which can be extended.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +  CONTENT_AUTHORITY);


    /*
     * The path part of URI for query
     */
    public static final String PATH_USERFAV = "user_fav";

    /**
     * A Innner class that define the table contents for User Favorite Table
     */
    public static final class UserFavEntry implements BaseColumns {

        /*
         * The Uri to query the whole table
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USERFAV)
                .build();

        public static Uri buildContentUriWithMovieId(int movieId){
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(movieId)).build();
        }
        /*
         * Table name and columns.
         */
        public static final String TABLE_NAME = "user_fav";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_POSTER_URL = "poster_url";

        public static final String COLUMN_INSERT_TIMESTAMP = "insert_timestamp";


    }

}
