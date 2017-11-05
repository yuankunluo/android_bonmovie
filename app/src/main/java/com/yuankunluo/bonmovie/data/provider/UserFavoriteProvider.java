package com.yuankunluo.bonmovie.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yuank on 2017-11-05.
 */

public class UserFavoriteProvider extends ContentProvider {

    public static final String TAG = UserFavoriteProvider.class.getSimpleName();
    public static final int CODE_USER_FAV = 100;

    public static final int CODE_USER_FAV_WITH_ID = 101;

    /*
     * Create a static UriMatcher which only matches predefined CODEs
     */
    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = UserFavoriteContract.CONTENT_AUTHORITY;


        /* Match this Uri for querying all user's favorites */
        matcher.addURI(authority, UserFavoriteContract.PATH_USERFAV, CODE_USER_FAV);

        /* Match this Uri for specific MovieId */
        matcher.addURI(authority, UserFavoriteContract.PATH_USERFAV + "/#", CODE_USER_FAV_WITH_ID);

        return matcher;
    }

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private UserFavoriteDbHelper mDbHelper;

    /**
     *  Initialize our content provider on startup.
     *  It will be called at launch time.
     *  It must be very quick.
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new UserFavoriteDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        // The returned cursor object.
        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            /*
             * Query if a movie exists in DB
             */
            case CODE_USER_FAV_WITH_ID: {
                Log.d(TAG, "query:" + uri);
                String movieId = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{movieId};

                cursor = mDbHelper.getReadableDatabase().query(
                        UserFavoriteContract.UserFavEntry.TABLE_NAME,
                        projection,
                        UserFavoriteContract.UserFavEntry.COLUMN_MOVIE_ID + " = ?",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_USER_FAV: {
                Log.d(TAG, "query:" + uri);
                cursor = mDbHelper.getReadableDatabase().query(
                        UserFavoriteContract.UserFavEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException(TAG + " query() Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }



    /**
     *  Handles requests to insert a new user's favorite.
     * @param uri The content://URI of insertion request
     * @param contentValues An array of sets of column_name:value pairs to added to the database.
     * @return The URI for newly inserted item.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "#insert " + uri);
        // get the writable Db.
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri newRowUri = null;
        switch (sUriMatcher.match(uri)){
            case CODE_USER_FAV:
                db.beginTransaction();
                Integer movieId = contentValues.getAsInteger(UserFavoriteContract.UserFavEntry.COLUMN_MOVIE_ID);
                Log.d(TAG, "Begin Transaction for inserting Movie " + movieId.toString() + " into Db");
                long newRowId = -1;

                try {
                    newRowId = db.insert(UserFavoriteContract.UserFavEntry.TABLE_NAME, null, contentValues );
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (newRowId > 0){
                    // notify observer on this uri
                    getContext().getContentResolver().notifyChange(uri, null);
                    newRowUri =  uri.buildUpon()
                            .appendPath(UserFavoriteContract.PATH_USERFAV)
                            .appendPath("/"+ movieId.toString())
                            .build();
                    Log.d(TAG, "Success: Insert Movie To db: " + newRowUri.toString());
                }
                return newRowUri;
            default:
                throw new UnsupportedOperationException(TAG + " insert() Unknown uri: " + uri);
        }
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        /*
         * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
         * deleted. To know how many rows were deleted, we need pass "1".
         */
        if (null == selection){
            selection = "1";
        }

        switch (sUriMatcher.match(uri)){
            case CODE_USER_FAV_WITH_ID :
                String movieId = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{movieId};
                numRowsDeleted = mDbHelper.getWritableDatabase().delete(
                        UserFavoriteContract.UserFavEntry.TABLE_NAME,
                        UserFavoriteContract.UserFavEntry.COLUMN_MOVIE_ID  + " = ?",
                        selectionArguments);
                Log.d(TAG, "delete " + uri );
                break;

            default:
                throw new UnsupportedOperationException(TAG + " delete() Unknown uri: " + uri);
        }

        /*
         * Notify the change has occurred to this URI
         */
        if (numRowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }



    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException(TAG + " We are not implementing getType()");
    }
}
