package com.kukroid.smartshows.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.kukroid.smartshows._Model.Movies;
import com.kukroid.smartshows.contract.HomeFragmentContract;
import com.kukroid.smartshows.network.NetworkCaller;

import java.util.List;

public class HomeFragmentPresenter implements HomeFragmentContract.NetworkInterface {

    private HomeFragmentContract view;
    private NetworkCaller networkCaller;

    public HomeFragmentPresenter(HomeFragmentContract view) {
        this.view = view;
        this.networkCaller = new NetworkCaller(this);
    }

    public void loadNowPlayingMovieList(int page) {

        if(view.checkInternetConnectivity()) {
            view.showProgressBar();
           networkCaller.getNowPlayingMovies(page);
        }else {
            view.onOffline();
        }

    }

    @Override
    public void onResponseSuccess(List<Movies> body) {

        view.setDataToRecyclerView(body);
        view.hideProgressBar();
    }

    @Override
    public void onResponseFailure(String localizedMessage) {

        view.onFailure(localizedMessage);
        view.hideProgressBar();
    }

    @Override
    public void onSuccessDataFromDatabase(LiveData<List<Movies>> favoriteMovieList) {

        view.setDataFromDatabase(favoriteMovieList);
        view.hideProgressBar();
    }

    @Override
    public void onFailureDataFromDatabase(String localizedMessage) {

    }

    public void loadMostPopularMovies() {

        if(view.checkInternetConnectivity()) {
            view.showProgressBar();
           networkCaller.getMostPopularMoview(1);

        }else {
            view.onOffline();
        }
    }

    public void loadTopRatedMovies() {

        if(view.checkInternetConnectivity()) {
            view.showProgressBar();
            networkCaller.getTopRatedMovies(1);

        }else {
            view.onOffline();
        }

    }

    public void loadFavoriteMovies() {
        networkCaller.getFavoriteMovies();
    }
}
