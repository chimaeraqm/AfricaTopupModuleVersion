package com.crazydwarf.comm_library.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.view.SmoothCheckBox;

import java.util.List;

public class LanguageItemAdapter extends RecyclerView.Adapter<LanguageItemAdapter.LanguageItemHolder>
{
    private Integer[] flagRes;
    private String[] countries;
    private int mSelePos = 0;

    private LanguageItemAdapter.onLanguageItemRVClickListener onLanguageItemRVClickListener;

    public LanguageItemAdapter(Integer[] flagRes,String[] countries) {
        this.countries = countries;
        this.flagRes = flagRes;
    }

    public void setmSelePos(int mSelePos) {
        this.mSelePos = mSelePos;
    }

    @NonNull
    @Override
    public LanguageItemAdapter.LanguageItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_languageitem,parent,false);
        LanguageItemAdapter.LanguageItemHolder languageItemHolder = new LanguageItemAdapter.LanguageItemHolder(view);
        return languageItemHolder;
    }


    public void setOnLanguageItemRVClickListener(LanguageItemAdapter.onLanguageItemRVClickListener onLanguageItemRVClickListener) {
        this.onLanguageItemRVClickListener = onLanguageItemRVClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final LanguageItemHolder holder, final int position, @NonNull List<Object> payloads)
    {
        if(payloads.isEmpty())
        {
            holder.cbFlag.setChecked(mSelePos == position);
            holder.imFlag.setBackgroundResource(flagRes[position]);
            holder.tvCountryName.setText(countries[position]);
        }
        else
        {
            holder.cbFlag.setChecked(mSelePos == position);
        }

        holder.cbFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onLanguageItemRVClickListener != null) {
                    onLanguageItemRVClickListener.onItemClick(v,position);
                }
                if(mSelePos != position)
                {
                    holder.cbFlag.setChecked(true);
                    notifyItemChanged(mSelePos);
                }
                mSelePos = position;
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageItemAdapter.LanguageItemHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return countries.length;
    }

    public static class LanguageItemHolder extends RecyclerView.ViewHolder
    {
        public SmoothCheckBox cbFlag;
        public ImageView imFlag;
        public TextView tvCountryName;
        public LanguageItemHolder(View itemView) {
            super(itemView);
            //this.cbFlag = itemView.findViewById(R.id.cb_flag);
            this.imFlag = itemView.findViewById(R.id.im_flag);
            this.tvCountryName = itemView.findViewById(R.id.tv_country);
        }
    }

    public interface onLanguageItemRVClickListener
    {
        void onItemClick(View view,int position);
    }

}
