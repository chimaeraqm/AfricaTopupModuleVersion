package com.crazydwarf.africatopup.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.crazydwarf.africatopup.R;

import java.util.List;

public class BundleItemAdapter extends RecyclerView.Adapter<BundleItemAdapter.BundleItemHolder> implements View.OnClickListener
{

    private String[] phoneNumbers;
    private String[] fees;
    private String[] names;
    private boolean[] checks;

    private onBundleItemRVClickListener onBundleItemRVClickListener;

    public BundleItemAdapter(String[] phoneNumbers, String[] fees,String[] names,boolean[] checks) {
        this.phoneNumbers = phoneNumbers;
        this.fees = fees;
        this.names = names;
        this.checks = checks;
    }

    public void setOnBundleItemRVClickListener(onBundleItemRVClickListener onBundleItemRVClickListener) {
        this.onBundleItemRVClickListener = onBundleItemRVClickListener;
    }

    @NonNull
    @Override
    public BundleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bundleitem,parent,false);
        BundleItemHolder bundleItemHolder = new BundleItemHolder(view);

        return bundleItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BundleItemHolder holder, int position) {
        holder.etPhoneNumber.setText(phoneNumbers[position]);
        holder.etFee.setText(fees[position]);
        holder.etName.setText(names[position]);
        holder.cbSeleConfirm.setChecked(checks[position]);
    }

    @Override
    public int getItemCount() {
        return fees.length;
    }

    @Override
    public void onClick(View view) {
        if(onBundleItemRVClickListener != null)
        {
            onBundleItemRVClickListener.onItemClick(view);
        }
    }

    public static class BundleItemHolder extends RecyclerView.ViewHolder
    {
        public SmoothCheckBox cbSeleConfirm;
        public EditText etPhoneNumber;
        public EditText etFee;
        public EditText etName;
        public Button bnDelete;

        public BundleItemHolder(View itemView) {
            super(itemView);
            this.cbSeleConfirm = itemView.findViewById(R.id.cb_sele_confirm);
            this.etPhoneNumber = itemView.findViewById(R.id.et_phonenumber);
            this.etFee = itemView.findViewById(R.id.et_fee);
            this.etName = itemView.findViewById(R.id.et_name);
            this.bnDelete = itemView.findViewById(R.id.bn_delete);
        }
    }

    public static interface onBundleItemRVClickListener
    {
        void onItemClick(View view);
    }
}
