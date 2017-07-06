package com.yuankunluo.bonmovie.services.utilities;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-26.
 */

public class TheMovieApiJsonResultsParser {

    final static String TAG = TheMovieApiJsonResultsParser.class.getSimpleName();

    Gson mGson;
    TheMovieApiUriBuilder mUriBuilder;

    @Inject
    public TheMovieApiJsonResultsParser(Gson gson, TheMovieApiUriBuilder uriBuilder){
        mGson = gson;
        mUriBuilder = uriBuilder;
    }



    public <T> T[] parseResponseResultsToArray(JSONObject response, Class<T> klass, Class<T[]> klassArray){
        try{
            JSONArray results = response.getJSONArray("results");
            int movie_id = 0;
            if(response.has("id")){
                movie_id = response.getInt("id");
            }

            for(int i = 0; i < results.length(); i++){
                JSONObject object = results.getJSONObject(i);
                if(APIResultsWithPagesParsable.class.isAssignableFrom(klass)){
                    int totalPages = response.getInt("total_pages");
                    int page = response.getInt("page");
                    object.put("page", page);
                }
                if(APIResultsWithPosterPathParsable.class.isAssignableFrom(klass)){
                    String poster_url = mUriBuilder.getPosterBaseUri() + object.getString("poster_path");
                    object.put("poster_url", poster_url);
                }
                if(APIResultsWithMovieIdParsable.class.isAssignableFrom(klass)){
                    object.put("movie_id", movie_id);
                }
            }
            return  mGson.fromJson(results.toString(), klassArray);

        } catch (JSONException e){
            Log.e(TAG, "json parse exeception: " + response.toString());
            e.printStackTrace();
        }
        return null;
    }

    public <T> T parseResponseDirectAsObject(JSONObject response, Class<T> klass){
        try {
            if (APIResultsWithPosterPathParsable.class.isAssignableFrom(klass)) {
                String poster_url = mUriBuilder.getPosterBaseUri() + response.getString("poster_path");
                response.put("poster_url", poster_url);
            }
        } catch (JSONException e){
            Log.e(TAG, "parser error");
            e.printStackTrace();
        }
        return mGson.fromJson(response.toString(),klass);
    }









}
