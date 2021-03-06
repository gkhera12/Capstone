package com.example.eightleaves.comedybox;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.eightleaves.comedybox.adapter.CBAdapter;
import com.example.eightleaves.comedybox.data.CBContract;
import com.example.eightleaves.comedybox.data.models.Comedy;
import com.example.eightleaves.comedybox.events.MarkFavouriteEvent;
import com.example.eightleaves.comedybox.otto.ComedyBus;
import com.example.eightleaves.comedybox.sync.ComedySyncAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>,View.OnClickListener{

    private GridView gridView;
    private CBAdapter cbAdapter;
    private static final int COMEDY_LOADER =0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SORT_TYPE = "sort_type";

    private String sortType;
    private Cursor mCursor ;
    private OnFragmentInteractionListener mListener;

    static final int COL_COMEDY_ID = 0;
    private static final int COL_COMEDY_COMEDY_ID = 1;
    public static final int COL_COMEDY_POSTER_PATH = 2;
    public static final int COL_COMEDY_TITLE = 3;
    static final int COL_COMEDY_RELEASE_DATE = 4;
    static final int COL_COMEDY_OVERVIEW = 5;
    static final int COL_SORT_SETTING = 6;
    private static final String[] COMEDY_COLUMNS = {
            CBContract.ComedyEntry.TABLE_NAME + "." + CBContract.ComedyEntry._ID,
            CBContract.ComedyEntry.COLUMN_COMEDY_ID,
            CBContract.ComedyEntry.COLUMN_POSTER_PATH,
            CBContract.ComedyEntry.COLUMN_TITLE,

    };
    private SwipeRefreshLayout swipeRefresh;
    private View rootView;

    @Override
    public void onClick(View view) {
        if(Utils.isNetworkAvailable(getContext())){
            getLoaderManager().restartLoader(COMEDY_LOADER,null,this);
            ComedySyncAdapter.syncImmediately(getContext());
        }else{
            Snackbar.make(rootView,getString(R.string.network_refresh),Snackbar.LENGTH_SHORT).show();
        }
    }

    public interface Callback {
        void onItemSelected(Uri Uri);
    }

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String sortType) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(SORT_TYPE, sortType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sortType = getArguments().getString(SORT_TYPE);
        }else{
            sortType = getString(R.string.popular);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(COMEDY_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cbAdapter = new CBAdapter(getActivity(),null);
        cbAdapter.notifyDataSetChanged();

        rootView = inflater.inflate(R.layout.comedy_fragment, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(cbAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                ((Callback) getActivity())
                        .onItemSelected(CBContract.ComedyEntry.buildComedySortWithComedyId(
                                sortType, cursor.getInt(COL_COMEDY_COMEDY_ID)
                        ));
            }
        });
        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefresh.setOnClickListener(this);
        return  rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = CBContract.ComedyEntry.COLUMN_COMEDY_ID + " ASC";
        Uri comedyForSortSettingUri = CBContract.ComedyEntry.buildComedySort(
                sortType);
        return new CursorLoader(getActivity(),comedyForSortSettingUri,COMEDY_COLUMNS,null,null,sortOrder);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        if(data == null || data.getCount()==0){
            Snackbar.make(rootView,getString(R.string.network_refresh),Snackbar.LENGTH_LONG).show();
        }
        swipeRefresh.setRefreshing(false);
        cbAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cbAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
