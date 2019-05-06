package com.crazydwarf.comm_library.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.Utilities.UserUtil;


public class DefineValueDialog extends Dialog
{
    private dialogItemSelectionListener dialogItemSelectionListener;
    private Context mContext;
    private Button bn_Confirm;
    private Button bn_Exit;
    private EditText et_DefineValue;
    private int mValue = 0;

    public DefineValueDialog(@NonNull Context context, dialogItemSelectionListener listener)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
        this.dialogItemSelectionListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_definevalue);

        et_DefineValue = findViewById(R.id.et_definevalue);
        et_DefineValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                double a = 0;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mValue = Integer.valueOf(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bn_Confirm = findViewById(R.id.bn_confirm);
        bn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogItemSelectionListener.onButtonConfirmClick(v,mValue);
                dismiss();
            }
        });

        bn_Exit = findViewById(R.id.bn_exit);
        bn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setDisplayDimension();
    }

    public interface dialogItemSelectionListener{
        void onButtonConfirmClick(View view, int value);
    }

    /**
     * 在dialog新建时即设置其尺寸，避免在每次调用时都重复设置的过程
     */
    private void setDisplayDimension()
    {
        Window dialogWindow = getWindow();
        //TODO:如果设置相对屏幕的尺寸，这里需要先获取屏幕尺寸
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = UserUtil.dip2px(mContext,200);
        layoutParams.height = UserUtil.dip2px(mContext,180);
        dialogWindow.setAttributes(layoutParams);
    }
}
