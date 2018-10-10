package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crazydwarf.africatopup.R;

public class HistoryDetailDialog extends Dialog
{
    private Button bn_SendWechat;
    private Button bn_SendEmail;
    private Context mContext;

    public HistoryDetailDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_history_detail,null);
        bn_SendWechat = view.findViewById(R.id.bn_sendtowechat);
        bn_SendEmail = view.findViewById(R.id.bn_sendtoemail);
        setContentView(view);
        setListener();
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

}
