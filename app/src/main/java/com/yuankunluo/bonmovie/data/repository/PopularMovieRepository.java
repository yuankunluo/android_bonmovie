package com.yuankunluo.bonmovie.data.repository;

import android.arch.lifecycle.LiveData;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.yuankunluo.bonmovie.data.dao.PopularMovieDao;
import com.yuankunluo.bonmovie.data.model.PopularMovie;
import com.yuankunluo.bonmovie.services.tools.TheMovieApiUriBuilder;
import com.yuankunluo.bonmovie.services.webservice.VolleyWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yuank on 2017-06-22.
 */
@Singleton
public class PopularMovieRepository {

    public final int TYPE_POPULAR_MOVIE = 1;
    public final int TYPE_TOP_RATED_MOVIE = 2;

    final static String TAG = PopularMovieRepository.class.getSimpleName();

    private VolleyWebService mVolleyWebService;
    private PopularMovieDao mPopularMovieDao;
    private Executor mExecutor;
    private TheMovieApiUriBuilder mApiUriBuilder;
    private Gson mGson;


    @Inject
    public PopularMovieRepository(VolleyWebService webService,
                                  PopularMovieDao movieDao,
                                  ExecutorService executorService,
                                  TheMovieApiUriBuilder movieApiUriBuilder,
                                  Gson gson){
        mVolleyWebService = webService;
        mPopularMovieDao = movieDao;
        mExecutor = executorService;
        mApiUriBuilder = movieApiUriBuilder;
        mGson = gson;
    }


    public LiveData<List<PopularMovie>> getPopularMovies(){
        return mPopularMovieDao.getAllMovies();
    }

    /**
     *
     * @param type 1 for popular, 2 for top rated
     * @param page
     */
    public void refreshMoviesAtPage(int type, final int page){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                boolean movieExists = mPopularMovieDao.hasMoviesAtPage(page);
                Log.i(TAG, Boolean.toString(movieExists));
                if(!movieExists){
                    String url = mApiUriBuilder.getPopularMovieUrlForPage(page).toString();
                    Log.i(TAG, "URL + :" +  url);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url
                            ,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
//                                    Log.i(TAG, response.toString());
                                    final PopularMovie[] newMovies = parsePopularMovies(response);
                                    mExecutor.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            mPopularMovieDao.insertPopularMovies(newMovies);
                                        }
                                    });
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, error.toString());
                                }
                            }
                    );
                    mVolleyWebService.addToRequestQueue(jsonObjectRequest);
                }
            }
        });
    }


    PopularMovie[] parsePopularMovies(JSONObject response){
        try {
            int page = response.getInt("page");
            JSONArray results = response.getJSONArray("results");
            PopularMovie[] movies = mGson.fromJson(results.toString(), PopularMovie[].class);
            for(PopularMovie m : movies){
                // set page
                m.setPage(page);
            }
            return movies;
        } catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG, "parseJsonResponse error");
        }
        return null;
    }

}
