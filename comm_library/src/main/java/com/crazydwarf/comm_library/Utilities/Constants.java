package com.crazydwarf.comm_library.Utilities;

public interface Constants
{
    /**
     * SharedPreferences保存用户界面基本设置
     * @param SELECTED_COUNTRY_POS 保存选择国家的国旗资源在列表中的位置，用于国家选择对话框弹出后的高亮显示
     * @param SELECTED_COUNTRY_RES 保存选择国家的国旗资源代码
     * @param SELECTED_COUNTRY_CODE 保存选择国家代码
     * @param SELECTED_COUNTRY_OPERATOR 保存选择国家的运营商列表
     */
    String USER_PREFS = "user_prefs";
    String SELECTED_COUNTRY_POS = "selected_country_pos";
    String SELECTED_COUNTRY_RES = "selected_country_res";
    String SELECTED_COUNTRY_CODE = "selected_country_code";
    String SELECTED_COUNTRY_OPERATOR = "selected_country_operator";

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
