package com.yuankunluo.bonmovie.utilities;

/**
 * Created by yuank on 2017-06-27.
 */

public interface ResultsWithPagesParserable {

    int getCurrentPage();
    void setCurrentPage(int page);

    int getIdInCurrentPage();
    void setIdInCurrentPage(int idInPage);
}
