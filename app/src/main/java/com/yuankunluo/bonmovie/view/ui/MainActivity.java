package com.yuankunluo.bonmovie.view.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.receiver.MovieSelectedBroadcastReceiver;
import com.yuankunluo.bonmovie.view.listener.OnMovieSelectedListener;

import javax.inject.Inject;

public class MainActivity extends FragmentActivity implements OnMovieSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieSelectedBroadcastReceiver mSelectedReceiver;
    private int mSelectedMovieId;
    private boolean mDualPanel;
    private View mMoviesListPanel;
    private View mMovieDetailPanel;
    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        BonMovieApp.getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        mDualPanel = mMovieDetailPanel != null;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("dualpanel", mDualPanel);
        editor.apply();
        Log.d(TAG, "dualpanel:" + Boolean.toString(mDualPanel));
        // insert fragments
        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null){
                return;
            }
            MoviePaggerFragment paggerFragment = new MoviePaggerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, paggerFragment).commit();
        }


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

    private void showMovieDetail(){
        Log.d(TAG,"showMovieDetail: " + mSelectedMovieId );
        if(mDualPanel){
            // Tablet
        } else {
            // Phone
            Log.d(TAG, "showMovieDetail: start new activity");
            Intent intent = new Intent();
            intent.setClass(this,MovieDetailActivity.class);
            intent.putExtra("movie_id", mSelectedMovieId);
            startActivity(intent);
        }
    }
}
