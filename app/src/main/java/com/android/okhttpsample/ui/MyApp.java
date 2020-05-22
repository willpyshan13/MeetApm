package com.android.okhttpsample.ui;

import android.app.Application;
import android.content.Context;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/11/29
 */

public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }


    public static Context getContext() {
        return context;
    }
}
