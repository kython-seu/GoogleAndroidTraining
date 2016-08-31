package com.ui.kason_zhang.sharefiles;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File dirfile = new File(MainActivity.this.getFilesDir(),"files");
        if(!dirfile.exists()){
            dirfile.mkdirs();
        }else if( !dirfile.isDirectory() && dirfile.canWrite() ){
            dirfile.delete();
            dirfile.mkdirs();
        }
        FileOutputStream mFileOutputStream = null;
        //create 10 files in /data/data/com.com.ui.kason_zhang.sharefiles/files/files directory.
        for(int i=0;i<10;i++){
            String filename= "file"+i+".txt";
            try {
                mFileOutputStream = new FileOutputStream(new File(dirfile,filename));
                mFileOutputStream.write("Hello.World".getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(mFileOutputStream!=null){
                    try {
                        mFileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.ui.kason_zhang.sharefiles",
                "com.ui.kason_zhang.sharefiles.FileSelectActivity"));
        startActivity(intent);
    }
}
