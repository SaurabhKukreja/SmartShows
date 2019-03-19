package com.kukroid.smartshows.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kukroid.smartshows.R;
import com.kukroid.smartshows._Model.Result;
import com.kukroid.smartshows.network.NetworkController;

public class MovieDetailsFragment extends Fragment {

    View view;
    ImageView thumbNailImage, posterImage;
    TextView movieName , movieDescription, movieReleaseDate;
    Result movieData;
    RatingBar movieRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_details,container,false);
        android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle.get("Movie") != null){
            movieData = (Result) bundle.get("Movie");
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
        setDataToUI();
    }

    private void setDataToUI() {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


}
