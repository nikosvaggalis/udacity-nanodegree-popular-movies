package moviedb.example.android.com.moviedb.utilities;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */

public interface IJSONUtil {

    ArrayList<?> convertJSONtoObject(JSONObject x) throws JSONException;

}
