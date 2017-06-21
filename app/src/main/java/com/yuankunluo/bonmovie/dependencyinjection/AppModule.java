package com.yuankunluo.bonmovie.dependencyinjection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-21.
 */

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application){
        mApplication = application;
    }


    @Provides
    @Singleton
    Application providesApplication(){
        return mApplication;
    }
}
