package com.yuankunluo.bonmovie.services.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yuankunluo.bonmovie.services.listener.OnConnectionChangedListener;

/**
 * Created by yuank on 2017-06-27.
 */

public class ConnectionChangeBroadcastReceiver extends BroadcastReceiver {
    OnConnectionChangedListener mChangedListener;


    public ConnectionChangeBroadcastReceiver(){
        super();
    }

    public ConnectionChangeBroadcastReceiver(OnConnectionChangedListener listener){
        super();
        mChangedListener = listener;
    }



    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
