package com.yuankunluo.bonmovie.dagger2.component;

import com.yuankunluo.bonmovie.BonMovieApp;
import com.yuankunluo.bonmovie.dagger2.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yuank on 2017-06-21.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(BonMovieApp app);
}
