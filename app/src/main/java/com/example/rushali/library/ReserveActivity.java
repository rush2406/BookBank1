package com.example.rushali.library;

import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rushali.library.data.BookContract;

import java.util.ArrayList;
import java.util.Arrays;

public class ReserveActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mCurrentUri;
    String id;
    TextView name,author,bookid,publisher,tags;
    Button reserve;
    private static  final int LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        name =(TextView) findViewById(R.id.fillname);
        author = (TextView)findViewById(R.id.fillauth);
        publisher = (TextView)findViewById(R.id.fillpub);
        bookid = (TextView)findViewById(R.id.fillid);
        reserve = (Button) findViewById(R.id.resbutton);
        tags = (TextView)findViewById(R.id.tags);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        Intent intent = getIntent();
        mCurrentUri = intent.getData();
        id = intent.getStringExtra("userid");

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);

    }

    private void update() {

        String projection[] = {
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_NAME,
                BookContract.BookEntry.COLUMN_RESIDS
        };
        Cursor c = getContentResolver().query(mCurrentUri,projection,null,null,null);
        c.moveToFirst();
        String current = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_RESIDS));
        current+=" ";
        current+= id;

       Log.d("printing",current);

       /* ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_NAME, name.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_BOOKID, bookid.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_PUBLISHER,publisher.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_AUTHOR, author.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_RESIDS,);



        int rowsAffected = getContentResolver().update(mCurrentUri, values, null, null);

        if (rowsAffected == 0) {
            Toast.makeText(getApplicationContext(), "Error with updating ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
            finish();
        }*/
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_NAME,
                BookContract.BookEntry.COLUMN_BOOKID,
                BookContract.BookEntry.COLUMN_AUTHOR,
                BookContract.BookEntry.COLUMN_PUBLISHER,
                BookContract.BookEntry.COLUMN_TOTALQUANT,
                BookContract.BookEntry.COLUMN_RESIDS,
                BookContract.BookEntry.COLUMN_TAGS,
                BookContract.BookEntry.COLUMN_RESQUANT
        };
        return new CursorLoader(this, mCurrentUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_NAME);
            int authColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_AUTHOR);
            int pubColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PUBLISHER);
            int bookidColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOKID);
            int tag = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_TAGS);
            int total = cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_TOTALQUANT));
            int res = cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_RESQUANT));
            if(total-res==0)
                reserve.setVisibility(View.INVISIBLE);


            name.setText(cursor.getString(nameColumnIndex));
            author.setText(cursor.getString(authColumnIndex));
            publisher.setText(cursor.getString(pubColumnIndex));
            bookid.setText(cursor.getString(bookidColumnIndex));
            tags.setText(cursor.getString(tag));

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
