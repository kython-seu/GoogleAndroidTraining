package com.ui.kason_zhang.savingdata;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends AppCompatActivity {

    private static final String TAG = "ContentProviderActivity";
    private ListView listView;
    ArrayAdapter<String> mArrayAdapter;
    List<String> mList = new ArrayList<>();
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        listView = (ListView)findViewById(R.id.contactList);
        context = this;
        getData();
        mArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mList);

        listView.setAdapter(mArrayAdapter);
    }

    private void getData() {
        //GET THE Contract message
        Uri contact_Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(contact_Uri,null,null,null,null);
        try {
            if(cursor != null){
                while(cursor.moveToNext()){
                    int columns = cursor.getColumnCount();
                    for(int i = 0; i < columns; i++){
                        String name = cursor.getColumnName(i);
                        String string = cursor.getString(cursor.getColumnIndex(name));
                        Log.i(TAG, "getData: name:"+name+" "+string);
                        mList.add(name+"-->"+string);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(cursor!=null){
                cursor.close();
            }
        }
    }
}
