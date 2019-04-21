package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.activity.BundleActivity;
import com.crazydwarf.africatopup.activity.HistoryActivity;
import com.crazydwarf.comm_library.dialogs.DepositDetailDialog;
import com.crazydwarf.comm_library.dialogs.LoginDialog;
import com.crazydwarf.comm_library.dialogs.LogupDialog;
import com.crazydwarf.comm_library.view.SmoothCheckBox;

public class NewUserFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_new,container,false);

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
}
