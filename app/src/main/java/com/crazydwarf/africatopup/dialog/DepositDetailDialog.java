package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Utilities.UserUtil;

public class DepositDetailDialog extends Dialog
{
    private Button bnConfirm;
    private Context mContext;

    public DepositDetailDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_deposit_detail);
        bnConfirm = findViewById(R.id.bn_sendtowechat);
        setListener();
        setDisplayDimension();
    }

    void setListener()
    {
        bnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setDisplayDimension()
    {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = UserUtil.dip2px(mContext,300);
        layoutParams.height = UserUtil.dip2px(mContext,360);
        dialogWindow.setAttributes(layoutParams);
    }
}
