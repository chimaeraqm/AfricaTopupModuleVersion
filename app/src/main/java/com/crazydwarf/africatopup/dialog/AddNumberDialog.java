package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.crazydwarf.africatopup.R;

public class AddNumberDialog extends Dialog
{
    private Button bnConfirm;
    private Button bnExit;

    public AddNumberDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_number,null);
        bnConfirm = view.findViewById(R.id.bn_confirm);
        bnExit = view.findViewById(R.id.bn_exit);
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
    }
}
