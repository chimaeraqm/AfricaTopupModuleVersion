package com.crazydwarf.africatopup.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.africatopup.activity.RechargeAliActivity;
import com.crazydwarf.africatopup.activity.RechargeWeActivity;
import com.crazydwarf.africatopup.dialog.CountrySelectDialog;

public class RechargeFragment extends Fragment
{
    private Button bnRechargeAli;
    private Button bnRechargeWe;
    private ImageView imFlag;
    private TextView tvPostCode;
    private int mSelePos = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recharge,container,false);
        bnRechargeAli = view.findViewById(R.id.bn_recharge_ali);
        bnRechargeWe = view.findViewById(R.id.bn_recharge_we);

        imFlag = view.findViewById(R.id.im_flag);
        tvPostCode = view.findViewById(R.id.tv_postcode);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         * 根据SharedPreferences中保存的选择显示对应的国家
         */
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
        int seleRes = preferences.getInt(Constants.SELECTED_COUNTRY_RES,R.drawable.flag_egypt);
        int seleCode = preferences.getInt(Constants.SELECTED_COUNTRY_CODE,20);
        mSelePos = preferences.getInt(Constants.SELECTED_COUNTRY_POS,0);
        String postcode = String.format("+%d",seleCode);
        tvPostCode.setText(postcode);
        imFlag.setBackgroundResource(seleRes);

        bnRechargeAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RechargeAliActivity.class);
                startActivity(intent);
            }
        });

        bnRechargeWe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RechargeWeActivity.class);
                startActivity(intent);
            }
        });

        imFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CountrySelectDialog countrySelectDialog = new CountrySelectDialog(getActivity(), new CountrySelectDialog.dialogItemSelectionListener() {
                    @Override
                    public void onClick(int position, String country, int code, int flag) {
                        imFlag.setBackgroundResource(flag);
                        String postcode = String.format("+%d",code);
                        tvPostCode.setText(postcode);
                        mSelePos = position;
                    }
                },mSelePos);
                countrySelectDialog.show();
            }
        });
    }

    //TODO : 用户保存数据更新后，所有页面需要同步刷新，如何降低刷新率，提高刷新速度？QueryFragment中需要做同样处理
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden)
        {
            /**
             * 根据SharedPreferences中保存的选择显示对应的国家
             */
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
            int seleRes = preferences.getInt(Constants.SELECTED_COUNTRY_RES,R.drawable.flag_egypt);
            int seleCode = preferences.getInt(Constants.SELECTED_COUNTRY_CODE,20);
            mSelePos = preferences.getInt(Constants.SELECTED_COUNTRY_POS,0);
            String postcode = String.format("+%d",seleCode);
            tvPostCode.setText(postcode);
            imFlag.setBackgroundResource(seleRes);
        }
    }
}
