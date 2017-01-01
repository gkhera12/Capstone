package com.example.eightleaves.comedybox.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;


/**
 * Created by gkhera on 19/02/2016.
 */
public class CBProvider extends ContentProvider {

    private static final SQLiteQueryBuilder sComedyBySortSettingQueryBuilder;
    private CBDbHelper CBDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int COMEDY = 200;
    static final int COMEDY_WITH_SORT_SETTING = 201;
    static final int COMEDY_WITH_SORT_SETTING_AND_COMEDY_ID = 202;
    static final int SORT_SETTING = 300;

    static{
        sComedyBySortSettingQueryBuilder = new SQLiteQueryBuilder();

        sComedyBySortSettingQueryBuilder.setTables(
                CBContract.ComedyEntry.TABLE_NAME + " INNER JOIN " +
                        CBContract.SortEntry.TABLE_NAME +
                        " ON " + CBContract.ComedyEntry.TABLE_NAME +
                        "." + CBContract.ComedyEntry.COLUMN_SORT_KEY +
                        " = " + CBContract.SortEntry.TABLE_NAME +
                        "." + CBContract.SortEntry._ID);
    }

    //sort.sort_setting = ?
    private static final String sSortSettingSelection =
            CBContract.SortEntry.TABLE_NAME+
                    "." + CBContract.SortEntry.COLUMN_SORT_SETTING + " = ? ";

    //sort.sort_setting = ? AND Comedy_id = ?
    private static final String sSortSettingAndComedySelection =
            CBContract.SortEntry.TABLE_NAME +
                    "." + CBContract.SortEntry.COLUMN_SORT_SETTING + " = ? AND " +
                    CBContract.ComedyEntry.COLUMN_COMEDY_ID + " = ? ";

    private Cursor getComedyBySortSetting(Uri uri, String[] projection, String sortOrder) {
        String sortSetting = CBContract.ComedyEntry.getSortSettingFromUri(uri);
        String[] selectionArgs;
        String selection;

        selection = sSortSettingSelection;
        selectionArgs = new String[]{sortSetting};


        return sComedyBySortSettingQueryBuilder.query(CBDbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getComedyBySortSettingAndComedyId(
            Uri uri, String[] projection, String sortOrder) {
        String sortSetting = CBContract.ComedyEntry.getSortSettingFromUri(uri);
        long comedyId = CBContract.ComedyEntry.getComedyIdFromUri(uri);

        return sComedyBySortSettingQueryBuilder.query(CBDbHelper.getReadableDatabase(),
                projection,
                sSortSettingAndComedySelection,
                new String[]{sortSetting, Long.toString(comedyId)},
                null,
                null,
                sortOrder
        );
    }

    static UriMatcher buildUriMatcher() {
        UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(CBContract.CONTENT_AUTHORITY, CBContract.PATH_CB_AUDIO, COMEDY);
        sURIMatcher.addURI(CBContract.CONTENT_AUTHORITY, CBContract.PATH_CB_AUDIO+"/*", COMEDY_WITH_SORT_SETTING);
        sURIMatcher.addURI(CBContract.CONTENT_AUTHORITY, CBContract.PATH_CB_AUDIO+"/*/#",COMEDY_WITH_SORT_SETTING_AND_COMEDY_ID);
        sURIMatcher.addURI(CBContract.CONTENT_AUTHORITY, CBContract.PATH_SORT_SETTING,SORT_SETTING);
        return sURIMatcher;
    }


    @Override
    public boolean onCreate() {
        CBDbHelper = new CBDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = CBDbHelper.getReadableDatabase();

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "comedy/*/*"
            case COMEDY_WITH_SORT_SETTING_AND_COMEDY_ID:
            {
                retCursor = getComedyBySortSettingAndComedyId(uri, projection, sortOrder);
                break;
            }
            // "movie/*"
            case COMEDY_WITH_SORT_SETTING: {
                retCursor = getComedyBySortSetting(uri, projection, sortOrder);
                break;
            }
            // "movie"
            case COMEDY: {
                retCursor = db.query(CBContract.ComedyEntry.TABLE_NAME,
                        projection,selection,selectionArgs,null, null, sortOrder);
                break;
            }
            // "sortsetting"
            case SORT_SETTING: {
                retCursor = db.query(CBContract.SortEntry.TABLE_NAME,
                        projection,selection,selectionArgs,null, null, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case COMEDY_WITH_SORT_SETTING_AND_COMEDY_ID:
                return  CBContract.ComedyEntry.CONTENT_ITEM_TYPE;
            case COMEDY_WITH_SORT_SETTING:
                return CBContract.ComedyEntry.CONTENT_TYPE;
            case COMEDY:
                return CBContract.ComedyEntry.CONTENT_TYPE;
            case SORT_SETTING:
                return CBContract.SortEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = CBDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case COMEDY: {

                long _id = db.insert(CBContract.ComedyEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CBContract.ComedyEntry.buildComedyUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case SORT_SETTING: {
                long _id = db.insert(CBContract.SortEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CBContract.SortEntry.buildSortSettingUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = CBDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnRows;
        if (selection == null){selection="1";}
        switch (match) {
            case COMEDY: {
                returnRows = db.delete(CBContract.ComedyEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case SORT_SETTING: {
                returnRows = db.delete(CBContract.SortEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(returnRows !=0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return returnRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = CBDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnRows;
        switch (match) {
            case COMEDY: {
                returnRows = db.update(CBContract.ComedyEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case SORT_SETTING: {
                returnRows = db.update(CBContract.SortEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(returnRows !=0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return returnRows;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = CBDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COMEDY:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(CBContract.ComedyEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        CBDbHelper.close();
        super.shutdown();
    }
}
