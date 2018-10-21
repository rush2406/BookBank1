package com.example.rushali.library.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rushali on 11/10/18.
 */

public class BookDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "books.db";

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " (" +
                BookContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BookContract.BookEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BookContract.BookEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                BookContract.BookEntry.COLUMN_PUBLISHER + " TEXT NOT NULL, " +
                BookContract.BookEntry.COLUMN_BOOKID + " TEXT NOT NULL, " +
                BookContract.BookEntry.COLUMN_RESQUANT + " INTEGER DEFAULT 0," +
                BookContract.BookEntry.COLUMN_TOTALQUANT + " INTEGER DEFAULT 0, " +
                BookContract.BookEntry.COLUMN_RESIDS + " TEXT NOT NULL, "+
                BookContract.BookEntry.COLUMN_TAGS + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}