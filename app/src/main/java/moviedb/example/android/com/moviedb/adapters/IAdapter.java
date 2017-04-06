package moviedb.example.android.com.moviedb.adapters;

import java.util.ArrayList;


/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */

public interface IAdapter<D, Context> {
    void setData(ArrayList<D> simpleTrailersData, Context context);

}


