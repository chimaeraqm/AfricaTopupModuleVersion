package com.crazydwarf.africatopup.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.GVariable;
import com.crazydwarf.comm_library.activity.BaseActivity;
import com.crazydwarf.comm_library.view.NewActivityView;

import java.util.List;

//TODO : 从LauncherActivityNew到LoginActivityNew，以及跳转到Signup的界面和动画有待完善
public class LauncherActivityNew extends BaseActivity
{
    /**
     * 如需要在线程中更新ui，则需要在此进行
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            double a = 0;
        }
    };

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
        //TODO : 根据simCountInUse确定主界面中默认的电话号码，现阶段无法获得sim卡对应的手机号
        int permissionCheck = ContextCompat.checkSelfPermission(LauncherActivityNew.this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            /**
             * 如果不同意授权
             */
            ActivityCompat.requestPermissions(LauncherActivityNew.this, new String[]{Manifest.permission.READ_PHONE_STATE}, Constants.REQUEST_READ_PHONE_STATE);
        } else {
        }

        Animation animation = AnimationUtils.loadAnimation(LauncherActivityNew.this,R.anim.dialog_fade_in);
        im_launch_icon.startAnimation(animation);
        tv_launch_appname.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                double a = 0;
                //TODO : 这里开启一个线程获取所有SharedPreferences中存储的内容，并给设置的对应Constants全局变量赋值
                /**
                 * selected_country_operator 保存的格式为operator#Operator2#operator3#...
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String selected_country = sharedPreferences.getString(Constants.SELECTED_COUNTRY,null);
                        int selected_country_pos = sharedPreferences.getInt(Constants.SELECTED_COUNTRY_POS,-1);
                        int selected_country_res = sharedPreferences.getInt(Constants.SELECTED_COUNTRY_RES,-1);
                        int selected_country_code = sharedPreferences.getInt(Constants.SELECTED_COUNTRY_CODE,-1);
                        int selected_country_operator = sharedPreferences.getInt(Constants.SELECTED_COUNTRY_OPERATOR,-1);

                        String selected_language = sharedPreferences.getString(Constants.SELECTED_LANGUAGE,null);
                        int selected_language_flag = sharedPreferences.getInt(Constants.SELECTED_LANGUAGE_FLAG,-1);

                        /**
                         * 对于异常值，设置缺省值并重置sharedPreferences中对应的保存值
                         */
                        if(selected_country == null || selected_country_pos < 0 || selected_country_res < 0 || selected_country_code < 0 || selected_country_operator < 0)
                        {
                            TypedArray countryArray = LauncherActivityNew.this.getResources().obtainTypedArray(com.crazydwarf.chimaeraqm.comm_library.R.array.select_countries);
                            TypedArray codeArray = LauncherActivityNew.this.getResources().obtainTypedArray(com.crazydwarf.chimaeraqm.comm_library.R.array.select_codes);
                            TypedArray flagsArray = LauncherActivityNew.this.getResources().obtainTypedArray(com.crazydwarf.chimaeraqm.comm_library.R.array.select_flags);
                            TypedArray operatorSeqArray = LauncherActivityNew.this.getResources().obtainTypedArray(R.array.operator_seq);

                            selected_country = countryArray.getString(0);
                            selected_country_pos = 0;
                            selected_country_res = flagsArray.getResourceId(0,0);
                            selected_country_code = codeArray.getInt(0,0);

                            selected_country_operator = operatorSeqArray.getResourceId(0,0);

                            editor.putString(Constants.SELECTED_COUNTRY,selected_country);
                            editor.putInt(Constants.SELECTED_COUNTRY_POS,selected_country_pos);
                            editor.putInt(Constants.SELECTED_COUNTRY_RES,selected_country_res);
                            editor.putInt(Constants.SELECTED_COUNTRY_CODE,selected_country_code);
                            editor.putInt(Constants.SELECTED_COUNTRY_OPERATOR,selected_country_operator);

                            countryArray.recycle();
                            codeArray.recycle();
                            flagsArray.recycle();
                            operatorSeqArray.recycle();
                        }
                        if(selected_language == null || selected_language_flag < 0)
                        {
                            TypedArray lanArray = LauncherActivityNew.this.getResources().obtainTypedArray(com.crazydwarf.chimaeraqm.comm_library.R.array.select_languages);
                            TypedArray lanFlagArray = LauncherActivityNew.this.getResources().obtainTypedArray(com.crazydwarf.chimaeraqm.comm_library.R.array.select_languageflags);

                            selected_language = lanArray.getString(0);
                            selected_language_flag = lanFlagArray.getResourceId(0,0);

                            editor.putString(Constants.SELECTED_LANGUAGE,selected_language);
                            editor.putInt(Constants.SELECTED_LANGUAGE_FLAG,selected_language_flag);

                            lanArray.recycle();
                            lanFlagArray.recycle();
                        }

                        //TODO : 为了正确显示本机的sim卡使用情况，每次启动都需要识别本机的sim卡信息，这样是否必要？
                        SubscriptionManager mSubscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                        //手机SIM卡数
                        int simCountInUse = mSubscriptionManager.getActiveSubscriptionInfoCount();
                        //手机SIM卡信息
                        List<SubscriptionInfo> activeSubscriptionInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
                        TelephonyManager telephonyManager = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
                        //TODO : operator_sim1,operator_country_sim1赋值有误，临时这么处理
                        String operator_sim1 = telephonyManager.getSimOperatorName();  //运营商信息
                        String operator_country_sim1 = telephonyManager.getNetworkOperatorName(); //网络显示名
                        //TODO : 检验sim2
                        String operator_sim2 = null;
                        String operator_country_sim2 = null;
                        if(simCountInUse > 1)
                        {

                        }
                        editor.putString(Constants.OPERATOR_SIM1,operator_sim1);
                        editor.putString(Constants.OPERATOR_COUNTRY_SIM1,operator_country_sim1);
                        editor.putString(Constants.OPERATOR_SIM2,operator_sim2);
                        editor.putString(Constants.OPERATOR_COUNTRY_SIM2,operator_country_sim2);

                        GVariable.STORED_COUNTRY = selected_country;
                        GVariable.STORED_COUNTRY_FLAG_RES = selected_country_res;
                        GVariable.STORED_COUNTRY_POS = selected_country_pos;
                        GVariable.STORED_COUNTRY_CODE = selected_country_code;
                        GVariable.STORED_COUNTRY_OPERATORS = selected_country_operator;

                        GVariable.STORED_LANGUAGE = selected_language;
                        GVariable.STORED_LANGUAGE_FLAG = selected_language_flag;

                        GVariable.STORED_OPERATOR_SIM1 = operator_sim1;
                        GVariable.STORED_OPERATOR_COUNTRY_SIM1 = operator_country_sim1;
                        GVariable.STORED_OPERATOR_SIM2 = operator_sim2;
                        GVariable.STORED_OPERATOR_COUNTRY_SIM2 = operator_country_sim2;

                        editor.apply();
                    }
                }).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /**
                 * 根据用户是否自动登录状态选择跳转的页面
                 */
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
                boolean mAutoLoginCheck = sharedPreferences.getBoolean(Constants.IS_AUTO_LOGIN,false);
                Intent intent;
                if(mAutoLoginCheck)
                {
                    GVariable.STORED_USER_LOGIN_STATUS = true;
                    intent = new Intent(LauncherActivityNew.this, MainActivity.class);
                }
                else
                {
                    GVariable.STORED_USER_LOGIN_STATUS = false;
                    intent = new Intent(LauncherActivityNew.this, LoginActivityNew.class);
                }
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
