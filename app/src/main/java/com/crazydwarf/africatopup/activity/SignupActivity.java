package com.crazydwarf.africatopup.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Utilities.Base64;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.comm_library.activity.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends BaseActivity
{
    private static final String TAG = "SignupActivity";

    private EditText _nameText;
    private EditText _phoneNoText;
    private EditText _passwordText;
    private Button _signupButton;
    private TextView _loginLink;

    private String mUserName;
    private String mPhoneNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        _nameText = findViewById(R.id.input_name);
        _phoneNoText = findViewById(R.id.input_phoneno);
        _passwordText = findViewById(R.id.input_password);
        _signupButton = findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        mUserName = _nameText.getText().toString().trim();
        final String name = mUserName;

        mPhoneNo = _phoneNoText.getText().toString().trim();
        final String phoneNo = mPhoneNo;

        String password = _passwordText.getText().toString();
        final String encode_psw = UserUtil.getMessageDigest(password.getBytes());
        //TODO : uid是注册使用手机的唯一识别号，现在暂时用name的MD5代替
        final String encode_uid = UserUtil.getMessageDigest(name.getBytes());

        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        RequestBody requestBody = new FormBody.Builder()
                                .add("username",name)
                                .add("phone",phoneNo)
                                .add("pwd",encode_psw)
                                .add("uid",encode_uid)
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
                                onSignupFailed();
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String res = response.body().string();
                                try {
                                    JSONObject jsonObject1 = new JSONObject(res);
                                    String status = jsonObject1.getString("code");
                                    if (status.equals("SUCCESS")) {
                                        onSignupSuccess();
                                    } else {
                                        onSignupFailed();
                                    }
                                } catch (Exception e) {
                                    //TODO : add exception handle process
                                    onSignupFailed();
                                }
                            }
                        });
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        /**
         * 注册成功直接登录，用户设置相当于首次登录
         */
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CURRENT_USER_SETUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.CURRENT_USER_NAME,mUserName);
        editor.putString(Constants.CURRENT_USER_PHONENO,mPhoneNo);
        editor.putString(Constants.CURRENT_USER_PASSWORD,"");
        editor.putBoolean(Constants.IS_FIRST_LOGIN,false);
        editor.putBoolean(Constants.IS_REMEMBER_PW,false);
        editor.putBoolean(Constants.IS_AUTO_LOGIN,false);
        editor.apply();
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    //TODO : 注册用户名需用手机号，手机号需要通过验证码验证
    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String phoneNo = _phoneNoText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("User name is at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (!UserUtil.isSimpleMobileNumber(phoneNo)) {
            _phoneNoText.setError("enter a valid phone number");
            valid = false;
        } else {
            _phoneNoText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Valid password is between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
