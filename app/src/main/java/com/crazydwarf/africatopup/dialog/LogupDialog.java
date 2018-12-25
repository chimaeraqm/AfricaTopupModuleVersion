package com.crazydwarf.africatopup.dialog;

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

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.comm_library.view.SmoothCheckBox;

public class LogupDialog extends Dialog
{
    private SmoothCheckBox cb_LocalPhone;
    private EditText et_UserName;
    private EditText et_Password;
    private EditText et_PassWord2;
    private Button bn_Logup;
    private TextView tv_AlreadyLogup;
    private Context mContext;

    public LogupDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logup);
        cb_LocalPhone = findViewById(R.id.cb_localphone);
        cb_LocalPhone.setChecked(false);
        et_UserName = findViewById(R.id.et_username);
        et_Password = findViewById(R.id.et_password);
        et_PassWord2 = findViewById(R.id.et_password2);
        bn_Logup = findViewById(R.id.bn_logup);
        tv_AlreadyLogup = findViewById(R.id.tv_alreadylogup);
        setListener();
        setDisplayDimension();
    }

    void setListener()
    {
        cb_LocalPhone.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if(isChecked)
                {
                    //TODO: 用本机号码填写，这里暂时用hard code
                    et_UserName.setText("243_1234567");
                    et_Password.setText("7654321342");
                    et_PassWord2.setText("7654321342");
                }
                else
                {
                    et_UserName.setText("");
                    et_Password.setText("");
                    et_PassWord2.setText("");
                }
            }
        });

        et_UserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        et_Password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        et_PassWord2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        bn_Logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordCheck();
            }
        });

        tv_AlreadyLogup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                LoginDialog loginDialog = new LoginDialog(mContext);
                loginDialog.show();
            }
        });
    }

    //TODO: add check process between initial password and confirmed password
    void passwordCheck()
    {

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
