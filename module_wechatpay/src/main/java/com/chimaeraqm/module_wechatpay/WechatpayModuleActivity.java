package com.chimaeraqm.module_wechatpay;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.crazydwarf.chimaeraqm.module_wechatpay.R;
import com.crazydwarf.comm_library.activity.BaseActivity;

public class WechatpayModuleActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechatpaymodule);
        double a = 1.0;

        //在服务端签名
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //假装请求了服务器 获取到了所有的数据,注意参数不能少
                WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                builder.setAppId("123")
                        .setPartnerId("56465")
                        .setPrepayId("41515")
                        .setPackageValue("5153")
                        .setNonceStr("5645")
                        .setTimeStamp("56512")
                        .setSign("54615")
                        .build().toWXPayNotSign(WechatpayModuleActivity.this);
            }
        });

        //在客户端签名
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //假装请求了服务端信息，并获取了appid、partnerId、prepayId，注意参数不能少
                WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                builder.setAppId("123")
                        .setPartnerId("213")
                        .setPrepayId("3213")
                        .setPackageValue("Sign=WXPay")
                        .build()
                        .toWXPayAndSign(WechatpayModuleActivity.this,"123","key");
            }
        });
    }

}
