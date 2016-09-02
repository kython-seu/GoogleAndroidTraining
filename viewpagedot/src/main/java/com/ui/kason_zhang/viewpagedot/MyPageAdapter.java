package com.ui.kason_zhang.viewpagedot;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kason_zhang on 9/1/2016.
 */
public class MyPageAdapter extends PagerAdapter {
    private List<View> viewList;

    public MyPageAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    //滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        //container.removeView(viewList.get(position % viewList.size()));
    }

    //每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if(viewList.get(position % viewList.size()).getParent()!=null){
            ((ViewPager)viewList.get(position % viewList.size()).getParent()).
                    removeView(viewList.get(position % viewList.size()));
        }
        container.addView(viewList.get(position % viewList.size()),0);
        return viewList.get(position % viewList.size());
    }
}
