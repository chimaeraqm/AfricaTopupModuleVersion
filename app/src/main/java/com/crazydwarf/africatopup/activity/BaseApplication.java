package com.crazydwarf.africatopup.activity;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.crazydwarf.africatopup.AppLanguageUtils;
import com.crazydwarf.africatopup.ConstantLanguages;
import com.crazydwarf.africatopup.R;

import java.util.Locale;

public class BaseApplication extends Application
{
//    private static BaseApplication sInstances;
//    private static Context sContext;

//    public static BaseApplication getInstances() {
//        return sInstances;
//    }
//
//    public static Context getContext() {
//        return sContext;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppLanguageUtils.changeAppLanguage(this, Locale.SIMPLIFIED_CHINESE,true);
//        sInstances = this;
//        sContext = this;
        //onLanguageChange();
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(AppLanguageUtils.attachBaseContext(base, getAppLanguage(base)));
//    }
//
//    /**
//     * Handling Configuration Changes
//     * @param newConfig newConfig
//     */
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        onLanguageChange();
//    }
//
//    private void onLanguageChange() {
//        AppLanguageUtils.changeAppLanguage(this, AppLanguageUtils.getSupportLanguage(getAppLanguage(this)));
//    }
//
//    private String getAppLanguage(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context)
//                .getString(context.getString(R.string.app_language_pref_key), ConstantLanguages.ENGLISH);
//    }
}
