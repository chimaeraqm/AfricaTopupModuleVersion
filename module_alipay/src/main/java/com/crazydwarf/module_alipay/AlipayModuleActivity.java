package com.crazydwarf.module_alipay;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.crazydwarf.chimaeraqm.module_alipay.R;
import com.crazydwarf.comm_library.activity.BaseActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AlipayModuleActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipaymodule);
        double a = 1.0;
    }

}
