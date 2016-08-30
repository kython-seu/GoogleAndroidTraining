package com.ui.kason_zhang.googleandroidtraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(1,100,1,"sharetext");
        menu.add(1,101,1,"shareBinary");
        //menu.add()
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100:
                shareText();
                break;
            case 101:
                shareBinary();
                break;
        }
        return true;
    }

    private void shareBinary() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        //intent.putExtra(Intent.EXTRA_STREAM,)
    }

    private void shareText() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "THIS IS TEXT");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"hehehhe"));
    }
}
