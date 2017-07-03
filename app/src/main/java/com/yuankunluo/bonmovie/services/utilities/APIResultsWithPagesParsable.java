package com.yuankunluo.bonmovie.services.utilities;

/**
 * Created by yuank on 2017-06-27.
 */

public interface APIResultsWithPagesParsable {

    int getTotalPages();
    void setTotalPages(int total);

    int getCurrentPage();
    void setCurrentPage(int page);

    int getIdInCurrentPage();
    void setIdInCurrentPage(int idInPage);
}
