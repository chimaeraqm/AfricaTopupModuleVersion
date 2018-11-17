package com.crazydwarf.africatopup.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    public static final String APPID = "2018090561232507";

    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCmq92bAmyJaGO5oiZ66aOelDBTkQFoHJ1M72sSaFXqpParZZRIWKItD5twjz0vz6PozsZKmZauCrtbKP0AaM5gtttk7jJ8EfqRoK+PFLy40BnDjMZj8/XG+MIxt/jzyOWQPyPJzMV+Z2Lnue+HLnZ9PZ2zonjjBRP29QybUCJM+HmGyuuyibap2WNIMMwZY5I974FfNPeqCGPzcLFtVHqABnsWpOKWXbhfSkWgYxIygOv9gzWNHs0ropRV4B73VrWOy82aLFsTLSMF9E6ior2iVFA4Hd5wahgyHRTYCr3dcJpSJb99AKb84o8cw/AE1YDxYnR+lwJ0DxiwT3aMVUTdAgMBAAECggEAXwSH98DwA34BrGimq1fbMaKl0l5OgP4fJycu0XWt1XFqNthYKs5s1meZZBgk98bWWPjYztq0rk/r89JwOfWGAlj8xpONMHJHeRI0Q8u8s1ff+D2fNIh2S5Kxkwqg4MpdJVj5nCgjRybFmfnEdjqkzk18RFaRuErC0P1uzHRouZvZvLYcJNRiGvfFHmI2zfGMymZZjpfjeMihu1k3Vae41dRFkZpk/mHmd8Qopg9Vf70EO5lxM8dNGb9Eb5QVMhkfolfxfWg5cXRNh5Kb7oTlkjORRNcp06173uzI8XhNqKJsf06ag5sjTsUjjBgUsW9dwN2GMVA7wi/XeFSvLLGO+QKBgQDg5ZHKJXGxcuq1mYHTgBqZHLJAL5AWt4tfExtI2xyZJxWtOCRiiLLTKi7D0R76F9H8x9v3BolTfrHh95smb7JCEiR0Alvo7GEvhXcRLe4hudKtzDUIb8rzEYcL8Bq3izunDSLQcR7HeMZJD7xfZNNAcS1rWJOoNpNCFAVRcNNyGwKBgQC9uNX2+xcfi0CgWPiUXk03X2fKmdoeDDWo9x3SQydusOzHhnEbVs/nQ+q7h0Q79ap7G2mucx8AD6jcQT3FYtv7CNUYInUJNdhcLA8zxfaQilAHzJYrlyKDLGTr4TNUcJnpbp9LgRfOC10QFzpKdw/BdyBj/6rDl4GqLoV8UozUZwKBgQCMy4wzFrAP3JbOLBVYGLoOIyYBAwXdAvmRAwAw42QLCaoLcLtuqI/znVP7qX8QKTuAWor+Iqx0hjvM8NvD+2eI75y8uAFcWCgbvR2mtq9/k7surUqCRqKy+8UlFyNSxysIUTP7dOZFEPpqIRzYR+HtVS2PJ7wBMnGMi8Ysj0sBAwKBgQClP6iGTl/upsDsqXIi8Rx6nYvu6SEVyPa4LD36VWm0PUCP7ab49B10RzDelEw3mZwbTF45h6Y5zGn9JopiMeB/gErzkZe1JQRGDE5Vfxlq/j4E/QrT/vkdnf5tgiLOaqF0tFjlUBTm7/joOgMYtymvDk7VKuyXHpDfJVQ5XEG0AwKBgQDMKH3b+gpUZVVc77UYqtNDF3u1Mtn3Q6AuP+MZGLCreDozWUzothI2meCyJ+R4Ji85p3FOXhTVpIsR1JF/isNOTrPCAsHU3u+OYovmEx5RV+FJicuadosll6MzsiKqkJ3onEds/SMoBWJ3r89jqfNVh1tzvhDDLys7CyfezqlC0w==";
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
