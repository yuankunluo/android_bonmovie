package com.yuankunluo.bonmovie.data.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yuank on 2017-07-24.
 */

public class UserFavoriteContract {

    public static final String CONTENT_AUTHORITY = "com.yuankunluo.com.bonmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +  CONTENT_AUTHORITY);

    public static final String PATH_USER_FAVORITE = "user_favorite";


    public static final class UserFavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USER_FAVORITE)
                .build();

        public static final String TABLE_NAME = "user_favorite";
        public static final String COLUMN_MOVIEID = "movieId";
        public static final String COLUMN_POSTER_URL = "poster_url";
        public static final String COLUMN_INSERT_TIMESTAMP = "insert_timestamp";

        public static Uri buildUserFavoriteMovieWithId(int id){
            return CONTENT_URI.buildUpon()
                    .appendEncodedPath(Integer.toString(id))
                    .build();
        }


    }

}
