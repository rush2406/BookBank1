package com.example.rushali.library;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rushali.library.data.BookContract;

import org.w3c.dom.Text;

/**
 * Created by rushali on 11/10/18.
 */

public class BookCursorAdapter extends CursorAdapter {
    int flag=0;
    public BookCursorAdapter(Context context, Cursor c,int x) {
        super(context, c, 0);
        flag=x;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView id = (TextView)view.findViewById(R.id.bid);
        TextView pub = (TextView)view.findViewById(R.id.pub);
        TextView author = (TextView)view.findViewById(R.id.auth);
        TextView resq = (TextView)view.findViewById(R.id.res);
        TextView total = (TextView)view.findViewById(R.id.total);
        TextView resid = (TextView)view.findViewById(R.id.resids);
        TextView reserve = (TextView)view.findViewById(R.id.reserve);
        String productName = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_NAME));
        String bid = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOKID));
        String auth = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_AUTHOR));
        String publis = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PUBLISHER));
        int tot = cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_TOTALQUANT));
        int res = cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_RESQUANT));
        String list = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_RESIDS));
        String reserveids = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_RESERVE));
        resq.setText(String.valueOf(res));
        nameTextView.setText(productName);
        id.setText(bid);
        author.setText(auth);
        pub.setText(publis);
        total.setText(String.valueOf(tot));
        if(list.length()>0)
        resid.setText(list);
        else
            resid.setText(" ");
        if(reserveids.length()>0)
            reserve.setText(reserveids);
        else
            reserve.setText(" ");
        if(flag==1) {
            resid.setVisibility(View.INVISIBLE);
            reserve.setVisibility(View.INVISIBLE);
        }


    }
}
