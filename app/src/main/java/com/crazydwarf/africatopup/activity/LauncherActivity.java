package com.crazydwarf.africatopup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.view.NewActivityView;
import com.crazydwarf.comm_library.activity.BaseActivity;

public class LauncherActivity extends BaseActivity
{
    private static final String ORDER_REQUEST_URL = "https://wx.dwarfworkshop.com/congo/wxAppPay.php";

    private Button bnEnter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        bnEnter = findViewById(R.id.bn_enter);
        bnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        NewActivityView newActivityView = findViewById(R.id.insert_dialog);
        Animation animation = AnimationUtils.loadAnimation(LauncherActivity.this,R.anim.dialog_fade_in);
        newActivityView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
