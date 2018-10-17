package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.UserUtil;
import com.crazydwarf.africatopup.activity.HistoryActivity;

public class HistoryDetailDialog extends Dialog
{
    private Button bn_SendWechat;
    private Button bn_SendEmail;
    private Context mContext;

    public HistoryDetailDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_history_detail);
        bn_SendWechat = findViewById(R.id.bn_sendtowechat);
        bn_SendEmail = findViewById(R.id.bn_sendtoemail);
        setListener();
        setDisplayDimension();
    }

    void setListener()
    {
        //TODO : 增加将发票信息发送至微信号或者邮箱的功能，发送完成后显示发送信息
        bn_SendWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "当前充值发票信息已发送至绑定微信号：username", Toast.LENGTH_LONG).show();
            }
        });

        bn_SendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "当前充值发票信息已发送至绑定邮箱：username@google.com", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDisplayDimension()
    {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = UserUtil.dip2px(mContext,300);
        layoutParams.height = UserUtil.dip2px(mContext,432);;
        dialogWindow.setAttributes(layoutParams);
    }

}
