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

import com.chimaeraqm.module_wechatpay.RechargeWeActivity;
import com.chimaeraqm.module_wechatpay.WechatpayModuleActivity;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.dialogs.PurchaseBottomSheetDialog;
import com.crazydwarf.africatopup.view.BundleItemAdapter;
import com.crazydwarf.chimaeraqm.wavetoolbar.WaveToolbar;
import com.crazydwarf.comm_library.Listener.DialogListener;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.dialogs.AddNumberDialog;
import com.crazydwarf.module_alipay.RechargeAliActivity;

public class BundleActivity extends BaseActivity
{
    private Button bnAdd;
    private Button bnListPick;
    private float mRequestPrice = 10.0f;
    //TODO : 将支付金额设为选择金额的1/1000，实际上线后取消该设置
    private float mMulti = 0.001f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        WaveToolbar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);
        toolBar.setBackIconClickListener(new WaveToolbar.BackIconClickListener() {
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

        Button bn_confirm = findViewById(R.id.bn_confirm);
        bn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(BundleActivity.this, 10f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res){
                            //TODO : 选择的是美元，支付的是RMB
                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(BundleActivity.this,WechatpayModuleActivity.class);
                                /**
                                 * wxpay会转换成单位 分
                                 */
                                mRequestPrice = 10 * mMulti * rate * 100;
                            }
                            else{
                                intent = new Intent(BundleActivity.this,RechargeAliActivity.class);
                                mRequestPrice = 10 * mMulti * rate;
                            }
                            String strRequestPrice = String.format("%.2f",mRequestPrice);
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}
