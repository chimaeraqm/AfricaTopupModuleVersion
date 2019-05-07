package com.crazydwarf.africatopup.activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.crazydwarf.comm_library.Utilities.SharedPreferencesUtils;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.comm_library.activity.BaseActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivityNew extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener
{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText _emailText;
    private EditText _passwordText;
    private CheckBox _autoLogin;
    private CheckBox _rememberPW;
    private Button _loginButton;
    private Button _withoutLoginButton;
    private TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        initView();
        initEvent();
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
                Intent intent = new Intent(LoginActivityNew.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
    }

    private void initView()
    {
        _emailText = findViewById(R.id.et_phoneno);
        _passwordText = findViewById(R.id.et_password);
        _autoLogin = findViewById(R.id.cb_autologin);
        _rememberPW = findViewById(R.id.cb_remember_pw);
        _loginButton = findViewById(R.id.bn_login);
        _withoutLoginButton = findViewById(R.id.bn_enter);
        _signupLink = findViewById(R.id.tv_tosignup);
    }

    private void initEvent()
    {
        _autoLogin.setOnCheckedChangeListener(this);
        _rememberPW.setOnCheckedChangeListener(this);
        _loginButton.setOnClickListener(this);
        _withoutLoginButton.setOnClickListener(this);
        _signupLink.setOnClickListener(this);
    }

    private void initData()
    {
        //判断用户是否第一次登陆
        if (firstLogin()) {
            _autoLogin.setChecked(false);//取消记住密码的复选框
            _rememberPW.setChecked(false);//取消自动登录的复选框
        }
        //判断是否记住密码
        if (remenberPassword()) {
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
     * 设置数据到输入框中
     */
    public void setTextName() {
        _emailText.setText("" + getLocalName());
    }

    /**
     * 把本地保存的数据设置数据到输入框中
     */
    public void setTextNameAndPassword() {
        _emailText.setText("" + getLocalName());
        _passwordText.setText("" + getLocalPassword());
    }

    /**
     * 获得保存在本地的用户名
     */
    public String getLocalName() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String name = helper.getString("name");
        return name;
    }

    /**
     * 获得保存在本地的密码
     */
    public String getLocalPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String password = helper.getString("password");
        //解码一下
        return Base64.decode(password).toString();
    }

    /**
     * 判断是否是第一次登陆
     */
    private boolean firstLogin() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean first = helper.getBoolean("first", true);
        if (first) {
            //创建一个ContentVa对象（自定义的）设置不是第一次登录，,并创建记住密码和自动登录是默认不选，创建账号和密码为空
            helper.putValues(new SharedPreferencesUtils.ContentValue("first", false),
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("name", ""),
                    new SharedPreferencesUtils.ContentValue("password", ""));
            return true;
        }
        return false;
    }
    /**
     * 判断是否自动登录
     */
    private boolean autoLogin() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean autoLogin = helper.getBoolean("autoLogin", false);
        return autoLogin;
    }

    /**
     * 判断是否记住密码
     */
    private boolean remenberPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean remenberPassword = helper.getBoolean("remenberPassword", false);
        return remenberPassword;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bn_login:
                //保存用户名
                loadUserName();
                login(); //登陆
                break;
            case R.id.bn_enter:
                break;
            case R.id.tv_tosignup:
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                break;
        }
    }

    /**
     * 保存用户账号
     */
    public void loadUserName() {
        if (!getAccount().equals("") || !getAccount().equals("请输入登录账号")) {
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
            helper.putValues(new SharedPreferencesUtils.ContentValue("name", getAccount()));
        }
    }

    /**
     * 获取账号
     */
    public String getAccount() {
        //输入需去掉空格
        return _emailText.getText().toString().trim();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton == _rememberPW)
        {
            //记住密码选框发生改变时
            if (!b)
            {
                //如果取消“记住密码”，那么同样取消自动登陆
                _autoLogin.setChecked(false);
            }
        }
        else if(compoundButton == _autoLogin)
        {
            //自动登陆选框发生改变时
            if(b)
            {
                //如果选择“自动登录”，那么同样选中“记住密码”
                _rememberPW.setChecked(true);
            }
        }
    }


    /**
     * 登录
     */
    private void login()
    {

        Log.i("_emailText",_emailText.getText().toString());
        if(TextUtils.isEmpty(_emailText.getText().toString()))
        {
            Toast.makeText(LoginActivityNew.this,"enter a valid phone number",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(_passwordText.getText())){
            Toast.makeText(LoginActivityNew.this,"between 4 and 10 alphanumeric characters",Toast.LENGTH_LONG).show();
            return;
        }

        final String name = _emailText.getText().toString().trim();
        String password = _passwordText.getText().toString();
        final String encode_psw = UserUtil.getMessageDigest(password.getBytes());
        final String uid = "cccccccccc";


        final ProgressDialog progressDialog = new ProgressDialog(LoginActivityNew.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: add different login failed status here
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        RequestBody requestBody = new FormBody.Builder()
                                .add("username",name)
                                .add("pwd",encode_psw)
                                .add("uid",uid)
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
                                String res = response.body().toString();
                                if(res == "SUCCESS")
                                {
                                    onLoginSuccess();
                                }
                                else
                                {
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
        loadCheckBoxState();//记录下当前用户记住密码和自动登录的状态;
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
    public void loadCheckBoxState(CheckBox checkBox_password, CheckBox checkBox_login) {

        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");

        //如果设置自动登录
        if (checkBox_login.isChecked()) {
            //创建记住密码和自动登录是都选择,保存密码数据
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("autoLogin", true),
                    new SharedPreferencesUtils.ContentValue("password", Base64.encode(getPassword().getBytes())));

        } else if (!checkBox_password.isChecked()) { //如果没有保存密码，那么自动登录也是不选的
            //创建记住密码和自动登录是默认不选,密码为空
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("password", ""));
        } else if (checkBox_password.isChecked()) {   //如果保存密码，没有自动登录
            //创建记住密码为选中和自动登录是默认不选,保存密码数据
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("password", Base64.encode(getPassword().getBytes())));
        }
    }

    /**
     * 是否可以点击登录按钮
     *
     * @param clickable
     */
    public void setLoginBtnClickable(boolean clickable) {
        _loginButton.setClickable(clickable);
    }
    /**
     * 获取密码
     */
    public String getPassword() {
        //去掉密码输入中的空格
        return _passwordText.getText().toString().trim();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(LoginActivityNew.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }
    }

    /*@Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }*/

}
