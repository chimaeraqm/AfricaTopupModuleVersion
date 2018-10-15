package com.crazydwarf.africatopup.dialog;

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

import com.crazydwarf.africatopup.Utilities.AppLanguageUtils;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.UserUtil;
import com.crazydwarf.africatopup.view.LanguageItemAdapter;


public class LanguageSelectDialog extends Dialog
{
    private dialogItemSelectionListener dialogItemSelectionListener;
    private Context mContext;
    private Button bn_Confirm;
    private Button bn_Exit;
    private int mCurrentSeleLanguage;
    private int mPostSeleLanguage;

    public LanguageSelectDialog(@NonNull Context context, dialogItemSelectionListener listener)
    {
        super(context, R.style.CurrentDialog);
        this.mContext = context;
        this.dialogItemSelectionListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_languages);
        RecyclerView mRecyclerview = findViewById(R.id.rv_languages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        TypedArray namesArray = mContext.getResources().obtainTypedArray(R.array.select_languages);
        final TypedArray flagsArray = mContext.getResources().obtainTypedArray(R.array.select_languageflags);
        String[] names = new String[namesArray.length()];
        Integer[] flags = new Integer[flagsArray.length()];


        for(int i=0;i<namesArray.length();i++)
        {
            names[i] = namesArray.getString(i);
            flags[i] = flagsArray.getResourceId(i,0);
        }
        final LanguageItemAdapter languageItemAdapter = new LanguageItemAdapter(flags,names);
        mRecyclerview.setAdapter(languageItemAdapter);
        //mPostSeleLanguage在这里获取保存的语言设置，并初始化弹出的对话框
        String language = AppLanguageUtils.getSavedLanguage(mContext);
        mPostSeleLanguage = AppLanguageUtils.getSelePosByLanguage(language);
        languageItemAdapter.setmSelePos(mPostSeleLanguage);
        languageItemAdapter.notifyDataSetChanged();

        languageItemAdapter.setOnLanguageItemRVClickListener(new LanguageItemAdapter.onLanguageItemRVClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(dialogItemSelectionListener != null)
                {
                    dialogItemSelectionListener.onClick(view,position);
                    //mCurrentSeleLanguage获取新点击的语言选项
                    mCurrentSeleLanguage = position;
                }
            }
        });

        bn_Confirm = findViewById(R.id.bn_confirm);
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
        });
        setDisplayDimension();
    }

    public interface dialogItemSelectionListener{
        void onClick(View view, int position);
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
