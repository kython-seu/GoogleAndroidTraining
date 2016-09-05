package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

/**
 * Created by kason_zhang on 9/5/2016.
 */
public class MyDatabaseUtil {
    private static final String TAG = "MyDatabaseUtil";
    private MyDbOpenHelper myDbOpenHelper;
    private static final String DATABASE_NAME = "BookStore.db";//specify the database name;
    private static final int DATABASE_VERSION = 1;//specify the database version;
    private Context mContext;
    public MyDatabaseUtil(Context mContext){
        this.mContext = mContext;
        //to access the database
        myDbOpenHelper = new MyDbOpenHelper(mContext,DATABASE_NAME,null,DATABASE_VERSION);
    }
    
    public void create(){
        myDbOpenHelper.getWritableDatabase();
    }
    //CRUD,CREATE,RETRIEVE,UPDATE,DELETE
    public void insert(){
        final SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Random random = new Random();
        int nextInt = random.nextInt(100);
        values.put("author","kason"+nextInt);
        values.put("pages",509+nextInt);
        values.put("price",47.5+nextInt);
        values.put("name","Java");
        writableDatabase.insert("Book",null,values);
        Log.i(TAG, "insert success");
    }

    public void delete(){
        final SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
        int bookid = writableDatabase.delete("Book", "id = ?", new String[]{"1"});
        Log.i(TAG, "delete: --->"+bookid);
    }

    public void update(){
        final SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name","Python");
        int book = writableDatabase.update("Book", values, "pages > ?", new String[]{"500"});
        Log.i(TAG, "update: "+book);
    }

    public void query(){
        final SQLiteDatabase writableDatabase = myDbOpenHelper.getReadableDatabase();
        Cursor cursor = writableDatabase.query("Book", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Log.i(TAG, "query: name: "+name);
        }
        cursor.close();
    }
}
