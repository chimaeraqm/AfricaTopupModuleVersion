package com.crazydwarf.africatopup.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.fragment.QueryFragment;
import com.crazydwarf.africatopup.fragment.RechargeFragment;
import com.crazydwarf.africatopup.fragment.UserFragment;
import com.crazydwarf.africatopup.view.DialogListItem;
import com.crazydwarf.africatopup.view.SimpleBottomSheetDialog;
import com.crazydwarf.africatopup.view.SimpleToolBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RechargeFragment mRechargeFragment;
    private QueryFragment mQueryFragment;
    private UserFragment mUserFragment;
    private List<Fragment> fragments = new ArrayList<>();

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
                SimpleBottomSheetDialog simpleBottomSheetDialog = new SimpleBottomSheetDialog(MainActivity.this);
                final List<DialogListItem> items = new ArrayList<DialogListItem>();
                items.add(new DialogListItem("中文"));
                items.add(new DialogListItem("英语"));
                items.add(new DialogListItem("法语"));
                simpleBottomSheetDialog.addItems(items);
                simpleBottomSheetDialog.setItemClick(new SimpleBottomSheetDialog.OnItemClickListener() {
                    @Override
                    public void click(DialogListItem item) {

                    }
                });
                simpleBottomSheetDialog.show();
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
}
