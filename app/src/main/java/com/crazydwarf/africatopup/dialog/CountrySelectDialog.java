package com.crazydwarf.africatopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.UserUtil;
import com.crazydwarf.africatopup.activity.HistoryActivity;
import com.crazydwarf.africatopup.view.CountryItemAdapter;
import com.crazydwarf.africatopup.view.HistoryItemAdapter;

import java.util.Locale;

public class CountrySelectDialog extends Dialog
{
    private dialogItemSelectionListener dialogItemSelectionListener;
    private Context mContext;

    public CountrySelectDialog(@NonNull final Context context,dialogItemSelectionListener listener)
    {
        super(context, R.style.CurrentDialog);
        this.dialogItemSelectionListener = listener;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_countries);
        RecyclerView mRecyclerview = findViewById(R.id.rv_countries);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));

        TypedArray countryArray = mContext.getResources().obtainTypedArray(R.array.select_countries);
        TypedArray codeArray = mContext.getResources().obtainTypedArray(R.array.select_codes);
        final TypedArray flagsArray = mContext.getResources().obtainTypedArray(R.array.select_flags);
        String[] countries = new String[countryArray.length()];
        Integer[] codes = new Integer[codeArray.length()];
        Integer[] flags = new Integer[flagsArray.length()];
        for(int i=0;i<countryArray.length();i++)
        {
            countries[i] = countryArray.getString(i);
            codes[i] = codeArray.getInteger(i,0);
            flags[i] = flagsArray.getResourceId(i,0);
        }
        CountryItemAdapter countryItemAdapter = new CountryItemAdapter(countries,codes,flags);
        mRecyclerview.setAdapter(countryItemAdapter);

        //TODO: 显示RecycleView点击事件
        countryItemAdapter.setOnCountryItemRVClickListener(new CountryItemAdapter.onCountryItemRVClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Integer seleFlag = flagsArray.getResourceId(position,0);
                dismiss();
            }
        });
        setDisplayDimension();
    }

    public interface dialogItemSelectionListener{
        public void onClick(View view,int position);
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
