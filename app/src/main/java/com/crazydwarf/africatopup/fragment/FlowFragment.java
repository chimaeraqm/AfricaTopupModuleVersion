package com.crazydwarf.africatopup.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chimaeraqm.module_wechatpay.WechatpayModuleActivity;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.dialogs.PurchaseBottomSheetDialog;
import com.crazydwarf.comm_library.Listener.DialogListener;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.GVariable;
import com.crazydwarf.comm_library.dialogs.DefineValueDialog;
import com.crazydwarf.comm_library.view.MaterialEditText;
import com.crazydwarf.module_alipay.RechargeAliActivity;

import me.yokeyword.fragmentation.SupportFragment;

public class FlowFragment extends SupportFragment
{
    private static final int RESULT_OK = 0;
    private float mRequestPrice;

    //TODO : mDiscount为折扣倍率，默认设置为1.0; mMulti为测试倍率将支付金额设为选择金额的1/1000，实际上线后取消
    private float mDiscount = 1.0f;
    private float mMulti = 0.001f;

    public static FlowFragment newInstance()
    {
        Bundle args = new Bundle();

        FlowFragment fragment = new FlowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_flow_new,container,false);

        //TODO ：暂时取sim1的信息作为默认显示的信息
        TextView tv_account_info = view.findViewById(R.id.tv_account_info);
        String operatorSim1 = GVariable.STORED_OPERATOR_SIM1;
        tv_account_info.setText(operatorSim1 + "(Sim1)");

        //TODO : 这里显示登录用户注册时使用的手机号，如果是双卡手机，则需根据该手机的运营商归属确定默认运营商是sim1或者sim2,
        MaterialEditText et_phonenumber = view.findViewById(R.id.et_phonenumber);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
        String phoneno = sharedPreferences.getString(Constants.CURRENT_USER_PHONENO,null);
        if(phoneno != null)
            et_phonenumber.setText(phoneno);

        TextView tv_discount_info = view.findViewById(R.id.tv_discount_info);
        if(GVariable.STORED_USER_LOGIN_STATUS)
        {
            tv_discount_info.setVisibility(View.VISIBLE);
            mDiscount = 0.95f;
        }
        else
        {
            tv_discount_info.setVisibility(View.INVISIBLE);
            mDiscount = 1.0f;
        }
        //TODO : 金额选择需要做的更灵活，自定义一个button包含一个type和int属性，分别代表充值的种类和金额
        //TODO : 现在的支付流程分别写在alipay和wxpay对应的空activity里，需要将支付逻辑和界面分开
        Button bn_recharge_50 = view.findViewById(R.id.bn_recharge_10);
        bn_recharge_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 5f*mDiscount, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res){
                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                                /**
                                 * wxpay会转换成单位 分
                                 */
                                mRequestPrice = 5 * mDiscount * mMulti * rate * 100;
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                                mRequestPrice = 5 * mDiscount * mMulti * rate;
                            }
                            String strRequestPrice = String.format("%.2f",mRequestPrice);
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_500 = view.findViewById(R.id.bn_recharge_50);
        bn_recharge_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), 50f * mDiscount, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res) {
                            Intent intent;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                                /**
                                 * wxpay会转换成单位 分
                                 */
                                mRequestPrice = 50 * mDiscount * mMulti * rate * 100;
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                                mRequestPrice = 50 * mDiscount * mMulti * rate;
                            }
                            String strRequestPrice = String.format("%.2f",mRequestPrice);
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        }
                    }
                });
                dialog.show();
            }
        });

        Button bn_recharge_define = view.findViewById(R.id.bn_recharge_define);
        bn_recharge_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefineValueDialog defineValueDialog = new DefineValueDialog(getContext(), new DefineValueDialog.dialogItemSelectionListener() {
                    @Override
                    public void onButtonConfirmClick(View view, final int value) {
                        PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(), (float)value * mDiscount, new DialogListener() {
                            @Override
                            public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                                if(res) {
                                    Intent intent;
                                    if(payment_method == 1){
                                        intent = new Intent(getContext(),WechatpayModuleActivity.class);
                                        /**
                                         * wxpay会转换成单位 分
                                         */
                                        mRequestPrice = value * mDiscount * mMulti * rate * 100 * 0.1f;
                                    }
                                    else{
                                        intent = new Intent(getContext(),RechargeAliActivity.class);
                                        mRequestPrice = value * mDiscount * mMulti * rate * 0.1f;
                                    }
                                    String strRequestPrice = String.format("%.2f",mRequestPrice);
                                    intent.putExtra("REQUEST_PRICE",strRequestPrice);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                }
                            }
                        });
                        dialog.show();
                    }
                });
                defineValueDialog.show();
            }
        });


        Button bn_history = view.findViewById(R.id.bn_history);
        bn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(HistoryFragment.newInstance());
            }
        });

        Button bn_bundle = view.findViewById(R.id.bn_bundle);
        bn_bundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(BundleFragment.newInstance());
            }
        });

        Button bn_operatorinfo = view.findViewById(R.id.bn_operatorinfo);
        bn_operatorinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(QueryFragment.newInstance());
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
