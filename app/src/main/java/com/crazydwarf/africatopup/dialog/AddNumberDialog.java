package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.UserUtil;
import com.crazydwarf.africatopup.activity.HistoryActivity;

public class AddNumberDialog extends Dialog
{
    private Button bnConfirm;
    private Button bnExit;
    private Button bnCountry;

    private Context mContext;

    public AddNumberDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_number,null);
        bnConfirm = view.findViewById(R.id.bn_confirm);
        bnExit = view.findViewById(R.id.bn_exit);
        bnCountry = view.findViewById(R.id.bn_country);
        setContentView(view);
        setListener();
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
                CountrySelectDialog countrySelectDialog = new CountrySelectDialog(mContext);
                countrySelectDialog.show();

                Window dialogWindow = countrySelectDialog.getWindow();
                WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
                layoutParams.width = UserUtil.dip2px(mContext,240);
                layoutParams.height = UserUtil.dip2px(mContext,400);
                layoutParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
                dialogWindow.setAttributes(layoutParams);

            }
        });
    }
}
