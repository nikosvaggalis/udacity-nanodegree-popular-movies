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
public class ReviewData implements IJSONUtil {


    public String myReview;


    public ReviewData() {
        super();
    }


    public ReviewData(String myReview) {
        super();
        this.myReview = myReview;
    }


    @Override
    public ArrayList<ReviewData> convertJSONtoObject(JSONObject x) throws JSONException {
        ArrayList<ReviewData> reviewDataArrayList = new ArrayList<ReviewData>();
        JSONArray jsonArray = new JSONArray();


        String myReview;

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


            myReview = jsonObject.getString("content");


            if (myReview != null) {
                reviewDataArrayList.add(new ReviewData(myReview));
            }
        }

        return reviewDataArrayList;
    }
}

