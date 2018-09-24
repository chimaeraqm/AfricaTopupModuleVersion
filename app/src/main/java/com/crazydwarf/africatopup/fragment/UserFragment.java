package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.activity.BundleActivity;
import com.crazydwarf.africatopup.activity.HistoryActivity;
import com.crazydwarf.africatopup.dialog.DepositDetailDialog;
import com.crazydwarf.africatopup.dialog.LoginDialog;
import com.crazydwarf.africatopup.dialog.LogupDialog;

public class UserFragment extends Fragment
{
    private Button bnLogin;
    private Button bnLogup;
    private Button bnBundle;
    private Button bnHistory;

    private RadioButton rbLocalPhone1;
    private RadioButton rbLocalPhone2;
    private RadioButton rbOtherPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        bnLogin = view.findViewById(R.id.bn_login);
        bnLogup = view.findViewById(R.id.bn_logup);
        bnBundle = view.findViewById(R.id.bn_bundle);
        bnHistory = view.findViewById(R.id.bn_history);

        rbLocalPhone1 = view.findViewById(R.id.rb_localphone1);
        rbLocalPhone2 = view.findViewById(R.id.rb_localphone2);
        rbOtherPhone = view.findViewById(R.id.rb_otherphone);
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

        rbLocalPhone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepositDetailDialog depositDetailDialog = new DepositDetailDialog(getActivity());
                depositDetailDialog.show();
            }
        });

        rbLocalPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepositDetailDialog depositDetailDialog = new DepositDetailDialog(getActivity());
                depositDetailDialog.show();
            }
        });

        rbOtherPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepositDetailDialog depositDetailDialog = new DepositDetailDialog(getActivity());
                depositDetailDialog.show();
            }
        });
    }
}
