package moviedb.example.android.com.moviedb.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import moviedb.example.android.com.moviedb.R;
import moviedb.example.android.com.moviedb.data.MovieDbData;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */

public class MovieDbAdapter extends RecyclerView.Adapter<MovieDbAdapter.RecyclerViewHolder> {

    ArrayList<MovieDbData> mMovieDataArrayList;
    private Context mcontext;

    private String mImageUrl = "http://image.tmdb.org/t/p/w500//";

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(MovieDbData clickedItemIndex);
    }

    public MovieDbAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
    }


    public void setMovieDbData(ArrayList<MovieDbData> mMovieDbDataArrayListIn, Context context) {
        mMovieDataArrayList = mMovieDbDataArrayListIn;
        mcontext = context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recylerview_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.TextRecyclerView.setText(mMovieDataArrayList.get(position).myTitle);
        Uri builtUri = Uri.parse(mImageUrl).buildUpon().appendEncodedPath(
                mMovieDataArrayList.get(position).myImage)
                .build();
        Picasso.with(mcontext).load(builtUri).into(holder.ImageRecyclerView);
    }

    @Override
    public int getItemCount() {
        return mMovieDataArrayList != null ? mMovieDataArrayList.size() : 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TextRecyclerView;
        ImageView ImageRecyclerView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            TextRecyclerView = (TextView) itemView.findViewById(R.id.tv_movie_name);
            ImageRecyclerView = (ImageView) itemView.findViewById(R.id.iv_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(mMovieDataArrayList.get(clickedPosition));
        }

    }
}
