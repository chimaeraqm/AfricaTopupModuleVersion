package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chimaeraqm.module_wechatpay.WechatpayModuleActivity;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.dialogs.PurchaseBottomSheetDialog;
import com.crazydwarf.africatopup.view.BundleItemAdapter;
import com.crazydwarf.chimaeraqm.wavetoolbar.WaveToolbar;
import com.crazydwarf.comm_library.Listener.DialogListener;
import com.crazydwarf.comm_library.Utilities.GVariable;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.dialogs.AddNumberDialog;
import com.crazydwarf.module_alipay.RechargeAliActivity;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

//TODO : 这里批量充值的方式可能需要修改
public class BundleFragment extends SwipeBackFragment
{
    private Button bnAdd;
    private Button bnListPick;
    //TODO : mDiscount为折扣倍率，默认设置为1.0; mMulti为测试倍率将支付金额设为选择金额的1/1000，实际上线后取消
    private float mDiscount = 1.0f;
    private float mMulti = 0.001f;
    private TextView etTotalfee;
    private float mRequestPrice = 0f;

    public static BundleFragment newInstance()
    {
        Bundle args = new Bundle();
        BundleFragment fragment = new BundleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bundle,container,false);

        RecyclerView mRecyclerview = view.findViewById(R.id.rv_additems);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        String[] newphones = {"(123)1234567","(123)1234567","(123)1234567","(123)1234567",
                "(123)1234567","(123)1234567","(123)1234567","(123)1234567",
                "(123)1234567","(123)1234567","(123)1234567","(123)1234567"};
        double[] newfees = {99.99,99.99,99.99,99.99,99.99,99.99,99.99,99.99,99.99,99.99,99.99,99.99};
        String[] newnames = {"zhangsan","zhangsan","zhangsan","zhangsan",
                "zhangsan","zhangsan","zhangsan","zhangsan",
                "zhangsan","zhangsan","zhangsan","zhangsan"};
        boolean[] newchecks = {true,true,true,true,true,true,true,true,true,true,true,true};
        final BundleItemAdapter bundleItemAdapter = new BundleItemAdapter(newphones,newfees,newnames,newchecks);
        bundleItemAdapter.setOnBundleItemRVClickListener(new BundleItemAdapter.onBundleItemRVClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mRequestPrice = (float) bundleItemAdapter.getTotleFee();
                etTotalfee.setText(String.valueOf(mRequestPrice));
            }
        });
        mRecyclerview.setAdapter(bundleItemAdapter);

        etTotalfee = view.findViewById(R.id.et_totalfee);
        mRequestPrice = (float) bundleItemAdapter.getTotleFee();
        etTotalfee.setText(String.valueOf(mRequestPrice));

        bnAdd = view.findViewById(R.id.bn_add);
        bnListPick = view.findViewById(R.id.bn_listpick);

        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNumberDialog addNumberDialog = new AddNumberDialog(getContext());
                addNumberDialog.show();
            }
        });

        bnListPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:添加点击事件
                Toast.makeText(getContext(), "打开手机通讯录批量选择手机号", Toast.LENGTH_SHORT).show();
            }
        });


        if(GVariable.STORED_USER_LOGIN_STATUS)
        {
            mDiscount = 0.95f;
        }
        else
        {
            mDiscount = 1.0f;
        }

        Button bn_confirm = view.findViewById(R.id.bn_confirm);
        bn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getContext(), mRequestPrice * mDiscount, new DialogListener() {
                    @Override
                    public void getPurchaseRequestFromDialog(boolean res,float rate,int payment_method) {
                        if(res){
                            //TODO : 选择的是美元，支付的是RMB
                            Intent intent;
                            float inputValue = 0;
                            if(payment_method == 1){
                                intent = new Intent(getContext(),WechatpayModuleActivity.class);
                                /**
                                 * wxpay会转换成单位 分
                                 */
                                inputValue = mRequestPrice * mDiscount * mMulti * rate * 100;
                            }
                            else{
                                intent = new Intent(getContext(),RechargeAliActivity.class);
                                inputValue = mRequestPrice * mDiscount * mMulti * rate;
                            }
                            String strRequestPrice = String.format("%.2f",inputValue);
                            intent.putExtra("REQUEST_PRICE",strRequestPrice);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}
