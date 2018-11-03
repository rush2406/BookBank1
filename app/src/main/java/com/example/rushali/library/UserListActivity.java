package com.example.rushali.library;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.rushali.library.data.BookContract;
import com.example.rushali.library.data.UserContract;

public class UserListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    String[] PROJECTION = {
            UserContract.UserEntry._ID,
            UserContract.UserEntry.COLUMN_NAME,
            UserContract.UserEntry.COLUMN_UID,
            UserContract.UserEntry.COLUMN_EMAIL,
            UserContract.UserEntry.COLUMN_DEPT
    };
    public static final int LOADER_ID = 30;
    UserCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ListView list= (ListView)findViewById(R.id.list);
       adapter = new UserCursorAdapter(this,null);
        list.setAdapter(adapter);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,
                UserContract.UserEntry.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    adapter.swapCursor(null);
    }
}
