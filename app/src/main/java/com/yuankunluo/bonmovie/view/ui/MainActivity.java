package com.yuankunluo.bonmovie.view.ui;

import android.content.IntentFilter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.services.BonMovieAction;
import com.yuankunluo.bonmovie.services.receiver.MovieSelectedBroadcastReceiver;
import com.yuankunluo.bonmovie.view.adapter.PagerAdapter;
import com.yuankunluo.bonmovie.view.listener.OnMovieSelectedListener;

public class MainActivity extends AppCompatActivity  implements OnMovieSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    PagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    MovieSelectedBroadcastReceiver mSelectedReceiver;
    private int mSelectedMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), this));

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
    }

    @Override
    public int getSelectedMovieId() {
        return 0;
    }
}
