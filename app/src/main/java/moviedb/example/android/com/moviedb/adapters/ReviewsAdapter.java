package moviedb.example.android.com.moviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import moviedb.example.android.com.moviedb.R;
import moviedb.example.android.com.moviedb.data.ReviewData;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.RecyclerViewHolder> implements IAdapter<ReviewData, Context> {

    ArrayList<ReviewData> mReviewDataArrayList;


    @Override
    public void setData(ArrayList<ReviewData> mReviewDataArrayListIn, Context context) {
        mReviewDataArrayList = mReviewDataArrayListIn;
        notifyDataSetChanged();

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.child_recyclerview_reviews;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.TextRecyclerView.setText(mReviewDataArrayList.get(position).myReview);
    }

    @Override
    public int getItemCount() {
        return mReviewDataArrayList != null ? mReviewDataArrayList.size() : 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TextRecyclerView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            TextRecyclerView = (TextView) itemView.findViewById(R.id.tx_child_review);

        }

        @Override
        public void onClick(View v) {

        }

    }
}
