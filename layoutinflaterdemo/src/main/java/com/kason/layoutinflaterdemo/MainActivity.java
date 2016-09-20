package com.kason.layoutinflaterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*LinearLayout linearLayout = (LinearLayout)findViewById(R.id.main_linear);
        //LayoutInflater layoutInflater = (LayoutInflater)this
               // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View inflate = layoutInflater.inflate(R.layout.button, null);
        linearLayout.addView(inflate);*/
    }
}
