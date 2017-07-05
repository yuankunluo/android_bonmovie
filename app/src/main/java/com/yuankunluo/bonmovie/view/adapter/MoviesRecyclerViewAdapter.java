package com.yuankunluo.bonmovie.view.adapter;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.services.utilities.APIResultsWithMovieIdParsable;
import com.yuankunluo.bonmovie.services.utilities.APIResultsWithPosterPathParsable;
import com.yuankunluo.bonmovie.view.viewholder.MovieGridViewHolder;

import java.util.List;

/**
 * Created by yuank on 2017-06-25.
 */

public class MoviesRecyclerViewAdapter<T> extends RecyclerView.Adapter<MovieGridViewHolder>
        implements Observer<List<T>>{
    private final String TAG = MoviesRecyclerViewAdapter.class.getSimpleName();
    private List<T> mMovies;
    private Context mContext;

    public MoviesRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onChanged(@Nullable List<T> ts) {
        mMovies = ts;
        if(ts != null){
            Log.d(TAG, "onChanged: " +Integer.toString(ts.size()));
        }
        notifyDataSetChanged();
    }


    @Override
    public MovieGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_holder_movie_item_grid, parent, false);
        MovieGridViewHolder viewHolder = new MovieGridViewHolder(itemView, mContext);
        // inject ImageViewHolder
        BonMovieApp.getAppComponent().inject(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MovieGridViewHolder holder, int position) {
        T movie = mMovies.get(position);
        if(movie instanceof APIResultsWithMovieIdParsable){
            holder.setMovieId(((APIResultsWithMovieIdParsable)movie).getMovieId());
        }
        if(movie instanceof APIResultsWithPosterPathParsable){
            holder.setImageUrl(((APIResultsWithPosterPathParsable)movie).getPosterImageUrl());
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
