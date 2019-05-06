package com.crazydwarf.comm_library.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.view.MaterialEditText;

import java.util.Locale;

public class CaptchaAdapter extends RecyclerView.Adapter<CaptchaAdapter.CaptchaHolder>
{
    private Context mContext;
    private Integer[] values;
    private int mSelePos = 0;

    public CaptchaAdapter(Context mContext, Integer[] values,int selepos) {
        this.mContext = mContext;
        this.values = values;
        this.mSelePos = selepos;
    }

    @NonNull
    @Override
    public CaptchaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edittext_captcha,parent,false);
        CaptchaHolder captchaHolder = new CaptchaHolder(view);
        return captchaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CaptchaHolder holder, int position) {
        /**
         * mSelePos == position时，即光标移动到的位置
         */

        int value = values[position];
        if(mSelePos == position)
        {
            holder.tv_back.setText(value);
            holder.tv_back.setTextColor(ContextCompat.getColor(mContext,R.color.colorBlue));
            holder.im_bline.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorBlue));
        }
        else
        {
            holder.tv_back.setText(values[position]);
            holder.tv_back.setTextColor(ContextCompat.getColor(mContext,R.color.colorGray));
            holder.im_bline.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorGray));
        }
    }

    @Override
    public int getItemCount() {
        return values.length;
    }

    public int getmSelePos() {
        return mSelePos;
    }

    public void setmSelePos(int mSelePos) {
        this.mSelePos = mSelePos;
    }

    public static class CaptchaHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_back;
        private ImageView im_bline;
        private MaterialEditText et_main;
        public CaptchaHolder(View itemView) {
            super(itemView);
            this.tv_back = itemView.findViewById(R.id.tv_back);
            this.im_bline = itemView.findViewById(R.id.im_bline);
            this.et_main = itemView.findViewById(R.id.et_main);

        }
    }
}
