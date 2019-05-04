package com.crazydwarf.comm_library.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.Utilities.AppLanguageUtils;
import com.crazydwarf.comm_library.Utilities.UserUtil;
import com.crazydwarf.comm_library.adapters.LanguageItemAdapterNew;


public class DefineValueDialog extends Dialog
{
    private dialogItemSelectionListener dialogItemSelectionListener;
    private Context mContext;
    private Button bn_Confirm;
    private Button bn_Exit;
    private int mCurrentSeleLanguage;
    private int mPostSeleLanguage;

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

        /*bn_Confirm = findViewById(R.id.bn_confirm);
        bn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //语言选择改变时
                if(mPostSeleLanguage != mCurrentSeleLanguage)
                {
                    dialogItemSelectionListener.onButtonConfirmClick(v,mCurrentSeleLanguage);
                    mPostSeleLanguage = mCurrentSeleLanguage;
                }
                dismiss();
            }
        });

        bn_Exit = findViewById(R.id.bn_exit);
        bn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*/
        setDisplayDimension();
    }

    public interface dialogItemSelectionListener{
        void onButtonConfirmClick(View view, int sele);
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
        layoutParams.height = UserUtil.dip2px(mContext,220);
        dialogWindow.setAttributes(layoutParams);
    }
}
