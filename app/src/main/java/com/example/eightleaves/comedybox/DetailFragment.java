package com.example.eightleaves.comedybox;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eightleaves.comedybox.adapter.TrailerAdapter;
import com.example.eightleaves.comedybox.data.CBContract;
import com.example.eightleaves.comedybox.data.CBDataUpdator;
import com.example.eightleaves.comedybox.data.models.Trailer;
import com.example.eightleaves.comedybox.events.EventExecutor;
import com.example.eightleaves.comedybox.events.GetTrailersEvent;
import com.example.eightleaves.comedybox.events.GetTrailersResultEvent;
import com.example.eightleaves.comedybox.otto.ComedyBus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener{
    static final String DETAIL_URI = "URI";
    private Uri mUri;
    private static final int DETAIL_LOADER=1;
    private ImageView imageView;
    private TextView titleText;
    private TextView releaseDateText;
    private RecyclerView trailersListView;
    private ArrayList<Trailer> trailerList;
    private Cursor mCursor;
    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;
    private EventExecutor executor;
    private CBDataUpdator comedyDataUpdator;
    private static final String TRAILERS_KEY = "trailers";

    static final int COL_COMEDY_ID = 0;
    static final int COL_COMEDY_COMEDY_ID = 1;
    private static final int COL_COMEDY_POSTER_PATH = 2;
    private static final int COL_COMEDY_TITLE = 3;
    private static final int COL_COMEDY_RELEASE_DATE = 4;
    private static final int COL_COMEDY_OVERVIEW = 5;
    static final int COL_SORT_KEY = 6;
    static final int COL_SORT_SETTING = 8;
    private static final String[] COMEDY_DETAIL_COLUMNS = {
            CBContract.ComedyEntry.TABLE_NAME + "." + CBContract.ComedyEntry._ID,
            CBContract.ComedyEntry.COLUMN_COMEDY_ID,
            CBContract.ComedyEntry.COLUMN_POSTER_PATH,
            CBContract.ComedyEntry.COLUMN_TITLE,
            CBContract.ComedyEntry.COLUMN_RELEASE_DATE,
            CBContract.ComedyEntry.COLUMN_OVERVIEW,
            CBContract.ComedyEntry.COLUMN_SORT_KEY,
            CBContract.SortEntry.COLUMN_SORT_SETTING
    };

    public DetailFragment() {
        // Required empty public constructor
        ComedyBus.getInstance().register(this);

    }

    @Override
    public void onDestroy(){
        ComedyBus.getInstance().unregister(this);
        super.onDestroy();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DetailFragment.DETAIL_URI);
        }
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        imageView = (ImageView)rootView.findViewById(R.id.list_item_comedy_image);
        titleText = (TextView) rootView.findViewById(R.id.list_item_comedy_title);
        releaseDateText = (TextView)rootView.findViewById(R.id.list_item_comedy_year);
        trailersListView = (RecyclerView)rootView.findViewById(R.id.list_item_comedy_trailers_list);

        if(savedInstanceState != null && savedInstanceState.containsKey(TRAILERS_KEY)){
            trailerList = savedInstanceState.getParcelableArrayList(TRAILERS_KEY);
            setupTrailerRecyclerView();
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TRAILERS_KEY, trailerList);
        super.onSaveInstanceState(outState);
    }

    private void setupTrailerRecyclerView() {
        TrailerAdapter trailerAdapter = new TrailerAdapter(this.getContext(), trailerList);
        trailersListView.setAdapter(trailerAdapter);

        /*This solution is taken To get Recycler View height dynamically
        http://stackoverflow.com/questions/32337403/making-recyclerview-fixed-height-and-scrollable*/
        trailersListView.setLayoutManager(new MyLinearLayoutManager(this.getContext(), 1, false));
        // trailersListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        trailerAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    COMEDY_DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data !=null && data.moveToFirst()) {
            mCursor = data;
            String title = data.getString(COL_COMEDY_TITLE);
            titleText.setText(title);
            String posterPath = data.getString(COL_COMEDY_POSTER_PATH);
            String imageUrl = posterPath;
            Picasso.with(getActivity()).load(imageUrl)
                    .placeholder(R.mipmap.ic_launcher).into(imageView);
            int comedyId = (int) data.getLong(COL_COMEDY_COMEDY_ID);
            if(trailerList == null){
                getTrailers(comedyId);
            }
        }
    }

    private void getTrailers(int comedyId){
        comedyDataUpdator = new CBDataUpdator(getContext());
        executor = new EventExecutor(getContext());
        GetTrailersEvent event = new GetTrailersEvent();
        event.setComedyId(comedyId);
        ComedyBus.getInstance().post(event);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Subscribe
    public void getTrailersResult(GetTrailersResultEvent event){
        trailerList = event.getTrailersResult().getResults();
        if (getActivity() != null) {
            if (!trailerList.isEmpty() && trailerList != null) {
                setupTrailerRecyclerView();
            }
        }
    }

    private long addSortSetting(String sortSetting) {
        long sortSettingId;
        Cursor cur = this.getContext().getContentResolver().query(
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

            Uri sortSettingUri = this.getContext().getContentResolver().insert(
                    CBContract.SortEntry.CONTENT_URI, values);
            sortSettingId = ContentUris.parseId(sortSettingUri);
        }
        return sortSettingId;
    }

    @Override
    public void onClick(View v) {

        comedyDataUpdator = new CBDataUpdator(getContext());
        executor = new EventExecutor(getContext());
    }

}
