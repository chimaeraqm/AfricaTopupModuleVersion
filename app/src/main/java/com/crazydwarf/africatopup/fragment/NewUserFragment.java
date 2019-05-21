package com.crazydwarf.africatopup.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.activity.LoginActivityNew;
import com.crazydwarf.comm_library.Utilities.AppLanguageUtils;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.GVariable;
import com.crazydwarf.comm_library.dialogs.CountrySelectDialog;
import com.crazydwarf.comm_library.dialogs.LanguageSelectDialog;

import java.util.Locale;

public class NewUserFragment extends Fragment
{
    private Button bn_setup_account;
    private Button bn_setup_pay;
    private Button bn_setup_language;
    private Button bn_setup_csservice;
    private Button bn_setup_about;
    private Button bn_setup_logout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_new,container,false);
        bn_setup_account = view.findViewById(R.id.bn_setup_account);
        bn_setup_pay = view.findViewById(R.id.bn_setup_pay);
        bn_setup_language = view.findViewById(R.id.bn_setup_language);
        bn_setup_csservice = view.findViewById(R.id.bn_setup_csservice);
        bn_setup_about = view.findViewById(R.id.bn_setup_about);
        bn_setup_logout = view.findViewById(R.id.bn_setup_logout);
        initView();
        return view;
    }

    void initView()
    {
        bn_setup_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bn_setup_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LanguageSelectDialog languageSelectDialog = new LanguageSelectDialog(getContext(), new LanguageSelectDialog.dialogItemSelectionListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }

                    @Override
                    public void onButtonConfirmClick(View view, int sele) {
                        String language = AppLanguageUtils.getLanguageBySelePos(sele);
                        Locale locale = AppLanguageUtils.getLocaleByLanguage(language);
                        AppLanguageUtils.saveLanguageSetting(getContext(),locale);
                        getActivity().recreate();

                    }
                });
                languageSelectDialog.show();
            }
        });

        if(GVariable.STORED_USER_LOGIN_STATUS == false) {
            bn_setup_logout.setText(R.string.string_user_login);
        }
        else
        {
            bn_setup_logout.setText(R.string.string_logout);
        }
        bn_setup_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 退出登录即将用户设置设为null，并返回登录页面
                 */
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.CURRENT_USER_PASSWORD,"");
                editor.putBoolean(Constants.IS_REMEMBER_PW,false);
                editor.putBoolean(Constants.IS_AUTO_LOGIN,false);
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivityNew.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                getActivity().finish();
            }
        });
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
