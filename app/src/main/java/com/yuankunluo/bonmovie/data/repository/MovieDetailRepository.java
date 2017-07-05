package com.yuankunluo.bonmovie.data.repository;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.yuankunluo.bonmovie.data.dao.MovieDetailDao;
import com.yuankunluo.bonmovie.data.dao.MovieReviewDao;
import com.yuankunluo.bonmovie.data.dao.MovieVideoDao;
import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.data.model.MovieReview;
import com.yuankunluo.bonmovie.data.model.MovieVideo;
import com.yuankunluo.bonmovie.services.jobs.FetchMovieDetailFromAPIJobService;
import com.yuankunluo.bonmovie.services.jobs.FetchMovieVideosFromAPIJobServices;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;

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

    @Inject
    public MovieDetailRepository(MovieDetailDao detailDao, MovieVideoDao videoDao,
                                 MovieReviewDao reviewDao, ExecutorService executor,
                                 FirebaseJobDispatcher dispatcher, VolleyWebService webService){
        mDetailDao = detailDao;
        mVideoDao = videoDao;
        mReviewDao = reviewDao;
        mExecutor = executor;
        mDispatcher = dispatcher;
        mWebService = webService;
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
                Bundle jobBundle = new Bundle();
                jobBundle.putInt("movie_id", id);
                Job fetchMovieDetailJob = mDispatcher.newJobBuilder()
                        .setService(FetchMovieDetailFromAPIJobService.class)
                        .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                        .setTrigger(Trigger.executionWindow(0,0))
                        .setRecurring(false)
                        .setReplaceCurrent(false)
                        .setConstraints(Constraint.ON_ANY_NETWORK)
                        .setExtras(jobBundle)
                        .setTag("movie_detail - " + Integer.toString(id))
                        .build();
                mDispatcher.schedule(fetchMovieDetailJob);
                Log.d(TAG, "schedule job to fetch moviedetail " + id + " " + fetchMovieDetailJob.toString());
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
                Bundle jobBundle = new Bundle();
                jobBundle.putInt("movie_id", movieId);
                Job fetchMovieDetailJob = mDispatcher.newJobBuilder()
                        .setService(FetchMovieVideosFromAPIJobServices.class)
                        .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                        .setTrigger(Trigger.executionWindow(0,0))
                        .setRecurring(false)
                        .setReplaceCurrent(false)
                        .setConstraints(Constraint.ON_ANY_NETWORK)
                        .setExtras(jobBundle)
                        .setTag("movie_video - " + Integer.toString(movieId))
                        .build();
                mDispatcher.schedule(fetchMovieDetailJob);
                Log.d(TAG, "schedule job to fetch movie videos " + movieId  + " " +  fetchMovieDetailJob.toString());
            }
        });
    }


}
