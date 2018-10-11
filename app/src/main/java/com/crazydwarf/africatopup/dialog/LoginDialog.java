package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.UserUtil;
import com.crazydwarf.africatopup.view.SmoothCheckBox;

public class LoginDialog extends Dialog
{
    private EditText edUserName;
    private EditText edPassword;
    private Button bnLogin;
    private Button bnLogup;
    private TextView tvForget;
    private SmoothCheckBox cb_LocalPhone;
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
        cb_LocalPhone = view.findViewById(R.id.cb_localphone);
        cb_LocalPhone.setChecked(false);
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

                Window dialogWindow = logupDialog.getWindow();
                WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
                layoutParams.width = UserUtil.dip2px(mContext,300);
                layoutParams.height = UserUtil.dip2px(mContext,360);
                dialogWindow.setAttributes(layoutParams);

            }
        });

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cb_LocalPhone.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if(isChecked)
                {
                    edUserName.setText("1234567");
                }
                else
                {
                    edUserName.setText("");
                }

            }
        });
    }
}
