package com.yuankunluo.bonmovie.services.jobs;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.services.tools.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-24.
 */

public class FetchPopularMoviesFromApiJobService extends JobService {
    private final String TAG = FetchPopularMoviesFromApiJobService.class.getSimpleName();

    @Inject VolleyWebService mWebService;
    @Inject Gson mGson;
    @Inject TheMovieApiUriBuilder mUriBuilder;
    @Inject PopularMovieDao mDao;
    @Inject ExecutorService mExecutor;

    @Override
    public boolean onStartJob(JobParameters job) {
        BonMovieApp.getAppComponent().inject(this);
        int page = job.getExtras().getInt("page");
        Log.i(TAG, "onStartJob page: " + Integer.toString(page));
        String url = mUriBuilder.getPopularMovieAtPageUrl(page).toString();
        Log.i(TAG, "onStartJob url: " +  url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final PopularMovie[] newMovies = parseResponse(response);
                        // save result into local db in worker thread
                        mExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                mDao.insertPopularMovies(newMovies);
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
        // should not restarted
        return false;
    }

    private PopularMovie[] parseResponse(JSONObject response){
        try{
            int page = response.getInt("page");
            JSONArray results = response.getJSONArray("results");
            PopularMovie[] movies = mGson.fromJson(results.toString(), PopularMovie[].class);
            for(PopularMovie m : movies){
                m.setPage(page);
                m.setPoster_url(mUriBuilder.getPosterBaseUri().toString() + m.getPoster_path());
            }

            return movies;
        } catch (JSONException e){
            Log.e(TAG, "json parse exeception: " + response.toString());
            e.printStackTrace();
        }
        return null;
    }
}
