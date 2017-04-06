package moviedb.example.android.com.moviedb.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import moviedb.example.android.com.moviedb.utilities.IJSONUtil;


/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */
public class MovieDbData implements Parcelable, IJSONUtil {

    public String myTitle;
    public String myImage;
    public String myOverview;
    public String myRelease_date;
    public int myId;
    public String myBackdrop_path;
    public String myVote_average;

    public MovieDbData() {
        super();
    }


    public MovieDbData(String myTitle, String myImage, String myOverview, String myRelease_date, int myId, String myBackdrop_path, String myVote_average) {
        super();
        this.myTitle = myTitle;
        this.myImage = myImage;
        this.myOverview = myOverview;
        this.myRelease_date = myRelease_date;
        this.myId = myId;
        this.myBackdrop_path = myBackdrop_path;
        this.myVote_average = myVote_average;
    }


    protected MovieDbData(Parcel in) {
        myTitle = in.readString();
        myImage = in.readString();
        myOverview = in.readString();
        myRelease_date = in.readString();
        myId = in.readInt();
        myBackdrop_path = in.readString();
        myVote_average = in.readString();
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(myTitle);
        dest.writeString(myImage);
        dest.writeString(myOverview);
        dest.writeString(myRelease_date);
        dest.writeInt(myId);
        dest.writeString(myBackdrop_path);
        dest.writeString(myVote_average);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieDbData> CREATOR = new Parcelable.Creator<MovieDbData>() {
        @Override
        public MovieDbData createFromParcel(Parcel in) {
            return new MovieDbData(in);
        }

        @Override
        public MovieDbData[] newArray(int size) {
            return new MovieDbData[size];
        }
    };


    @Override
    public ArrayList<MovieDbData> convertJSONtoObject(JSONObject x) throws JSONException {

        ArrayList<MovieDbData> MovieDbDataArrayList = new ArrayList<MovieDbData>();
        JSONArray jsonArray = new JSONArray();

        String title;
        String posterPath;
        String overview;
        String release_date;
        int id;
        String backdrop_path;
        String vote_average;

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

            title = jsonObject.getString("title");
            posterPath = jsonObject.getString("poster_path");
            overview = jsonObject.getString("overview");
            release_date = jsonObject.getString("release_date");
            id = jsonObject.getInt("id");
            backdrop_path = jsonObject.getString("backdrop_path");
            vote_average = jsonObject.getString("vote_average");

            MovieDbDataArrayList.add(new MovieDbData(title, posterPath, overview, release_date, id, backdrop_path, vote_average));

        }

        return MovieDbDataArrayList;
    }
}

