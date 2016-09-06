package com.ui.kason_zhang.customcontentproviderdemoclient;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.add)
    Button add;
    @Bind(R.id.query)
    Button query;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.delete)
    Button delete;
    @Bind(R.id.queryByIndex)
    Button queryByIndex;

    private String newid;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.i(TAG, "onCreate: ------------->");

    }

    @OnClick({R.id.add, R.id.query, R.id.update, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                ContentResolver contentResolver = MainActivity.this.getContentResolver();
                uri = Uri.parse("content://com.ui.kason_zhang." +
                        "customcontentproviderdemo.provider/book");
                ContentValues values = new ContentValues();
                values.put("author", "lilei");
                values.put("price", 99.99);
                values.put("pages", 100);
                values.put("name", "Python");
                Uri insert = contentResolver.insert(uri, values);
                List<String> result = insert.getPathSegments();
                /*for (String s : result) {
                    Log.i(TAG, "ContentProvider Add : " + s);
                }*/
                for(int i=0;i<result.size();i++){
                    Log.i(TAG, "index : "+i+" value : "+result.get(i));
                }
                newid = result.get(1);
                Log.i(TAG, "onCreate: insert successfully"+newid);
                break;
            case R.id.query:
                uri = Uri.parse("content://com.ui.kason_zhang." +
                        "customcontentproviderdemo.provider/book");
                Cursor cursor = MainActivity.this.getContentResolver()
                        .query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        Log.i(TAG, "name : " + name + " price: " + price
                                + " pages : " + pages + " author : " + author);
                    }
                    cursor.close();
                }
                break;
            case R.id.update:
                uri = Uri.parse("content://com.ui.kason_zhang." +
                        "customcontentproviderdemo.provider/book");
                ContentValues contentValues = new ContentValues();
                contentValues.put("name","C++ Style");
                int update_numbers = getContentResolver().update(uri, contentValues,
                        "id = ?",new String[]{"2"});
                break;
            case R.id.delete:
                uri = Uri.parse("content://com.ui.kason_zhang." +
                        "customcontentproviderdemo.provider/book/"+newid);
                int delete_numbers = getContentResolver().delete(uri, "id = ?", new String[]{newid});
                break;
        }
    }

    @OnClick(R.id.queryByIndex)
    public void onClick() {
        Uri uri = Uri.parse("content://com.ui.kason_zhang." +
                "customcontentproviderdemo.provider/book");
        Cursor cursor = getContentResolver().query(uri, null, "id = ?", new String[]{"2"}, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                Log.i(TAG, "name : " + name + " price: " + price
                        + " pages : " + pages + " author : " + author);
            }
            cursor.close();
        }
    }
}
