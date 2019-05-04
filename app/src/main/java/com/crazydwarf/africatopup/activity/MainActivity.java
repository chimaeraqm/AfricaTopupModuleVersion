package com.crazydwarf.africatopup.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crazydwarf.africatopup.fragment.LivingFragment;
import com.crazydwarf.africatopup.fragment.MainFragment;
import com.crazydwarf.africatopup.fragment.NewRechargeFragment;
import com.crazydwarf.africatopup.fragment.NewUserFragment;
import com.crazydwarf.chimaeraqm.wavetoolbar.WaveToolbar;
import com.crazydwarf.comm_library.Utilities.ActivityManager;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.fragment.QueryFragment;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.dialogs.CountrySelectDialog;
import java.util.ArrayList;
import java.util.List;
//veeria

public class MainActivity extends BaseActivity
{
    private NewRechargeFragment mRechargeFragment;
    private QueryFragment mQueryFragment;
//    private UserFragment mUserFragment;
    private NewUserFragment mNewUserFragment;

    private MainFragment mMainFragment;
    private LivingFragment mLivingFragment;
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

    private int mSelePos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO : 初始化选择国家队列，可以在启动时完成
        final TypedArray flagsArray = MainActivity.this.getResources().obtainTypedArray(com.crazydwarf.chimaeraqm.comm_library.R.array.select_flags);

        final WaveToolbar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);
        toolBar.setMenuIconClickListener(new WaveToolbar.MenuIconClickListener() {
            @Override
            public void OnClick(View view) {

                //TODO : menu显示为国旗，用于切换当前使用的国家，语言切换转移到用户设置界面
                CountrySelectDialog countrySelectDialog = new CountrySelectDialog(MainActivity.this, new CountrySelectDialog.dialogItemSelectionListener() {
                    @Override
                    public void onClick(int position, String country, int code, int flag) {
                        int resid = flagsArray.getResourceId(position,0);
                        Drawable menuicon = ContextCompat.getDrawable(getBaseContext(),resid);
                        toolBar.setmMenuIcon(menuicon);
                        mSelePos = position;
                    }
                },mSelePos);
                countrySelectDialog.show();

                /*LanguageSelectDialog dialog = new LanguageSelectDialog(MainActivity.this, new LanguageSelectDialog.dialogItemSelectionListener() {
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
                dialog.show();*/
            }
        });

        toolBar.setBackIconClickListener(new WaveToolbar.BackIconClickListener() {
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
        mMainFragment = new MainFragment();
        mLivingFragment = new LivingFragment();
        mNewUserFragment = new NewUserFragment();
/*
        mRechargeFragment = new NewRechargeFragment();
        mQueryFragment = new QueryFragment();
        mUserFragment = new UserFragment();*/
/*
        fragments.add(mRechargeFragment);
        fragments.add(mQueryFragment);
*/
        fragments.add(mMainFragment);
        fragments.add(mLivingFragment);
        fragments.add(mNewUserFragment);
//        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment,mRechargeFragment).show(mRechargeFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment,mMainFragment).show(mMainFragment).commit();
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

    private class UserFragment {
    }
}
