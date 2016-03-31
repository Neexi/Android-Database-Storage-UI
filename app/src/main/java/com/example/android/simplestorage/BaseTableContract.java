package com.example.android.simplestorage;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by new on 29/03/2016.
 */
public final class BaseTableContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.simplestorage";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DEFAULT = "default";

    public BaseTableContract() {

    }

    public static abstract class DefaultTable implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEFAULT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DEFAULT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DEFAULT;
        public static final String TABLE_NAME = "default";
        public static final String COLUMN_ITEM_NAME = "name";
        public static final String COLUMN_ITEM_QUANTITY = "quantity";
        public static final String COLUMN_ITEM_EXTRA = "extra";
        public static final String COLUMN_ITEM_FULL = "full";
        public static final String COLUMN_FULL_MATCHES_EXTRA = "fullMatchesExtra";

        public static Uri buildDefaultUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static int getIdFromUri (Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }
}
