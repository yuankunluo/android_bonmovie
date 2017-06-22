package com.yuankunluo.bonmovie.dagger2.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-22.
 */
@Module
public class SharedPreferencesModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@Named("appcontext")Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
