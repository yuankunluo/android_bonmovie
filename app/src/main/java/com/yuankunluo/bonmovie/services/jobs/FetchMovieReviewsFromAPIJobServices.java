package com.yuankunluo.bonmovie.services.jobs;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.data.dao.MovieReviewDao;
import com.yuankunluo.bonmovie.data.dao.MovieVideoDao;
import com.yuankunluo.bonmovie.data.model.MovieReview;
import com.yuankunluo.bonmovie.data.model.MovieVideo;
import com.yuankunluo.bonmovie.services.utilities.TheMovieApiJsonResultsParser;
import com.yuankunluo.bonmovie.services.utilities.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-07-04.
 */

public class FetchMovieReviewsFromAPIJobServices extends JobService {
    private static final String TAG = FetchMovieReviewsFromAPIJobServices.class.getSimpleName();
    @Inject
    VolleyWebService mWebService;
    @Inject
    MovieReviewDao mDao;
    @Inject
    ExecutorService mExecutor;
    @Inject
    TheMovieApiUriBuilder mUriBuilder;
    @Inject
    TheMovieApiJsonResultsParser mParser;

    @Override
    public boolean onStartJob(JobParameters job) {
        BonMovieApp.getAppComponent().inject(this);
        final int movieIdExtra = job.getExtras().getInt("movie_id");
        final int page = job.getExtras().getInt("page");
        Log.d(TAG, "onStartJob start: " + movieIdExtra + " page " + page);
        String url = mUriBuilder.getMovieReviewsForMovieIdAtPage(movieIdExtra, page).toString();
        Log.d(TAG, "onStartJob start job with URL: " + url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final MovieReview[] reviews = mParser.parseResponseResultsToArray(response, MovieReview.class, MovieReview[].class);
                        mExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                mDao.insertReviews(reviews);
                                Log.d(TAG, "insert reviews into DB: " + reviews.length);
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