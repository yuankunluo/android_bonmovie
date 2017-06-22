package com.yuankunluo.bonmovie.dagger2.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-21.
 */

@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application){
        mApplication = application;
    }

    @Provides
    @Singleton
    @Named("appcontext")
    Context provideContext(){
        return mApplication;
    }



}
