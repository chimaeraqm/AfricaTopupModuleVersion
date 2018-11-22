package com.crazydwarf.africatopup.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.crazydwarf.africatopup.AlipayUtil.OrderInfoUtil2_0;
import com.crazydwarf.africatopup.AlipayUtil.PayResult;
import com.crazydwarf.africatopup.Objects.User;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.view.SimpleToolBar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据flag选择相关业务，SDK_PAY_FLAG支付业务，SDK_AUTH_FLAG授权业务
     */
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    /**
     * APPID 填写支付宝应用平台中的当前应用appid，使用沙箱时需切换为沙箱的appid
     */
    //public static final String APPID = "2018090561232507";
    public static final String APPID = "2016091600526477";

    /**
     * RSA2_PRIVATE 填写生成的应用私钥
     * 应用公钥和支付宝公钥对于不同的app是相同的，对沙箱环境也是如此
     * 使用沙箱测试时，应用私钥可以不用重新生成，只需公钥和私钥对应即可
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC07TxclIblEDoWKaXsipiQx0UPPZoSyPCgZUuLJHlcgrjlf02/rkyBEj8zi0XPWkgpGXACpQpsDEsodF1MO7bqFQi4ylFOm95VQmzFqujRieNWH5wGqaOpxHMzkEkAbn4fPaPFL3l+jVRPRjGi7EPgyC2imFJMcKv8UHNDgmYU3+31t74f2o8zGHVMTRpIJ/rkYr/qEK1wRnNAgtDLbNIJVClP5Ei3+l4921CzdT2p8IiRM/24G+tJeyUW1PRf35tWMPEYlyGR+7XYFgBav8Ld6xK7ktZ5LPzw4UZlNtVkrKYNQ6sg/Oyea530bE6MTT5j9b2y0UCJyKjdbZkfHKtnAgMBAAECggEAD5vf90TBFoX93Oee7vdODj+Cz7vKzRAU4mGa0NhGuBp3BSWkeYL6CrCeTz4WubraOuF87l16trOg0E6ptef48dz6saaSuSttVQG0DKGgEGPK/yUe+twryHuwTKSIB8eAjgVtWZes7aQXv/cYVSv49y2N8lcd0oZt8AioyQw47lf2L+rP+LRxXdfxhogNP6/yNAIsKdA2CRemBhsOXvw2aIce3aVYqLQCDRQp3+nKncDOqYkjykzG8sFofuwAm+mrmVVrbzzMjBtuA8hvqy59OOnHGjVGcVZNeoUb2Oi0AkpZaosQWe1arhfg1+cDwajrmfAu49sOF2+fgxnOjERVoQKBgQDfyynhtUerST/xds1tlNS98HjRl1PA0hvz3uUU883vaXqInLJ8GaxlL10/pusEklFAuK1H0Ra+fzukkCeY2Q4lIA0H2v4bRT71ykjKWnpwoalycp9AFwCmKAoIMC/PKtDibcelFv5Ak2/r+118OXu3yvM703dg23ssWTeGqzfcbwKBgQDO9s1BOe1C5J/ySj5k5uM54D8Lkr5hSOCNym4rrR+us+JZN5OtAycuWDHdyopKIyYZSOoUjuJYXko4s2OTfmJIKfWxbcM7r7chWJR1Iqy3mAjBs1ZDaDTnosfsKcYN+AIsQ7rrR8RTr5Zpcq4ft8HNdrL2UyyCBj6u1RulVIWMiQKBgA17Q66hKQwcgYmeSonLaXV3ww99OkJnZd+vEcXNWh6OEB+isO3g7g26aLeo6od/+P0ZIvcslV4sc/9Z38jJapy57Y8Hlj82ULCl2vS1tqKKWpbWaNmHiTZ5OwF7RaYJQkQhsOE1HWPufdONdPhCzj0oMLbt6kcfcbAeP8YREnBpAoGAITs5q958PUBuej33lMYJ+DIMjXQrg31rKPk3BWZP7wJNNtwYpzAZa3SX1S//UnYgWPD/PPvQVjYVntMXCwjBCStLmwivz3agZgdOFxzRNM2BonCESmCCFitqBH6UoYuP9cGOUtb8LB/Ge/oBAXDE+pOzmp+yRtJv4CyxurM+mlkCgYEAlu4hWg5/r+xwtIeKaD643V4k2SpcuUxEIl6KS1xmJKiVGB2r7D0zkWYS1Euj7Gr3sG12ZWQzZJNdbvSEq1sHs/7NCAIft92oF50kv91oYPBsyHX03vMZ7Ms+9MOD1ZrgSLFSsLbleFu+MOl53cvateU/+ZLqUOnoT5GFzjcNR5M=";
    public static final String RSA_PRIVATE = "";

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
                        Toast.makeText(RechargeAliActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeAliActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_ali);

        //动态获取权限
        requestPermission();

        SimpleToolBar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);
        toolBar.setBackIconClickListener(new SimpleToolBar.BackIconClickListener() {
            @Override
            public void OnClick() {
                finish();
            }
        });

        Button bnHistory = findViewById(R.id.bn_history);
        bnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payV2(view);
//                Intent intent = new Intent(RechargeAliActivity.this,HistoryActivity.class);
//                startActivity(intent);
            }
        });


        //测试服务器数据库INSERT功能
        String func_name = "INSERT";
        String insert_name = "test3";
        String insert_pw = "vfbgnhmj";
        String insert_info = "this is a insert test message";
        //sendInsertRequest(func_name,insert_name,insert_pw,insert_info);

        String request_name = "test2";
        String request_func = "SELECT_ALL";
//        sendRequest(request_func,request_name);

    }

    /**
     * Alipay task
     */
    public void payV2(View v)
    {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以此处加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */

        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        Log.i("orderInfo", orderInfo);//打印一下看看对不对

        Runnable payRunnable = new Runnable()
        {
            @Override
            public void run() {
                URL url = null;
                try {
                    /*url = new URL("sandboxOrderInfo.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String orderInfo = "";
                    String len;

                    while ((len = br.readLine()) != null) {
                        orderInfo += len;
                    }*/

                    PayTask alipay = new PayTask(RechargeAliActivity.this);
                    Map<String, String> result = alipay.payV2(orderInfo, true);


                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_REQUEST_CODE);

        } else {
            showToast(this, "支付宝 SDK 已有所需的权限");
        }
    }

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {

                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    showToast(this, "无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        showToast(this, "无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                        return;
                    }
                }

                // 所需的权限均正常获取
                showToast(this, "支付宝 SDK 所需的权限已经正常获取");
            }
        }
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
    void sendInsertRequest(final String func_name, String insert_name, String insert_pw, String insert_info)
    {
        RequestBody requestBody = new FormBody.Builder()
                .add("func_name",func_name)
                .add("username",insert_name)
                .add("password",insert_pw)
                .add("info",insert_info)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(RechargeAliActivity.this, "fail to connect to sever", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                double a = 0;
            }
        });
    }

    void sendRequest(final String request_func, String request_name)
    {
        RequestBody requestBody = new FormBody.Builder()
                .add("func_name",request_func)
                .add("username",request_name)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
                        Toast.makeText(RechargeAliActivity.this, "fail to connect to sever", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("result");
                List<User> userList = new ArrayList<>();
                for (JsonElement user : jsonArray) {
                    User userBean = new Gson().fromJson(user, new TypeToken<User>(){}.getType());
                    userList.add(userBean);
                }

                for (User user : userList) {
                    Log.i("tag", "id : " + user.getId());
                    Log.i("tag", "username : " + user.getUsername());
                    Log.i("tag", "password : " + user.getPassword());
                    Log.i("tag", "info : " + user.getInfo());
                    Log.i("tag", "addtime : " + user.getAdd_time());
                    Log.i("tag", "————————————————————");
                }
            }
        });
    }

}
