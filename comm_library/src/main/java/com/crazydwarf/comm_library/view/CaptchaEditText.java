package com.crazydwarf.comm_library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.adapters.CaptchaAdapter;
import com.crazydwarf.comm_library.adapters.CountryItemAdapter;

public class CaptchaEditText extends LinearLayout
{
    private Context mContext;
    private InputMethodManager imm;
    private int mSelePos = 0;

    public CaptchaEditText(Context context, Context mContext) {
        super(context);
        this.mContext = mContext;
        loadView();
    }

    public CaptchaEditText(Context context, @Nullable AttributeSet attrs, Context mContext) {
        super(context, attrs);
        this.mContext = mContext;
    }

    public CaptchaEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr, Context mContext) {
        super(context, attrs, defStyleAttr);
        this.mContext = mContext;
    }

    void loadView()
    {
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.edittext_captcha, this);
        initView(view);
        initEvent();
    }

    void initView(View view)
    {
        RecyclerView mRecyclerview = findViewById(R.id.rv_main);
        Integer[] values = {0,0,0,0};

        CaptchaAdapter captchaAdapter = new CaptchaAdapter(mContext,values,mSelePos);
        mRecyclerview.setAdapter(captchaAdapter);
    }

    void initEvent()
    {

    }
}
