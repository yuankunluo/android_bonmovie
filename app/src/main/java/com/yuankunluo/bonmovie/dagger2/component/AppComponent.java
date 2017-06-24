package com.yuankunluo.bonmovie.dagger2.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.dagger2.module.AppModule;
import com.yuankunluo.bonmovie.dagger2.module.DataBaseModule;
import com.yuankunluo.bonmovie.dagger2.module.RepositoryModule;
import com.yuankunluo.bonmovie.dagger2.module.ServiceModule;
import com.yuankunluo.bonmovie.dagger2.module.SharedPreferencesModule;
import com.yuankunluo.bonmovie.data.database.BonMovieDatabase;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.data.repository.BonMovieRepository;
import com.yuankunluo.bonmovie.services.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.VolleyWebService;
import com.yuankunluo.bonmovie.viewmodel.PopularMovieViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yuank on 2017-06-21.
 */
@Singleton
@Component(modules = {AppModule.class, DataBaseModule.class,
        RepositoryModule.class, ServiceModule.class, SharedPreferencesModule.class})
public interface AppComponent {
    void inject(BonMovieApp app);
    void inject(PopularMovieViewModel viewModel);
    BonMovieRepository getMovieRepository();
    BonMovieDatabase getDatabase();
    SharedPreferences getSharedPreference();
    VolleyWebService getWebService();
    TheMovieApiUriBuilder getApiUriBuilder();
    Gson getGson();

}
