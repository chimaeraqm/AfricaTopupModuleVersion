package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Utilities.UserUtil;

public class LocalLogupDialog extends Dialog
{
    private Context mContext;
    public LocalLogupDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_local_logup);
        setListener();
        setDisplayDimension();
    }

    void setListener()
    {
    }

    private void setDisplayDimension()
    {
        Window dialogWindow = getWindow();
        //TODO:如果设置相对屏幕的尺寸，这里需要先获取屏幕尺寸
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = UserUtil.dip2px(mContext,300);
        layoutParams.height = UserUtil.dip2px(mContext,200);
        dialogWindow.setAttributes(layoutParams);
    }
}
