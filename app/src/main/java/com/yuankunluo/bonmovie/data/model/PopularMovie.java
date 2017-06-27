package com.yuankunluo.bonmovie.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.yuankunluo.bonmovie.utilities.ResultsWithPosterPathParserable;
import com.yuankunluo.bonmovie.utilities.ResultsWithPagesParserable;
import com.yuankunluo.bonmovie.view.interfaces.BonMovieGridDisplayable;

import java.util.Date;

/**
 * Created by yuank on 2017-06-21.
 */

@Entity
public class PopularMovie implements BonMovieGridDisplayable, ResultsWithPagesParserable, ResultsWithPosterPathParserable {

    @PrimaryKey
    public int id;
    public int id_in_page;
    public String poster_path;
    public int page;
    public String poster_url;


    @Override
    public void setPosterImageUrl(String url) {
        this.poster_url = url;
    }

    @Override
    public String getPosterImageUrl() {
        return this.poster_url;
    }

    @Override
    public int getGridItemId() {
        return this.id;
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    @Override
    public void setCurrentPage(int page) {
        this.page = page;
    }

    @Override
    public int getIdInCurrentPage() {
        return id_in_page;
    }

    @Override
    public void setIdInCurrentPage(int idInPage) {
        this.id_in_page = idInPage;
    }


    @Override
    public String toString() {
        return "PopularMovie{" +
                "id=" + id +
                ", id_in_page=" + id_in_page +
                ", poster_path='" + poster_path + '\'' +
                ", page=" + page +
                ", poster_url='" + poster_url + '\'' +
                '}';
    }
}
