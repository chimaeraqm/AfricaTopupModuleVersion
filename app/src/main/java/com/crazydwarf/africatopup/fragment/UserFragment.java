package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.UserUtil;
import com.crazydwarf.africatopup.activity.BundleActivity;
import com.crazydwarf.africatopup.activity.HistoryActivity;
import com.crazydwarf.africatopup.dialog.DepositDetailDialog;
import com.crazydwarf.africatopup.dialog.LoginDialog;
import com.crazydwarf.africatopup.dialog.LogupDialog;
import com.crazydwarf.africatopup.view.SmoothCheckBox;

public class UserFragment extends Fragment
{
    private Button bnLogin;
    private Button bnLogup;
    private Button bnBundle;
    private Button bnHistory;

    private SmoothCheckBox cbLocalPhone1;
    private SmoothCheckBox cbLocalPhone2;
    private SmoothCheckBox cbOtherPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        bnLogin = view.findViewById(R.id.bn_login);
        bnLogup = view.findViewById(R.id.bn_logup);
        bnBundle = view.findViewById(R.id.bn_bundle);
        bnHistory = view.findViewById(R.id.bn_history);

        cbLocalPhone1 = view.findViewById(R.id.cb_localphone1);
        cbLocalPhone2 = view.findViewById(R.id.cb_localphone2);
        cbOtherPhone = view.findViewById(R.id.cb_otherphone);

        cbLocalPhone1.setChecked(true);
        cbLocalPhone2.setChecked(false);
        cbOtherPhone.setChecked(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginDialog loginDialog = new LoginDialog(getActivity());
                loginDialog.show();
            }
        });

        //TODO:用户注册界面
        bnLogup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogupDialog logupDialog = new LogupDialog(getActivity());
                logupDialog.show();
            }
        });

        bnBundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),BundleActivity.class);
                startActivity(intent);
            }
        });

        bnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        cbLocalPhone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbLocalPhone1.setChecked(true);
                if(cbLocalPhone1.isChecked())
                {
                    cbLocalPhone2.setChecked(false);
                    cbOtherPhone.setChecked(false);
                    DepositDetailDialog depositDetailDialog = new DepositDetailDialog(getActivity());
                    depositDetailDialog.show();
                }
            }
        });

        cbLocalPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbLocalPhone2.setChecked(true);
                if(cbLocalPhone2.isChecked())
                {
                    cbLocalPhone1.setChecked(false);
                    cbOtherPhone.setChecked(false);
                    DepositDetailDialog depositDetailDialog = new DepositDetailDialog(getActivity());
                    depositDetailDialog.show();
                }
            }
        });

        cbOtherPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbOtherPhone.setChecked(true);
                if(cbOtherPhone.isChecked())
                {
                    cbLocalPhone1.setChecked(false);
                    cbLocalPhone2.setChecked(false);
                    DepositDetailDialog depositDetailDialog = new DepositDetailDialog(getActivity());
                    depositDetailDialog.show();
                }
            }
        });
    }
}
