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
                PurchaseBottomSheetDialog dialog = new PurchaseBottomSheetDialog(getActivity());
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
