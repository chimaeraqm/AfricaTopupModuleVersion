package com.crazydwarf.africatopup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.view.NewActivityView;

//TODO : 从LauncherActivityNew到LoginActivity，以及跳转到Signup的界面和动画有待完善
public class LauncherActivityNew extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_new);

        final ImageView im_launch_icon = findViewById(R.id.im_launch_icon);
        final TextView tv_launch_appname = findViewById(R.id.tv_launch_appname);

        Animation animation = AnimationUtils.loadAnimation(LauncherActivityNew.this,R.anim.dialog_fade_in);
        im_launch_icon.startAnimation(animation);
        tv_launch_appname.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(LauncherActivityNew.this, im_launch_icon, "transit_launch_icon");
                Intent intent = new Intent(LauncherActivityNew.this, LoginActivity.class);
                startActivity(intent,activityOptionsCompat.toBundle());
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
