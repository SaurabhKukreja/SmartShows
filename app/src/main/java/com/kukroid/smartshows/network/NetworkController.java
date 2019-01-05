package com.kukroid.smartshows.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkController {
    private static Retrofit retrofit;
    private static String BASE_URL = "https://api.themoviedb.org/";
    public static String BASE_IMAGE_URL_BACKDROP = "https://image.tmdb.org/t/p/original/";
    public static String BASE_IMAGE_URL_POSTER = "https://image.tmdb.org/t/p/original/";


    public static Retrofit getNetworkInstance(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
