package com.example.rushali.library.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rushali on 13/10/18.
 */

public class UserProvider extends ContentProvider {

    private static final int USERS = 100;
    private static final int USER_ID = 101;
    private static final String LOG_TAG = UserProvider.class.getSimpleName();
    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH, USERS);
        uriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH + "/#", USER_ID);
        return uriMatcher;
    }

    private UserDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new UserDbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                long id = db.insert(UserContract.UserEntry.TABLE_NAME, null, contentValues);
                if (id < 0) {
                    Toast.makeText(getContext(), "Insert Failed", Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "Insert failed");
                } else
                    getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(UserContract.UserEntry.CONTENT_URI, id);
            default:
                throw new UnsupportedOperationException("Unknown Uri = " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor c;
        switch (match) {
            case USERS:
                c = db.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case USER_ID:
                selection = UserContract.UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c = db.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri = " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return updateInventory(uri, contentValues, selection, selectionArgs);
            case USER_ID:
                selection = UserContract.UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateInventory(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for" + uri);
        }

    }

    private int updateInventory(Uri uri,ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        if (contentValues.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(UserContract.UserEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rows;
        switch (match) {
            case USERS:
                int rowsDeleted = db.delete(UserContract.UserEntry.TABLE_NAME, s, strings);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case USER_ID:
                s = UserContract.UserEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rows = db.delete(UserContract.UserEntry.TABLE_NAME, s, strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri = " + uri);
        }
        if (rows > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rows;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return UserContract.CONTENT_LIST_TYPE;
            case USER_ID:
                return UserContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }
}
