package com.crazydwarf.comm_library.Utilities;

import android.content.SharedPreferences;

public class GVariable
{
    /**
     * 将SharedPreferences中存储的数据在LauncherActivity时提取并保存到如下的全局变量中
     * 此处需要和Constants中规定保存在SharedPreferences中的变量一致
     * @param STORED_COUNTRY 保存选择国家的名称
     * @param STORED_COUNTRY_FLAG_RES 待显示的国旗resourceid
     * @param STORED_COUNTRY_POS 待显示的国家所在序列的位置，用于国家选择对话框的显示
     * @param STORED_COUNTRY_CODE 保存选择国家代码
     * @param STORED_COUNTRY_OPERATORS 选择国家，移动运营商的列表对应的数组资源号
     *
     * @param STORED_LANGUAGE 选择的语言
     * @param STORED_LANGUAGE_FLAG 选择语言对应的国旗资源
     *
     * @param STORED_USER_LOGIN_STATUS 登录状态，用户名登录或者直接登录
     *
     * 下面是sim卡的状态，如果未获得相应权限，则下面变量保持为null
     * @param STORED_OPERATOR_SIM1 sim1的运营商
     * @param STORED_OPERATOR_COUNTRY_SIM1 sim1的运营商国家
     * @param STORED_OPERATOR_SIM2 sim2的运营商
     * @param STORED_OPERATOR_COUNTRY_SIM2 sim2的运营商国家
     *
     */
    public static String STORED_COUNTRY = null;
    public static int STORED_COUNTRY_FLAG_RES = -1;
    public static int STORED_COUNTRY_POS = -1;
    public static int STORED_COUNTRY_CODE = -1;
    public static int STORED_COUNTRY_OPERATORS = -1;

    public static String STORED_LANGUAGE = null;
    public static int STORED_LANGUAGE_FLAG = -1;

    public static String STORED_OPERATOR_SIM1 = null;
    public static String STORED_OPERATOR_COUNTRY_SIM1 = null;
    public static String STORED_OPERATOR_SIM2 = null;
    public static String STORED_OPERATOR_COUNTRY_SIM2 = null;
    public static boolean STORED_USER_LOGIN_STATUS = false;

    public static void setSelectedCountry(String storedCountry, int storedCountryCode, int storedCountryFlagRes,
                                          int storedCountryPos, int storedCountryOperators, SharedPreferences.Editor editor) {
        STORED_COUNTRY = storedCountry;
        STORED_COUNTRY_CODE = storedCountryCode;
        STORED_COUNTRY_FLAG_RES = storedCountryFlagRes;
        STORED_COUNTRY_POS = storedCountryPos;
        STORED_COUNTRY_OPERATORS = storedCountryOperators;
        editor.putString(Constants.SELECTED_COUNTRY,storedCountry);
        editor.putInt(Constants.SELECTED_COUNTRY_CODE,storedCountryCode);
        editor.putInt(Constants.SELECTED_COUNTRY_RES,storedCountryFlagRes);
        editor.putInt(Constants.SELECTED_COUNTRY_POS,storedCountryPos);
        editor.putInt(Constants.SELECTED_COUNTRY_OPERATOR,storedCountryOperators);
        editor.apply();
    }

    public static void setSelectedLanguage(String storedLanguage,int storedLanguageFlag,SharedPreferences.Editor editor) {
        STORED_LANGUAGE = storedLanguage;
        STORED_LANGUAGE_FLAG = storedLanguageFlag;
        editor.putString(Constants.SELECTED_LANGUAGE,storedLanguage);
        editor.putInt(Constants.SELECTED_LANGUAGE_FLAG,storedLanguageFlag);
        editor.apply();
    }

    public static void setStoredUserLoginStatus(boolean storedUserLoginStatus,SharedPreferences.Editor editor) {
        STORED_USER_LOGIN_STATUS = storedUserLoginStatus;
    }

    public static void setSelectSim1(String storedOperatorSim1,String storedOperatorCountrySim1,SharedPreferences.Editor editor) {
        STORED_OPERATOR_SIM1 = storedOperatorSim1;
        STORED_OPERATOR_COUNTRY_SIM1 = storedOperatorCountrySim1;
        editor.putString(Constants.OPERATOR_SIM1,storedOperatorSim1);
        editor.putString(Constants.OPERATOR_COUNTRY_SIM1,storedOperatorCountrySim1);
        editor.apply();
    }

    public static void setSelectSim2(String storedOperatorSim2,String storedOperatorCountrySim2,SharedPreferences.Editor editor) {
        STORED_OPERATOR_SIM2 = storedOperatorSim2;
        STORED_OPERATOR_COUNTRY_SIM2 = storedOperatorCountrySim2;
        editor.putString(Constants.OPERATOR_SIM2,storedOperatorSim2);
        editor.putString(Constants.OPERATOR_COUNTRY_SIM2,storedOperatorCountrySim2);
        editor.apply();
    }
}
