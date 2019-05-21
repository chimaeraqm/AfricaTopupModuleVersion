package com.crazydwarf.africatopup.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.fragment.FlowFragment;
import com.crazydwarf.africatopup.fragment.NewRechargeFragment;
import com.crazydwarf.chimaeraqm.wavetoolbar.WaveToolbar;
import com.crazydwarf.comm_library.Utilities.GVariable;
import com.crazydwarf.comm_library.activity.BaseActivity;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class FlowActivity extends BaseActivity
{
    private int mSelePos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        final WaveToolbar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);

        int flag_res = GVariable.STORED_COUNTRY_FLAG_RES;
        Drawable menuicon = ContextCompat.getDrawable(getBaseContext(),flag_res);
        toolBar.setmMenuIcon(menuicon);
        toolBar.setMenuIconClickListener(new WaveToolbar.MenuIconClickListener() {
            @Override
            public void OnClick(View view) {

            }
        });

        toolBar.setBackIconClickListener(new WaveToolbar.BackIconClickListener() {
            @Override
            public void OnClick() {
                Intent intent = new Intent(FlowActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        if (findFragment(FlowFragment.class) == null) {
            loadRootFragment(R.id.view_fragment, FlowFragment.newInstance());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
