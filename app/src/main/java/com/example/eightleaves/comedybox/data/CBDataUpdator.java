package com.example.eightleaves.comedybox.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.eightleaves.comedybox.data.models.Comedy;
import com.example.eightleaves.comedybox.data.models.ComedyResults;
import com.example.eightleaves.comedybox.events.ComedyUpdateSuccessEvent;
import com.example.eightleaves.comedybox.events.GetComedyDataResultEvent;
import com.example.eightleaves.comedybox.events.MarkFavouriteEvent;
import com.example.eightleaves.comedybox.otto.ComedyBus;
import com.squareup.otto.Subscribe;

import java.util.Vector;

/**
 * Created by gkhera on 21/03/2016.
 */
public class CBDataUpdator {
    private Context mContext;

    public CBDataUpdator(Context context) {
        mContext = context;
        ComedyBus.getInstance().register(this);
    }

    @Subscribe
    public void updateComedyData(GetComedyDataResultEvent event){
        addComedyData(event.getComedyResults(), event.getSortBy());
        ComedyBus.getInstance().unregister(this);
    }


    private long addSortSetting(String sortSetting) {
        long sortSettingId;
        Cursor cur = mContext.getContentResolver().query(
                CBContract.SortEntry.CONTENT_URI,
                new String[]{CBContract.SortEntry._ID},
                CBContract.SortEntry.COLUMN_SORT_SETTING + "=?",
                new String[]{sortSetting},
                null);

        if (cur != null && cur.moveToFirst()) {
            int sortIndex = cur.getColumnIndex(CBContract.SortEntry._ID);
            sortSettingId = cur.getLong(sortIndex);
            cur.close();
        } else {
            ContentValues values = new ContentValues();
            values.put(CBContract.SortEntry.COLUMN_SORT_SETTING, sortSetting);

            Uri sortSettingUri = mContext.getContentResolver().insert(
                    CBContract.SortEntry.CONTENT_URI, values);
            sortSettingId = ContentUris.parseId(sortSettingUri);
        }
        return sortSettingId;
    }

    public void addComedyData(ComedyResults comedyResults, String sortBy) {
        Vector<ContentValues> cVVector = new Vector<>(comedyResults.getResults().size());
        for(int i = 0; i < comedyResults.getResults().size(); i++) {
            String posterPath;
            String overview;
            String releaseDate;
            String artist;
            String id;
            long sortId = addSortSetting(sortBy);
            Comedy comedy = comedyResults.getResults().get(i);

            posterPath = comedy.getPosterPath();
            overview = comedy.getOverview();
            releaseDate = comedy.getReleaseDate();
            artist = comedy.getArtist();
            id = Integer.toString(comedy.getId());

            ContentValues contentValues = new ContentValues();

            contentValues.put(CBContract.ComedyEntry.COLUMN_COMEDY_ID, id);
            contentValues.put(CBContract.ComedyEntry.COLUMN_POSTER_PATH, posterPath);
            contentValues.put(CBContract.ComedyEntry.COLUMN_OVERVIEW, overview);
            contentValues.put(CBContract.ComedyEntry.COLUMN_TITLE, artist);
            contentValues.put(CBContract.ComedyEntry.COLUMN_RELEASE_DATE, releaseDate);
            contentValues.put(CBContract.ComedyEntry.COLUMN_SORT_KEY, sortId);
            cVVector.add(contentValues);
        }
        int inserted = 0;
        // add to database
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            inserted = mContext.getContentResolver().bulkInsert(CBContract.ComedyEntry.CONTENT_URI, cvArray);
        }

        Log.d("Popular comedy", "FetchComedy Complete. " + inserted + " Inserted");

    }

    @Subscribe
    public void getMarkFavoriteEvent(MarkFavouriteEvent event){
        long sortId = addSortSetting(event.getSortBy());
        ContentValues comedyValues = new ContentValues();

        comedyValues.put(CBContract.ComedyEntry.COLUMN_COMEDY_ID, event.getId());
        comedyValues.put(CBContract.ComedyEntry.COLUMN_POSTER_PATH,event.getPosterPath());
        comedyValues.put(CBContract.ComedyEntry.COLUMN_OVERVIEW, event.getOverview());
        comedyValues.put(CBContract.ComedyEntry.COLUMN_TITLE, event.getTitle());
        comedyValues.put(CBContract.ComedyEntry.COLUMN_RELEASE_DATE, event.getReleaseDate());
        comedyValues.put(CBContract.ComedyEntry.COLUMN_SORT_KEY, sortId);

        Uri inserted = mContext.getContentResolver().insert(CBContract.ComedyEntry.CONTENT_URI, comedyValues);
        if(inserted != null)
        {
            Toast.makeText(mContext, "Favourite Movie Added", Toast.LENGTH_SHORT).show();
        }
    }

}
