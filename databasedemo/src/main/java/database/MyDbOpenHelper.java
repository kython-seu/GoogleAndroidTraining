package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kason_zhang on 9/1/2016.
 */
public class MyDbOpenHelper extends SQLiteOpenHelper{

    private static final String TAG = "MyDbOpenHelper";
    private Context mContext;
    private static final String CREATE_BOOK = "create table Book ("+
            "id integer primary key autoincrement,"+
            "author text,"+
            "price real,"+
            "pages integer,"+
            "name text)";
    private static final String CREATE_CATEGORY = "create table Category ("
            +"id integer primary key autoincrement,"
            +"category_name text,"
            +"category_code integer)";
    public MyDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Log.i(TAG, "Database has created successfully");
        //Toast.makeText(mContext,"Database has created successfully",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists table Book");
        db.execSQL("drop table if exists table Category");
        onCreate(db);
    }
}