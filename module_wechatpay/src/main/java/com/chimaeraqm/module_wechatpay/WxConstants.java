package com.chimaeraqm.module_wechatpay;

public interface WxConstants
{
    String WECHAT_APP_ID = "wxd930ea5d5a258f4f";

    String ORDER_REQUEST_URL = "https://wx.dwarfworkshop.com/congo/wxAppPay.php";

    String REQUEST_NOTIFY_URL = "https://wx.dwarfworkshop.com/congo/wxAppPay_Validate.php";


    int WECHAT_PERMISSIONS_REQUEST_STORAGE = 1;

    /**
     * 获取权限使用的 RequestCode
     */
    int PERMISSIONS_REQUEST_CODE = 1002;
}
