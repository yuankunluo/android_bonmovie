package com.yuankunluo.bonmovie.view.ui;

import android.app.LoaderManager;
import android.arch.lifecycle.LifecycleFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.provider.UserFavoriteContract;
import com.yuankunluo.bonmovie.view.adapter.MoviesRecyclerViewAdapter;
import com.yuankunluo.bonmovie.view.adapter.UserFavoriteCursorRecyclerViewAdapter;


/**
 * Created by yuank on 2017-06-22.
 */

public class UserFavoriteMovieFragment extends LifecycleFragment
implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>
{
    final String TAG = UserFavoriteMovieFragment.class.getSimpleName();
    final int USER_FAVE_LOADER = 10000;
    private RecyclerView mRecyclerView;
    private UserFavoriteCursorRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                UserFavoriteContract.UserFavEntry.CONTENT_URI, null, null, null,
                UserFavoriteContract.UserFavEntry.COLUMN_INSERT_TIMESTAMP + " DESC"
                );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished " + data.getCount() );
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new UserFavoriteCursorRecyclerViewAdapter(getContext(), null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialize loader
        getLoaderManager().initLoader(USER_FAVE_LOADER, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View root =  inflater.inflate(R.layout.fragment_user_favorite_movies, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview_movies);
        // Set Adapter to RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        // Set layout manager
        int columnNumber = getResources().getInteger(R.integer.grid_column);
        mLayoutManager = new GridLayoutManager(getContext(),columnNumber, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
