package com.kukroid.smartshows.network;

import android.arch.lifecycle.LiveData;

import com.kukroid.smartshows._Model.Example;
import com.kukroid.smartshows._Model.MovieData;
import com.kukroid.smartshows._Model.Reviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API {

    @GET("/3/movie/now_playing?language=en-US")
    Call<MovieData> getNowPlayingMovies(
            @Query("api_key") String api_key,
            @Query("page")int page
    );

    //https://api.themoviedb.org/3/movie/top_rated?api_key=35fa2d19cbb3f743376ab63108094ef3&language=en-US&page=1

    @GET("/3/movie/top_rated?language=en-US")
    Call<MovieData> getTopRatedMovies(
            @Query("api_key") String api_key,
            @Query("page")int page
    );

    //https://api.themoviedb.org/3/movie/popular?api_key=35fa2d19cbb3f743376ab63108094ef3&language=en-US&page=1
    @GET("/3/movie/popular?language=en-US")
    Call<MovieData> getpopularMovies(
            @Query("api_key") String api_key,
            @Query("page")int page
    );

    @GET
    Call<Example> getVideos(@Url String url);

    @GET
    Call<Reviews> getReviews(@Url String url);
}
