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

    public static TxtDisplayFragment newInstance()
    {
        Bundle args = new Bundle();

        TxtDisplayFragment fragment = new TxtDisplayFragment();
        fragment.setArguments(args);
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
        tvInfo = view.findViewById(R.id.tv_info);
        tvInfo.setText("this is the info text");
        tvInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
        return view;
    }
}
