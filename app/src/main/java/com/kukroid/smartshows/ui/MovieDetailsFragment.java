package com.kukroid.smartshows.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kukroid.smartshows.AppExecutors;
import com.kukroid.smartshows.R;
import com.kukroid.smartshows.Util.Utils;
import com.kukroid.smartshows._Model.Example;
import com.kukroid.smartshows._Model.MovieDao;
import com.kukroid.smartshows._Model.Movies;
import com.kukroid.smartshows._Model.Reviews;
import com.kukroid.smartshows.database.MoviesDatabase;
import com.kukroid.smartshows.network.API;
import com.kukroid.smartshows.network.NetworkController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment  implements View.OnClickListener {

    View view;
    ImageView thumbNailImage, posterImage;
    TextView movieName , movieDescription, movieReleaseDate;
    Movies movieData;
    RatingBar movieRating;
    ImageView addFavorite;
            TextView playTrailer,userReviews;
    private API api;
    private Call<Example> example;
    private Call<Reviews> reviews;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_details,container,false);
        android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle.get("Movie") != null){
            movieData = (Movies) bundle.get("Movie");
        }
        getActivity().invalidateOptionsMenu();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();

    }

    private void initView() {
        thumbNailImage = view.findViewById(R.id.thumbnailImage);
        posterImage = view.findViewById(R.id.posterImage);
        movieName = view.findViewById(R.id.movienName);
        movieDescription = view.findViewById(R.id.movieDescription);
        movieRating = view.findViewById(R.id.movieRating);
        movieReleaseDate = view.findViewById(R.id.movieReleaseDate);
        addFavorite = view.findViewById(R.id.addFavorites);
        addFavorite.setOnClickListener(this);
        playTrailer = view.findViewById(R.id.playTrailer);
        playTrailer.setOnClickListener(this);
        userReviews = view.findViewById(R.id.userReviews);
        userReviews.setOnClickListener(this);
        setDataToUI();
    }

    private void setDataToUI() {
        checkIfMovieIsFavorite();
        Glide.with(this)
                .load(NetworkController.BASE_IMAGE_URL_BACKDROP + movieData.getBackdropPath())
                .into(thumbNailImage);

        Glide.with(this)
                .load(NetworkController.BASE_IMAGE_URL_POSTER + movieData.getPosterPath())
                .into(posterImage);

        movieName.setText(movieData.getOriginalTitle());
        movieRating.setRating(Float.parseFloat(movieData.getVoteAverage().toString()));
        movieReleaseDate.setText(movieData.getReleaseDate());
        movieDescription.setText(movieData.getOverview());
    }

    private void checkIfMovieIsFavorite() {
        final MovieDao moviesDao = MoviesDatabase.getMoviesDatabase(getContext()).getMovies();
        AppExecutors.getExecutors().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (moviesDao.getFavoriteMovieDetails(movieData.getId()) != null) {
                    setFavoriteIcon(R.mipmap.favorites_star);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addFavorites:
                final MovieDao moviesDao = MoviesDatabase.getMoviesDatabase(getContext()).getMovies();
                AppExecutors.getExecutors().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(moviesDao.getFavoriteMovieDetails(movieData.getId())!=null){

                            moviesDao.deleteFavoriteMovie(movieData);
                            setFavoriteIcon(R.mipmap.favorite);
                        }
                        else {

                            moviesDao.insertFavoriteMovie(movieData);
                            setFavoriteIcon(R.mipmap.favorites_star);

                        }
                    }
                });
                break;
            case R.id.playTrailer:
                getVideoIdForMovie();
                break;

            case R.id.userReviews:
                getUserReviews();
                break;
        }

    }

    private void getUserReviews() {

        Bundle bundle = new Bundle();
        bundle.putInt("MovieId",movieData.getId());

        Fragment fragment = new ReviewFragment();
        fragment.setArguments(bundle);

        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container,fragment,"Reviews");
        ft.addToBackStack("Fragment");
        ft.commit();
    }

    private void getVideoIdForMovie() {
        api = NetworkController.getNetworkInstance().create(API.class);
        example = (Call<Example>) api.getVideos("https://api.themoviedb.org/3/movie/"+movieData.getId()+"/videos?api_key="+ Utils.api_key);
        example.enqueue(exampleCallback);
    }

    private void setFavoriteIcon(final int favorites) {
        AppExecutors.getExecutors().getMainThreadIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        addFavorite.setImageResource(favorites);
                    }
                }
        );
    }

    Callback<Example> exampleCallback = new Callback<Example>() {

        @Override
        public void onResponse(Call<Example> call, Response<Example> response) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + response.body().getResults().get(0).getKey()));
            startActivity(intent);

        }

        @Override
        public void onFailure(Call<Example> call, Throwable t) {

        }
    };

}
