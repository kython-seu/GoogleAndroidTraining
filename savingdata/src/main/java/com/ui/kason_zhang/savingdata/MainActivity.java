package com.ui.kason_zhang.savingdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Context context ;
    private Button createFiles;
    private Button createDb;
    private Button writeDb;
    private MyDbOpenHelper myDbOpenHelper;
    private Button queryDb;
    private Button deleteDb;
    private Button updateDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        context = MainActivity.this;

        createFiles = (Button)findViewById(R.id.createFiles);
        createDb = (Button)findViewById(R.id.createDB);
        writeDb = (Button)findViewById(R.id.WriteDb);
        queryDb = (Button)findViewById(R.id.queryDb);
        deleteDb = (Button)findViewById(R.id.deleteDb);
        updateDb = (Button)findViewById(R.id.updateDb);
        createFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeFileintointernal_filePath("internal_test.txt");

                storeFileintointernal_cachefilePath("internal_cache_test.txt");

                storeFileintoexternalFilesDir_private("externalFilesDir_private_test.txt");

                //storeFileintoExternalStoragePublicDirectory("externalStoragePublicDirectory_test.txt");
            }
        });
        myDbOpenHelper = new MyDbOpenHelper(context,"BookStore.db",null,1);
        createDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDbOpenHelper = new MyDbOpenHelper(context,"BookStore.db",null,1);
                //myDbOpenHelper.getWritableDatabase();
            }

        });

        writeDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("author","lisan");
                contentValues.put("price",22.99);
                contentValues.put("pages",450);
                long book_id = writableDatabase.insert("Book", null, contentValues);
                Log.i(TAG, "onClick: the row number that has been inserted: "+book_id);

                contentValues.clear();//i want to insert second data;
                contentValues.put("author","zhangkai");
                contentValues.put("price",62.99);
                contentValues.put("pages",380);
                contentValues.put("name","android");
                book_id = writableDatabase.insert("Book", null, contentValues);
                Log.i(TAG, "onClick: the row number that has been inserted: "+book_id);

                contentValues.clear();
                contentValues.put("author","xuyehong");
                contentValues.put("price",72.48);
                contentValues.put("pages",620);
                contentValues.put("name","Java");
                book_id = writableDatabase.insert("Book", null, contentValues);
                Log.i(TAG, "onClick: the row number that has been inserted: "+book_id);
            }
        });

        queryDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase readableDatabase = myDbOpenHelper.getReadableDatabase();
                String[] columns = {"author","price","pages","name"};
                String selection = "author = ?";
                String[] selectionArgs = {"zhangkai"};
                Cursor cursor = readableDatabase.query("Book",
                        columns,selection,selectionArgs,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int price = cursor.getInt(cursor.getColumnIndex("price"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        Log.i(TAG, "onClick: price is " + price + " name is: " + name+" author" +
                                author);
                    }while(cursor.moveToNext());
                }
            }
        });

        updateDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase readableDatabase = myDbOpenHelper.getReadableDatabase();
                String tablename = "Book";
                ContentValues contentValues = new ContentValues();
                contentValues.put("author","xuyehong");
                String whereClause = "author = ?";
                String[] whereArgs = {"zhangsan"};
                readableDatabase.update(tablename, contentValues, whereClause, whereArgs);
                Log.i(TAG, "onClick: All of the data is below");
                getAllData();
            }
        });

        deleteDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
                String tablename = "Book";
                String whereClause = "id = ?";
                String[] whereArgs = {"3"};
                writableDatabase.delete(tablename,whereClause,whereArgs);
                Log.i(TAG, "onClick: delete the row 3");
                getAllData();
            }
        });

    }

    private void getAllData(){
        SQLiteDatabase readableDatabase = myDbOpenHelper.getReadableDatabase();
        String[] columns = {"author","price","pages","name"};
        //String selection = "author = ?";
        //String[] selectionArgs = {"zhangkai"};
        Cursor cursor = readableDatabase.query("Book",
                columns,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                Log.i(TAG, "onClick: price is " + price + " name is: " + name+" author" +
                        author);
            }while(cursor.moveToNext());
        }
    }
    private void storeFileintoExternalStoragePublicDirectory(String s) {
        //get root directory of the directory_pictures-----public
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.i(TAG, "public path---->"+externalStoragePublicDirectory.getAbsolutePath());
        String albumName = "mypicture";
        File public_picture_fileDir = new File(externalStoragePublicDirectory,albumName);

        if(!public_picture_fileDir.mkdirs()){
            Log.i(TAG, "onCreate: public_picture_fileDir cannot create");
        }
        File file = new File(public_picture_fileDir,s);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream mFileOutputStream = null;
        try {
            mFileOutputStream = new FileOutputStream(file);
            mFileOutputStream.write("Hello,It is external  file private".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(mFileOutputStream != null){
                try {
                    mFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void storeFileintoexternalFilesDir_private(String s) {
        //get root directory of the DIRECTORY_PICTURES-----private
        File externalFilesDir_private = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.i(TAG, "onCreate: private path--->"+externalFilesDir_private.getAbsolutePath());
        String albumName = "mypicture";
        File private_picture_fileDir = new File(externalFilesDir_private,albumName);
        if(!private_picture_fileDir.mkdirs()){
            Log.i(TAG, "onCreate: private_picture_fileDir cannot create");
        }

        File file = new File(private_picture_fileDir,s);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream mFileOutputStream = null;
        try {
            mFileOutputStream = new FileOutputStream(file);
            mFileOutputStream.write("Hello,It is external  file private".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(mFileOutputStream != null){
                try {
                    mFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void storeFileintointernal_cachefilePath(String s) {
        File internal_cachefilePath = context.getCacheDir();

        Log.i(TAG, "onCreate: internal_cachefilePath--->"+internal_cachefilePath.getAbsolutePath());
        File file = new File(internal_cachefilePath,s);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream mFileOutputStream = null;
        try {
            mFileOutputStream = new FileOutputStream(file);
            mFileOutputStream.write("Hello,It is internal cache file".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(mFileOutputStream != null){
                try {
                    mFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void storeFileintointernal_filePath(String s) {
        File internal_filePath = context.getFilesDir();
        Log.i(TAG, "onCreate: internal_filePath---->"+internal_filePath.getAbsolutePath());
        File file = new File(internal_filePath,s);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream mFileOutputStream = null;
        try {
            mFileOutputStream = new FileOutputStream(file);
            mFileOutputStream.write("Hello,It is internal file".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(mFileOutputStream != null){
                try {
                    mFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
