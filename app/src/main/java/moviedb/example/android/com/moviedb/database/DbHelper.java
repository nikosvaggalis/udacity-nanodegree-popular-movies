package moviedb.example.android.com.moviedb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nikos Vaggalis as part of Udacity Nanodegree's
 * project 'Most Popular Movies' in 2017.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favouritemoviesDB.db";

    private static final int VERSION = 2;


    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE " + Contract.TableEntry.TABLE_NAME + " (" +
                Contract.TableEntry._ID + " INTEGER PRIMARY KEY, " +
                Contract.TableEntry.COLUMN_MOVIEDBID + " INTEGER UNIQUE NOT NULL " + ");";

        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.TableEntry.TABLE_NAME);
        onCreate(db);
    }
}
