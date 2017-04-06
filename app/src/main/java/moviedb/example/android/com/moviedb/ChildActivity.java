package moviedb.example.android.com.moviedb;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.function.BiConsumer;
import java.util.function.Function;
import moviedb.example.android.com.moviedb.adapters.IAdapter;
import moviedb.example.android.com.moviedb.adapters.ReviewsAdapter;
import moviedb.example.android.com.moviedb.adapters.TrailersAdapter;
import moviedb.example.android.com.moviedb.data.ReviewData;
import moviedb.example.android.com.moviedb.data.TrailerData;
import moviedb.example.android.com.moviedb.Loaders.LoaderCallbacks2;
import moviedb.example.android.com.moviedb.data.MovieDbData;
import moviedb.example.android.com.moviedb.database.Contract;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */

public class ChildActivity extends AppCompatActivity implements TrailersAdapter.ListItemClickListener {

    private String mImageUrl = "http://image.tmdb.org/t/p/w500//";
    private int favouriteId;
    private RecyclerView tRecyclerView;
    private RecyclerView rRecyclerView;
    private TrailersAdapter tAdapter;
    private ReviewsAdapter rAdapter;

    CheckBox mFavourite;
    String stringId;
    Uri uri;
    Exception ex;

    Function<Uri, Integer> delete = (uri) -> getContentResolver().delete(uri, null, null);
    Function<Uri, Cursor> select = (uri) -> getContentResolver().query(uri, null, null, null, null);
    BiConsumer<Uri, ContentValues> insert = (a, b) -> getContentResolver().insert(a, b);

    private static final int SQL_LOADER_ID = 24;
    private static final int TRAILERS_LOADER_ID = 25;
    private static final int REVIEWS_LOADER_ID = 26;


