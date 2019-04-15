package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chimaeraqm.module_wechatpay.WechatpayModuleActivity;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.dialogs.PurchaseBottomSheetDialog;
import com.crazydwarf.comm_library.Listener.DialogListener;
import com.crazydwarf.module_alipay.RechargeAliActivity;

public class NewRechargeFragment extends Fragment
{
    private static final int RESULT_OK = 0;
    private float mRequestPrice;

    //TODO : 将支付金额设为选择金额的1/1000，实际上线后取消该设置
    private float mMulti = 0.001f;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.fragment_recharge_new,container,false);

        //TODO : 金额选择需要做的更灵活，自定义一个button包含一个type和int属性，分别代表充值的种类和金额
        Button bn_recharge_10 = view.findViewById(R.id.bn_recharge_10);
        bn_recharge_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 10f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res){
                            //TODO : 选择的是美元，支付的是RMB
                            mRequestPrice = 10 * mMulti * rate;
                            String strRequestPrice = String.format("%.2f",mRequestPrice);
                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                            }
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_20 = view.findViewById(R.id.bn_recharge_20);
        bn_recharge_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 20f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res){
                            mRequestPrice = 20 * mMulti * rate;
                            String strRequestPrice = String.format("%.2f",mRequestPrice);
                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                            }
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);

                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_30 = view.findViewById(R.id.bn_recharge_30);
        bn_recharge_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 30f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res) {
                            mRequestPrice = 30 * mMulti * rate;
                            String strRequestPrice = String.format("%.2f", mRequestPrice);

                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                            }
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_50 = view.findViewById(R.id.bn_recharge_50);
        bn_recharge_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 50f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res) {
                            mRequestPrice = 50 * mMulti * rate;
                            String strRequestPrice = String.format("%.2f", mRequestPrice);

                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                            }
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_100 = view.findViewById(R.id.bn_recharge_100);
        bn_recharge_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 100f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res) {
                            mRequestPrice = 100 * mMulti * rate;
                            String strRequestPrice = String.format("%.2f", mRequestPrice);

                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                            }
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_200 = view.findViewById(R.id.bn_recharge_200);
        bn_recharge_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 200f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res) {
                            mRequestPrice = 200 * mMulti * rate;
                            String strRequestPrice = String.format("%.2f", mRequestPrice);

                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                            }
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_300 = view.findViewById(R.id.bn_recharge_300);
        bn_recharge_300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 300f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res) {
                            mRequestPrice = 300 * mMulti * rate;
                            String strRequestPrice = String.format("%.2f", mRequestPrice);

                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                            }
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_500 = view.findViewById(R.id.bn_recharge_500);
        bn_recharge_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 500f, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res) {
                            mRequestPrice = 500 * mMulti * rate;
                            String strRequestPrice = String.format("%.2f", mRequestPrice);

                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                            }
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    //TODO : 用户保存数据更新后，所有页面需要同步刷新，如何降低刷新率，提高刷新速度？QueryFragment中需要做同样处理
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }
}
