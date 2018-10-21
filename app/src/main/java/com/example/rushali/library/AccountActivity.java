
package com.example.rushali.library;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rushali.library.data.BookContract;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    ArrayList<Book> book = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent i = getIntent();
        final String uid = i.getStringExtra("userid");
        query();

        ListView list = (ListView) findViewById(R.id.books);
        BookAdapter adapter = new BookAdapter(this, book);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AccountActivity.this, ReserveActivity.class);
                Log.d("id = ",String.valueOf(id));
                Uri currentProductUri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, id+1);
                intent.setData(currentProductUri);
                intent.putExtra("userid",uid);
                startActivity(intent);
            }
        });
    }

    void query()
    {
        String[] proj={
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_NAME,
                BookContract.BookEntry.COLUMN_AUTHOR,
                BookContract.BookEntry.COLUMN_TAGS,
                BookContract.BookEntry.COLUMN_BOOKID,
                BookContract.BookEntry.COLUMN_PUBLISHER,
                BookContract.BookEntry.COLUMN_TOTALQUANT,
                BookContract.BookEntry.COLUMN_RESQUANT,
                BookContract.BookEntry.COLUMN_RESIDS
        };
        Cursor c = getContentResolver().query(BookContract.BookEntry.CONTENT_URI,proj,null,null,null,null);
       int count=c.getCount();
       c.moveToFirst();
        while(count>0)
        {
            String name = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_NAME));
            String author = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_AUTHOR));
            String pub = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_PUBLISHER));
            String id = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_BOOKID));
            String tags = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_TAGS));
            String resids = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_RESIDS));
            int total = c.getInt(c.getColumnIndex(BookContract.BookEntry.COLUMN_TOTALQUANT));
            int res = c.getInt(c.getColumnIndex(BookContract.BookEntry.COLUMN_RESQUANT));

            Book b = new Book(name,author,pub,id,total,res,resids,tags);
            book.add(b);

            count--;
            c.moveToNext();
        }

    }
}
