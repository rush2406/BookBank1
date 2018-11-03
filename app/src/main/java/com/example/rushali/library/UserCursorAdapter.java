package com.example.rushali.library;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rushali.library.data.BookContract;
import com.example.rushali.library.data.UserContract;

/**
 * Created by rushali on 2/11/18.
 */

public class UserCursorAdapter extends CursorAdapter {

    public UserCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.user_list, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView email = (TextView)view.findViewById(R.id.mail);
        TextView dept = (TextView)view.findViewById(R.id.dept);
        TextView reg = (TextView)view.findViewById(R.id.reg);
        String Name = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME));
        String Email = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_EMAIL));
        String Dept = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_DEPT));
        String regno = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_UID));
        nameTextView.setText(Name);
        email.setText(Email);
        dept.setText(Dept);
        reg.setText(regno);
    }
}

