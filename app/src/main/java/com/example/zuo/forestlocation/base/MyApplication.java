package com.example.zuo.forestlocation.base;

import android.app.Application;

import org.xutils.x;

/**
 * Created by zuo on 2017/4/9.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
