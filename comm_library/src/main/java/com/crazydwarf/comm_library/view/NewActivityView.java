package com.crazydwarf.comm_library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.crazydwarf.chimaeraqm.comm_library.R;

public class NewActivityView extends LinearLayout
{
    private Context mContext;
    public NewActivityView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public NewActivityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public NewActivityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    void initView()
    {
        LayoutInflater.from(mContext).inflate(R.layout.view_new_activity,this,true);
    }
}
