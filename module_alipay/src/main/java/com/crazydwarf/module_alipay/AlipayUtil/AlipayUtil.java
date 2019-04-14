package com.crazydwarf.module_alipay.AlipayUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.module_alipay.RechargeAliActivity;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AlipayUtil
{
    private static final String ORDER_REQUEST_URL = "https://wx.dwarfworkshop.com/congo/alipay_sign.php";

    /**
     * 根据flag选择相关业务，SDK_PAY_FLAG支付业务，SDK_AUTH_FLAG授权业务
     */
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg)
        {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // 同步返回需要验证的信息
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        UserUtil.showToastLong("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        UserUtil.showToastLong("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    /**
     * Alipay task
     */
    public void payV2(Context mContext, double mAmount)
    {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以此处加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        String tip = String.format("Pay $%d to Target Alipay account.",mAmount);
        UserUtil.showToastLong(tip);
        String request_price = String.valueOf(mAmount);
        sendOrderRequestMD5(mContext,"supersmashbros",request_price);
    }

    void sendOrderRequestMD5(Context mContext, final String request_uid, String request_price)
    {
        String key = "c4c4c4c4c4c4c4c4c4c4c4c4";
        String secret = "282828282828282828282828";

        Date date = new Date();
        long timestamp = System.currentTimeMillis()/1000;
        String timestamp_str = String.valueOf(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sim = dateFormat.format(date);

        //sorted for alphabeta in treemap according to key
        Map<String, String> keyValues = new TreeMap<String, String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }
        );
        keyValues.put("uid", request_uid);
        keyValues.put("price", request_price);
        keyValues.put("key", key);
        keyValues.put("timestamp", timestamp_str);

        //getMapToString中包含数组排序的操作
        StringBuilder keyValues_sb = getMapToString(keyValues);

        StringBuilder keyValues_add_secret_sb = keyValues_sb.append(secret);
        String keyValues_add_secret_str = keyValues_add_secret_sb.toString();
        String keyValues_md5_str = EncoderByMd5(keyValues_add_secret_str);

        RequestBody requestBody = new FormBody.Builder()
                .add("uid",request_uid)
                .add("price",request_price)
                .add("key",key)
                .add("timestamp",timestamp_str)
                .add("sign",keyValues_md5_str)
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
                /*runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(mContext, "fail to connect to sever", Toast.LENGTH_SHORT).show();
                    }
                });*/
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String str = response.body().string();
                payProcess(str);
            }
        });
    }

    private void payProcess(final String ori_OrderInfo)
    {
        Runnable payRunnable = new Runnable()
        {
            @Override
            public void run() {
                try {
                    /*PayTask alipay = new PayTask(RechargeAliActivity.this);
                    Map<String, String> result = alipay.payV2(ori_OrderInfo, true);


                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);*/
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    //MD5 加密
    private static String EncoderByMd5(String str)
    {
        try{
            //返回实现指定摘要算法的MessageDigest对象
            MessageDigest md5 = MessageDigest.getInstance("md5");
            //先将字符串转换成byte数组，再用byte数组更新摘要
            md5.update(str.getBytes());
            //哈希计算，即加密
            byte[] nStr = md5.digest();
            //加密的结果是byte数组，将byte数组转换成StringBuilder
            return bytes2Hex(nStr);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.getStackTrace();
        }
        return null;
    }

    //将加密的byte转换成String
    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;

        for (int i = 0; i < bts.length; i++)
        {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1)
            {
                des += "0";
            }
            des += tmp;
        }
        return des;
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

}
