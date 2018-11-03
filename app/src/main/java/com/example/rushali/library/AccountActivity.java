
package com.example.rushali.library;

import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rushali.library.data.BookContract;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ArrayList<Book> book = new ArrayList<>();
    String[] PROJECTION = {
            BookContract.BookEntry._ID,
            BookContract.BookEntry.COLUMN_NAME,
            BookContract.BookEntry.COLUMN_BOOKID,
            BookContract.BookEntry.COLUMN_AUTHOR,
            BookContract.BookEntry.COLUMN_PUBLISHER,
            BookContract.BookEntry.COLUMN_TOTALQUANT,
            BookContract.BookEntry.COLUMN_RESQUANT,
            BookContract.BookEntry.COLUMN_RESIDS,
            BookContract.BookEntry.COLUMN_RESERVE,
    };
    public static final int LOADER_ID = 100;
    BookCursorAdapter mCursorAdapter;
    public static String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent i = getIntent();
        uid = i.getStringExtra("userid");

        ListView list = (ListView) findViewById(R.id.books);
        mCursorAdapter = new BookCursorAdapter(this,null,1);
        list.setAdapter(mCursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(AccountActivity.this, ReserveActivity.class);
                Log.d("id = ",String.valueOf(id));
                Uri currentProductUri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, id);
                intent.setData(currentProductUri);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.acc)
        {
            Intent intent = new Intent(AccountActivity.this,UserAccount.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,
                BookContract.BookEntry.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor c) {
        mCursorAdapter.swapCursor(c);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

}
