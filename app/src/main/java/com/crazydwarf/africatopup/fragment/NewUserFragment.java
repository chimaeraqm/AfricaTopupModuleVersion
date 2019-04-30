package com.crazydwarf.africatopup.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Utilities.AppLanguageUtils;
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
    private Button bn_setup_switchaccount;
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
        bn_setup_switchaccount = view.findViewById(R.id.bn_setup_switchaccount);
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
