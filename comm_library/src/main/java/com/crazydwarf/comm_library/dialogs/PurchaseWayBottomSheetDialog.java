package com.crazydwarf.comm_library.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.crazydwarf.chimaeraqm.comm_library.R;

public class PurchaseWayBottomSheetDialog extends BottomSheetDialog
{
//    private Context mContext;
    private View contentView;

    public PurchaseWayBottomSheetDialog(@NonNull Context context) {
        super(context);
//        this.mContext = context;
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
    }

    private int getWindowHeight() {
        Resources res = getContext().getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
