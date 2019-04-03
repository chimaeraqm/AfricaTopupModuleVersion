package com.crazydwarf.africatopup.fragment;

import android.os.Bundle;
import android.provider.BlockedNumberContract;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.dialogs.PurchaseBottomSheetDialog;

public class NewRechargeFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_recharge_new,container,false);
        Button bn_recharge_10 = view.findViewById(R.id.bn_recharge_10);
        bn_recharge_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(),10f);
                dialog.show();
            }
        });

        Button bn_recharge_20 = view.findViewById(R.id.bn_recharge_20);
        bn_recharge_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(),20f);
                dialog.show();
            }
        });

        Button bn_recharge_30 = view.findViewById(R.id.bn_recharge_30);
        bn_recharge_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(),30f);
                dialog.show();
            }
        });

        Button bn_recharge_50 = view.findViewById(R.id.bn_recharge_50);
        bn_recharge_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(),50f);
                dialog.show();
            }
        });

        Button bn_recharge_100 = view.findViewById(R.id.bn_recharge_100);
        bn_recharge_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(),100f);
                dialog.show();
            }
        });

        Button bn_recharge_200 = view.findViewById(R.id.bn_recharge_200);
        bn_recharge_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(),200f);
                dialog.show();
            }
        });

        Button bn_recharge_300 = view.findViewById(R.id.bn_recharge_300);
        bn_recharge_300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(),300f);
                dialog.show();
            }
        });

        Button bn_recharge_500 = view.findViewById(R.id.bn_recharge_500);
        bn_recharge_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity(),500f);
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
