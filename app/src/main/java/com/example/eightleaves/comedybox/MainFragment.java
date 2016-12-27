package com.example.eightleaves.comedybox;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.eightleaves.comedybox.adapter.CBAdapter;
import com.example.eightleaves.comedybox.data.CBContract;
import com.example.eightleaves.comedybox.data.CBDataUpdator;
import com.example.eightleaves.comedybox.data.models.Comedy;
import com.example.eightleaves.comedybox.events.EventExecutor;
import com.example.eightleaves.comedybox.events.GetComedyDataEvent;
import com.example.eightleaves.comedybox.otto.ComedyBus;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{

    private GridView gridView;
    private CBAdapter cbAdapter;
    private static final int COMEDY_LOADER =0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    static final int COL_COMEDY_ID = 0;
    private static final int COL_COMEDY_COMEDY_ID = 1;
    public static final int COL_COMEDY_POSTER_PATH = 2;
    static final int COL_COMEDY_TITLE = 3;
    static final int COL_COMEDY_RELEASE_DATE = 4;
    static final int COL_COMEDY_OVERVIEW = 5;
    static final int COL_SORT_SETTING = 6;
    private static final String[] COMEDY_COLUMNS = {
            CBContract.ComedyEntry.TABLE_NAME + "." + CBContract.ComedyEntry._ID,
            CBContract.ComedyEntry.COLUMN_COMEDY_ID,
            CBContract.ComedyEntry.COLUMN_POSTER_PATH,


    };

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(COMEDY_LOADER, null, this);
        updateComedyBox();
    }

    private void updateComedyBox() {
        EventExecutor executor = new EventExecutor(getContext());
        CBDataUpdator udpator = new CBDataUpdator(getContext());
        GetComedyDataEvent event = new GetComedyDataEvent();
        ComedyBus.getInstance().post(event);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cbAdapter = new CBAdapter(getActivity(),null);
        cbAdapter.notifyDataSetChanged();

        View rootView = inflater.inflate(R.layout.comedy_fragment, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(cbAdapter);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = CBContract.ComedyEntry.COLUMN_COMEDY_ID + " ASC";
        Uri movieForSortSettingUri = CBContract.ComedyEntry.buildComedySort(
                "popular");
        return new CursorLoader(getActivity(),movieForSortSettingUri,COMEDY_COLUMNS,null,null,sortOrder);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null || data.getCount()==0){
            Toast.makeText(getContext(),"No data available, Try changing the Sort Order",
                    Toast.LENGTH_SHORT).show();
        }
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
