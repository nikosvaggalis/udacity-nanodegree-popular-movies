package moviedb.example.android.com.moviedb.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import moviedb.example.android.com.moviedb.utilities.IJSONUtil;


/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */
public class TrailerData implements IJSONUtil {


    public String myVideoId;

    public TrailerData() {
        super();
    }


    public TrailerData(String myVideoId) {
        super();
        this.myVideoId = myVideoId;
    }


    @Override
    public ArrayList<TrailerData> convertJSONtoObject(JSONObject x) throws JSONException {
        ArrayList<TrailerData> trailerDataArrayList = new ArrayList<TrailerData>();
        JSONArray jsonArray = new JSONArray();


        String videoId;

        /* Is there an error? */
        if (x.has("status_code")) {
            String errorMessage = x.getString("status_message");
            throw new JSONException(errorMessage);
        }

        if (x.has("results")) {
            jsonArray = x.getJSONArray("results");
        } else {
            jsonArray.put(0, x);
        }

        for (int i = 0; i < jsonArray.length(); i++) {


            JSONObject jsonObject = jsonArray.getJSONObject(i);


            videoId = jsonObject.getString("key");


            if (videoId != null) {
                trailerDataArrayList.add(new TrailerData(videoId));
            }
        }

        return trailerDataArrayList;
    }
}

