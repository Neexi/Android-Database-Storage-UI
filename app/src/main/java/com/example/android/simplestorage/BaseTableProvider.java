package com.example.android.simplestorage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by new on 30/03/2016.
 */
public class BaseTableProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BaseTableHelper btHelper;

    private static final int DEF_ALL_ENTRIES = 100;
    private static final int DEF_SINGLE_ENTRY = 101;

    @Override
    public boolean onCreate() {
        btHelper = new BaseTableHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case DEF_ALL_ENTRIES:
            {
                retCursor = btHelper.getReadableDatabase().query(
                        BaseTableContract.DefaultTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case DEF_SINGLE_ENTRY:
            {
                int entryId = BaseTableContract.DefaultTable.getIdFromUri(uri);
                selection = BaseTableContract.DefaultTable.TABLE_NAME + "." + BaseTableContract.DefaultTable._ID + " = ?";
                selectionArgs = new String[]{Integer.toString(entryId)};
                retCursor = btHelper.getReadableDatabase().query(
                        BaseTableContract.DefaultTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            // Student: Uncomment and fill out these two cases
            case DEF_ALL_ENTRIES:
                return BaseTableContract.DefaultTable.CONTENT_TYPE;
            case DEF_SINGLE_ENTRY:
                return BaseTableContract.DefaultTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = btHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case DEF_ALL_ENTRIES:
                long _id = db.insert(BaseTableContract.DefaultTable.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BaseTableContract.DefaultTable.buildDefaultUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BaseTableContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, BaseTableContract.PATH_DEFAULT, DEF_ALL_ENTRIES);
        matcher.addURI(authority, BaseTableContract.PATH_DEFAULT + "/*", DEF_SINGLE_ENTRY);
        return matcher;
    }
}
