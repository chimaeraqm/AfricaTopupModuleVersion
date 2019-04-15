package com.crazydwarf.comm_library.Listener;

public interface DialogListener
{
    /**
     *
     * @param res
     * @param rate
     * @param payment_method 从PurchaseWayBottomSheetDialog得到的payment method要继续返回到主支付页面
     */
    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method);
}
