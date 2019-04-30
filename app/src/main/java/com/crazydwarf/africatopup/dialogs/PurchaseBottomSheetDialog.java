package com.crazydwarf.africatopup.dialogs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chimaeraqm.module_wechatpay.WXPayUtils;
import com.chimaeraqm.module_wechatpay.WechatpayModuleActivity;
import com.crazydwarf.africatopup.R;
import com.crazydwarf.comm_library.Listener.DialogListener;
import com.crazydwarf.comm_library.Listener.PurchaseWayBottomSheetDialogListener;
import com.crazydwarf.comm_library.Utilities.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PurchaseBottomSheetDialog extends BottomSheetDialog
{
    private View contentView;
    private float mAmount;
    private float mRate = 0.0f;
    private static final String EXCHANGE_RATE_REQUEST_URL = "https://wx.dwarfworkshop.com/congo/OpenExchangeRateRequest.php";
    private int mPaymentMethod = 0;

    /**
     * tv_exchange_rate_info显示的内容会在线程中刷新，故作为公共变量
     */
    private TextView tv_exchange_rate_info;

    /**
     * add interface mDialogListner to grab purchase process confirmed or not
     */
    private DialogListener mDialogListner;

    //TODO : 现在暂时采取每次充值窗口打开时更新一次汇率，且刷新在线程中，有滞后，以后要改成定时刷新，先完成汇率确认再进入支付界面
    public PurchaseBottomSheetDialog(@NonNull Context context,float amount,DialogListener dialogListener) {
        super(context);
        this.mAmount = amount;
        this.mDialogListner = dialogListener;
        mDialogListner.getPurchaseRequestFromDialog(false,mRate,0);
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

        final Button bn_purchaseway = findViewById(R.id.bn_purchaseway);
        bn_purchaseway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseWayBottomSheetDialog purchaseWayBottomSheetDialog = new PurchaseWayBottomSheetDialog(getContext(), mPaymentMethod,new PurchaseWayBottomSheetDialogListener() {
                    @Override
                    public void getPaymentMethodFromDialog(int methodid) {
                        if(methodid != mPaymentMethod){
                            if(methodid == 1) {
                                bn_purchaseway.setText(R.string.string_wechat_pay);
                            }
                            else{
                                bn_purchaseway.setText(R.string.string_alipay);
                            }
                            mPaymentMethod = methodid;
                        }
                    }
                });
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

        //TODO : 点击确认支付后，返回支付process开始确认和当前汇率
        Button bn_Confirm = findViewById(R.id.bn_confirm);
        bn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogListner.getPurchaseRequestFromDialog(true,mRate,mPaymentMethod);
                dismiss();
            }
        });

        tv_exchange_rate_info = findViewById(R.id.tv_exchange_rate_info);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(EXCHANGE_RATE_REQUEST_URL)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                UserUtil.showToastShort("汇率更新失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String str = response.body().string();
                //TODO : 向服务端请求并得到反馈信息，获取了id,rate,time,rate_CDF，现在仅对rate美元汇率做处理，rate_CDF备用
                try{
                    JSONObject jsonObject = new JSONObject(str);
                    String rate_response = jsonObject.getString("rate");
                    String rateCDF_response = jsonObject.getString("rate_CDF");
                    String dollar = getContext().getResources().getString(R.string.string_curreny_usd);
                    String rmb = getContext().getResources().getString(R.string.string_curreny_rmb);
                    String str_exchange_rate_info = String.format("%s 1.0 = %s %s", dollar,rmb,rate_response);
                    tv_exchange_rate_info.setText(str_exchange_rate_info);
                    mRate = Float.valueOf(rate_response);
                } catch (JSONException e){
                    e.printStackTrace();
                }
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
