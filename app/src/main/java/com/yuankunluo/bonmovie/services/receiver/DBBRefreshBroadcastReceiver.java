package com.yuankunluo.bonmovie.services.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.listener.OnSwipeRefreshListener;

/**
 * Created by yuank on 2017-06-26.
 */

public class DBBRefreshBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = DBBRefreshBroadcastReceiver.class.getSimpleName();
    private OnSwipeRefreshListener mListener;

    public DBBRefreshBroadcastReceiver(){
        super();
    }

    public DBBRefreshBroadcastReceiver(OnSwipeRefreshListener listener){
        super();
        mListener = listener;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String type;
       switch (intent.getAction()){
           case BonMovieAction.ACTION_DB_INSERTED_MOVIES:
               int page = intent.getIntExtra("page", 0);
               type = intent.getStringExtra("type");
               Log.d(TAG, "onReceive " + type +" page " + page);
               if(page == 1){
                   if(mListener != null){
                       mListener.onRefreshOver();
                   }
               }
               break;
           case BonMovieAction.ACTION_DB_INSERTED_MOVIE_DETAL:
               int movieId = intent.getIntExtra("movie_id",0);
               Log.d(TAG, "onReceive movie detail inserted: " + movieId );
               if(mListener != null){
                   mListener.onRefreshOver();
               }
       }
    }
}
