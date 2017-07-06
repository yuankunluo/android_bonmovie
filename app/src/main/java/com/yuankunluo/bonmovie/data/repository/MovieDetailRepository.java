package com.yuankunluo.bonmovie.data.repository;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import com.yuankunluo.bonmovie.data.dao.MovieDetailDao;
import com.yuankunluo.bonmovie.data.dao.MovieReviewDao;
import com.yuankunluo.bonmovie.data.dao.MovieVideoDao;
import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.data.model.MovieReview;
import com.yuankunluo.bonmovie.data.model.MovieVideo;

import com.yuankunluo.bonmovie.services.utilities.TheMovieApiJsonResultsParser;
import com.yuankunluo.bonmovie.services.utilities.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-27.
 */

public class MovieDetailRepository {
    private static final String TAG = MovieDetailRepository.class.getSimpleName();

    MovieDetailDao mDetailDao;
    MovieVideoDao mVideoDao;
    MovieReviewDao mReviewDao;
    ExecutorService mExecutor;
    FirebaseJobDispatcher mDispatcher;
    VolleyWebService mWebService;
    TheMovieApiUriBuilder mApiUriBuilder;
    TheMovieApiJsonResultsParser mParser;

    @Inject
    public MovieDetailRepository(MovieDetailDao detailDao, MovieVideoDao videoDao,
                                 MovieReviewDao reviewDao, ExecutorService executor,
                                 TheMovieApiUriBuilder uriBuilder,
                                 TheMovieApiJsonResultsParser parser,VolleyWebService webService){
        mDetailDao = detailDao;
        mVideoDao = videoDao;
        mReviewDao = reviewDao;
        mExecutor = executor;
        mWebService = webService;
        mApiUriBuilder = uriBuilder;
        mParser = parser;
    }



    public LiveData<MovieDetail> getMovieDetailByMovieId(int movieId){
        refreshMovieDetailById(movieId);
        return mDetailDao.getMovieById(movieId);
    }

    private void refreshMovieDetailById(final int id){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(mDetailDao.hasMovieById(id)){
                    Log.d(TAG, "refreshMovieDetailById: MovieDetailActivity exists " + Integer.toString(id));
                    return;
                }

                String url = mApiUriBuilder.getMovieDetailByMovieId(id).toString();
                Log.d(TAG, "refreshMovieDetailById url: " + url );
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                final MovieDetail movieDetail = mParser.parseResponseDirectAsObject(response, MovieDetail.class);
                                // save movie detail into db
                                mExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDetailDao.insertMovieDetail(movieDetail);
                                        Log.d(TAG, "insert movie detail into db " + id);
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

                Log.d(TAG, "schedule webservice to fetch moviedetail " + id);
            }
        });
    }

    LiveData<List<MovieReview>> getMovieReviewsByMovieIdAndPage(int id, int page){
        return mReviewDao.getMovieReviewsByMovieIdAndPage(id, page);
    }

    public LiveData<List<MovieVideo>> getMovieVideosByMovieId(int id){
        refreshMovieVideoByMovieId(id);
        return mVideoDao.getVideosByMovieId(id);
    }

    private void refreshMovieVideoByMovieId(final int movieId){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(mVideoDao.hasVideosForMovieId(movieId)){
                    Log.d(TAG, "movie video exists: " + movieId);
                    return;
                }
                String url = mApiUriBuilder.getMovieVideosUri(movieId).toString();
                Log.d(TAG, "onStartJob start job with URL: " + url);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                final MovieVideo[] movieVideos = mParser.parseResponseResultsToArray(response, MovieVideo.class, MovieVideo[].class);
                                mExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mVideoDao.insertMovieVideos(movieVideos);
                                        Log.d(TAG, "insert videos into DB: " + movieVideos.length);
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
                Log.d(TAG, "schedule webservice to fetch movie videos " + movieId );
            }
        });
    }

    public LiveData<List<MovieReview>> getMovieReviewsAtPageByMovieId(int movieId, int page){
        refreshMovieReviewsAtPageByMovieId(movieId, page);
        return mReviewDao.getMovieReviewsByMovieIdAndPage(movieId, page);
    }

    public void refreshMovieReviewsAtPageByMovieId(final int movieId, final int page){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(mReviewDao.hasReiewsAtPageByMovieId(movieId, page)){
                    return;
                }
                String url = mApiUriBuilder.getMovieReviewsForMovieIdAtPage(movieId, page).toString();
                Log.d(TAG, "onStartJob start job with URL: " + url);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                final MovieReview[] reviews = mParser.parseResponseResultsToArray(response, MovieReview.class, MovieReview[].class);
                                mExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mReviewDao.insertReviews(reviews);
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
                Log.d(TAG, "schedule webservice to fetch movie reviews " + movieId  + " page " + page);
            }
        });
    }


}
