package com.yuankunluo.bonmovie.services;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yuank on 2017-06-21.
 */

public class VolleyWebService {
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Inject
    public VolleyWebService(@Named("appcontext") Context context){
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache(){
                    private final LruCache<String, Bitmap> cache =
                            new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url,bitmap);
                    }
                });
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getmImageLoader(){
        return mImageLoader;
    }
}
