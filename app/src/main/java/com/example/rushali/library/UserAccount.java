package com.example.rushali.library;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rushali.library.data.BookContract;
import com.example.rushali.library.data.UserContract;
import com.example.rushali.library.data.UserDbHelper;

public class UserAccount extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        TextView name = (TextView) findViewById(R.id.name);
        TextView mail = (TextView)findViewById(R.id.mail);
        TextView issue = (TextView)findViewById(R.id.issue);
        TextView res = (TextView)findViewById(R.id.res);
        TextView fine = (TextView)findViewById(R.id.fine);

        String sql1 = "SELECT * FROM " + UserContract.UserEntry.TABLE_NAME + " WHERE userid = ?";
        UserDbHelper DbHelper = new UserDbHelper(getApplicationContext());
        SQLiteDatabase db1 = DbHelper.getReadableDatabase();
        Cursor c = db1.rawQuery(sql1, new String[]{AccountActivity.uid});
        c.moveToFirst();
        String fname = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_UID));
        String email =c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_EMAIL));
        String issued = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_ISSUED));
        String reserve = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_RESERVE));
        int fin = c.getInt(c.getColumnIndex(UserContract.UserEntry.COLUMN_FINE));

        name.setText(fname);
        mail.setText(email);
        issue.setText(issued);
        fine.setText(String.valueOf(fin));
        res.setText(reserve);
    }
}
