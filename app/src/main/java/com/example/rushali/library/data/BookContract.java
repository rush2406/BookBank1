package com.example.rushali.library.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rushali on 11/10/18.
 */

public class BookContract {


    public static final String CONTENT_AUTHORITY = "com.example.rushali.library";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "books";

    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

    public static final class BookEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH)
                .build();

        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_PUBLISHER="publisher";
        public static final String COLUMN_BOOKID = "bookid";
        public static final String COLUMN_RESQUANT ="resquant";
        public static final String COLUMN_TOTALQUANT="totalquant";
        public static final String COLUMN_RESIDS="resid";
        public static final String COLUMN_TAGS="tags";
        public static final String COLUMN_RESERVE="reserve";
    }
}
