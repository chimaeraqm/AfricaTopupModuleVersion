package com.crazydwarf.africatopup.Utilities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import java.util.Locale;

public class UserUtil
{
    private final static int REQUEST_READ_PHONE_STATE = 1;

    public static void verifyReadStatePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机分辨率从DP转成PX
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


//    public static Context attachBaseContext(Context context, String abbrCountry) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return updateResources(context, abbrCountry);
//        } else {
//            return context;
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.N)
//    private static Context updateResources(Context context, String abbrCountry) {
//        Resources resources = context.getResources();
//        Locale locale = getLocaleByLanguage(abbrCountry);
//        Configuration configuration = resources.getConfiguration();
//        configuration.setLocale(locale);
//        configuration.setLocales(new LocaleList(locale));
//        return context.createConfigurationContext(configuration);
//    }
//
//    private static Locale getLocaleByLanguage(String abbrCountry)
//    {
//        if(abbrCountry == "en")
//        {
//            return Locale.ENGLISH;
//        }
//        else if(abbrCountry == "fr")
//        {
//            return Locale.FRANCE;
//        }
//        else
//        {
//            return Locale.SIMPLIFIED_CHINESE;
//        }
//    }
//
//    @SuppressWarnings("deprecation")
//    public static void changeAppLanguage(Context context, String newLanguage) {
//        Resources resources = context.getResources();
//        Configuration configuration = resources.getConfiguration();
//
//        // app locale
//        Locale locale = getLocaleByLanguage(newLanguage);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            configuration.setLocale(locale);
//        } else {
//            configuration.locale = locale;
//        }
//
//        // updateConfiguration
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        resources.updateConfiguration(configuration, dm);
//    }


}
