package com.crazydwarf.africatopup.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.fragment.NewRechargeFragment;
import com.crazydwarf.chimaeraqm.wavetoolbar.WaveToolbar;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.dialogs.CountrySelectDialog;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class RechargeActivity extends BaseActivity
{
    private int mSelePos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        //TODO : 初始化选择国家队列，可以在启动时完成
        final TypedArray flagsArray = RechargeActivity.this.getResources().obtainTypedArray(com.crazydwarf.chimaeraqm.comm_library.R.array.select_flags);

        final WaveToolbar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);
        toolBar.setMenuIconClickListener(new WaveToolbar.MenuIconClickListener() {
            @Override
            public void OnClick(View view) {

                //TODO : menu显示为国旗，用于切换当前使用的国家，语言切换转移到用户设置界面
                CountrySelectDialog countrySelectDialog = new CountrySelectDialog(RechargeActivity.this, new CountrySelectDialog.dialogItemSelectionListener() {
                    @Override
                    public void onClick(int position, String country, int code, int flag) {
                        int resid = flagsArray.getResourceId(position,0);
                        Drawable menuicon = ContextCompat.getDrawable(getBaseContext(),resid);
                        toolBar.setmMenuIcon(menuicon);
                        mSelePos = position;
                    }
                },mSelePos);
                countrySelectDialog.show();
            }
        });

        toolBar.setBackIconClickListener(new WaveToolbar.BackIconClickListener() {
            @Override
            public void OnClick() {
                Intent intent = new Intent(RechargeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (findFragment(NewRechargeFragment.class) == null) {
            loadRootFragment(R.id.view_fragment, NewRechargeFragment.newInstance());
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
