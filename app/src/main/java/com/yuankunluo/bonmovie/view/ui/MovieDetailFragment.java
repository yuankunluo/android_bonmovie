package com.yuankunluo.bonmovie.view.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.MovieDetail;
import com.yuankunluo.bonmovie.data.model.MovieVideo;
import com.yuankunluo.bonmovie.databinding.FragmentMovieDetailBinding;
import com.yuankunluo.bonmovie.data.provider.UserFavoriteContract;
import com.yuankunluo.bonmovie.view.widget.MovieVideoItemView;
import com.yuankunluo.bonmovie.viewmodel.MovieDetailViewModel;
import com.yuankunluo.bonmovie.viewmodel.UserFavoriteMovieViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-27.
 */

public class MovieDetailFragment extends LifecycleFragment {
    final static String TAG = MovieDetailFragment.class.getSimpleName();

    private MovieDetailViewModel mViewModel;
    private NetworkImageView mImageView;
    private FragmentMovieDetailBinding mBinding;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBarMovieVides;
    private LinearLayout mVideosContainer;
    private MovieReviewsFragment movieReviewsFragment;
    private Button mFavoriteButtonAdd;
    private Button mFavoriteButtonRemove;
    private MovieDetail mMovieDetail;
    private UserFavoriteMovieObserver mObserver;

    private int mMovieId;
    @Inject
    ImageLoader mImageLoader;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BonMovieApp.getAppComponent().inject(this);
        mMovieId = getArguments().getInt("movie_id");
        movieReviewsFragment = new MovieReviewsFragment();
        Bundle reviewBundle = new Bundle();
        reviewBundle.putInt("movie_id", mMovieId);
        movieReviewsFragment.setArguments(reviewBundle);
        mObserver = new UserFavoriteMovieObserver(new Handler());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        BonMovieApp.getAppComponent().inject(mViewModel);
        mViewModel.init(mMovieId);
        mViewModel.getMovieDetail().observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(@Nullable MovieDetail movieDetail) {
                if (movieDetail != null) {
                    mMovieDetail = movieDetail;
                    Log.d(TAG, "onActivityCreated: onChanged movieDetails");
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mImageView.setImageUrl(movieDetail.getPosterImageUrl(), mImageLoader);
                    mBinding.setMovieDetail(movieDetail);
                    // Initialize Buttons
                    mObserver.onChange(true,
                            UserFavoriteContract.UserFavEntry.buildContentUriWithMovieId(mMovieId));
                }
            }
        });
        mViewModel.getMovieVideos().observe(this, new Observer<List<MovieVideo>>() {
            @Override
            public void onChanged(@Nullable List<MovieVideo> movieVideos) {
                if (movieVideos != null) {
                    Log.d(TAG, "onActivityCreated: onChanged movieVideos " + movieVideos.size());
                    if(movieVideos.size()!=0){
                        mProgressBarMovieVides.setVisibility(View.INVISIBLE);
                        for (MovieVideo v : movieVideos) {
                            MovieVideoItemView videoItemView = new MovieVideoItemView(getContext());
                            videoItemView.setMovieVideo(v);
                            mVideosContainer.addView(videoItemView);
                        }
                    }
                }
            }
        });

    }

    class UserFavoriteMovieObserver extends ContentObserver{


        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Log.d(TAG, "UserFavoriteMovieObserver#onChange:" + selfChange + " " + uri);
            Cursor cursor = getContext().getContentResolver().query(
                    uri,null,null,null,null
            );
            if (cursor.getCount()>0) {
                // already favorite
                mFavoriteButtonAdd.setVisibility(View.INVISIBLE);
                mFavoriteButtonAdd.setEnabled(false);
                mFavoriteButtonRemove.setVisibility(View.VISIBLE);
                mFavoriteButtonRemove.setEnabled(true);
            } else {
                mFavoriteButtonAdd.setVisibility(View.VISIBLE);
                mFavoriteButtonAdd.setEnabled(true);
                mFavoriteButtonRemove.setVisibility(View.INVISIBLE);
                mFavoriteButtonRemove.setEnabled(false);
            }
            cursor.close();

        }

        public UserFavoriteMovieObserver(Handler handler) {
            super(handler);
        }
     }




    @Override
    public void onResume() {
        super.onResume();
        getContext().getContentResolver().registerContentObserver(
                UserFavoriteContract.UserFavEntry.buildContentUriWithMovieId(mMovieId),
                true,
                mObserver
        );
    }


    @Override
    public void onStop() {
        super.onStop();
        getContext().getContentResolver().unregisterContentObserver(
                mObserver
        );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // initial view elements
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        View root = mBinding.getRoot();
        mImageView = root.findViewById(R.id.imv_movie_detail_header_poster);
        // Favorite Button ADD
        mFavoriteButtonAdd = root.findViewById(R.id.bt_favorite_add);
        mFavoriteButtonAdd.setEnabled(true);
        mFavoriteButtonAdd.setVisibility(View.INVISIBLE);
        mFavoriteButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMovieDetail != null) {
                    Log.d(TAG, " ButtonAdd Pressed " + mMovieId );
                    ContentValues values = new ContentValues();
                    values.put(UserFavoriteContract.UserFavEntry.COLUMN_MOVIE_ID, mMovieId);
                    values.put(UserFavoriteContract.UserFavEntry.COLUMN_INSERT_TIMESTAMP, System.currentTimeMillis());
                    values.put(UserFavoriteContract.UserFavEntry.COLUMN_POSTER_URL, mMovieDetail.getPosterImageUrl());
                    Log.d(TAG, "values:" + values.toString());
                    getContext().getContentResolver().insert(
                            UserFavoriteContract.UserFavEntry.CONTENT_URI,
                            values
                    );
                }
            }
        });
        mFavoriteButtonRemove = root.findViewById(R.id.bt_favorite_remove);
        mFavoriteButtonRemove.setEnabled(true);
        mFavoriteButtonRemove.setVisibility(View.INVISIBLE);
        mFavoriteButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ButtonRemove Pressed" + mMovieId);
                getContext().getContentResolver().delete(
                        UserFavoriteContract.UserFavEntry.buildContentUriWithMovieId(mMovieId),
                        null,null
                );
            }
        });
        mVideosContainer = root.findViewById(R.id.video_container);
        mProgressBar = root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBarMovieVides = root.findViewById(R.id.progressBar_movie_videos);
        mProgressBarMovieVides.setVisibility(View.VISIBLE);
        getFragmentManager().beginTransaction().add(
                R.id.reviews_container, movieReviewsFragment,"reviews-fragment"
        ).commit();
        return root;
    }


}
