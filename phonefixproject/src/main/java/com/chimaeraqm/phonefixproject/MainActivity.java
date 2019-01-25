package com.chimaeraqm.phonefixproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.chimaeraqm.phonefixproject.fragments.FeeQueryFragment;
import com.chimaeraqm.phonefixproject.fragments.ListFragment;
import com.chimaeraqm.phonefixproject.fragments.UserFragment;
import com.crazydwarf.comm_library.Utilities.ActivityManager;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.view.SimpleToolBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
{
    private ListFragment mListFragment;
    private FeeQueryFragment mFeeQueryFragment;
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
        mListFragment = new ListFragment();
        mFeeQueryFragment = new FeeQueryFragment();
        mUserFragment = new UserFragment();
        fragments.add(mListFragment);
        fragments.add(mFeeQueryFragment);
        fragments.add(mUserFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment,mListFragment).show(mListFragment).commit();
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
