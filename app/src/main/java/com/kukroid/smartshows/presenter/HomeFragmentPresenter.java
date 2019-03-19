package com.kukroid.smartshows.presenter;

import com.kukroid.smartshows._Model.Result;
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
    public void onResponseSuccess(List<Result> body) {

        view.setDataToRecyclerView(body);
        view.hideProgressBar();
    }

    @Override
    public void onResponseFailure(String localizedMessage) {

        view.onFailure(localizedMessage);
        view.hideProgressBar();
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
}
