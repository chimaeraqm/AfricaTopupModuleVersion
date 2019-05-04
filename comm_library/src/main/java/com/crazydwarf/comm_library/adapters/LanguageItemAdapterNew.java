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

public class LanguageItemAdapterNew extends RecyclerView.Adapter<LanguageItemAdapterNew.LanguageItemHolderNew>
{
    private Context mContext;
    private Integer[] flagRes;
    private String[] countries;
    private int mSelePos = 0;

    private LanguageItemAdapterNew.onLanguageItemRVClickListener onLanguageItemRVClickListener;

    public LanguageItemAdapterNew(Context context,Integer[] flagRes, String[] countries) {
        this.countries = countries;
        this.flagRes = flagRes;
        this.mContext = context;
    }

    public void setmSelePos(int mSelePos) {
        this.mSelePos = mSelePos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LanguageItemHolderNew onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_languageitem,parent,false);
        LanguageItemAdapterNew.LanguageItemHolderNew languageItemHolder = new LanguageItemAdapterNew.LanguageItemHolderNew(view);
        return languageItemHolder;
    }


    public void setOnLanguageItemRVClickListener(LanguageItemAdapterNew.onLanguageItemRVClickListener onLanguageItemRVClickListener) {
        this.onLanguageItemRVClickListener = onLanguageItemRVClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final LanguageItemHolderNew holder, final int position)
    {
        holder.imFlag.setBackgroundResource(flagRes[position]);
        holder.tvCountryName.setText(countries[position]);

        holder.languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onLanguageItemRVClickListener != null)
                {
                    onLanguageItemRVClickListener.onItemClick(view,position);
                }
            }
        });
        initItemBgColor(holder,position);
    }


    void initItemBgColor(@NonNull final LanguageItemHolderNew holder,final int position)
    {
        if(position == mSelePos)
        {
            holder.tvCountryName.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite));
            holder.languageLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorBlue));
        }
        else
        {
            //TODO : 这里不重新设置若干条目会显示异常，需检查？
            holder.tvCountryName.setTextColor(ContextCompat.getColor(mContext,R.color.colorBlack));
            holder.languageLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorTrans));
        }
    }
    @Override
    public int getItemCount() {
        return countries.length;
    }

    public static class LanguageItemHolderNew extends RecyclerView.ViewHolder
    {
        public ImageView imFlag;
        public TextView tvCountryName;
        public ConstraintLayout languageLayout;
        public LanguageItemHolderNew(View itemView) {
            super(itemView);
            this.imFlag = itemView.findViewById(R.id.im_flag);
            this.tvCountryName = itemView.findViewById(R.id.tv_country);
            this.languageLayout = itemView.findViewById(R.id.languageitem_layout);
        }
    }

    public interface onLanguageItemRVClickListener
    {
        void onItemClick(View view, int position);
    }

}
