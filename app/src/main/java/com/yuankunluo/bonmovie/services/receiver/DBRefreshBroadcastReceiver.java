package com.yuankunluo.bonmovie.services.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.listener.OnDBRefreshListener;

/**
 * Created by yuank on 2017-06-26.
 */

public class DBRefreshBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = DBRefreshBroadcastReceiver.class.getSimpleName();
    private OnDBRefreshListener mListener;

    public DBRefreshBroadcastReceiver(){
        super();
    }

    public DBRefreshBroadcastReceiver(OnDBRefreshListener listener){
        super();
        mListener = listener;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
       switch (intent.getAction()){
           case BonMovieAction.ACTION_DB_INSERTED:
               int page = intent.getIntExtra("page", 0);
               String type = intent.getStringExtra("type");
               Log.d(TAG, "onReceive " + type +" page " + page);
               if(page == 1){
                   if(mListener != null){
                       mListener.onRefreshOver();
                   }
               }
       }
    }
}
