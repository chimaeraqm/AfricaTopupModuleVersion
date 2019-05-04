package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.chimaeraqm.wavetoolbar.WaveToolbar;
import com.crazydwarf.comm_library.activity.BaseActivity;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public class TxtDisplayFragment extends SwipeBackFragment
{
    private TextView tvInfo;

    private static final String TXT_INFO = "txt_info";

    public static TxtDisplayFragment newInstance(String txt)
    {
        Bundle bundle = new Bundle();
        bundle.putString(TXT_INFO,txt);
        TxtDisplayFragment fragment = new TxtDisplayFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_txt_display,container,false);
        Bundle bundle = this.getArguments();
        String _txt = bundle.getString(TXT_INFO);
        tvInfo = view.findViewById(R.id.tv_info);
        tvInfo.setText(_txt);
        tvInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
        return view;
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}
