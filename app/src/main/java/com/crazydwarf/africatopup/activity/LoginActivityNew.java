package com.crazydwarf.africatopup.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Utilities.Base64;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.GVariable;
import com.crazydwarf.comm_library.Utilities.SharedPreferencesUtils;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.comm_library.activity.BaseActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivityNew extends BaseActivity
{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText _userNameText;
    private EditText _passwordText;
    private CheckBox _autoLogin;
    private CheckBox _rememberPW;
    private Button _loginButton;
    private Button _withoutLoginButton;
    private TextView _signupLink;

    private String mUserName;
    private String mPhoneNumber;
    private String mPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        initView();
        initData();

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(LoginActivityNew.this, SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        _withoutLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GVariable.STORED_USER_LOGIN_STATUS = false;
                Intent intent = new Intent(LoginActivityNew.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        _autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //如果选择“自动登录”，那么同样选中“记住密码”
                    _rememberPW.setChecked(true);
                }
            }
        });

        _rememberPW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    //如果取消“记住密码”，那么同样取消自动登陆
                    _autoLogin.setChecked(false);
                }
            }
        });
    }

    private void initView()
    {
        _userNameText = findViewById(R.id.et_phoneno);
        _passwordText = findViewById(R.id.et_password);
        _autoLogin = findViewById(R.id.cb_autologin);
        _rememberPW = findViewById(R.id.cb_remember_pw);
        _loginButton = findViewById(R.id.bn_login);
        _withoutLoginButton = findViewById(R.id.bn_enter);
        _signupLink = findViewById(R.id.tv_tosignup);
    }

    private void initData()
    {
        //判断用户是否第一次登陆
        if (firstLogin()) {
            _autoLogin.setChecked(false);//取消记住密码的复选框
            _rememberPW.setChecked(false);//取消自动登录的复选框
        }
        //判断是否记住密码
        if (rememberPassword()) {
            _rememberPW.setChecked(true);//勾选记住密码
            setTextNameAndPassword();//把密码和账号输入到输入框中
        } else {
            setTextName();//把用户账号放到输入账号的输入框中
        }

        //判断是否自动登录
        if (autoLogin()) {
            _autoLogin.setChecked(true);
            login();//去登录就可以
        }
    }

    /**
     * 判断是否是第一次登陆
     */
    private boolean firstLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
        boolean firstLoginCheck = sharedPreferences.getBoolean(Constants.IS_FIRST_LOGIN,true);
        if(firstLoginCheck)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.CURRENT_USER_NAME,"");
            editor.putString(Constants.CURRENT_USER_PHONENO,"");
            editor.putString(Constants.CURRENT_USER_PASSWORD,"");
            editor.putBoolean(Constants.IS_FIRST_LOGIN,false);
            editor.putBoolean(Constants.IS_REMEMBER_PW,false);
            editor.putBoolean(Constants.IS_AUTO_LOGIN,false);
            editor.apply();
            return true;
        }
        return false;
    }

    /**
     * 判断是否自动登录
     */
    private boolean autoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
        boolean autoLoginCheck = sharedPreferences.getBoolean(Constants.IS_AUTO_LOGIN,false);
        return autoLoginCheck;
    }

    /**
     * 判断是否记住密码
     */
    private boolean rememberPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
        boolean rememberPasswordCheck = sharedPreferences.getBoolean(Constants.IS_REMEMBER_PW,false);
        return rememberPasswordCheck;
    }

    private void login()
    {
        mUserName = _userNameText.getText().toString().trim();
        if(!UserUtil.isSimpleMobileNumber(mUserName) && (mUserName.isEmpty() || mUserName.length() < 3))
        {
            Toast.makeText(LoginActivityNew.this,"Enter a valid phone number or UserName",Toast.LENGTH_LONG).show();
            return;
        }
        String tmpPassword = _passwordText.getText().toString();
        if(TextUtils.isEmpty(tmpPassword)){
            Toast.makeText(LoginActivityNew.this,"between 4 and 10 alphanumeric characters",Toast.LENGTH_LONG).show();
            return;
        }

        final String name = mUserName;
        mPassword = UserUtil.getMessageDigest(tmpPassword.getBytes());
        final String encode_psw = mPassword;
        final String encode_uid = UserUtil.getMessageDigest(name.getBytes());

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivityNew.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: add different login failed status here
        // TODO: how to deal with 3000ms delayed here as well as in signup process.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        RequestBody requestBody = new FormBody.Builder()
                                .add("username",name)
                                .add("pwd",encode_psw)
                                .add("uid",encode_uid)
                                .add("phone","")
                                .build();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(Constants.USERS_DATA_URL)
                                .post(requestBody)
                                .build();

                        Call call = okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e)
                            {
                                onLoginFailed();
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException
                            {
                                String res = response.body().string();
                                try{
                                    JSONObject jsonObject1 = new JSONObject(res);
                                    String status = jsonObject1.getString("code");
                                    if (status.equals("SUCCESS")) {
                                        mPhoneNumber = jsonObject1.getString("phone");
                                        onLoginSuccess();
                                    } else {
                                        onLoginFailed();
                                    }

                                }catch (Exception e){
                                    onLoginFailed();
                                }
                            }
                        });
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginSuccess()
    {
        /**
         * 当前用户成功登录后，首先记录当前用户记住密码和自动登录的状态
         */
        loadCheckBoxState();
        GVariable.STORED_USER_LOGIN_STATUS = true;
        Intent intent = new Intent(LoginActivityNew.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    public void onLoginFailed()
    {
        UserUtil.showToast(getBaseContext(), "Login failed",true);
    }

    /**
     * 保存用户选择“记住密码”和“自动登陆”的状态
     */
    private void loadCheckBoxState() {
        loadCheckBoxState(_rememberPW, _autoLogin);
    }

    /**
     * 保存按钮的状态值
     */
    public void loadCheckBoxState(CheckBox checkBox_password, CheckBox checkBox_login)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //首先保存用户名和手机号
        editor.putString(Constants.CURRENT_USER_NAME,mUserName);
        editor.putString(Constants.CURRENT_USER_PHONENO,mPhoneNumber);

        //创建记住密码和自动登录是都选择,保存密码数据
        if(checkBox_login.isChecked())
        {
            editor.putString(Constants.CURRENT_USER_PASSWORD,mPassword);
            editor.putBoolean(Constants.IS_AUTO_LOGIN,true);
            editor.putBoolean(Constants.IS_REMEMBER_PW,true);
        }
        //如果没有保存密码，那么自动登录也是不选的
        else if(!checkBox_password.isChecked())
        {
            editor.putString(Constants.CURRENT_USER_PASSWORD,"");
            editor.putBoolean(Constants.IS_AUTO_LOGIN,false);
            editor.putBoolean(Constants.IS_REMEMBER_PW,false);
        }
        //如果保存密码，没有自动登录
        else if(checkBox_password.isChecked())
        {
            editor.putString(Constants.CURRENT_USER_PASSWORD,mPassword);
            editor.putBoolean(Constants.IS_AUTO_LOGIN,false);
            editor.putBoolean(Constants.IS_REMEMBER_PW,true);
        }
        editor.apply();
    }

    /**
     * 设置数据到输入框中
     */
    public void setTextName()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
        mUserName = sharedPreferences.getString(Constants.CURRENT_USER_NAME,"");
        _userNameText.setText(mUserName);
    }

    /**
     * 把本地保存的数据设置数据到输入框中
     */
    public void setTextNameAndPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
        mUserName = sharedPreferences.getString(Constants.CURRENT_USER_NAME,"");
        _userNameText.setText(mUserName);
        mPassword = sharedPreferences.getString(Constants.CURRENT_USER_PASSWORD,"");
        String decode_pw = Base64.decode(mPassword).toString();
        _passwordText.setText(decode_pw);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                GVariable.STORED_USER_LOGIN_STATUS = true;
                Intent intent = new Intent(LoginActivityNew.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }
    }
}
