package com.yuankunluo.bonmovie.services;

import android.content.Context;
import android.net.Uri;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by yuank on 2017-06-24.
 */

@Singleton
public class TheMovieApiUriBuilder {

    Context mContext;

    @Inject
    public TheMovieApiUriBuilder(@Named("appcontext") Context context){
        mContext = context;
    }

    private  Uri getBaseUri(){
        return new Uri.Builder()
                .scheme(mContext.getString(R.string.themoviedb_url_scheme))
                .authority(mContext.getString(R.string.themoviedb_host))
                .appendPath(mContext.getString(R.string.themoviedb_path_api_version))
                .appendQueryParameter(mContext.getString(R.string.themoviedb_param_api_key),
                        mContext.getString(R.string.themoviedb_param_api_key_value))
                .build();
    }

    public Uri getPopularMovieUrlForPage(int page){
        return getBaseUri().buildUpon()
                .appendPath(mContext.getString(R.string.themoviedb_path_movie))
                .appendPath(mContext.getString(R.string.themoviedb_path_popular))
                .appendQueryParameter(
                        mContext.getString(R.string.themoviedb_param_page),
                        Integer.toString(page)
                ).build();
    }

}
