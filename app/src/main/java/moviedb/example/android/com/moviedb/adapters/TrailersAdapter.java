package moviedb.example.android.com.moviedb.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import moviedb.example.android.com.moviedb.R;
import moviedb.example.android.com.moviedb.data.TrailerData;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.RecyclerViewHolder> implements IAdapter<TrailerData, Context> {

    ArrayList<TrailerData> mTrailerDataArrayList;
    private Context mcontext;

    private String mImageUrl = "http://img.youtube.com/vi";

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(String clickedItemVideoId);
    }


    public TrailersAdapter(ListItemClickListener listener) {

        mOnClickListener = listener;
    }

    @Override
    public void setData(ArrayList<TrailerData> mTrailerDataArrayListIn, Context context) {
        mTrailerDataArrayList = mTrailerDataArrayListIn;
        mcontext = context;
        notifyDataSetChanged();

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.child_recyclerview_trailers;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        String VideoId = mTrailerDataArrayList.get(position).myVideoId;
        holder.itemView.setTag(VideoId);

        Uri builtUri = Uri.parse(mImageUrl).buildUpon().appendPath(VideoId).appendPath("1.jpg").build();
        Picasso.with(mcontext).load(builtUri).into(holder.ImageRecyclerView);
    }

    @Override
    public int getItemCount() {

        return mTrailerDataArrayList != null ? mTrailerDataArrayList.size() : 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ImageRecyclerView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            ImageRecyclerView = (ImageView) itemView.findViewById(R.id.iv_child_trailer);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick((String) v.getTag());

        }

    }
}
