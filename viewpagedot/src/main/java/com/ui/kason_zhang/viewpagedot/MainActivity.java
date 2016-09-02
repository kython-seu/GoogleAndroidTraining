package com.ui.kason_zhang.viewpagedot;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager viewPage;
    private LinearLayout linearLayout;
    private int currentPage;
    private List<View> viewList;
    private List<ImageView> dotList;
    private Handler mHandler;
    private boolean isAuto = true;
    private static final int PAGE_SIZE = 2;
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
        initView();
        initData();
        MyPageAdapter myPageAdapter = new MyPageAdapter(viewList);
        viewPage.setAdapter(myPageAdapter);
        //viewPage.setOnPageChangeListener(new MyPageChangedListenr());
        viewPage.addOnPageChangeListener(new MyPageChangedListenr());
        //mHandler.postDelayed(r,3000);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(isAuto){//
                currentPage = (currentPage) % PAGE_SIZE;
                viewPage.setCurrentItem(currentPage);
                mHandler.postDelayed(r,3000);
            }else{
                mHandler.postDelayed(r,3000);
            }
        }
    };
    private void initData() {
        currentPage = 0;//set the current page equal 0;the first page
        int[] res = {R.drawable.blue,R.drawable.green,R.drawable.orange,R.drawable.red};
        for(int i = 0; i < PAGE_SIZE; i++){
            View view = View.inflate(this,R.layout.page_view,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageview);
            imageView.setImageResource(res[i%PAGE_SIZE]);
            viewList.add(view);

            //view = View.inflate(this,R.layout.dot,null);
            //ImageView dot = (ImageView)view.findViewById(R.id.dotview);
            //dot.setImageResource(R.drawable.dot_blur);
            ImageView imageView1 = null;
            if( i == 0){
                imageView1 = new ImageView(this);
                imageView1.setImageResource(R.drawable.dot_focus);
            }else {
                imageView1 = new ImageView(this);
                imageView1.setImageResource(R.drawable.dot_blur);
            }
            dotList.add(imageView1);
            linearLayout.addView(imageView1);
        }
    }

    private void initView() {
        mHandler = new Handler();
        viewList = new ArrayList<>();
        dotList = new ArrayList<>();
        viewPage = (ViewPager)findViewById(R.id.viewPage);
        linearLayout = (LinearLayout)findViewById(R.id.dots);//add the dots in the linearlayout
    }

    private class MyPageChangedListenr implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            position = position % PAGE_SIZE;
            for (int i = 0; i < PAGE_SIZE; i++) {
                if (i == position) {//let the dot be the red
                    ((ImageView) linearLayout.getChildAt(i)).
                            setImageResource(R.drawable.dot_focus);
                    currentPage = position;
                } else {
                    ((ImageView) linearLayout.getChildAt(i)).
                            setImageResource(R.drawable.dot_blur);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            switch (state) {
                //SCROLL_STATE_DRAGGING
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isAuto = false;
                    break;
                //SCROLL_STATE_SETTLING2
                case ViewPager.SCROLL_STATE_SETTLING:
                    isAuto = true;
                    break;
                default:
                    break;
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
