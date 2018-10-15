package com.crazydwarf.africatopup.activity;

import android.app.Application;
import android.content.Context;

import com.crazydwarf.africatopup.Utilities.AppLanguageUtils;

public class BaseApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        //onLanguageChange();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(AppLanguageUtils.updateResources(base));
    }
}
