package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;

import java.util.zip.Inflater;

public class LoginDialog extends Dialog
{
    private EditText edUserName;
    private EditText edPassword;
    private Button bnLogin;
    private Button bnLogup;
    private TextView tvForget;
    private Context mContext;

    public LoginDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_login,null);
        edUserName = view.findViewById(R.id.et_username);
        edPassword = view.findViewById(R.id.et_password);
        bnLogin = view.findViewById(R.id.bn_login);
        bnLogup = view.findViewById(R.id.bn_logup);
        tvForget = view.findViewById(R.id.tv_forgetpw);
        setContentView(view);

        setListener();
    }

    void setListener()
    {
        edUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });

        edPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });

        bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bnLogup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                LogupDialog logupDialog = new LogupDialog(mContext);
                logupDialog.show();
            }
        });

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
