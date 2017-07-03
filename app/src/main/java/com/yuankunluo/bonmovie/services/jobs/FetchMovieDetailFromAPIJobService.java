package com.yuankunluo.bonmovie.services.jobs;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.data.dao.MovieDetailDao;
import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.utilities.TheMovieApiJsonResultsParser;
import com.yuankunluo.bonmovie.services.utilities.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-07-01.
 */

public class FetchMovieDetailFromAPIJobService extends JobService {
    private static final String TAG = FetchMovieDetailFromAPIJobService.class.getSimpleName();
    @Inject
    VolleyWebService mWebService;
    @Inject
    MovieDetailDao mDao;
    @Inject
    ExecutorService mExexutor;
    @Inject
    TheMovieApiUriBuilder mUriBuilder;
    @Inject
    TheMovieApiJsonResultsParser mParser;


    @Override
    public boolean onStartJob(JobParameters job) {
        BonMovieApp.getAppComponent().inject(this);
        final int movieIdExtra = job.getExtras().getInt("movie_id");
        Log.d(TAG, "onStart, movie id: " + Integer.toString(movieIdExtra));
        String url = mUriBuilder.getMovieDetailByMovieId(movieIdExtra).toString();
        Log.d(TAG, "onStartJob url: " + url );
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final MovieDetail movieDetail = mParser.parseResponseDirectAsObject(response, MovieDetail.class);
                        mExexutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                // save movie detail into db
                                mDao.insertMovieDetail(movieDetail);
                                Log.d(TAG, "insert movie detail into db");
                                // send broadcasting
                                Intent intent = new Intent();
                                intent.setAction(BonMovieAction.ACTION_DB_INSERTED_MOVIE_DETAL);
                                intent.putExtra("movie_id", movieIdExtra);
                                intent.putExtra("type", MovieDetail.class.getSimpleName());
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
        mWebService.addToRequestQueue(request);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
