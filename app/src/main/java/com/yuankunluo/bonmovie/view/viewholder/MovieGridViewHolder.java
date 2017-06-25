package com.yuankunluo.bonmovie.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuankunluo.bonmovie.R;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-24.
 */

public class MovieGridViewHolder extends RecyclerView.ViewHolder {

    private final String TAG = MovieGridViewHolder.class.getSimpleName();
    private NetworkImageView mImageView;

    @Inject ImageLoader mImageLoader;

    public MovieGridViewHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.networkimage_view_poster);
    }

    public void setImageUrl(String url){
        mImageView.setImageUrl(url, mImageLoader);
    }
}
