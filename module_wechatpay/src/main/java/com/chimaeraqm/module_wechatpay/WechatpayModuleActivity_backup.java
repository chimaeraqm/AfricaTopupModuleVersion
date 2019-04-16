package com.chimaeraqm.module_wechatpay;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crazydwarf.chimaeraqm.module_wechatpay.R;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.comm_library.activity.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WechatpayModuleActivity_backup extends BaseActivity
{
    private static final String ORDER_REQUEST_URL = "https://wx.dwarfworkshop.com/congo/wxAppPay.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechatpaymodule);
        UserUtil.requestPermission(this);
        payV2();
    }

    /**
     * Alipay task
     */
    public void payV2()
    {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以此处加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        UserUtil.showToastLong("Pay $0.02 to Target Wechat account.");
//        sendOrderRequest("supersmashbros","0.01");
        sendOrderRequestMD5("devilmaycry","0.02");
    }

    void sendOrderRequestMD5(final String request_uid, String request_price)
    {
        String request_appid = "wxb4737b9a4e5b24b4";
        String request_mch_id = "1527548721";
        String request_notify_url = "https://wx.dwarfworkshop.com/congo/wxAppPay_Validate.php";
        final String request_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASC";
        String request_total_fee = "2";
        String request_out_trade_no = genTimeStampStr();
//        String request_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASC";

        RequestBody requestBody = new FormBody.Builder()
                .add("appid",request_appid)
                .add("mch_id",request_mch_id)
                .add("notify_url",request_notify_url)
                .add("key",request_key)
                .add("total_fee",request_total_fee)
                .add("out_trade_no",request_out_trade_no)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(ORDER_REQUEST_URL)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(WechatpayModuleActivity_backup.this, "fail to connect to sever", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String str = response.body().string();
                //向服务端请求并得到反馈信息，获取了appid、partnerId、prepayId
                try{
                    JSONObject jsonObject = new JSONObject(str);

                    //showToast(MainActivity.this, str);

                    String response_appid = jsonObject.getString("appid");
                    String response_partnerid = jsonObject.getString("partnerid");
                    String response_prepayid = jsonObject.getString("prepayid");
                    String response_package = jsonObject.getString("package");
                    String response_timestamp = jsonObject.getString("timestamp");
                    String response_noncestr = jsonObject.getString("noncestr");
                    //String response_key = jsonObject.getString("sandbox_key");
                    String response_sign = jsonObject.getString("sign");
                    WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                    /*builder.setAppId(response_appid)
                            .setPartnerId(response_partnerid)
                            .setPrepayId(response_prepayid)
                            .setPackageValue(response_package)
                            .setTimeStamp(response_timestamp)
                            .setNonceStr(response_noncestr)
                            .build()
                            .toWXPayAndSign(MainActivity.this,response_appid,response_key);*/
                    builder.setAppId(response_appid)
                            .setPartnerId(response_partnerid)
                            .setPrepayId(response_prepayid)
                            .setPackageValue(response_package)
                            .setTimeStamp(response_timestamp)
                            .setNonceStr(response_noncestr)
                            .setSign(response_sign)
                            .build()
                            .toWXPayNotSign(WechatpayModuleActivity_backup.this);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //convert values in map to String
    private static StringBuilder getMapToString(Map<String,String> map)
    {
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        //因为String拼接效率会很低的，所以转用StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if (map.get(keyArray[i]).trim().length() > 0) {
                sb.append(keyArray[i]).append("=").append(map.get(keyArray[i]).trim());
            }
            if(i != keyArray.length-1){
                sb.append("&");
            }
        }
        return sb;
    }

    private String genTimeStampStr() {
        String rtn = String.format("%s%d","devilmaycry",System.currentTimeMillis() / 1000);
        return rtn;
    }
}