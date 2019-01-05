package com.kukroid.smartshows.presenter;

import com.kukroid.smartshows._Model.Result;
import com.kukroid.smartshows.contract.HomeFragmentContract;
import com.kukroid.smartshows.network.NetworkCaller;

import java.util.List;

public class HomeFragmentPresenter implements HomeFragmentContract.NetworkInterface {

    private HomeFragmentContract view;

    public HomeFragmentPresenter(HomeFragmentContract view) {
        this.view = view;
    }

    public void loadNowPlayingMovieList(int page) {

        if(view.checkInternetConnectivity()) {
            view.showProgressBar();
            new NetworkCaller(this).getNowPlayingMovies(page);
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
        new NetworkCaller(this).getMostPopularMoview(1);
    }

    public void loadTopRatedMovies() {

        new NetworkCaller(this).getTopRatedMovies(1);
    }
}
