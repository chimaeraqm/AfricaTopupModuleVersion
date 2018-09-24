package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;

public class LogupDialog extends Dialog
{
    public LogupDialog(@NonNull Context context)
    {
        super(context, R.style.CurrentDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_logup,null);
        setContentView(view);

        setListener();
    }

    void setListener()
    {
    }
}
