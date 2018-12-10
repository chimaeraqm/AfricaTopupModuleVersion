package com.crazydwarf.africatopup.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crazydwarf.comm_library.Utilities.ActivityManager;
import com.crazydwarf.comm_library.Utilities.AppLanguageUtils;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.dialog.LanguageSelectDialog;
import com.crazydwarf.africatopup.fragment.QueryFragment;
import com.crazydwarf.africatopup.fragment.RechargeFragment;
import com.crazydwarf.africatopup.fragment.UserFragment;
import com.crazydwarf.africatopup.view.SimpleToolBar;
import com.crazydwarf.comm_library.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity
{
    private RechargeFragment mRechargeFragment;
    private QueryFragment mQueryFragment;
    private UserFragment mUserFragment;
    private List<Fragment> fragments = new ArrayList<>();
    private long exitTime = 0;

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
                        String language = AppLanguageUtils.getLanguageBySelePos(sele);
                        Locale locale = AppLanguageUtils.getLocaleByLanguage(language);
                        AppLanguageUtils.saveLanguageSetting(MainActivity.this,locale);
                        recreate();
                    }
                });
                dialog.show();
            }
        });

        toolBar.setBackIconClickListener(new SimpleToolBar.BackIconClickListener() {
            @Override
            public void OnClick() {
                //MainActivity点击back两次后关闭程序
                activityExit();
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

    public void activityExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManager.getInstance().finishAllActivity();
        }
    }
}
