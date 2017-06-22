package com.yuankunluo.bonmovie.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.data.repository.BonMovieRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-22.
 */

public class PopularMovieViewModel extends ViewModel {

    private MutableLiveData<List<PopularMovie>> mPopularMovies;
    @Inject BonMovieRepository mRepository;


    public void init(){
        if(mPopularMovies != null){
            return;
        }
        mPopularMovies = new MutableLiveData<>();
    }

    public MutableLiveData<List<PopularMovie>> getPopularMovies(){
        mRepository.getMoviesAtPage(1);
        return getPopularMovies();
    }

}
