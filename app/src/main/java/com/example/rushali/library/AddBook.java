package com.example.rushali.library;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import com.example.rushali.library.data.BookDbHelper;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AddBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Button b = (Button)findViewById(R.id.addbook);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBook();
                finish();
            }
        });
    }

    void insertBook()
    {
        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        EditText title = (EditText)findViewById(R.id.fillname);
        EditText auth = (EditText) findViewById(R.id.fillauth);
        EditText pub = (EditText) findViewById(R.id.fillpub);
        EditText id = (EditText) findViewById(R.id.fillid);
        EditText quan = (EditText) findViewById(R.id.fillquant);
        CheckBox cse = (CheckBox)findViewById(R.id.cse);
        CheckBox ece = (CheckBox)findViewById(R.id.ece);
        CheckBox eee = (CheckBox)findViewById(R.id.eee);
        CheckBox mech = (CheckBox)findViewById(R.id.mech);
        CheckBox civil = (CheckBox)findViewById(R.id.civil);
        CheckBox met = (CheckBox)findViewById(R.id.meta);
        CheckBox chem = (CheckBox)findViewById(R.id.chem);
        CheckBox bio = (CheckBox)findViewById(R.id.bio);

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

        Log.d("Adding",a);

        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_NAME,title.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_AUTHOR,auth.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_RESQUANT, Integer.parseInt(quan.getText().toString()));
        values.put(BookContract.BookEntry.COLUMN_TOTALQUANT,Integer.parseInt(quan.getText().toString()));
        values.put(BookContract.BookEntry.COLUMN_BOOKID,id.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_RESIDS,"");
        values.put(BookContract.BookEntry.COLUMN_RESERVE,"");
        values.put(BookContract.BookEntry.COLUMN_TAGS,a);
        values.put(BookContract.BookEntry.COLUMN_PUBLISHER,pub.getText().toString());

        // Insert a new row for pet in the database, returning the ID of that new row.
        Uri uri = getContentResolver().insert(BookContract.BookEntry.CONTENT_URI,values);

        // Show a toast message depending on whether or not the insertion was successful
        if (uri == null) {
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
        }

    }

}
