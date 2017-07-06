package com.yuankunluo.bonmovie.view.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.data.model.MovieReview;

/**
 * Created by yuank on 2017-07-04.
 */

public class MovieReviewItemView extends ConstraintLayout {


    private static final String TAG = MovieReviewItemView.class.getSimpleName();
    private View mRootView;
    private TextView mTextViewAuthor;
    private TextView mTextViewContent;
    private Context mContext;


    public MovieReviewItemView(Context context){
        super(context);
        init(context);
        mContext = context;
    }

    public MovieReviewItemView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
        mContext = context;
    }



    private void init(Context context){
        mRootView = inflate(context, R.layout.view_movie_review_item, this);
        mTextViewAuthor = mRootView.findViewById(R.id.tv_review_author);
        mTextViewContent = mRootView.findViewById(R.id.tv_review_content);

    }

    public void setMovieReview(final MovieReview review){
        mTextViewAuthor.setText(review.author);
        mTextViewContent.setText(review.content);
    }

}
