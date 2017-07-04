package com.yuankunluo.bonmovie.view.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.MovieDetail;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    MovieDetailFragment mMovieDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        int movieIdExtra = intent.getIntExtra("movie_id",0);
        Log.d(TAG,"onCreate : " + movieIdExtra );
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mMovieDetailFragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movieIdExtra);
        mMovieDetailFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, mMovieDetailFragment);
        fragmentTransaction.commit();
    }


}
