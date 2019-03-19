package com.kukroid.smartshows.contract;

import com.kukroid.smartshows._Model.MovieData;
import com.kukroid.smartshows._Model.Result;

import java.util.List;

public interface HomeFragmentContract {


    boolean checkInternetConnectivity();
    void setDataToRecyclerView(List<Result> body);
    void showProgressBar();
    void hideProgressBar();
    void onOffline();
    void onFailure(String localizedMessage);

    interface NetworkInterface{
        void onResponseSuccess(List<Result> body);
        void onResponseFailure(String localizedMessage);
    }

    interface HomeModel{
        void getMovieList();
    }


}
