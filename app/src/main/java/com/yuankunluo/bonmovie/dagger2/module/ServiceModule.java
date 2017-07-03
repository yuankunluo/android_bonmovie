package com.yuankunluo.bonmovie.dagger2.module;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuank on 2017-06-21.
 */

@Module
public class ServiceModule {

    @Provides
    @Singleton
    VolleyWebService provideVolleyWebService(@Named("appcontext")Context context){
        return new VolleyWebService(context);
    }

    @Provides
    ImageLoader provideVolleyImageLoader(VolleyWebService volleyWebService){
        return volleyWebService.getmImageLoader();
    }
    

    @Provides
    @Singleton
    FirebaseJobDispatcher provideFireBaseJobDispatcher(@Named("appcontext") Context context){
        return new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }


}
