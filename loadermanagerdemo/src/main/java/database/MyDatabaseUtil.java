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
    private static final String TABLE_NAME = "Person";
    private MyDbOpenHelper myDbOpenHelper;
    private static final String DATABASE_NAME = "PersonDatabase.db";//specify the database name;
    private static final int DATABASE_VERSION = 2;//specify the database version;
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
        int nextInt = random.nextInt(30);
        values.put("name","kason"+nextInt);
        values.put("age",29+nextInt);
        writableDatabase.insert(TABLE_NAME,null,values);
        Log.i(TAG, "insert success");
    }

    public void delete(){
        final SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
        int personid = writableDatabase.delete(TABLE_NAME, "_id = ?", new String[]{"1"});
        Log.i(TAG, "delete: --->"+personid);
    }

    public void update(){
        final SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name","lili");
        int person_update_number = writableDatabase.update(TABLE_NAME, values, "age > ?", new String[]{"25"});
        Log.i(TAG, "update: "+person_update_number);
    }

    public void query(){
        final SQLiteDatabase writableDatabase = myDbOpenHelper.getReadableDatabase();
        Cursor cursor = writableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            Log.i(TAG, "query: name: "+name+" id: "+_id+" age: "+age);
        }
        cursor.close();
    }
    public Cursor queryAll(){
        final SQLiteDatabase writableDatabase = myDbOpenHelper.getReadableDatabase();
        Cursor cursor = writableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
}
