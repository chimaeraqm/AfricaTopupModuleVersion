package com.crazydwarf.africatopup.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;

import java.util.Locale;

public class CountryItemAdapter extends RecyclerView.Adapter<CountryItemAdapter.CountryItemHolder> implements View.OnClickListener
{
    private String[] countries;
    private Integer[] codes;
    private Integer[] flagRes;

    private onCountryItemRVClickListener onCountryItemRVClickListener;

    public CountryItemAdapter(String[] countries, Integer[] codes,Integer[] flagRes) {
        this.countries = countries;
        this.codes = codes;
        this.flagRes = flagRes;
    }

    @NonNull
    @Override
    public CountryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_countryitem,parent,false);
        CountryItemHolder countryItemHolder = new CountryItemHolder(view);
        view.setOnClickListener(this);
        return countryItemHolder;
    }


    public void setOnCountryItemRVClickListener(CountryItemAdapter.onCountryItemRVClickListener onCountryItemRVClickListener) {
        this.onCountryItemRVClickListener = onCountryItemRVClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryItemHolder holder, int position) {
        holder.tvCountryName.setText(countries[position]);
        holder.tvCode.setText(String.format(Locale.US,"%d",codes[position]));
        holder.imFlag.setBackgroundResource(flagRes[position]);
    }

    @Override
    public int getItemCount() {
        return countries.length;
    }

    @Override
    public void onClick(View view) {
        if(onCountryItemRVClickListener != null)
        {
            onCountryItemRVClickListener.onItemClick(view);
        }
    }

    public static class CountryItemHolder extends RecyclerView.ViewHolder
    {
        public TextView tvCountryName;
        public TextView tvCode;
        public ImageView imFlag;

        public CountryItemHolder(View itemView) {
            super(itemView);
            this.tvCountryName = itemView.findViewById(R.id.tv_countryname);
            this.tvCode = itemView.findViewById(R.id.tv_code);
            this.imFlag = itemView.findViewById(R.id.im_flag);
        }
    }

    public static interface onCountryItemRVClickListener
    {
        void onItemClick(View view);
    }
}
