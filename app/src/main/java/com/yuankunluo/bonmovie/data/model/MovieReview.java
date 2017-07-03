package com.yuankunluo.bonmovie.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.yuankunluo.bonmovie.services.utilities.APIResultsWithMovieIdParsable;
import com.yuankunluo.bonmovie.services.utilities.APIResultsWithPagesParsable;

/**
 * Created by yuank on 2017-06-27.
 */
@Entity
public class MovieReview implements APIResultsWithMovieIdParsable, APIResultsWithPagesParsable{
    public int page;
    public int id_in_page;
    public int total_pages;
    @PrimaryKey
    public int id;
    public String author;
    public String content;
    public String url;
    public int movie_id;

    @Override
    public void setMovieId(int movieId) {
        this.movie_id = movieId;
    }

    @Override
    public int getMovieId() {
        return movie_id;
    }

    @Override
    public int getTotalPages() {
        return total_pages;
    }

    @Override
    public void setTotalPages(int total) {
        this.total_pages = total;
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
}
