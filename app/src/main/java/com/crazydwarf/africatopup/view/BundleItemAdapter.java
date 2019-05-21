package com.crazydwarf.africatopup.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.view.SmoothCheckBox;

import java.util.List;

public class BundleItemAdapter extends RecyclerView.Adapter<BundleItemAdapter.BundleItemHolder>
{

    private String[] phoneNumbers;
    private double[] fees;
    private String[] names;
    private boolean[] checks;

    private onBundleItemRVClickListener onBundleItemRVClickListener;

    public BundleItemAdapter(String[] phoneNumbers, double[] fees,String[] names,boolean[] checks) {
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
    public void onBindViewHolder(@NonNull final BundleItemHolder holder, final int position) {
        holder.etPhoneNumber.setText(phoneNumbers[position]);
        holder.etFee.setText(String.valueOf(fees[position]));
        holder.etName.setText(names[position]);
        holder.cbSeleConfirm.setChecked(checks[position]);

        if(onBundleItemRVClickListener != null){
            holder.etFee.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String valuestr = editable.toString();
                    double value = 0;
                    if(valuestr.length() > 0)
                    {
                        value = Double.valueOf(valuestr);
                    }
                    else
                    {
                        holder.etFee.setText(String.valueOf(0));
                    }
                    fees[position] = value;
                    onBundleItemRVClickListener.onItemClick(holder.etFee,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fees.length;
    }

    public static class BundleItemHolder extends RecyclerView.ViewHolder
    {
        public SmoothCheckBox cbSeleConfirm;
        public EditText etPhoneNumber;
        public EditText etFee;
        public EditText etName;
        public ImageView imDelete;

        public BundleItemHolder(View itemView) {
            super(itemView);
            this.cbSeleConfirm = itemView.findViewById(R.id.cb_sele_confirm);
            this.etPhoneNumber = itemView.findViewById(R.id.et_phonenumber);
            this.etFee = itemView.findViewById(R.id.et_fee);
            this.etName = itemView.findViewById(R.id.et_name);
            this.imDelete = itemView.findViewById(R.id.im_delete);
        }
    }

    public double getTotleFee(){
        double[] tmpfees = fees;
        double rtn = 0;
        for (int i = 0; i < tmpfees.length; i++) {
            double tmpfee = tmpfees[i];
            rtn += tmpfee;
        }
        return rtn;
    }

    public static interface onBundleItemRVClickListener
    {
        void onItemClick(View view,int position);
    }
}
