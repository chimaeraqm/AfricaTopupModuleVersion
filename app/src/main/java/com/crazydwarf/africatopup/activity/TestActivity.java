package com.crazydwarf.africatopup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.view.CaptchaEditText;

public class TestActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        CaptchaEditText captchaEditText = findViewById(R.id.view_test);
    }
}
