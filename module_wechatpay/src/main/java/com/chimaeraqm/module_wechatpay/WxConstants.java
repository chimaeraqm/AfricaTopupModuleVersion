package com.chimaeraqm.module_wechatpay;

public interface WxConstants
{
    String WECHAT_APP_ID = "wxb4737b9a4e5b24b4";

    String ORDER_REQUEST_URL = "https://wx.dwarfworkshop.com/congo/wxAppPay.php";

    String REQUEST_NOTIFY_URL = "https://wx.dwarfworkshop.com/congo/wxAppPay_Validate.php";


    int WECHAT_PERMISSIONS_REQUEST_STORAGE = 1;

    /**
     * 获取权限使用的 RequestCode
     */
    int PERMISSIONS_REQUEST_CODE = 1002;
}
