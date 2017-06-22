package com.yuankunluo.bonmovie;

import android.app.Application;

import com.yuankunluo.bonmovie.dagger2.component.AppComponent;
import com.yuankunluo.bonmovie.dagger2.component.DaggerAppComponent;

/**
 * Created by yuank on 2017-06-21.
 */

public class BonMovieApp extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.create();
        mAppComponent.inject(this);
    }

    public AppComponent getmAppComponent() {
        return mAppComponent;
    }
}
