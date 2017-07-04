package com.yuankunluo.bonmovie.view.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity  implements OnMovieSelectedListener{
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
        mMoviesListPanel = findViewById(R.id.movies_list_panel);
        mMovieDetailPanel = findViewById(R.id.movie_detail_panel);
        mDualPanel = mMovieDetailPanel != null;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("dualpanel", mDualPanel);
        editor.commit();
        Log.d(TAG, "dualpanel:" + Boolean.toString(mDualPanel));
        // insert fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MoviePaggerFragment paggerFragment = new MoviePaggerFragment();
        fragmentTransaction.add(R.id.movies_list_panel, paggerFragment);
        fragmentTransaction.commit();

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
