package com.yuankunluo.bonmovie.utilities;

import android.util.Log;

import com.google.gson.Gson;
import com.yuankunluo.bonmovie.data.model.PopularMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by yuank on 2017-06-26.
 */

public class TheMovieApiJsonParser {

    final static String TAG = TheMovieApiJsonParser.class.getSimpleName();

    Gson mGson;
    TheMovieApiUriBuilder mUriBuilder;

    @Inject
    public TheMovieApiJsonParser(Gson gson, TheMovieApiUriBuilder uriBuilder){
        mGson = gson;
        mUriBuilder = uriBuilder;
    }

    public <T> T[] parseResponseResultsToArray(JSONObject response, Class<T> klass, Class<T[]> klassArray){
        try{
            int page = response.getInt("page");
            JSONArray results = response.getJSONArray("results");

            for(int i = 0; i < results.length(); i++){
                JSONObject object = results.getJSONObject(i);
                if(ResultsWithPagesParserable.class.isAssignableFrom(klass)){
                    object.put("id_in_page", i+1);
                    object.put("page", page);
                }
                if(ResultsWithPosterPathParserable.class.isAssignableFrom(klass)){
                    String poster_url = mUriBuilder.getPosterBaseUri() + object.getString("poster_path");
                    object.put("poster_url", poster_url);
                }

            }

            return  mGson.fromJson(results.toString(), klassArray);

        } catch (JSONException e){
            Log.e(TAG, "json parse exeception: " + response.toString());
            e.printStackTrace();
        }
        return null;
    }
}
