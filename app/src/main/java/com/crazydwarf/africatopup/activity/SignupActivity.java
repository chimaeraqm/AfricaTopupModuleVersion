package com.crazydwarf.africatopup.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
    private EditText _passwordText;
    private Button _signupButton;
    private TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        _nameText = findViewById(R.id.input_name);
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

        final String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();
        final String encode_psw = UserUtil.getMessageDigest(password.getBytes());
        final String uid = "cccccccccc";

        // TODO: Implement your own signup logic here.
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
                                onSignupFailed();
                            }
                            @Override
                            public void onResponse(Call call, Response response)
                            {
                                onSignupSuccess();
                            }
                        });
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        //_signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        //_signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

/*
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
*/

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
