package com.yuankunluo.bonmovie.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.provider.UserFavoriteContract;
import com.yuankunluo.bonmovie.view.viewholder.MovieGridViewHolder;

/**
 * Created by yuank on 2017-11-05.
 */

public class UserFavoriteCursorRecyclerViewAdapter
    extends  RecyclerView.Adapter<MovieGridViewHolder>{

    final static String TAG = UserFavoriteCursorRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;
    private boolean mDataValid;
    private int mRowIdColumn;

    public class NotefyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mDataValid = false;
            notifyDataSetChanged();
        }
    }

    private DataSetObserver mDataSetObserver;

    public UserFavoriteCursorRecyclerViewAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
        mDataValid = cursor != null;
        // -1 if no data
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotefyingDataSetObserver();
        if (mCursor != null){
            // register a Observer to cursor
            mCursor.registerDataSetObserver(mDataSetObserver);
        }
    }

    @Override
    public MovieGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_movie_item_grid, parent, false);
        MovieGridViewHolder vh = new MovieGridViewHolder(itemView, mContext);
        // inject ImageViewHolder
        BonMovieApp.getAppComponent().inject(vh);
        return vh;
    }

    public Cursor getCursor(){
        return mCursor;
    }

    @Override
    public int getItemCount() {
        if (mDataValid && mCursor != null){
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)){
            return mCursor.getLong(mRowIdColumn);
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public void onBindViewHolder(MovieGridViewHolder holder, int position) {
        if (!mDataValid){
            throw new IllegalStateException("This should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)){
            throw new IllegalStateException("Couldn't move curosr to position " + position);
        }

        int movieId = mCursor.getInt(mCursor.getColumnIndex(UserFavoriteContract.UserFavEntry.COLUMN_MOVIE_ID));
        String posterUrl = mCursor.getString(mCursor.getColumnIndex(UserFavoriteContract.UserFavEntry.COLUMN_POSTER_URL));
        Log.d(TAG, "onBindViewHolder " + movieId + " " + posterUrl );
        holder.setMovieId(movieId);
        holder.setImageUrl(posterUrl);
    }




    public Cursor swapCursor(Cursor newCursor){
        if (newCursor == mCursor){
            return null;
        }
        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (mDataSetObserver != null) {
                mCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIdColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
        }
        return oldCursor;
    }

    public void changeCursor(Cursor cursor){
        Cursor old = swapCursor(cursor);
        if (old != null){
            old.close();
        }
    }

}
