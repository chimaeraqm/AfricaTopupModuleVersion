package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.activity.RechargeAliActivity;
import com.crazydwarf.africatopup.activity.RechargeWeActivity;
import com.crazydwarf.africatopup.view.SimpleToolBar;

public class RechargeFragment extends Fragment
{
    private Button bnRechargeAli;
    private Button bnRechargeWe;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recharge,container,false);
        bnRechargeAli = view.findViewById(R.id.bn_recharge_ali);
        bnRechargeWe = view.findViewById(R.id.bn_recharge_we);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
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
    }
}
