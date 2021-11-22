package com.pace.myteacher;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewItemAdapter  extends RecyclerView.Adapter<ReviewItemAdapter.ReviewItemViewHolder> {
    private Context mCtx;
    private List<ReviewItem> reviewItemList;

    public ReviewItemAdapter(Context mCtx, List<ReviewItem> reviewItemList) {
        this.mCtx = mCtx;
        this.reviewItemList = reviewItemList;
    }

    @NonNull
    @Override
    public ReviewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.review_item, parent, false);
        return new ReviewItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewItemViewHolder holder, final int position) {
        final ReviewItem reviewItem = reviewItemList.get(position);

        holder.reviewer.setText(reviewItem.getReviewer());
        holder.rating.setText(reviewItem.getRating());
        holder.review.setText(reviewItem.getReview());

    }

    @Override
    public int getItemCount() {
        return reviewItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ReviewItemViewHolder extends RecyclerView.ViewHolder {

        private TextView reviewer;
        private TextView rating;
        private TextView review;

        public ReviewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewer = itemView.findViewById(R.id.itemReviewer);
            rating = itemView.findViewById(R.id.itemRating);
            review = itemView.findViewById(R.id.itemReview);

        }
    }

}
