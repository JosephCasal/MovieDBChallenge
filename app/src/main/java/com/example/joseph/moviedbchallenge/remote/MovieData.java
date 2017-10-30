package com.example.joseph.moviedbchallenge.remote;


import com.example.joseph.moviedbchallenge.model.SearchResult;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieData {

    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String API_KEY = "d0634047b31917f1888fc3afc5b3d6bd";

    public static Retrofit create(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;

    }

    public static Observable<SearchResult> search(String search, int page){
        Retrofit retrofit = create();
        MovieService movieService = retrofit.create(MovieService.class);
        return movieService.search(API_KEY, search, page);
    }

//    public static Observable<SearchResult> searchMore(String search, int start) {
//        Retrofit retrofit = create();
//        MovieService movieService = retrofit.create(MovieService.class);
//        return movieService.searchMore(search, Integer.toString(start), "json", API_KEY);
//    }
}
