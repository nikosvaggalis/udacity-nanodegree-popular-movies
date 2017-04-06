package moviedb.example.android.com.moviedb;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import moviedb.example.android.com.moviedb.adapters.MovieDbAdapter;
import moviedb.example.android.com.moviedb.data.MovieDbData;
import moviedb.example.android.com.moviedb.database.Contract;
import moviedb.example.android.com.moviedb.utilities.JSONUtils;
import moviedb.example.android.com.moviedb.utilities.NetworkUtils;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */

public class MainActivity extends AppCompatActivity implements MovieDbAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<ArrayList<MovieDbData>> {

    private MovieDbAdapter mMovieDbAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;
    static String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    static String BUNDLE_RECYCLER_MOVIEDATA = "recycler_moviedata";
    private static final int MOVIEDBDATA_LOADER_ID = 22;
    private static final int CURSOR_LOADER_ID = 23;
    public final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    public final static String QUERY_PARAM = "api_key";
    public final static String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    ArrayList<MovieDbData> mMovieDbDataArrayList = new ArrayList<MovieDbData>();
    Exception ex = null;
    Exception ex1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_items);
        StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(3, 1);

        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieDbAdapter = new MovieDbAdapter(MainActivity.this);
        mRecyclerView.setAdapter(mMovieDbAdapter);

        if (savedInstanceState != null) {
            Parcelable savedRecyclerViewState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
            mMovieDbDataArrayList = savedInstanceState.getParcelableArrayList(BUNDLE_RECYCLER_MOVIEDATA);
            mMovieDbAdapter.setMovieDbData(mMovieDbDataArrayList, getApplicationContext());
        } else {

            Bundle queryBundle = new Bundle();
            queryBundle.putString(getResources().getString(R.string.put_extra_menu_choice), getResources().getString(R.string.menu_popular));
            getSupportLoaderManager().restartLoader(MOVIEDBDATA_LOADER_ID, queryBundle, this);

        }


    }

    private LoaderManager.LoaderCallbacks<Cursor> dataResultLoaderListener
            = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            return new AsyncTaskLoader<Cursor>(getApplicationContext()) {
                Cursor mData = null;

                @Override
                public Cursor loadInBackground() {
                    Cursor data;

                    mMovieDbDataArrayList = new ArrayList<MovieDbData>();

                    try {
                        data = getContentResolver().query(Contract.TableEntry.CONTENT_URI,
                                null,
                                null,
                                null,
                                Contract.TableEntry._ID);

                        int movieDbIdIndex = data.getColumnIndex(Contract.TableEntry.COLUMN_MOVIEDBID);


                        while (data.moveToNext()) {

                            int id = data.getInt(movieDbIdIndex);
                            Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(String.valueOf(id))
                                    .appendQueryParameter(QUERY_PARAM, API_KEY)
                                    .build();
                            String serviceResponse = NetworkUtils.buildAndCallUrl(builtUri);
                            MovieDbData test = new MovieDbData();
                            ArrayList<MovieDbData> mMovieDbDataArrayListOut = (ArrayList<MovieDbData>) JSONUtils.getJson(test, serviceResponse);

                            mMovieDbDataArrayList.add(mMovieDbDataArrayListOut.get(0));
                        }

                        return data;

                    } catch (Exception e) {
                        ex1 = e;
                        e.printStackTrace();
                        return null;
                    }


                }



                @Override
                protected void onStartLoading() {

                        mLoadingIndicator.setVisibility(View.VISIBLE);
                        forceLoad();

                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (data != null && data.getCount() > 0) {
                mMovieDbAdapter.setMovieDbData(mMovieDbDataArrayList, MainActivity.this);
                TextView sortBy = (TextView) findViewById(R.id.sort_by_textView);
                sortBy.setText("Favourites");
            } else if (ex1 != null) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.exception_SQLException) + ex1.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "You have not chosen any Favourite movies yet! ", Toast.LENGTH_LONG).show();
            }
        }


        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }


    };


    @Override
    public Loader<ArrayList<MovieDbData>> onCreateLoader(int i, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<MovieDbData>>(this) {
            @Override
            public ArrayList<MovieDbData> loadInBackground() {
                ArrayList<MovieDbData> mMovieDbDataArrayListOut;


                try {
                    Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(args.getString(getResources().getString(R.string.put_extra_menu_choice)))
                            .appendQueryParameter(QUERY_PARAM, API_KEY)
                            .build();
                    String serviceResponse = NetworkUtils.buildAndCallUrl(builtUri);
                    MovieDbData mEmptyMovieDbData = new MovieDbData();
                    mMovieDbDataArrayListOut = (ArrayList<MovieDbData>) JSONUtils.getJson(mEmptyMovieDbData, serviceResponse);

                } catch (Exception e) {
                    ex = e;
                    e.printStackTrace();
                    return null;
                }
                return mMovieDbDataArrayListOut;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) {
                    return;
                }
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

        };

    }


    @Override
    public void onLoadFinished(Loader<ArrayList<MovieDbData>> loader, ArrayList<MovieDbData> mMovieDbDataArrayListIn) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);

        if (mMovieDbDataArrayListIn != null && mMovieDbDataArrayListIn.size() > 0) {


            mMovieDbDataArrayList = mMovieDbDataArrayListIn;
            mMovieDbAdapter.setMovieDbData(mMovieDbDataArrayListIn, this);

            TextView sortBy = (TextView) findViewById(R.id.sort_by_textView);
            if (sortBy.getText() == "Favourites") {

                LoaderManager loaderManager = getSupportLoaderManager();
                loaderManager.restartLoader(CURSOR_LOADER_ID, null, dataResultLoaderListener);
            }
        } else if (ex != null) {

            if (ex instanceof SocketTimeoutException) {
                Toast.makeText(MainActivity.this,getResources().getString(R.string.exception_SocketTimeoutException) , Toast.LENGTH_LONG).show();
            } else if (ex instanceof MalformedURLException) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.exception_MalformedURLException) + ex.getMessage(), Toast.LENGTH_LONG).show();
            } else if (ex instanceof IOException) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.exception_IOException), Toast.LENGTH_LONG).show();
            } else if (ex instanceof JSONException) {
                Toast.makeText(MainActivity.this,getResources().getString(R.string.exception_JSONException) + ex.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.exception_GeneralException), Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<MovieDbData>> loader) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView sortBy = (TextView) findViewById(R.id.sort_by_textView);
        int id = item.getItemId();
        String choice = null;

        if (id == R.id.action_sort_by_popularity) {
            choice = getResources().getString(R.string.menu_popular);
            sortBy.setText("Most Popular movies");
        } else if (id == R.id.action_sort_by_rating) {
            choice = getResources().getString(R.string.menu_top_rated);
            sortBy.setText("Top Rated movies");
        } else if (id == R.id.action_favourites) {
            choice = getResources().getString(R.string.menu_favourites);
        }

        LoaderManager loaderManager = getSupportLoaderManager();

        StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        ;
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        if (choice != getResources().getString(R.string.menu_favourites)) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString(getResources().getString(R.string.put_extra_menu_choice), choice);

            loaderManager.restartLoader(MOVIEDBDATA_LOADER_ID, queryBundle, this);
        } else {
            loaderManager.restartLoader(CURSOR_LOADER_ID, null, dataResultLoaderListener);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(MovieDbData clickedItemIndex) {
        Intent startChildActivityIntent = new Intent(MainActivity.this, ChildActivity.class);
        Parcelable parcelableMovie = clickedItemIndex;
        startChildActivityIntent.putExtra(getResources().getString(R.string.put_extra_movie_details), parcelableMovie);
        startActivity(startChildActivityIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(BUNDLE_RECYCLER_MOVIEDATA, mMovieDbDataArrayList);
    }
}
