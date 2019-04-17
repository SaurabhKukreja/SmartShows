package com.kukroid.smartshows.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kukroid.smartshows.AppExecutors;
import com.kukroid.smartshows.R;
import com.kukroid.smartshows._Model.ReviewData;
import com.kukroid.smartshows._Model.Reviews;
import com.kukroid.smartshows.adapter.ReviewsAdapter;
import com.kukroid.smartshows.network.API;
import com.kukroid.smartshows.network.NetworkController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {


    private View view;
    private RecyclerView review;
    private API api;
    Callback<Reviews> reviewsCallback;
    ReviewsAdapter adapter;
    int movieId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_fragment,container,false);
        setReviewsCallback();
        initView();
        return view;
    }

    private void setReviewsCallback() {

        reviewsCallback = new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {

                adapter.setReviewsArrayList((ArrayList<ReviewData>) response.body().getResults());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

            }
        };

    }

    private void initView() {

        Bundle bundle = getArguments();
        movieId = (int) bundle.get("MovieId");
        review = view.findViewById(R.id.reviews);
        adapter = new ReviewsAdapter(this.getContext());
        review.setAdapter(adapter);
        review.setLayoutManager(new LinearLayoutManager(this.getContext()));
        api = NetworkController.getNetworkInstance().create(API.class);
        String URL = "https://api.themoviedb.org/3/movie/"+movieId+"/reviews?api_key=35fa2d19cbb3f743376ab63108094ef3&language=en-US";
        Call<Reviews> reviewsResponse = api.getReviews(URL);
        reviewsResponse.enqueue(reviewsCallback);


    }


}
