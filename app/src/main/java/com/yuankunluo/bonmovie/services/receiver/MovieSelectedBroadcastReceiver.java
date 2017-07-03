package com.yuankunluo.bonmovie.services.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.view.listener.OnMovieSelectedListener;

/**
 * Created by yuank on 2017-07-03.
 */

public class MovieSelectedBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = MovieSelectedBroadcastReceiver.class.getSimpleName();

    private OnMovieSelectedListener mListener;

    public MovieSelectedBroadcastReceiver(){
        super();

    }

    public MovieSelectedBroadcastReceiver(OnMovieSelectedListener listener){
        super();
        mListener = listener;
        Log.d(TAG, "MovieSelectedBroadcastReceiver()" + listener.toString());

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case BonMovieAction.ACTION_MOVIE_SELECTED : {
                int movieId = intent.getIntExtra("movie_id",0);
                Log.d(TAG, "onReceive id: " + movieId );
                if(mListener != null) {
                    mListener.selectMovie(movieId);
                }
            }
        }
    }
}
