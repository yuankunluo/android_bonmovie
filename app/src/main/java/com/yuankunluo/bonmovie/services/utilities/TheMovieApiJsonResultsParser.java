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

            for(int i = 0; i < results.length(); i++){
                JSONObject object = results.getJSONObject(i);
                if(APIResultsWithPagesParsable.class.isAssignableFrom(klass)){
                    int totalPages = response.getInt("total_pages");
                    int page = response.getInt("page");
                    object.put("id_in_page", i+1);
                    object.put("total_pages", totalPages);
                    object.put("page", page);
                }
                if(APIResultsWithPosterPathParsable.class.isAssignableFrom(klass)){
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

    public <T> T parseResponseDirectAsObject(JSONObject response, Class<T> klass){
        return mGson.fromJson(response.toString(),klass);
    }



}
