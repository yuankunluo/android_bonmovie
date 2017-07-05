package com.yuankunluo.bonmovie.view.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.MovieVideo;

/**
 * Created by yuank on 2017-07-04.
 */

public class MovieVideoItemView extends ConstraintLayout {


    private static final String TAG = MovieVideoItemView.class.getSimpleName();
    private View mRootView;
    private TextView mTextViewName;
    private TextView mTextViewType;
    private ImageView mButton;
    private MovieVideo mMovieVideo;


    public MovieVideoItemView(Context context){
        super(context);
        init(context);
    }

    public MovieVideoItemView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }



    private void init(Context context){
        mRootView = inflate(context, R.layout.view_holder_movie_video_item, this);
        mTextViewName = mRootView.findViewById(R.id.tv_video_name);
        mTextViewType = mRootView.findViewById(R.id.tv_video_type);
        mButton = mRootView.findViewById(R.id.bt_play_video);
        mRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMovieVideo != null) {
                    Log.d(TAG, "onClick" + mMovieVideo.key);
                }
            }
        });
    }

    public void setMovieVideo(final MovieVideo video){
        mMovieVideo = video;
        mTextViewName.setText(video.name);
        mTextViewType.setText(video.type);
    }

}
