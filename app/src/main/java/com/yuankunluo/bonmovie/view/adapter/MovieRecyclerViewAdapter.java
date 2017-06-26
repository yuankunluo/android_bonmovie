package com.yuankunluo.bonmovie.view.adapter;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.data.model.TopRatedMovie;
import com.yuankunluo.bonmovie.view.interfaces.BonMovieGridDisplayable;
import com.yuankunluo.bonmovie.view.viewholder.MovieGridViewHolder;

import java.util.List;

/**
 * Created by yuank on 2017-06-25.
 */

public class MovieRecyclerViewAdapter<T> extends RecyclerView.Adapter<MovieGridViewHolder>
        implements Observer<List<T>>{
    private final String TAG = MovieRecyclerViewAdapter.class.getSimpleName();

    private List<T> mMovies;


    @Override
    public void onChanged(@Nullable List<T> ts) {
        mMovies = ts;
        if(ts != null){
            Log.d(TAG, "onChanged: " +Integer.toString(ts.size()));
//            Log.d(TAG, "onChanged: " +ts.toString());
        }
        notifyDataSetChanged();
    }


    @Override
    public MovieGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_item_grid, parent, false);
        MovieGridViewHolder viewHolder = new MovieGridViewHolder(itemView);
        // inject ImageViewHolder
        BonMovieApp.getAppComponent().inject(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MovieGridViewHolder holder, int position) {
        T movie = mMovies.get(position);
        if(movie instanceof BonMovieGridDisplayable){
            holder.setMovieId(((BonMovieGridDisplayable)movie).getGridItemId());
            holder.setImageUrl(((BonMovieGridDisplayable)movie).getImageUrl());
        }
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;
    }
}
