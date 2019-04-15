package com.crazydwarf.module_alipay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.crazydwarf.chimaeraqm.module_alipay.R;
import com.crazydwarf.comm_library.Objects.User;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.module_alipay.AlipayUtil.PayResult;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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

public class RechargeAliActivity extends BaseActivity
{
    private static final String url = "sandboxOrderInfo.php";

    private static final String ORDER_REQUEST_URL = "https://wx.dwarfworkshop.com/congo/alipay_sign.php";

    /**
     * 根据flag选择相关业务，SDK_PAY_FLAG支付业务，SDK_AUTH_FLAG授权业务
     */
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    /**
     * APPID 填写支付宝应用平台中的当前应用appid，使用沙箱时需切换为沙箱的appid
     */
    public static final String APPID = "2018090561232507";

    /**
     * RSA2_PRIVATE 填写生成的应用私钥
     * 应用公钥和支付宝公钥对于不同的app是相同的，对沙箱环境也是如此
     * 使用沙箱测试时，应用私钥可以不用重新生成，只需公钥和私钥对应即可
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCWZ3WtQfRZLAgSsaKNflIoshcwkE7EyaYlF1s9WGxNY59GGosb25h76dxt6qnmK7YptomxswWGc5mlFmczcfAlcT8fBZNL762DbJdKvzYnP8JO2R+3e/eA+tFmCkYGXeS2HHR62LbpejX6phaFGBpGo5/P4bNNTKft6S6tXWIcFuC9wRTsh9UhPKpJSJcDz/TYXL2j5EdJQ0G2uk5LZI283QYm7g9NZJA+b4J7qD7k5ZLfDlwry27PlMFS2dqQR2aroUeflKQENc076qV7USi5Fug7nMcOlmvdaRmvhpRhG2LxWumDo5UJiTlsTOE3tw5HQ80+/Fv1ks1Drl9lyTr9AgMBAAECggEAN/bzYK7D/1JVBq+2brPsWlw6KMXpqWvnOLICL0dxtTI2l91Umd8SVDlm3jeNVKo7NDZJ8idNDQSCzV0StZ/V3fjPpflrH7xlchu8CAIyYlRlNvWvyZSFOicaN7/m4oOZkPHxnax6E1J4N3YJtyiKznIgCzhOgZreebZkufmfghelQoR7Q/6M8DDDCgFxJ2oqvmV5YinSJ+TYZH3WUzSUqK+vmnqXhb805W4K/PtFjYxWQNRqfWUntvdvXBrbo9b0ghaCurLzXDAwjYHDzwp+lehTXXBWqUxpgwqFrD5azepwJaYCrSeuAZlDTmCRIHH5hBdstRhnD01dWoDNaNkZAQKBgQDmsahoyI+gL5KrqzKYY4vVeJ2Y1tG0yau9KelkYgD+BM6tSvMt1Uzq9q+a8bsqES38ZUb+X+IAlCivMTr5pudSU1iH05Sp0Q7ESp89/5VV3EgmDdsRcbZoyKZgh8bHCiwsS+tWv1gEL2oiW2wcOr95ro8O2s44Na7AgfpP2VkinQKBgQCm5xrQ9j4yT8/LfCxCQ9mxpRJvkgUnFm9kXuVq+2TH07mFiUwMx5MCY6n7CHF0DgCSHgqvg3sGF6eMT1NsQKCC8GMRtY8VRbJFNAlRLXAN5ekCvir7BzpOSxN3DYvebV2hAmHGl8J5F7A5OslismZlWwgsC9GhlGIkkS5HAHRb4QKBgQCZo9JUTtaQyX42RKNCqHGVr2nOQ6uQawusxQACcd7VTmBTO2pvqPI8PiS/3aRYJO7qfIzmlvcOiZ3655+uawD+bDG27CvDWU8rXcNmaBSOBVIrveibWo8whAmCmorBPr4ilkFbGb5Fs0pNLXP37QxevunZ69GRz/bdkPMjQM7DZQKBgH7kQa0RKeRSbadFNtkCgOXgnI2atLQtCG+E/mNB0jNisy/lXJ5ytUAhycADgU48vw5YLMOX8NaG8WlpfgFVeTNT095I7Qm9PXYDw/ml1AWdAHSHZGIJ8rhHt/rRn79x4rdg+jlkdARgkChSm32gKN1yQlpKegygpaUrg8WTgOPBAoGAXbEi9YREYTeZ8Ff1vhJRmZZsWgAgCk/au+sUsAjGVu/SpZH/mXY6Uggec7e4028CorxLvzndlxf2haNB//rAJreJlq0OcEC0j7PBK1sWL31S/yKy7Nn1FpcYlo592qI6eRjDJoYZnPGt6AY3A7PhMZ6Y4zLOqjJZbZTPK8P9sqI=";
    public static final String RSA_PRIVATE = "";

    public static final String DEFINED_KEY = "c4c4c4c4c4c4c4c4c4c4c4c4";
    public static final String DEFINED_SECRET = "282828282828282828282828";


    public String mRequestPrice = "0.0";

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        //用于alipay沙箱支付测试
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_ali);
        mRequestPrice = this.getIntent().getStringExtra("REQUEST_PRICE");
        //动态获取权限
        //UserUtil.requestPermission(this);
        //TODO : 这里开启一个等待的动画，并设置activity进出的动画，使用fadein和fadeout
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
        String tip = String.format("Pay $%s to Target Alipay account.",mRequestPrice);
        UserUtil.showToastLong(tip);
        sendOrderRequestMD5("supersmashbros",mRequestPrice);
    }

    private void payProcess(final String ori_OrderInfo)
    {
        Runnable payRunnable = new Runnable()
        {
            @Override
            public void run() {
                try {
                    PayTask alipay = new PayTask(RechargeAliActivity.this);
                    Map<String, String> result = alipay.payV2(ori_OrderInfo, true);


                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                    finish();
                }
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_CODE: {

                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    UserUtil.showToastShort("无法获取支付宝SDK所需的权限,请到系统设置开启");
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        UserUtil.showToastShort("无法获取支付宝SDK所需的权限,请到系统设置开启");
                        return;
                    }
                }

                // 所需的权限均正常获取
                UserUtil.showToastShort("支付宝SDK所需的权限已经正常获取");
            }
        }
    }

    void sendOrderRequestMD5(final String request_uid, String request_price)
    {
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
        keyValues.put("key", DEFINED_KEY);
        keyValues.put("timestamp", timestamp_str);

        //getMapToString中包含数组排序的操作
        StringBuilder keyValues_sb = getMapToString(keyValues);

        StringBuilder keyValues_add_secret_sb = keyValues_sb.append(DEFINED_SECRET);
        String keyValues_add_secret_str = keyValues_add_secret_sb.toString();
        String keyValues_md5_str = EncoderByMd5(keyValues_add_secret_str);

        RequestBody requestBody = new FormBody.Builder()
                .add("uid",request_uid)
                .add("price",request_price)
                .add("key",DEFINED_KEY)
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
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        UserUtil.showToastShort("fail to connect to sever");
                    }
                });
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
