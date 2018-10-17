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
import android.widget.ImageView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.UserUtil;

public class AddNumberDialog extends Dialog
{
    private Button bnConfirm;
    private Button bnExit;
    private ImageView bnCountry;

    private Context mContext;

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
                    public void onClick(View view, int position) {

                    }
                });
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
