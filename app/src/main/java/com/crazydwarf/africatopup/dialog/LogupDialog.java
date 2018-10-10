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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.UserUtil;

public class LogupDialog extends Dialog
{
    private CheckBox cb_LocalPhone;
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_logup,null);
        setContentView(view);
        cb_LocalPhone = view.findViewById(R.id.cb_localphone);
        cb_LocalPhone.setChecked(false);
        et_UserName = view.findViewById(R.id.et_username);
        et_Password = view.findViewById(R.id.et_password);
        et_PassWord2 = view.findViewById(R.id.et_password2);
        bn_Logup = view.findViewById(R.id.bn_logup);
        tv_AlreadyLogup = view.findViewById(R.id.tv_alreadylogup);
        setListener();
    }

    void setListener()
    {
        cb_LocalPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_LocalPhone.setChecked(true);
                //TODO: 用本机号码填写，这里暂时用hard code
                et_UserName.setText("243_1234567");
                et_Password.setText("7654321342");
                et_PassWord2.setText("7654321342");
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

                Window dialogWindow = loginDialog.getWindow();
                WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
                layoutParams.width = UserUtil.dip2px(mContext,300);
                layoutParams.height = UserUtil.dip2px(mContext,360);
                dialogWindow.setAttributes(layoutParams);
            }
        });
    }

    //TODO: add check process between initial password and confirmed password
    void passwordCheck()
    {

    }
}
