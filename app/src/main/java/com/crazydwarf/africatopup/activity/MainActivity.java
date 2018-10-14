package com.crazydwarf.africatopup.activity;

import android.annotation.TargetApi;
import android.app.usage.ConfigurationStats;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.content.res.ConfigurationHelper;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.crazydwarf.africatopup.AppLanguageUtils;
import com.crazydwarf.africatopup.ConstantLanguages;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.UserUtil;
import com.crazydwarf.africatopup.dialog.CountrySelectDialog;
import com.crazydwarf.africatopup.dialog.LanguageSelectDialog;
import com.crazydwarf.africatopup.fragment.QueryFragment;
import com.crazydwarf.africatopup.fragment.RechargeFragment;
import com.crazydwarf.africatopup.fragment.UserFragment;
import com.crazydwarf.africatopup.view.DialogListItem;
import com.crazydwarf.africatopup.view.SimpleBottomSheetDialog;
import com.crazydwarf.africatopup.view.SimpleToolBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private RechargeFragment mRechargeFragment;
    private QueryFragment mQueryFragment;
    private UserFragment mUserFragment;
    private List<Fragment> fragments = new ArrayList<>();
    //private static int mSeleLanguage;

    private static final int CHANGE_LANGUAGE_REQUEST_CODE = 1;

    /**
     * @param lastfragment 上次浏览的fragment
     */
    private int lastfragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                {
                    if(lastfragment != 0)
                    {
                        switchFragment(0);
                        lastfragment = 0;
                    }
                }
                    return true;
                case R.id.navigation_dashboard:
                    if(lastfragment != 1)
                    {
                        switchFragment(1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if(lastfragment != 2)
                    {
                        switchFragment(2);
                        lastfragment = 2;
                    }
                    return true;
                    default:
                    {
                        if(lastfragment != 0)
                        {
                            switchFragment(0);
                            lastfragment = 0;
                        }

                    }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleToolBar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);
        toolBar.setMenuIconClickListener(new SimpleToolBar.MenuIconClickListener() {
            @Override
            public void OnClick(View view) {

                LanguageSelectDialog dialog = new LanguageSelectDialog(MainActivity.this, new LanguageSelectDialog.dialogItemSelectionListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }

                    @Override
                    public void onButtonConfirmClick(View view, int sele) {
                        if(sele == 1)
                        {
                            Locale locale = AppLanguageUtils.getLocaleByLanguage("en");
                            AppLanguageUtils.saveLanguageSetting(MainActivity.this,locale);
                        }
                        else
                        {
                            Locale locale = AppLanguageUtils.getLocaleByLanguage("zh");
                            AppLanguageUtils.saveLanguageSetting(MainActivity.this,locale);
                        }
                        //TODO : 这里利用SharedPreferences保存当前的语言设置

                        recreate();
                    }
                });
                dialog.show();
            }
        });
        initFragments();
    }

    void initFragments()
    {
        mRechargeFragment = new RechargeFragment();
        mQueryFragment = new QueryFragment();
        mUserFragment = new UserFragment();
        fragments.add(mRechargeFragment);
        fragments.add(mQueryFragment);
        fragments.add(mUserFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment,mRechargeFragment).show(mRechargeFragment).commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void switchFragment(int index)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragments.get(lastfragment));
        if(!fragments.get(index).isAdded())
        {
            fragmentTransaction.add(R.id.view_fragment,fragments.get(index));
        }
        fragmentTransaction.show(fragments.get(index)).commitAllowingStateLoss();
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
         //super.attachBaseContext(attachBaseContext(newBase,ConstantLanguages.ENGLISH));
        super.attachBaseContext(updateResources(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHANGE_LANGUAGE_REQUEST_CODE) {
            recreate();
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context) {
        Resources resources = context.getResources();
        String name = context.getPackageName() + "_LANGUAGE";
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String language = preferences.getString("LANGUAGE","zh");

        Locale locale = AppLanguageUtils.getLocaleByLanguage(language);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }
}
