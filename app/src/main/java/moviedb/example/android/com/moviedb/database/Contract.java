package moviedb.example.android.com.moviedb.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */
public class Contract {

    public static final String AUTHORITY = "com.example.android.moviedb";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVOURITES = "favourites";

    public static final class TableEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();

        public static final String TABLE_NAME = "favouritemovies";


        public static final String COLUMN_MOVIEDBID = "moviedbid";


    }
}
