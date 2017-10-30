package com.example.joseph.moviedbchallenge.remote;

import com.example.joseph.moviedbchallenge.model.SearchResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by joseph on 10/15/17.
 */

public interface MovieService {

    @GET("/3/search/movie")
    Observable<SearchResult> search(@Query("api_key") String apikey, @Query("query") String query, @Query("page") int page);

//    @GET("/v1/search")
//    Observable<SearchResult> searchMore(@Query("query") String query, @Query("start") String start, @Query("format") String format, @Query("apikey") String apikey);
}
