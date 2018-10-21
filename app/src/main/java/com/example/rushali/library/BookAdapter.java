package com.example.rushali.library;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rushali on 20/10/18.
 */

public class BookAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Book> books;

    BookAdapter(Context context,ArrayList<Book> books)
    {
        this.books = books;
        this.context = context;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_item, null);

        Book b = (Book) getItem(position);

        TextView name = (TextView)convertView.findViewById(R.id.name);
        name.setText(b.getName());
        TextView auth = (TextView)convertView.findViewById(R.id.auth);
        auth.setText(b.getAuthor());
        TextView pub = (TextView)convertView.findViewById(R.id.pub);
        pub.setText(b.getPublisher());
        TextView bid = (TextView)convertView.findViewById(R.id.bid);
        bid.setText(b.getId());
        TextView total = (TextView) convertView.findViewById(R.id.total);
        total.setVisibility(View.INVISIBLE);
        TextView res = (TextView) convertView.findViewById(R.id.res);
        res.setVisibility(View.INVISIBLE);
        return convertView;
    }
}
