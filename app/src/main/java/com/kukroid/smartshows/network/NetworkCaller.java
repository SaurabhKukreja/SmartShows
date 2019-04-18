package com.kukroid.smartshows.network;



import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.kukroid.smartshows.MyApplication;
import com.kukroid.smartshows.Util.Utils;
import com.kukroid.smartshows._Model.Movies;
import com.kukroid.smartshows.contract.HomeFragmentContract;
import com.kukroid.smartshows._Model.MovieData;
import com.kukroid.smartshows.database.MoviesDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCaller  {

    private API api;
    private HomeFragmentContract.NetworkInterface networkInterface;

    public NetworkCaller(HomeFragmentContract.NetworkInterface networkInterface) {

        this.networkInterface = networkInterface;
        this.api = NetworkController.getNetworkInstance().create(API.class);
    }

    public void getNowPlayingMovies(int page){
        Call<MovieData> movieList = api.getNowPlayingMovies(Utils.api_key,page);
        movieList.enqueue(movieCallbacks);
    }

    Callback<MovieData> movieCallbacks = new Callback<MovieData>() {
        @Override
        public void onResponse(Call<MovieData> call, Response<MovieData> response) {
            Utils.log("Request: "+call.request().url());
            Utils.log("Response: "+call.request().body());
          networkInterface.onResponseSuccess(response.body().getMovies());
        }

        @Override
        public void onFailure(Call<MovieData> call, Throwable t) {
            networkInterface.onResponseFailure(t.getLocalizedMessage());
            t.printStackTrace();
        }
    };

    public void getTopRatedMovies(int page) {
        Call<MovieData> movieList = api.getTopRatedMovies(Utils.api_key,page);
        movieList.enqueue(movieCallbacks);
    }

    public void getMostPopularMoview(int page) {
        Call<MovieData> movieList = api.getpopularMovies(Utils.api_key,page);
        movieList.enqueue(movieCallbacks);
    }

    public void getFavoriteMovies() {

        LiveData<List<Movies>> favoriteMovieList = MoviesDatabase.getMoviesDatabase(MyApplication.getAppContext())
                                                    .getMovies().getFavoritemovies();
        networkInterface.onSuccessDataFromDatabase(favoriteMovieList);

    }
}
