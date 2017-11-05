package com.yuankunluo.bonmovie.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.services.BonMovieAction;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-24.
 */

public class MovieGridViewHolder extends RecyclerView.ViewHolder {

    private final String TAG = MovieGridViewHolder.class.getSimpleName();
    private NetworkImageView mImageView;
    private int mMovieId;
    @Inject ImageLoader mImageLoader;


    public MovieGridViewHolder(View itemView, final Context context) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.networkimage_view_poster);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( mMovieId > 0) {
                    Log.i(TAG, Integer.toString(mMovieId));
                    Intent intent = new Intent();
                    intent.setAction(BonMovieAction.ACTION_MOVIE_SELECTED);
                    intent.putExtra("movie_id", mMovieId);
                    // send a click broadcast
                    context.sendBroadcast(intent);
                } else {
                    Log.d(TAG, "No movie id");
                }
            }
        });
    }

    public void setMovieId(int mMovieId) {
        Log.d(TAG, "setMovieId: " + mMovieId);
        this.mMovieId = mMovieId;
    }

    public void setImageUrl(String url){
        if (null != url) {
            Log.d(TAG, "setImageUrl: " + url);
            mImageLoader.get(url, ImageLoader.getImageListener(mImageView, R.drawable.ic_image_black_48px, R.drawable.ic_broken_image_black_48px));
            mImageView.setImageUrl(url, mImageLoader);
        }
    }

}
