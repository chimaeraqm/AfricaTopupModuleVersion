package com.crazydwarf.comm_library.Utilities;

public interface Constants
{
    /**
     * SharedPreferences保存用户界面基本设置
     * @param SELECTED_COUNTRY 保存选择国家的名称
     * @param SELECTED_COUNTRY_POS 保存选择国家的国旗资源在列表中的位置，用于国家选择对话框弹出后的高亮显示
     * @param SELECTED_COUNTRY_RES 保存选择国家的国旗资源代码
     * @param SELECTED_COUNTRY_CODE 保存选择国家代码
     * @param SELECTED_COUNTRY_OPERATOR 保存选择国家的运营商列表
     * @param SELECTED_LANGUAGE 用户选择的语言
     * @param SELECTED_LANGUAGE_FLAG 用户选择的语言对应的国旗资源
     *
     * @param OPERATOR_SIM1 本机sim1的运营商
     * @param OPERATOR_COUNTRY_SIM1 本机sim1的运营国家
     * @param OPERATOR_SIM2 本机sim2的运营商
     * @param OPERATOR_COUNTRY_SIM2 本机sim2的运营国家
     */
    String USER_PREFS = "user_prefs";
    String SELECTED_COUNTRY = "selected_country";
    String SELECTED_COUNTRY_POS = "selected_country_pos";
    String SELECTED_COUNTRY_RES = "selected_country_res";
    String SELECTED_COUNTRY_CODE = "selected_country_code";
    String SELECTED_COUNTRY_OPERATOR = "selected_country_operator";
    String SELECTED_LANGUAGE = "selected language";
    String SELECTED_LANGUAGE_FLAG = "selected language flag";

    String OPERATOR_SIM1 = "operator_sim1";
    String OPERATOR_COUNTRY_SIM1 = "operator_country_sim1";
    String OPERATOR_SIM2 = "operator_sim2";
    String OPERATOR_COUNTRY_SIM2 = "operator_country_sim2";

    /**
     * SharedPreferences保存当前登录用户
     * @param CURRENT_USER_SETUP 保存当前登录用户的数据文件名称
     * @param CURRENT_USER_NAME 保存当前登录用户名
     * @param CURRENT_USER_PHONENO 保存当前登录用户手机号
     * @param CURRENT_USER_PASSWORD 保存当前登录用户密码
     * @param IS_FIRST_LOGIN 判断当前用户是否是第一次登录
     * @param IS_REMEMBER_PW 当前登录用户是否选择记住密码
     * @param IS_AUTO_LOGIN 当前登录用户是否选择自动登录
     */
    String CURRENT_USER_SETUP = "current_user_setup";
    String CURRENT_USER_NAME = "current_username";
    String CURRENT_USER_PHONENO = "current_user_phoneno";
    String CURRENT_USER_PASSWORD = "current_user_password";
    String IS_FIRST_LOGIN = "is_first_login";
    String IS_REMEMBER_PW = "is_remember_pw";
    String IS_AUTO_LOGIN = "is_auto_login";

    /**
     * 获取权限使用的 RequestCode
     */
    public static final int PERMISSIONS_REQUEST_CODE = 1002;

    public final static int REQUEST_READ_PHONE_STATE = 1;

    public static final String USERS_DATA_URL = "https://wx.dwarfworkshop.com/congo/user.php";

    public static final String DEFAULT_CHARSET = "UTF-8";

    public enum KEY_STATUS{
        RESULT_STATUS_FAILED,
        RESULT_STATUS_SUCCESS
    }

}
