package com.yuankunluo.bonmovie.view.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.receiver.MovieSelectedBroadcastReceiver;
import com.yuankunluo.bonmovie.view.listener.OnMovieSelectedListener;

import javax.inject.Inject;

public class MainActivity extends FragmentActivity implements OnMovieSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieSelectedBroadcastReceiver mSelectedReceiver;
    private int mSelectedMovieId;
    private boolean mDualPanel;
    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BonMovieApp.getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        // insert fragments
        if(findViewById(R.id.fragment_pagger) != null){
            if(savedInstanceState != null){
                return;
            }
            MoviePaggerFragment paggerFragment = new MoviePaggerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pagger, paggerFragment).commit();
        }
        mDualPanel = false;
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        // Register MovieSelected Receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BonMovieAction.ACTION_MOVIE_SELECTED);
        mSelectedReceiver = new MovieSelectedBroadcastReceiver(this);
        registerReceiver(mSelectedReceiver, intentFilter);
        if(findViewById(R.id.fragment_detail) != null){
            mDualPanel = true;
        }
        Log.d(TAG, "onStart DualPanel " + mDualPanel);
    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        unregisterReceiver(mSelectedReceiver);
    }

    @Override
    public void selectMovie(int movieId) {
        Log.d(TAG,"selectMovie: " + movieId);
        mSelectedMovieId = movieId;
        showMovieDetail();
    }

    @Override
    public int getSelectedMovieId() {
        return mSelectedMovieId;
    }

    /**
     *
     */
    private void showMovieDetail(){

        if(mDualPanel){
            // Tablet
            Bundle bundle = new Bundle();
            bundle.putInt("movie_id", mSelectedMovieId);
            MovieDetailFragment detailFragment = new MovieDetailFragment();
            detailFragment.setArguments(bundle);
            if(getSupportFragmentManager().findFragmentByTag("current_detail_fragment") != null){
                getSupportFragmentManager().
                        beginTransaction().replace(R.id.fragment_detail, detailFragment,"current_detail_fragment")
                        .commit();
            } else {
                getSupportFragmentManager().
                        beginTransaction().add(R.id.fragment_detail, detailFragment,"current_detail_fragment")
                        .commit();
            }

            Log.d(TAG,"showMovieDetail dualpanel: " + mSelectedMovieId );
        } else {
            // Phone
            Intent intent = new Intent();
            intent.setClass(this,MovieDetailActivity.class);
            intent.putExtra("movie_id", mSelectedMovieId);
            startActivity(intent);
            Log.d(TAG, "showMovieDetail: start new activity");
        }
    }
}
