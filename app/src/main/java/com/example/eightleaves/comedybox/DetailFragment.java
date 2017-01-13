package com.example.eightleaves.comedybox;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.eightleaves.comedybox.adapter.TrailerAdapter;
import com.example.eightleaves.comedybox.data.CBContract;
import com.example.eightleaves.comedybox.data.CBDataUpdator;
import com.example.eightleaves.comedybox.data.models.Trailer;
import com.example.eightleaves.comedybox.events.EventExecutor;
import com.example.eightleaves.comedybox.events.GetTrailersEvent;
import com.example.eightleaves.comedybox.events.GetTrailersResultEvent;
import com.example.eightleaves.comedybox.events.PlayTrailerEvent;
import com.example.eightleaves.comedybox.otto.ComedyBus;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, ExoPlayer.Listener {
    static final String DETAIL_URI = "URI";
    public static final String ACTION_DATA_UPDATED = "com.example.eightleaves.comedybox.ACTION_DATA_UPDATED";
    private Uri mUri;
    private static final int DETAIL_LOADER = 1;
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
    private LinearLayout playerView;
    private ExoPlayer exoPlayer;
    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;
    private MediaCodecAudioTrackRenderer audioRenderer;
    private ImageView playPauseBtn;
    private ImageView prevButton;
    private ImageView nextButton;
    private TextView playerTitle;
    private SeekBar seekBar;
    private boolean isPlaying = false;
    ImageButton share;
    private Handler mHandler;
    private Runnable runnable;

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
    private int currentPosition;
    private AsyncTask<String, Void, Integer> mTask;

    public DetailFragment() {
        // Required empty public constructor
        ComedyBus.getInstance().register(this);

    }

    @Override
    public void onDestroy() {
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
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.list_item_comedy_image);
        titleText = (TextView) rootView.findViewById(R.id.list_item_comedy_title);
        trailersListView = (RecyclerView) rootView.findViewById(R.id.list_item_comedy_trailers_list);
        if (savedInstanceState != null && savedInstanceState.containsKey(TRAILERS_KEY)) {
            trailerList = savedInstanceState.getParcelableArrayList(TRAILERS_KEY);
            setupTrailerRecyclerView();
        }
        MobileAds.initialize(getContext(), getString(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        exoPlayer = ExoPlayer.Factory.newInstance(1);
        exoPlayer.addListener(this);
        playerView = (LinearLayout) rootView.findViewById(R.id.player_layout);
        playPauseBtn = (ImageView) rootView.findViewById(R.id.btn_play);
        playPauseBtn.setOnClickListener(this);
        nextButton = (ImageView) rootView.findViewById(R.id.btn_next);
        prevButton = (ImageView) rootView.findViewById(R.id.btn_previous);
        playerTitle = (TextView) rootView.findViewById(R.id.title_text);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        share = (ImageButton) rootView.findViewById(R.id.btn_share);
        share.setOnClickListener(this);
        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
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
        if (null != mUri) {
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
        if (data != null && data.moveToFirst()) {
            mCursor = data;
            String title = data.getString(COL_COMEDY_TITLE);
            titleText.setText(title);
            String posterPath = data.getString(COL_COMEDY_POSTER_PATH);
            String imageUrl = posterPath;
            Picasso.with(getActivity()).load(imageUrl)
                    .placeholder(R.mipmap.ic_launcher).into(imageView);
            imageView.setContentDescription(title);
            int comedyId = (int) data.getLong(COL_COMEDY_COMEDY_ID);
            if (trailerList == null) {
                getTrailers(comedyId);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer.isPlayWhenReadyCommitted()) {
            exoPlayer.release();
        }
    }

    private void getTrailers(int comedyId) {
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
    public void getTrailersResult(GetTrailersResultEvent event) {
        trailerList = event.getTrailersResult().getResults();
        if (getActivity() != null) {
            if (!trailerList.isEmpty() && trailerList != null) {
                setupTrailerRecyclerView();
            }
        }
        executor.onDestroy();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Subscribe
    public void processPlayTrailerEvent(PlayTrailerEvent event) {
        playerView.setVisibility(View.VISIBLE);
        playPauseBtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_pause));
        playerTitle.setText(event.getTitle());
        playerTitle.setContentDescription(event.getTitle());
        currentPosition = event.getPosition();
        Uri radioUri = Uri.parse(event.getUrl());
        // Settings for exoPlayer
        Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
        String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
        DataSource dataSource = new DefaultUriDataSource(getContext(), null, userAgent);
        ExtractorSampleSource sampleSource = new ExtractorSampleSource(
                radioUri, dataSource, allocator, BUFFER_SEGMENT_SIZE * BUFFER_SEGMENT_COUNT);
        audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
// Prepare ExoPlayer
        if (exoPlayer.isPlayWhenReadyCommitted()) {
            exoPlayer.release();
            if(mHandler != null){mHandler.removeCallbacks(runnable);}
            exoPlayer = ExoPlayer.Factory.newInstance(1);
        }
        exoPlayer.prepare(audioRenderer);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.addListener(this);
        isPlaying = true;
        seekBar.setMax(100);
        updateWidget(event.getTitle().toString(),true);
        updateSeekBar();
    }

    private void updateWidget(String title, boolean isPlaying) {
        Intent dataUpdated = new Intent(ACTION_DATA_UPDATED);
        dataUpdated.putExtra("title",title);
        dataUpdated.putExtra("isPlaying",isPlaying);
        getContext().sendBroadcast(dataUpdated);
    }

    private void updateSeekBar() {
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (exoPlayer != null && isPlaying && exoPlayer.getDuration()>0) {
                    float mCurrentPosition = ((float) exoPlayer.getCurrentPosition() / exoPlayer.getDuration() * 100);
                    seekBar.setProgress((int) mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        };
        getActivity().runOnUiThread(runnable);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (isPlaying) {
                    isPlaying = false;
                    exoPlayer.setPlayWhenReady(false);
                    playPauseBtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                } else {
                    isPlaying = true;
                    exoPlayer.setPlayWhenReady(true);
                    playPauseBtn.setImageDrawable(getContext().getDrawable(R.drawable.ic_pause));
                }
                break;
            case R.id.btn_share:
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText("Some sample text")
                        .getIntent(), trailerList.get(currentPosition).getSite()));
                break;
            case R.id.btn_next:
                currentPosition++;
                if (currentPosition >= 0 && currentPosition < trailerList.size()) {
                    Trailer nextTrailer = trailerList.get(currentPosition);
                    playTrailer(nextTrailer);
                } else {
                    currentPosition--;
                    //todo error
                }
                break;
            case R.id.btn_previous:
                currentPosition--;
                if (currentPosition < trailerList.size() && currentPosition >= 0) {
                    Trailer previousTrailer = trailerList.get(currentPosition);
                    playTrailer(previousTrailer);

                } else {
                    currentPosition++;
                    //todo error
                }
                break;

        }
    }

    private void playTrailer(Trailer trailer) {
        PlayTrailerEvent event = new PlayTrailerEvent();
        event.setPosition(currentPosition);
        event.setTitle(trailer.getName());
        event.setUrl(trailer.getSite());
        ComedyBus.getInstance().post(event);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Log.i("***GK:",String.valueOf(playbackState));
    }

    @Override
    public void onPlayWhenReadyCommitted() {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }
}
