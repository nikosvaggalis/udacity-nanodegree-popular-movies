package moviedb.example.android.com.moviedb.Loaders;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import moviedb.example.android.com.moviedb.R;
import moviedb.example.android.com.moviedb.adapters.IAdapter;
import moviedb.example.android.com.moviedb.utilities.IJSONUtil;
import moviedb.example.android.com.moviedb.utilities.JSONUtils;
import moviedb.example.android.com.moviedb.utilities.NetworkUtils;
import static moviedb.example.android.com.moviedb.MainActivity.API_KEY;
import static moviedb.example.android.com.moviedb.MainActivity.BASE_URL;
import static moviedb.example.android.com.moviedb.MainActivity.QUERY_PARAM;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's project 'Most Popular Movies' on 30-Mar-17.
 */

public class LoaderCallbacks2<D> {
    private IAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private Exception ex;

    public LoaderCallbacks2(IAdapter<D, Context> mAdapterIn, RecyclerView mRecyclerViewIn, Context contextIn) {
        mAdapter = mAdapterIn;
        mRecyclerView = mRecyclerViewIn;
        mContext = contextIn;
    }

    public LoaderCallbacks3<D> GetLoader(D mData) {

        return new LoaderCallbacks3<D>(
                mData);
    }

    ;


    class LoaderCallbacks3<D> extends android.app.Application implements LoaderManager.LoaderCallbacks<ArrayList<D>> {

        IJSONUtil mData;

        public LoaderCallbacks3(D mDataIn) {
            super();
            mData = (IJSONUtil) mDataIn;
        }

        @Override
        public Loader<ArrayList<D>> onCreateLoader(int id, Bundle args) {

            return new AsyncTaskLoader<ArrayList<D>>(mContext) {

                @Override
                public ArrayList<D> loadInBackground() {

                    ArrayList<D> mDataArrayList = new ArrayList<D>();

                    try {
                        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                                .appendPath(args.getString(mContext.getString(R.string.put_extra_movie_id)))
                                .appendPath(args.getString(mContext.getString(R.string.put_extra_path)))
                                .appendQueryParameter(QUERY_PARAM, API_KEY)
                                .build();

                        String serviceResponse = NetworkUtils.buildAndCallUrl(builtUri);

                        mDataArrayList = (ArrayList<D>) JSONUtils.getJson(mData, serviceResponse);

                    } catch (Exception e) {
                        ex = e;
                        e.printStackTrace();
                        return null;
                    }
                    return mDataArrayList;
                }


                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    if (args == null) {
                        return;
                    }
                    forceLoad();
                }
            };


        }


        @Override
        public void onLoadFinished(Loader<ArrayList<D>> loader, ArrayList<D> mDataArrayList) {


            if (mDataArrayList != null && mDataArrayList.size() > 0) {

                mAdapter.setData(mDataArrayList, mContext);
                mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);

            }
            else if (ex != null) {
                Toast.makeText(mContext, mContext.getString(R.string.exception_GeneralException), Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<D>> loader) {

        }


    }



}