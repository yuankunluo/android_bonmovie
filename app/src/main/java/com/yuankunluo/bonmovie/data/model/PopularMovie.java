package com.yuankunluo.bonmovie.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by yuank on 2017-06-21.
 */

@Entity
public class PopularMovie {

    @PrimaryKey
    private int id;
    private int id_in_page;
    private String poster_path;
    private String overview;
    private String original_title;
    private String title;
    private double vote_average;
    private double popularity;
    private int page;
    @TypeConverters(DateConverter.class)
    private Date last_updated_date;
    private String poster_url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Date getLast_updated_date() {
        return last_updated_date;
    }

    public void setLast_updated_date(Date last_updated_date) {
        this.last_updated_date = last_updated_date;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }


    public int getId_in_page() {
        return id_in_page;
    }

    public void setId_in_page(int id_in_page) {
        this.id_in_page = id_in_page;
    }


    @Override
    public String toString() {
        return "PopularMovie{" +
                "id=" + id +
                ", id_in_page=" + id_in_page +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", original_title='" + original_title + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                ", popularity=" + popularity +
                ", page=" + page +
                ", last_updated_date=" + last_updated_date +
                ", poster_url='" + poster_url + '\'' +
                '}';
    }
}
