package com.crazydwarf.africatopup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.fragment.BundleFragment;
import com.crazydwarf.africatopup.fragment.HistoryFragment;
import com.crazydwarf.africatopup.fragment.NewRechargeFragment;
import com.crazydwarf.africatopup.fragment.TxtDisplayFragment;
import com.crazydwarf.comm_library.activity.BaseActivity;

import me.yokeyword.fragmentation.SupportFragment;

public class RechargeActivity extends BaseActivity
{
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SupportFragment firstFragment = findFragment(NewRechargeFragment.class);
        if(firstFragment == null)
        {
            mFragments[FIRST] = NewRechargeFragment.newInstance();
            mFragments[SECOND] = HistoryFragment.newInstance();
            mFragments[THIRD] = BundleFragment.newInstance();
            mFragments[FOURTH] = TxtDisplayFragment.newInstance();
            loadMultipleRootFragment(R.id.view_fragment,FIRST,mFragments[FIRST],mFragments[SECOND],mFragments[THIRD],mFragments[FOURTH]);
        }
        else
        {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = HistoryFragment.newInstance();
            mFragments[THIRD] = BundleFragment.newInstance();
            mFragments[FOURTH] = TxtDisplayFragment.newInstance();
        }
    }
}
