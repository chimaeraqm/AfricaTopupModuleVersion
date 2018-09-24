package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.crazydwarf.africatopup.R;

public class DepositDetailDialog extends Dialog
{
    private Button bnConfirm;
    public DepositDetailDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_deposit_detail,null);
        bnConfirm = view.findViewById(R.id.bn_confirm);
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
    }
}
