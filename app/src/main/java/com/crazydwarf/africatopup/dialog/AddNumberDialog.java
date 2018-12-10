package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.UserUtil;

public class AddNumberDialog extends Dialog
{
    private Button bnConfirm;
    private Button bnExit;
    private ImageView bnCountry;
    private TextView tvPostCode;

    private Context mContext;

    private int mSelePos = 0;
    public AddNumberDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_number);
        bnConfirm = findViewById(R.id.bn_sendtowechat);
        bnExit = findViewById(R.id.bn_exit);

        bnCountry = findViewById(R.id.bn_country);
        tvPostCode = findViewById(R.id.tv_postcode);

        /**
         * 根据SharedPreferences中保存的选择显示对应的国家
         */
        SharedPreferences preferences = mContext.getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
        int seleRes = preferences.getInt(Constants.SELECTED_COUNTRY_RES,R.drawable.flag_egypt);
        int seleCode = preferences.getInt(Constants.SELECTED_COUNTRY_CODE,20);
        mSelePos = preferences.getInt(Constants.SELECTED_COUNTRY_POS,0);
        String postcode = String.format("+%d",seleCode);
        tvPostCode.setText(postcode);
        bnCountry.setBackgroundResource(seleRes);
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

        bnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        bnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountrySelectDialog countrySelectDialog = new CountrySelectDialog(mContext, new CountrySelectDialog.dialogItemSelectionListener() {
                    @Override
                    public void onClick(int position, String country, int code, int flag) {
                        bnCountry.setBackgroundResource(flag);
                        String postcode = String.format("+%d",code);
                        tvPostCode.setText(postcode);
                        mSelePos = position;
                    }
                },mSelePos);
                countrySelectDialog.show();

            }
        });
    }

    private void setDisplayDimension()
    {
        Window dialogWindow = getWindow();
        //TODO:如果设置相对屏幕的尺寸，这里需要先获取屏幕尺寸
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = UserUtil.dip2px(mContext,300);
        layoutParams.height = UserUtil.dip2px(mContext,240);
        dialogWindow.setAttributes(layoutParams);
    }
}
