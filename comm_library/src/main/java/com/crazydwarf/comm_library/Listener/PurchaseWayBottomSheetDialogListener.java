package com.crazydwarf.comm_library.Listener;

public interface PurchaseWayBottomSheetDialogListener {

    /**
     * methodid 现可选方式为0 alipay / 1 wxpay
     * 默认为 methodid = 0
     */

    public void getPaymentMethodFromDialog(int methodid);
}
