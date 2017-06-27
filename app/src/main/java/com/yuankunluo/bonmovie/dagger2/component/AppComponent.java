package com.yuankunluo.bonmovie.dagger2.component;

import android.content.SharedPreferences;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.google.gson.Gson;
import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.dagger2.module.AppModule;
import com.yuankunluo.bonmovie.dagger2.module.DataBaseModule;
import com.yuankunluo.bonmovie.dagger2.module.RepositoryModule;
import com.yuankunluo.bonmovie.dagger2.module.ServiceModule;
import com.yuankunluo.bonmovie.dagger2.module.SharedPreferencesModule;
import com.yuankunluo.bonmovie.dagger2.module.UtiltyModule;
import com.yuankunluo.bonmovie.data.database.BonMovieDatabase;
import com.yuankunluo.bonmovie.data.repository.MovieShortRepository;
import com.yuankunluo.bonmovie.services.jobs.FetchPopularMoviesFromApiJobService;
import com.yuankunluo.bonmovie.services.jobs.FetchTopRatedMoviesFromApiJobService;
import com.yuankunluo.bonmovie.utilities.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;
import com.yuankunluo.bonmovie.view.viewholder.MovieGridViewHolder;
import com.yuankunluo.bonmovie.viewmodel.PopularMovieViewModel;
import com.yuankunluo.bonmovie.viewmodel.TopRatedMovieViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yuank on 2017-06-21.
 */
@Singleton
@Component(modules = {AppModule.class, DataBaseModule.class, UtiltyModule.class,
        RepositoryModule.class, ServiceModule.class, SharedPreferencesModule.class})
public interface AppComponent {
    void inject(BonMovieApp app);
    void inject(PopularMovieViewModel viewModel);
    void inject(TopRatedMovieViewModel viewModel);
    void inject(FetchPopularMoviesFromApiJobService jobService);
    void inject(FetchTopRatedMoviesFromApiJobService jobService);
    void inject(MovieGridViewHolder holder);
    MovieShortRepository getMovieRepository();
    BonMovieDatabase getDatabase();
    SharedPreferences getSharedPreference();
    VolleyWebService getWebService();
    TheMovieApiUriBuilder getApiUriBuilder();
    FirebaseJobDispatcher getFirebaseJobDispatcher();
    Gson getGson();

}
