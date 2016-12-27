package com.example.eightleaves.comedybox.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class CBContract {

    public static final String CONTENT_AUTHORITY = "com.example.eightleaves.comedybox";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CB_AUDIO = "comedy";
    public static final String PATH_SORT_SETTING = "sort";

    public static final class SortEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SORT_SETTING).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SORT_SETTING;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SORT_SETTING;

        public static Uri buildSortSettingUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "sort";
        public static final String COLUMN_SORT_SETTING = "sort_setting";
    }


    public static final class ComedyEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CB_AUDIO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CB_AUDIO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CB_AUDIO;



        public static final String TABLE_NAME = "comedy";
        public static final String COLUMN_COMEDY_ID = "comedy_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW =  "overview";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_SORT_KEY = "sort_id";

        public static Uri buildComedyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildComedySort(String sortSetting) {
            return CONTENT_URI.buildUpon().appendPath(sortSetting).build();
        }

        public static Uri buildComedySortWithComedyId(String sortSetting, long comedyId) {
            return CONTENT_URI.buildUpon().appendPath(sortSetting)
                    .appendPath(Long.toString(comedyId)).build();
        }

        public static String getSortSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getComedyIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }
    }
}
