package com.chimaeraqm.module_wechatpay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.crazydwarf.chimaeraqm.module_wechatpay.R;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.view.SimpleToolBar;

public class RechargeWeActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_we);
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
//                Intent intent = new Intent(RechargeWeActivity.this,HistoryActivity.class);
//                startActivity(intent);
            }
        });

    }
}
