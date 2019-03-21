package com.crazydwarf.comm_library.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.chimaeraqm.comm_library.R;

import java.util.Locale;

public class CountryItemAdapter extends RecyclerView.Adapter<CountryItemAdapter.CountryItemHolder>
{
    private Context mContext;
    private String[] countries;
    private Integer[] codes;
    private Integer[] flagRes;

    private onCountryItemRVClickListener onCountryItemRVClickListener;

    private int mSelePos = 0;

    public CountryItemAdapter(Context context,String[] countries, Integer[] codes,Integer[] flagRes,int selepos) {
        this.mContext = context;
        this.countries = countries;
        this.codes = codes;
        this.flagRes = flagRes;
        this.mSelePos = selepos;
    }

    @NonNull
    @Override
    public CountryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_countryitem,parent,false);
        CountryItemHolder countryItemHolder = new CountryItemHolder(view);
        return countryItemHolder;
    }


    public void setOnCountryItemRVClickListener(CountryItemAdapter.onCountryItemRVClickListener onCountryItemRVClickListener) {
        this.onCountryItemRVClickListener = onCountryItemRVClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryItemHolder holder, final int position) {
        holder.tvCountryName.setText(countries[position]);
        holder.tvCode.setText(String.format(Locale.US,"+%d",codes[position]));
        holder.imFlag.setBackgroundResource(flagRes[position]);
        holder.countryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCountryItemRVClickListener != null)
                {
                    onCountryItemRVClickListener.onItemClick(v,position);
                }
            }
        });
        //选中的country高亮显示
        if(position == mSelePos)
        {
            holder.tvCountryName.setTextColor(ContextCompat.getColor(mContext,R.color.colorBlack));
            holder.tvCode.setTextColor(ContextCompat.getColor(mContext,R.color.colorBlack));
            holder.countryLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorOrange));
        }
        else
        {
            //TODO : 这里不重新设置若干条目会显示异常，需检查？
            holder.tvCountryName.setTextColor(ContextCompat.getColor(mContext,R.color.colorOrange));
            holder.tvCode.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite));
            holder.countryLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorTrans));
        }
    }

    @Override
    public int getItemCount() {
        return countries.length;
    }

    public static class CountryItemHolder extends RecyclerView.ViewHolder
    {
        public TextView tvCountryName;
        public TextView tvCode;
        public ImageView imFlag;
        public ConstraintLayout countryLayout;
        public CountryItemHolder(View itemView) {
            super(itemView);
            this.tvCountryName = itemView.findViewById(R.id.tv_countryname);
            this.tvCode = itemView.findViewById(R.id.tv_code);
            this.imFlag = itemView.findViewById(R.id.im_flag);
            this.countryLayout = itemView.findViewById(R.id.countryitem_layout);
        }
    }

    public static interface onCountryItemRVClickListener
    {
        void onItemClick(View view,int position);
    }
}
