package com.yuankunluo.bonmovie.view.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuankunluo.bonmovie.R;
import com.yuankunluo.bonmovie.view.adapter.PagerAdapter;

public class MainActivity extends AppCompatActivity {
    PagerAdapter mPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), this));

    }
}
