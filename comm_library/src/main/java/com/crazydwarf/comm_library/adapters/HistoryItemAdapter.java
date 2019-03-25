package com.crazydwarf.comm_library.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.crazydwarf.chimaeraqm.comm_library.R;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemHolder> implements View.OnClickListener
{
    private String[] dates;
    private String[] fees;
    private String[] tradeIds;
    private String[] status;

    private onHistoryItemRVClickListener onHistoryItemRVClickListener;

    public HistoryItemAdapter(String[] dates, String[] fees, String[] tradeIds, String[] status) {
        this.dates = dates;
        this.fees = fees;
        this.tradeIds = tradeIds;
        this.status = status;
    }

    public void setOnHistoryItemRVClickListener(onHistoryItemRVClickListener onHistoryItemRVClickListener) {
        this.onHistoryItemRVClickListener = onHistoryItemRVClickListener;
    }

    @NonNull
    @Override
    public HistoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_historyitem,parent,false);
        HistoryItemHolder historyItemHolder = new HistoryItemHolder(view);
        view.setOnClickListener(this);
        return historyItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemHolder holder, int position) {
        holder.tvRechargeDate.setText(dates[position]);
        holder.tvRechargeFee.setText(fees[position]);
        holder.tvRechargeId.setText(tradeIds[position]);
        holder.tvRechargeStatus.setText(status[position]);
    }


    @Override
    public int getItemCount() {
        return dates.length;
    }

    @Override
    public void onClick(View view) {
        if(onHistoryItemRVClickListener != null)
        {
            onHistoryItemRVClickListener.onItemClick(view);
        }
    }

    public static class HistoryItemHolder extends RecyclerView.ViewHolder
    {
        public TextView tvRechargeDate;
        public TextView tvRechargeFee;
        public TextView tvRechargeId;
        public TextView tvRechargeStatus;

        public HistoryItemHolder(View itemView) {
            super(itemView);
            this.tvRechargeDate = itemView.findViewById(R.id.tv_recharge_date);
            this.tvRechargeFee = itemView.findViewById(R.id.tv_recharge_fee);
            this.tvRechargeId = itemView.findViewById(R.id.tv_recharge_id);
            this.tvRechargeStatus = itemView.findViewById(R.id.tv_recharge_status);
        }
    }

    public static interface onHistoryItemRVClickListener
    {
        void onItemClick(View view);
    }
}
