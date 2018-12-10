package com.crazydwarf.comm_library.activity;

import android.app.Application;
import android.content.Context;

import com.crazydwarf.comm_library.Utilities.AppLanguageUtils;

public class BaseApplication extends Application
{

    private static BaseApplication application;
    //private ActivityManager activityManager;

    @Override
    public void onCreate() {
        super.onCreate();
//        activityManager = new ActivityManager();
        //onLanguageChange();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(AppLanguageUtils.updateResources(base));
        application = this;
    }

    public static BaseApplication getApplication() {
        return application;
    }
}
