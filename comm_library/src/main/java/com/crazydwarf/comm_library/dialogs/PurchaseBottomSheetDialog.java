package com.crazydwarf.comm_library.dialogs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.Objects.DialogListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PurchaseBottomSheetDialog extends BottomSheetDialog/* implements View.OnClickListener*/
{
//    private Context mContext;
    private View contentView;
    private float mAmount;

    public PurchaseBottomSheetDialog(@NonNull Context context,float amount) {
        super(context);
        this.mAmount = amount;
//        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        contentView = View.inflate(getContext(), R.layout.dialog_bottomsheet_purchase, null);
        setContentView(contentView);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) contentView.getParent());
        int dialogHeight = getWindowHeight();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT/*dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight*/);
        bottomSheetBehavior.setPeekHeight(dialogHeight);

        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        };

        Button bn_purchaseway = findViewById(R.id.bn_purchaseway);
        bn_purchaseway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseWayBottomSheetDialog purchaseWayBottomSheetDialog = new PurchaseWayBottomSheetDialog(getContext());
                purchaseWayBottomSheetDialog.show();
            }
        });

        ImageButton bn_exit = findViewById(R.id.bn_exit);
        bn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TextView tvValue = findViewById(R.id.tv_value);
        String tvValueStr = String.format("%.2f",mAmount);
        tvValue.setText(tvValueStr);

        Button bn_Confirm = findViewById(R.id.bn_confirm);
        bn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    private int getWindowHeight() {
        Resources res = getContext().getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 设置默认高度为2/3屏幕高度
     */
    public int getDafaultHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        return height;
    }


}
