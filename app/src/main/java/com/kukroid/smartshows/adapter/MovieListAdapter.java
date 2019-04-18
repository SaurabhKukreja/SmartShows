package com.kukroid.smartshows.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kukroid.smartshows.R;
import com.kukroid.smartshows._Model.Movies;
import com.kukroid.smartshows.interfaces.MovieClick;
import com.kukroid.smartshows.network.NetworkController;

import java.util.List;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>{


    private MovieClick movieClick;
    private List<Movies> movieDataList;
    private Context mContext;
    RequestOptions requestOptions;

    public MovieListAdapter(Context mContext , MovieClick movieClick, List<Movies> movieDataList) {

        this.movieClick = movieClick;
        this.movieDataList = movieDataList;
        this.mContext = mContext;
         requestOptions= new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item,parent,false);
        return new MovieListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {

        Glide.with(mContext)
                .load(NetworkController.BASE_IMAGE_URL_POSTER + movieDataList.get(position).getPosterPath())
                .apply(requestOptions)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }



    class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       // TextView movieName;
        ImageView movieImage;
        public MovieListViewHolder(View itemView) {
            super(itemView);
           // movieName = itemView.findViewById(R.id.movienName);
            movieImage = itemView.findViewById(R.id.movieImage);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            movieClick.onMovieClick(getAdapterPosition());
        }
    }


}
