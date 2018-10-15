package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.UserUtil;
import com.crazydwarf.africatopup.activity.RechargeAliActivity;
import com.crazydwarf.africatopup.activity.RechargeWeActivity;
import com.crazydwarf.africatopup.dialog.CountrySelectDialog;

public class RechargeFragment extends Fragment
{
    private Button bnRechargeAli;
    private Button bnRechargeWe;
    private ImageView imFlag;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recharge,container,false);
        bnRechargeAli = view.findViewById(R.id.bn_recharge_ali);
        bnRechargeWe = view.findViewById(R.id.bn_recharge_we);
        imFlag = view.findViewById(R.id.im_flag);
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

        imFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountrySelectDialog countrySelectDialog = new CountrySelectDialog(getActivity(), new CountrySelectDialog.dialogItemSelectionListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }
                });
                countrySelectDialog.show();

                Window dialogWindow = countrySelectDialog.getWindow();
                WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
                layoutParams.width = UserUtil.dip2px(getActivity(),240);
                layoutParams.height = UserUtil.dip2px(getActivity(),400);
                layoutParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
                dialogWindow.setAttributes(layoutParams);
            }
        });
    }
}
