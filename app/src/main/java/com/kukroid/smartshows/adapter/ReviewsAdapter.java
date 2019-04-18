package com.kukroid.smartshows.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kukroid.smartshows.R;
import com.kukroid.smartshows._Model.ReviewData;
import com.kukroid.smartshows._Model.Reviews;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>{

    Context context;
    ArrayList<ReviewData> reviewsArrayList = new ArrayList<>();

    public void setReviewsArrayList(ArrayList<ReviewData> reviewsArrayList) {
        this.reviewsArrayList = reviewsArrayList;
    }

    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_layout,parent,false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {

        holder.reviewContent.setText(reviewsArrayList.get(position).getContent());
        holder.reviewAuthor.setText(reviewsArrayList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        TextView reviewContent, reviewAuthor;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            reviewContent = itemView.findViewById(R.id.reviewContent);
            reviewAuthor = itemView.findViewById(R.id.reviewAuthor);
        }
    }
}
