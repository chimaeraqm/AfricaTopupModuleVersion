package com.crazydwarf.comm_library.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.comm_library.view.SmoothCheckBox;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        edUserName = findViewById(R.id.et_username);
        edPassword = findViewById(R.id.et_password);
        bnLogin = findViewById(R.id.bn_login);
        bnLogup = findViewById(R.id.bn_logup);
        tvForget = findViewById(R.id.tv_forgetpw);
        cb_LocalPhone = findViewById(R.id.cb_localphone);
        cb_LocalPhone.setChecked(false);
        setListener();
        setDisplayDimension();
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

    private void setDisplayDimension()
    {
        Window dialogWindow = getWindow();
        //TODO:如果设置相对屏幕的尺寸，这里需要先获取屏幕尺寸
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = UserUtil.dip2px(mContext,300);
        layoutParams.height = UserUtil.dip2px(mContext,360);
        dialogWindow.setAttributes(layoutParams);
    }
}
