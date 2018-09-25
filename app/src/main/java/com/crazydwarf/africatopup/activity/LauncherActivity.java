package com.crazydwarf.africatopup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.view.NewActivityView;

public class LauncherActivity extends AppCompatActivity
{
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
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
