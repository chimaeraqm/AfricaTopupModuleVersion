package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.activity.RechargeActivity;

public class MainFragment extends Fragment implements View.OnClickListener
{
    private Button bn_recharge;
    private Button bn_flow;
    private Button bn_tv_net;
    private Button bn_air_tickets;
    private Button bn_discount;
    private Button bn_more;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        bn_recharge = view.findViewById(R.id.bn_recharger);
        bn_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),RechargeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                getActivity().finish();
            }
        });
        bn_flow = view.findViewById(R.id.bn_flow);
        bn_tv_net = view.findViewById(R.id.bn_tvnet);
        bn_air_tickets = view.findViewById(R.id.bn_air_tickets);
        bn_discount = view.findViewById(R.id.bn_discount);
        bn_more = view.findViewById(R.id.bn_more);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (getId()) {
            case R.id.bn_recharger:
                Intent intent = new Intent(getActivity(),RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.bn_flow:
                break;
            case R.id.bn_tvnet:
                break;
            case R.id.bn_air_tickets:
                break;
            case R.id.bn_discount:
                break;
            case R.id.bn_more:
                break;
                default:
                    break;
        }
    }
}
