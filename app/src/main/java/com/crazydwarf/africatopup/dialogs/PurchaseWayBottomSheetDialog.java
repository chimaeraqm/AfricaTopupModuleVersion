package com.crazydwarf.africatopup.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Listener.DialogListener;
import com.crazydwarf.comm_library.Listener.PurchaseWayBottomSheetDialogListener;
import com.crazydwarf.comm_library.view.SmoothCheckBox;


public class PurchaseWayBottomSheetDialog extends BottomSheetDialog
{
    private View contentView;

    /**
     * corresponding to paymentMethod is as followed:
     * alipay = 0;
     * wxpay = 1;
     * orange money(ompay) = 2;
     * m pese(mppay) = 3;
     */
    private SmoothCheckBox cb_alipay_check;
    private SmoothCheckBox cb_wxpay_check;
    private SmoothCheckBox cb_ompay_check;
    private SmoothCheckBox cb_mppay_check;
    private int mPaymentMethod;
    /**
     * add interface mDialogListner to get payment method
     */
    private PurchaseWayBottomSheetDialogListener mDialogListner;

    public PurchaseWayBottomSheetDialog(@NonNull Context context,int paymentMethod,PurchaseWayBottomSheetDialogListener dialogListener) {
        super(context);
        this.mDialogListner = dialogListener;
        this.mPaymentMethod = paymentMethod;
        mDialogListner.getPaymentMethodFromDialog(mPaymentMethod);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        contentView = View.inflate(getContext(), R.layout.dialog_bottomsheet_purchaseway, null);
        setContentView(contentView);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) contentView.getParent());
        int dialogHeight = getWindowHeight();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT/*dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight*/);
        bottomSheetBehavior.setPeekHeight(dialogHeight);

        ImageButton bn_exit = findViewById(R.id.bn_exit);
        bn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //TODO : PurchaseBottomSheetDialog与此dialog切换时需记住上次选择的支付方式
        boolean isAlipay = false;
        boolean isWxpay = false;
        boolean isOmpay = false;
        boolean isMppay = false;

        if(mPaymentMethod == 0){
            isAlipay = true;
        }
        else if(mPaymentMethod == 1){
            isWxpay = true;
        }

        cb_alipay_check = findViewById(R.id.im_alipay_check);
        cb_alipay_check.setChecked(isAlipay);
        cb_alipay_check.setClickable(false);

        cb_wxpay_check = findViewById(R.id.im_wxpay_check);
        cb_wxpay_check.setChecked(isWxpay);
        cb_wxpay_check.setClickable(false);

        cb_ompay_check = findViewById(R.id.im_ompay_check);
        cb_ompay_check.setChecked(isOmpay);
        cb_ompay_check.setClickable(false);

        cb_mppay_check = findViewById(R.id.im_mppay_check);
        cb_mppay_check.setChecked(isMppay);
        cb_mppay_check.setClickable(false);

        Button bn_alipay = findViewById(R.id.bn_alipay);
        bn_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_alipay_check.setChecked(true,true);
                cb_wxpay_check.setChecked(false);
                mPaymentMethod = 0;
                mDialogListner.getPaymentMethodFromDialog(mPaymentMethod);
                dismiss();
            }
        });

        Button bn_wxpay = findViewById(R.id.bn_wxpay);
        bn_wxpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_alipay_check.setChecked(false);
                cb_wxpay_check.setChecked(true,true);
                mPaymentMethod = 1;
                mDialogListner.getPaymentMethodFromDialog(mPaymentMethod);
                dismiss();
            }
        });


    }

    private int getWindowHeight() {
        Resources res = getContext().getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
