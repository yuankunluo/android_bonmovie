package com.yuankunluo.bonmovie;

import android.app.Application;
import android.util.Log;

import com.yuankunluo.bonmovie.dagger2.component.AppComponent;
import com.yuankunluo.bonmovie.dagger2.component.DaggerAppComponent;
import com.yuankunluo.bonmovie.dagger2.module.AppModule;
import com.yuankunluo.bonmovie.dagger2.module.DataBaseModule;
import com.yuankunluo.bonmovie.dagger2.module.RepositoryModule;
import com.yuankunluo.bonmovie.dagger2.module.ServiceModule;
import com.yuankunluo.bonmovie.dagger2.module.SharedPreferencesModule;

/**
 * Created by yuank on 2017-06-21.
 */

public class BonMovieApp extends Application {
    private static AppComponent mAppComponent;
    private final String TAG = BonMovieApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataBaseModule(new DataBaseModule())
                .serviceModule(new ServiceModule())
                .sharedPreferencesModule(new SharedPreferencesModule())
                .repositoryModule(new RepositoryModule())
                .build();
        mAppComponent.inject(this);

    }


    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
