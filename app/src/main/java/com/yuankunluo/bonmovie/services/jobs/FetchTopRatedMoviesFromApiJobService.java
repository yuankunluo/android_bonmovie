package com.yuankunluo.bonmovie.services.jobs;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.data.dao.TopRatedMovieDao;
import com.yuankunluo.bonmovie.data.model.TopRatedMovie;
import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;
import com.yuankunluo.bonmovie.utilities.TheMovieApiJsonParser;
import com.yuankunluo.bonmovie.utilities.TheMovieApiUriBuilder;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-24.
 */

public class FetchTopRatedMoviesFromApiJobService extends JobService {
    private final String TAG = FetchTopRatedMoviesFromApiJobService.class.getSimpleName();

    @Inject VolleyWebService mWebService;
    @Inject Gson mGson;
    @Inject TheMovieApiUriBuilder mUriBuilder;
    @Inject TopRatedMovieDao mDao;
    @Inject ExecutorService mExecutor;
    @Inject TheMovieApiJsonParser mJsonParser;

    @Override
    public boolean onStartJob(JobParameters job) {
        BonMovieApp.getAppComponent().inject(this);
        final int page = job.getExtras().getInt("page");
        Log.d(TAG, "onStartJob page: " + Integer.toString(page));
        String url = mUriBuilder.getTopRatedMovieAtPageUri(page).toString();
        Log.d(TAG, "onStartJob url: " +  url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final TopRatedMovie[] newMovies = mJsonParser.parseResponseResultsToArray(response, TopRatedMovie.class, TopRatedMovie[].class);
                        // save result into local db in worker thread
                        mExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                mDao.insertMovies(newMovies);
                                Intent intent = new Intent();
                                intent.setAction(BonMovieAction.ACTION_DB_INSERTED);
                                intent.putExtra("page", page);
                                intent.putExtra("type",TopRatedMovie.class.getSimpleName());
                                sendBroadcast(intent);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse");
                        error.printStackTrace();
                    }
                }
        );
        mWebService.addToRequestQueue(jsonObjectRequest);
        // one time job
        return false;
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        // should restarted?
        return false;
    }


}