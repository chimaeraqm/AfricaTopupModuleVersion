package com.crazydwarf.africatopup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.dialog.AddNumberDialog;
import com.crazydwarf.africatopup.view.BundleItemAdapter;
import com.crazydwarf.africatopup.view.SimpleToolBar;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.module_alipay.RechargeAliActivity;

public class BundleActivity extends BaseActivity
{
    private Button bnAdd;
    private Button bnListPick;
    private Button bnAliPay;
    private Button bnWePay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        SimpleToolBar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);
        toolBar.setBackIconClickListener(new SimpleToolBar.BackIconClickListener() {
            @Override
            public void OnClick() {
                finish();

            }
        });

        RecyclerView mRecyclerview = findViewById(R.id.rv_additems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BundleActivity.this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(BundleActivity.this,DividerItemDecoration.VERTICAL));

        String[] newphones = {"(123)1234567","(123)1234567","(123)1234567","(123)1234567",
                "(123)1234567","(123)1234567","(123)1234567","(123)1234567",
                "(123)1234567","(123)1234567","(123)1234567","(123)1234567"};
        String[] newfees = {"99.99","99.99","99.99","99.99",
                "99.99","99.99","99.99","99.99",
                "99.99","99.99","99.99","99.99"};
        String[] newnames = {"zhangsan","zhangsan","zhangsan","zhangsan",
                "zhangsan","zhangsan","zhangsan","zhangsan",
                "zhangsan","zhangsan","zhangsan","zhangsan"};
        boolean[] newchecks = {true,true,true,true,true,true,true,true,true,true,true,true};
        BundleItemAdapter bundleItemAdapter = new BundleItemAdapter(newphones,newfees,newnames,newchecks);
        mRecyclerview.setAdapter(bundleItemAdapter);

        bnAdd = findViewById(R.id.bn_add);
        bnListPick = findViewById(R.id.bn_listpick);
        bnAliPay = findViewById(R.id.bn_recharge_ali);
        bnWePay = findViewById(R.id.bn_recharge_we);

        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNumberDialog addNumberDialog = new AddNumberDialog(BundleActivity.this);
                addNumberDialog.show();
            }
        });

        bnListPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:添加点击事件
                Toast.makeText(BundleActivity.this, "打开手机通讯录批量选择手机号", Toast.LENGTH_SHORT).show();
            }
        });

        bnAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BundleActivity.this, RechargeAliActivity.class);
                startActivity(intent);
            }
        });

        bnWePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BundleActivity.this, RechargeWeActivity.class);
                startActivity(intent);
            }
        });
    }
}
