package com.crazydwarf.africatopup.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.view.NewActivityView;

import java.util.List;

//TODO : 从LauncherActivityNew到LoginActivityNew，以及跳转到Signup的界面和动画有待完善
public class LauncherActivityNew extends BaseActivity
{
    public final static int REQUEST_READ_PHONE_STATE = 1;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_new);

        final ImageView im_launch_icon = findViewById(R.id.im_launch_icon);
        final TextView tv_launch_appname = findViewById(R.id.tv_launch_appname);

        /**
         * 必须要动态申请该权限
         */
        //TODO : 这里获取权限及回调的逻辑要重新写，根据SubscriptionInfo中的MCC(3位)+MNC(2位)判断运营商及运营国家
        //TODO : 根据simCountInUse确定主界面中默认的电话号码
        int permissionCheck = ContextCompat.checkSelfPermission(LauncherActivityNew.this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            /**
             * 如果不同意授权
             */
            ActivityCompat.requestPermissions(LauncherActivityNew.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            SubscriptionManager mSubscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            //手机SIM卡数
            int simCountMax = mSubscriptionManager.getActiveSubscriptionInfoCountMax();
            //手机使用的SIM卡数
            int simCountInUse = mSubscriptionManager.getActiveSubscriptionInfoCount();
            //手机SIM卡信息
            List<SubscriptionInfo> activeSubscriptionInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();

            TelephonyManager telephonyManager = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
            String simOperatorName = telephonyManager.getSimOperatorName();  //运营商信息
            String networkOperatorName = telephonyManager.getNetworkOperatorName(); //网络显示名
            String line1Number = telephonyManager.getLine1Number();  //电话号码
            String deviceId = telephonyManager.getDeviceId();  //IMEI值
            String subscriberId = telephonyManager.getSubscriberId();  //IMSI值
        }

        Animation animation = AnimationUtils.loadAnimation(LauncherActivityNew.this,R.anim.dialog_fade_in);
        im_launch_icon.startAnimation(animation);
        tv_launch_appname.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //TODO：将获取当前选择国家的过程放在这里，开一个额外的线程处理
                Intent intent = new Intent(LauncherActivityNew.this, LoginActivityNew.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*if (requestCode == REQUEST_CODE_INFO_OF_PHONE_SETTINGS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                L.d("imei", "permission is granted after requested！");
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                L.d("imei", "permission is not granted after requested！");
                //这里表示申请权限后被用户拒绝了
            } else {
                L.d("imei", "permission is not granted after requested！");
            }
        }*/
    }
}
