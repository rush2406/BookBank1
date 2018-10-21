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
import android.widget.Toast;
import com.example.rushali.library.data.BookContract.BookEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mCurrentUri;
    EditText name,author,bookid,total,publisher;
    String res;
    private static  final int LOADER_ID = 0;
    CheckBox cse,ece,eee,mech,met,civil,chem,bio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        
        name =(EditText) findViewById(R.id.fillname);
        author = (EditText)findViewById(R.id.fillauth);
        publisher = (EditText)findViewById(R.id.fillpub);
        bookid = (EditText)findViewById(R.id.fillid);
        total=(EditText)findViewById(R.id.fillquant);
        cse = (CheckBox)findViewById(R.id.cse);
        ece = (CheckBox)findViewById(R.id.ece);
        eee = (CheckBox)findViewById(R.id.eee);
        mech = (CheckBox)findViewById(R.id.mech);
        civil = (CheckBox)findViewById(R.id.civil);
        met = (CheckBox)findViewById(R.id.meta);
        chem = (CheckBox)findViewById(R.id.chem);
        bio = (CheckBox)findViewById(R.id.bio);


        Intent intent = getIntent();
        mCurrentUri = intent.getData();

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
        Button up = (Button)findViewById(R.id.update);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void update() {
        ContentValues values = new ContentValues();
        String a="";
        if(cse.isChecked())
            a+="CSE ";
        if(ece.isChecked())
            a+="ECE ";
        if(eee.isChecked())
            a+="EEE ";
        if(mech.isChecked())
            a+="Mech ";
        if(civil.isChecked())
            a+="Civil ";
        if(met.isChecked())
            a+="Metallurgy ";
        if(chem.isChecked())
            a+="Chemical ";
        if(bio.isChecked())
            a+="BioTech ";


        res= a;
        Log.d("updated",res);
        values.put(BookEntry.COLUMN_NAME, name.getText().toString());
        values.put(BookEntry.COLUMN_BOOKID, bookid.getText().toString());
        values.put(BookEntry.COLUMN_TOTALQUANT, Integer.parseInt(total.getText().toString()));
        values.put(BookEntry.COLUMN_PUBLISHER,publisher.getText().toString());
        values.put(BookEntry.COLUMN_AUTHOR, author.getText().toString());
        values.put(BookEntry.COLUMN_TAGS,res);

        int rowsAffected = getContentResolver().update(mCurrentUri, values, null, null);

        if (rowsAffected == 0) {
            Toast.makeText(getApplicationContext(), "Error with updating ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    void del()
    {
        int rows = getContentResolver().delete(mCurrentUri,null,null);
        if(rows==-1)
            Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"deleted",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_del, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.del)
        {
            del();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_NAME,
                BookEntry.COLUMN_BOOKID,
                BookEntry.COLUMN_AUTHOR,
                BookEntry.COLUMN_PUBLISHER,
                BookEntry.COLUMN_TOTALQUANT,
                BookEntry.COLUMN_RESIDS,
                BookEntry.COLUMN_TAGS,
                BookEntry.COLUMN_RESQUANT
        };
        return new CursorLoader(this, mCurrentUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_NAME);
            int authColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_AUTHOR);
            int pubColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PUBLISHER);
            int bookidColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOKID);
            int totalNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_TOTALQUANT);
            String x = cursor.getString(cursor.getColumnIndex(BookEntry.COLUMN_TAGS));
            String y="";
            ArrayList<String> list = new ArrayList<>(Arrays.asList(x.split(" ")));
            for(int i=0;i<list.size();i++)
                y+=list.get(i);
            Log.d("updated",x);

            if(list.contains("CSE"))
                cse.setChecked(true);
            else
                cse.setChecked(false);
            if(list.contains("ECE"))
                ece.setChecked(true);
            else
                ece.setChecked(false);
            if(list.contains("EEE"))
                eee.setChecked(true);
            else
                eee.setChecked(false);
            if(list.contains("Mech"))
                mech.setChecked(true);
            else
                mech.setChecked(false);
            if(list.contains("Chemical"))
                chem.setChecked(true);
            else
                chem.setChecked(false);
            if(list.contains("Civil"))
                civil.setChecked(true);
            else
                civil.setChecked(false);
            if(list.contains("Metallurgy"))
                met.setChecked(true);
            else
                met.setChecked(false);
            if(list.contains("BioTech"))
                bio.setChecked(true);
            else
                bio.setChecked(false);


            name.setText(cursor.getString(nameColumnIndex));
            author.setText(cursor.getString(authColumnIndex));
            publisher.setText(cursor.getString(pubColumnIndex));
            bookid.setText(String.valueOf(cursor.getString(bookidColumnIndex)));
            total.setText(String.valueOf(cursor.getInt(totalNameColumnIndex)));

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
