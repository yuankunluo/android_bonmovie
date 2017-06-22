package com.yuankunluo.bonmovie.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.yuankunluo.bonmovie.data.model.PopularMovie;

import java.util.List;

/**
 * Created by yuank on 2017-06-22.
 */

public class PopularMovieViewModel extends ViewModel {

    private MutableLiveData<List<PopularMovie>> mPopularMovies;


    public void init(){
    }

    public MutableLiveData<List<PopularMovie>> getPopularMovies(){
        return getPopularMovies();
    }

}
