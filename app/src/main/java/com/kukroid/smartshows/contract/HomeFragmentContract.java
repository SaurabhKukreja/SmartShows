package com.kukroid.smartshows.contract;

import android.arch.lifecycle.LiveData;

import com.kukroid.smartshows._Model.Movies;

import java.util.List;

public interface HomeFragmentContract {


    boolean checkInternetConnectivity();
    void setDataToRecyclerView(List<Movies> body);
    void showProgressBar();
    void hideProgressBar();
    void onOffline();
    void onFailure(String localizedMessage);

    void setDataFromDatabase(LiveData<List<Movies>> favoriteMovieList);

    interface NetworkInterface{
        void onResponseSuccess(List<Movies> body);
        void onResponseFailure(String localizedMessage);
        void onSuccessDataFromDatabase(LiveData<List<Movies>> favoriteMovieList);
        void onFailureDataFromDatabase(String localizedMessage);
    }

    interface HomeModel{
        void getMovieList();
    }


}
