package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.Constants;
import com.crazydwarf.africatopup.Utilities.UserUtil;
import com.crazydwarf.africatopup.view.CountryItemAdapter;


public class CountrySelectDialog extends Dialog
{
    private dialogItemSelectionListener dialogItemSelectionListener;
    private Context mContext;
    private String[] countries;
    private Integer[] codes;
    private Integer[] flags;

    /**
     * @param mSelepos 记录当前选择的country，在list中设置高亮显示
     */
    private int mSelepos;

    public CountrySelectDialog(@NonNull final Context context,dialogItemSelectionListener listener,int selePos)
    {
        super(context, R.style.CurrentDialog);
        this.dialogItemSelectionListener = listener;
        this.mContext = context;
        this.mSelepos = selePos;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_countries);
        RecyclerView mRecyclerview = findViewById(R.id.rv_countries);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.scrollToPosition(mSelepos);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));

        final TypedArray countryArray = mContext.getResources().obtainTypedArray(R.array.select_countries);
        final TypedArray codeArray = mContext.getResources().obtainTypedArray(R.array.select_codes);
        final TypedArray flagsArray = mContext.getResources().obtainTypedArray(R.array.select_flags);
        countries = new String[countryArray.length()];
        codes = new Integer[codeArray.length()];
        flags = new Integer[flagsArray.length()];
        for(int i=0;i<countryArray.length();i++)
        {
            countries[i] = countryArray.getString(i);
            codes[i] = codeArray.getInteger(i,0);
            flags[i] = flagsArray.getResourceId(i,0);
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.USER_PREFS,Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        mSelepos = sharedPreferences.getInt(Constants.SELECTED_COUNTRY_POS,0);
        CountryItemAdapter countryItemAdapter = new CountryItemAdapter(mContext,countries,codes,flags,mSelepos);
        mRecyclerview.setAdapter(countryItemAdapter);
        //TODO: 显示RecycleView点击事件
        countryItemAdapter.setOnCountryItemRVClickListener(new CountryItemAdapter.onCountryItemRVClickListener() {
            @Override
            public void onItemClick(View view, final int position)
            {
                if(dialogItemSelectionListener != null)
                {
                    final String country = countries[position];
                    final int code = codes[position];
                    final int flag = flags[position];

                    final int oriCountryFlag = flags[mSelepos];
                    final int oriCountryCode = codes[mSelepos];
                    final String oldcountry = countries[mSelepos];
                    SwitchCountryConfirmDialog switchCountryConfirmDialog = new SwitchCountryConfirmDialog(mContext,
                            oriCountryFlag, flag, oriCountryCode, code, new SwitchCountryConfirmDialog.dialogBnClickListener() {
                        @Override
                        public void onClick(boolean res) {
                            if(res)
                            {
                                editor.putInt(Constants.SELECTED_COUNTRY_POS,position);
                                editor.putInt(Constants.SELECTED_COUNTRY_RES,flag);
                                editor.putInt(Constants.SELECTED_COUNTRY_CODE,code);
                                editor.apply();
                                dialogItemSelectionListener.onClick(position,country,code,flag);
                            }
                            else{
                                dialogItemSelectionListener.onClick(mSelepos,oldcountry,oriCountryCode,oriCountryFlag);
                            }
                        }
                    });
                    switchCountryConfirmDialog.show();
                }
                dismiss();
            }
        });
        setDisplayDimension();
    }

    public interface dialogItemSelectionListener{
        public void onClick(int position,String country,int code,int flag);
    }

    private void setDisplayDimension()
    {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = UserUtil.dip2px(mContext,240);
        layoutParams.height = UserUtil.dip2px(mContext,400);
        layoutParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
        dialogWindow.setAttributes(layoutParams);
    }
}
