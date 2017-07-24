package com.yuankunluo.bonmovie.data.contentprovider;

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
 * Created by yuank on 2017-07-24.
 */

public class UserFavoriteProvider extends ContentProvider {

    static final String TAG = UserFavoriteProvider.class.getSimpleName();

    public static final int CODE_FAVORITE_ALL = 100;
    public static final int CODE_FAVORITE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private UserFavoriteDbHelper mDbHelper;

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = UserFavoriteContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, UserFavoriteContract.PATH_USER_FAVORITE, CODE_FAVORITE_ALL);
        matcher.addURI(authority, UserFavoriteContract.PATH_USER_FAVORITE + "/#", CODE_FAVORITE_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new UserFavoriteDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)){

            case CODE_FAVORITE_WITH_ID:
                db.beginTransaction();
                long rowInserted = 0;
                try{
                    String insertTimeStamp = Long.toString(System.currentTimeMillis());
                    contentValues.put(UserFavoriteContract.UserFavoriteEntry.COLUMN_INSERT_TIMESTAMP, insertTimeStamp);
                    rowInserted = db.insert(UserFavoriteContract.UserFavoriteEntry.TABLE_NAME, null, contentValues);
                    db.setTransactionSuccessful();
                } catch (NullPointerException e){
                    Log.e(TAG, " insert error");
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
                if (rowInserted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return UserFavoriteContract.UserFavoriteEntry.CONTENT_URI.buildUpon()
                        .appendPath(Long.toString(rowInserted))
                        .build();
            default:
                return null;
        }
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case CODE_FAVORITE_ALL:
                cursor = mDbHelper.getReadableDatabase().query(
                        UserFavoriteContract.UserFavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return cursor;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        switch (sUriMatcher.match(uri)){
            case CODE_FAVORITE_WITH_ID:
                numRowsDeleted = mDbHelper.getWritableDatabase().delete(
                        UserFavoriteContract.UserFavoriteEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(numRowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in BonMovie.");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new RuntimeException("We are not implementing getType in BonMovie.");
    }
}
