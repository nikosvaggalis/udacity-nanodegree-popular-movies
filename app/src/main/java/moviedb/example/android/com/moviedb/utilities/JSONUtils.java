package moviedb.example.android.com.moviedb.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */


public final class JSONUtils {


    public static ArrayList<?> getJson(IJSONUtil objectIn, String serviceResponse) throws JSONException {
        JSONObject ObjJson = new JSONObject(serviceResponse);
        return objectIn.convertJSONtoObject(ObjJson);
    }


}

