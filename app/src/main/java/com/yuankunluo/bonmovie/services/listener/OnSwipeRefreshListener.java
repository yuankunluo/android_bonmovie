package com.yuankunluo.bonmovie.services.listener;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by yuank on 2017-06-26.
 */

public interface OnSwipeRefreshListener extends SwipeRefreshLayout.OnRefreshListener {
    public void onRefreshOver();
}
