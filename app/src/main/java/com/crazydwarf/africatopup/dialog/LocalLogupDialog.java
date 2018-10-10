package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.crazydwarf.africatopup.R;

public class LocalLogupDialog extends Dialog
{

    public LocalLogupDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_local_logup,null);
        setContentView(view);
        setListener();
    }

    void setListener()
    {
    }
}
