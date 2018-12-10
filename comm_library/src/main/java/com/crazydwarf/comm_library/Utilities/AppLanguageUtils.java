package com.crazydwarf.comm_library.Utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Locale;

public class AppLanguageUtils
{
    private class ConstantLanguages
    {
        // 简体中文
        public static final String SIMPLIFIED_CHINESE = "zh";
        // 英文
        public static final String ENGLISH = "en";
        // 法语
        public static final String FRANCE = "fr";
    }

    private static HashMap<String, Locale> mAllLanguages = new HashMap<String, Locale>(2)
    {{
        put(ConstantLanguages.SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        put(ConstantLanguages.ENGLISH, Locale.ENGLISH);
        put(ConstantLanguages.FRANCE, Locale.FRANCE);
    }};

    private static boolean isSupportLanguage(String language) {
        return mAllLanguages.containsKey(language);
    }

    public static String getSupportLanguage(String language) {
        if (isSupportLanguage(language)) {
            return language;
        }

        return ConstantLanguages.ENGLISH;
    }

    /**
     * 获取指定语言的locale信息，如果指定语言不存在{@link #mAllLanguages}，返回本机语言，如果本机语言不是语言集合中的一种{@link #mAllLanguages}，返回英语
     *
     * @param language language
     * @return
     */
    public static Locale getLocaleByLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mAllLanguages.get(language);
        } /*else {
            Locale locale = Locale.getDefault();
            for (String key : mAllLanguages.keySet()) {
                if (TextUtils.equals(mAllLanguages.get(key).getLanguage(), locale.getLanguage())) {
                    return locale;
                }
            }
        }*/
        return Locale.SIMPLIFIED_CHINESE;
    }

    public static Context attachBaseContext(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        } else {
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Resources resources = context.getResources();
        Locale locale = AppLanguageUtils.getLocaleByLanguage(language);

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Context updateResources(Context context) {
        Resources resources = context.getResources();
        String language = AppLanguageUtils.getSavedLanguage(context);
        Locale locale = AppLanguageUtils.getLocaleByLanguage(language);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    public static void changeAppLanguage(Context context, String newLanguage) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        // app locale
        Locale locale = getLocaleByLanguage(newLanguage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }

        // updateConfiguration
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }

    @SuppressWarnings("deprecation")
    public static void changeAppLanguage(Context context,Locale locale,boolean persistence)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            configuration.setLocale(locale);
        }
        else
        {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);
        if (persistence)
        {
            saveLanguageSetting(context, locale);
        }
    }

    private static String LANGUAGE = "LANGUAGE";
    private static String COUNTRY = "COUNTRY";

    public static void saveLanguageSetting(Context context, Locale locale) {

        String name = context.getPackageName() + "_" + LANGUAGE;
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putString(LANGUAGE, locale.getLanguage()).apply();
        //preferences.edit().putString(COUNTRY, locale.getCountry()).apply();
    }

    public static int getSelePosByLanguage(String abbrCountry)
    {
        //TODO : 直接使用abbrCountry == "en"不能给出正确判断，为何？
        if(abbrCountry.compareTo("en") == 0)
        {
            return 1;
        }
        else if(abbrCountry.compareTo("fr") == 0)
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }

    public static String getLanguageBySelePos(int selePos)
    {
        if(selePos == 1)
        {
            return "en";
        }
        else if(selePos == 2)
        {
            return "fr";
        }
        else
        {
            return "zh";
        }
    }

    public static String getSavedLanguage(Context context)
    {
        String name = context.getPackageName() + "_LANGUAGE";
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String language = preferences.getString("LANGUAGE","zh");
        return language;
    }
}
