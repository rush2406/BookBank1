package com.example.rushali.library;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rushali.library.data.UserContract;
import com.example.rushali.library.data.UserDbHelper;

public class FineActivity extends AppCompatActivity {

    EditText student,amt;
    Button pay;
    String uid="";
    int paying=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine);

        student = (EditText)findViewById(R.id.stid);
        amt = (EditText) findViewById(R.id.amt);
        pay = (Button)findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finepay();
            }
        });
    }

    void finepay()
    {
        String sql1 = "SELECT * FROM " + UserContract.UserEntry.TABLE_NAME + " WHERE userid = ?";
        UserDbHelper DbHelper = new UserDbHelper(getApplicationContext());
        SQLiteDatabase db1 = DbHelper.getReadableDatabase();
        uid = student.getText().toString();
        if(amt.getText().toString().length()>0)
            paying = Integer.parseInt(amt.getText().toString());
        Cursor c = db1.rawQuery(sql1, new String[]{uid});
        Log.d("query",uid);
        if (c != null && c.moveToFirst()) {
            Log.d("Hello","Entered");
            int fine = c.getInt(c.getColumnIndex(UserContract.UserEntry.COLUMN_FINE));
            if(fine==0)
            {
                Toast.makeText(getApplicationContext(),"No fine",Toast.LENGTH_SHORT).show();
                finish();
            }
            if(fine>paying)
            {
                Toast.makeText(getApplicationContext(),"Insufficient amount",Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                if(fine<paying) {
                    Toast.makeText(getApplicationContext(), "Return " + String.valueOf(paying - fine), Toast.LENGTH_SHORT).show();
                }

                int ids = c.getInt(c.getColumnIndex(UserContract.UserEntry._ID));
                Uri currentProduct = ContentUris.withAppendedId(UserContract.UserEntry.CONTENT_URI, ids);
                ContentValues values = new ContentValues();
                values.put(UserContract.UserEntry.COLUMN_FINE,0);

                int rowsA = getContentResolver().update(currentProduct, values, null, null);
                if (rowsA == 0) {
                    Toast.makeText(getApplicationContext(), "Error with updating ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
        c.close();

    }
}