    private LoaderManager.LoaderCallbacks<Boolean> sqlOperation
            = new LoaderManager.LoaderCallbacks<Boolean>() {
        @Override
        public Loader<Boolean> onCreateLoader(int id, Bundle args) {

            return new AsyncTaskLoader<Boolean>(ChildActivity.this) {

                @Override
                public Boolean loadInBackground() {
                    Uri myUri = Uri.parse(args.getString(getResources().getString(R.string.put_extra_uri)));

                    try {
                        if (args.getString(getResources().getString(R.string.put_extra_sql_operation)) == "select") {

                            return (select.apply(myUri)).getCount() > 0 ? true : false;
                        } else if (args.getString(getString(R.string.put_extra_sql_operation)) == "delete") {
                            delete.apply(myUri);
                        } else if (args.getString(getString(R.string.put_extra_sql_operation)) == "insert") {
                            int myId = args.getInt(getResources().getString(R.string.put_extra_movie_id));
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(Contract.TableEntry.COLUMN_MOVIEDBID, myId);
                            insert.accept(myUri, contentValues);
                        }
                    } catch (Exception e) {
                        ex = e;
                    }

                    return null;
                }


                @Override
                protected void onStartLoading() {
                    forceLoad();

                }
            };
        }


        @Override
        public void onLoadFinished(Loader<Boolean> loader, Boolean result) {

            if (result != null) {
                mFavourite.setChecked(result);
            } else if (ex != null) {
                Toast.makeText(ChildActivity.this, getResources().getString(R.string.exception_SQLException) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<Boolean> loader) {

        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child2);

        TextView mTitle = (TextView) findViewById(R.id.child_title_textView);
        ImageView mImage = (ImageView) findViewById(R.id.child_imageView);
        TextView mDetails = (TextView) findViewById(R.id.child_details_textView);
        TextView mOverview = (TextView) findViewById(R.id.child_overview_textView);
        mFavourite = (CheckBox) findViewById(R.id.child_favorite);


        tRecyclerView = (RecyclerView) findViewById(R.id.child_recyclerview_trailers);
        StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        tRecyclerView.setLayoutManager(mGridLayoutManager);
        tAdapter = new TrailersAdapter(ChildActivity.this);

        rRecyclerView = (RecyclerView) findViewById(R.id.child_recyclerview_reviews);

        LinearLayoutManager rLinearLayoutLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rRecyclerView.setLayoutManager(rLinearLayoutLayoutManager);
        rAdapter = new ReviewsAdapter();
        rRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(60, 70).size(15).build());

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(getResources().getString(R.string.put_extra_movie_details))) {

            MovieDbData movieDetails = getIntent().getExtras().getParcelable(getResources().getString(R.string.put_extra_movie_details));

            favouriteId = movieDetails.myId;
            mTitle.setText(movieDetails.myTitle);

            Uri builtUri = Uri.parse(mImageUrl).buildUpon().appendEncodedPath(movieDetails.myImage).build();
            Picasso.with(ChildActivity.this).load(builtUri).into(mImage);

            mOverview.setText(movieDetails.myOverview);
            mDetails.setText("Release Date: ");
            mDetails.append("\n");
            mDetails.append(movieDetails.myRelease_date);
            mDetails.append("\n\n");
            mDetails.append("Average Vote: ");
            mDetails.append("\n");
            mDetails.append(movieDetails.myVote_average);

            stringId = Integer.toString(favouriteId);
            uri = Contract.TableEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(stringId).build();

            Bundle mySqlBundle = new Bundle();
            mySqlBundle.putString(getResources().getString(R.string.put_extra_sql_operation), "select");
            mySqlBundle.putString(getResources().getString(R.string.put_extra_uri), uri.toString());
            getSupportLoaderManager().restartLoader(SQL_LOADER_ID, mySqlBundle, sqlOperation);

            Bundle myBundle = new Bundle();
            myBundle.putString(getResources().getString(R.string.put_extra_movie_id), stringId);
            myBundle.putString(getResources().getString(R.string.put_extra_path), "videos");
            myBundle.putString(getResources().getString(R.string.put_extra_path), "videos");

            TrailerData mDummyTrailerData = new TrailerData();
            IAdapter<TrailerData, Context> mTrailersAdapter = new TrailersAdapter(this);

            LoaderCallbacks2<TrailerData> loadTrailers = new LoaderCallbacks2(mTrailersAdapter, tRecyclerView, this);
            LoaderManager.LoaderCallbacks<TrailerData> mTrailerLoader = (LoaderManager.LoaderCallbacks) loadTrailers.GetLoader(mDummyTrailerData);
            getSupportLoaderManager().restartLoader(TRAILERS_LOADER_ID, myBundle, mTrailerLoader);


            myBundle = new Bundle();
            myBundle.putString(getResources().getString(R.string.put_extra_movie_id), stringId);
            myBundle.putString(getResources().getString(R.string.put_extra_path), "reviews");

            ReviewData reviewData = new ReviewData();
            IAdapter<ReviewData, Context> reviewAdapter = new ReviewsAdapter();

            LoaderCallbacks2<ReviewData> loadReviews = new LoaderCallbacks2(reviewAdapter, rRecyclerView, this);
            LoaderManager.LoaderCallbacks<ReviewData> mReviewsAdapter = (LoaderManager.LoaderCallbacks) loadReviews.GetLoader(reviewData);
            getSupportLoaderManager().restartLoader(REVIEWS_LOADER_ID, myBundle, mReviewsAdapter);


        }

    }

    public void onClickAddFavourite(View view) {

        if (mFavourite.isChecked()) {
            Bundle myBundle = new Bundle();
            myBundle.putString(getResources().getString(R.string.put_extra_sql_operation), "insert");
            myBundle.putString(getResources().getString(R.string.put_extra_uri), uri.toString());
            myBundle.putInt(getResources().getString(R.string.put_extra_movie_id), favouriteId);
            getSupportLoaderManager().restartLoader(SQL_LOADER_ID, myBundle, sqlOperation);
        } else {

            Bundle myBundle = new Bundle();
            myBundle.putString(getResources().getString(R.string.put_extra_sql_operation), "delete");
            myBundle.putString(getResources().getString(R.string.put_extra_uri), uri.toString());
            getSupportLoaderManager().restartLoader(SQL_LOADER_ID, myBundle, sqlOperation);
        }


    }

    public void onListItemClick(String clickedItemVideoId) {
        Uri builtUri = Uri.parse("http://www.youtube.com").buildUpon().appendPath("watch")
                .appendQueryParameter("v", clickedItemVideoId)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(builtUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }



}

