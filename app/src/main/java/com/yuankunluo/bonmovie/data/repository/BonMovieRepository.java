package com.yuankunluo.bonmovie.data.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Movie;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.database.BonMovieDatabase;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.services.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.VolleyWebService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by yuank on 2017-06-22.
 */
@Singleton
public class BonMovieRepository {


    final static String TAG = BonMovieRepository.class.getSimpleName();

    VolleyWebService mVolleyWebService;
    PopularMovieDao mPopularMovieDao;
    Executor mExecutor;
    TheMovieApiUriBuilder mApiUriBuilder;


    @Inject
    public BonMovieRepository(VolleyWebService webService,
                              PopularMovieDao movieDao,
                              ExecutorService executorService,
                              TheMovieApiUriBuilder movieApiUriBuilder){
        mVolleyWebService = webService;
        mPopularMovieDao = movieDao;
        mExecutor = executorService;
        mApiUriBuilder = movieApiUriBuilder;
    }


    public LiveData<List<PopularMovie>> getMovies(){
        return mPopularMovieDao.getAllMovies();
    }

    public void refreshMoviesAtPage(final int page){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                boolean movieExists = mPopularMovieDao.hasMoviesAtPage(page);
                Log.i(TAG, Boolean.toString(movieExists));
                if(!movieExists){
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            mApiUriBuilder.getPopularMovieUrlForPage(page).toString(),
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.i(TAG, response.toString());

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, error.toString());
                                }
                            }
                    );
                    mVolleyWebService.addToRequestQueue(jsonObjectRequest);
                }
            }
        });
    }







}
