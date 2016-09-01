package com.ui.kason_zhang.savingdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by kason_zhang on 9/1/2016.
 */
public class MyDbOpenHelper extends SQLiteOpenHelper{

    private Context mContext;
    private static final String CREATE_BOOK = "create table Book ("+
            "id integer primary key autoincrement,"+
            "author text,"+
            "price real,"+
            "pages integer,"+
            "name text)";
    public MyDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"Database has created successfully",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
