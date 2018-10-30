package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.UserUtil;

public class SwitchCountryConfirmDialog extends Dialog
{
    private Button bnConfirm;
    private Button bnExit;
    private int oriCountryFlag;
    private int newCountryFlag;
    private int oriCountryCode;
    private int newCountryCode;

    private ImageView imOriCountry;
    private ImageView imNewCountry;
    private TextView tvOriCountry;
    private TextView tvNewCountry;
    //clickRtn保存点击的是确认还是取消
    private boolean clickRtn;
    private dialogBnClickListener dialogBnClickListener;
    private Context mContext;

    public SwitchCountryConfirmDialog(@NonNull Context context, int oriCountryFlag, int newCountryFlag,
                                      int oriCountryCode, int newCountryCode,
                                      SwitchCountryConfirmDialog.dialogBnClickListener dialogBnClickListener) {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
        this.oriCountryFlag = oriCountryFlag;
        this.newCountryFlag = newCountryFlag;
        this.oriCountryCode = oriCountryCode;
        this.newCountryCode = newCountryCode;
        this.dialogBnClickListener = dialogBnClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_countrysele_confirm);
        bnConfirm = findViewById(R.id.bn_confirm);
        bnExit = findViewById(R.id.bn_exit);
        imOriCountry = findViewById(R.id.im_oricounty);
        imNewCountry = findViewById(R.id.im_newcountry);
        tvOriCountry = findViewById(R.id.tv_oricountry);
        tvNewCountry = findViewById(R.id.tv_newcountry);

        imOriCountry.setBackgroundResource(oriCountryFlag);
        imNewCountry.setBackgroundResource(newCountryFlag);
        String oriCountry = String.format("+%d",oriCountryCode);
        String newCountry = String.format("+%d",newCountryCode);
        tvOriCountry.setText(oriCountry);
        tvNewCountry.setText(newCountry);

        setListener();
        setDisplayDimension();
    }

    void setListener()
    {
        bnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBnClickListener.onClick(true);
                dismiss();
            }
        });

        bnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBnClickListener.onClick(false);
                dismiss();
            }
        });
    }

    public interface dialogBnClickListener{
        void onClick(boolean res);
    }


    private void setDisplayDimension()
    {
        Window dialogWindow = getWindow();
        //TODO:如果设置相对屏幕的尺寸，这里需要先获取屏幕尺寸
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = UserUtil.dip2px(mContext,240);
        layoutParams.height = UserUtil.dip2px(mContext,200);
        dialogWindow.setAttributes(layoutParams);
    }
}
