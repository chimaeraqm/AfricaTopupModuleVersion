package com.crazydwarf.africatopup.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.Utilities.UserUtil;
import com.crazydwarf.africatopup.activity.BundleActivity;
import com.crazydwarf.africatopup.activity.TxtDisplayActivity;
import com.crazydwarf.africatopup.view.CommonAdapter;

public class QueryFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_query,container,false);

        RecyclerView mRecyclerview = view.findViewById(R.id.list_carriers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        String[] newTitles = {"VODOCAM","ORANGE","AFRICELL","AIRTEL"};
        Integer[] ids = {R.drawable.ic_keyboard_arrow_left_black_32dp,
                R.drawable.ic_keyboard_arrow_left_black_32dp,
                R.drawable.ic_keyboard_arrow_left_black_32dp,
                R.drawable.ic_keyboard_arrow_left_black_32dp};
        CommonAdapter commonAdapter = new CommonAdapter(newTitles,ids);
        mRecyclerview.setAdapter(commonAdapter);

        //TODO:添加Recycleview点击事件
        commonAdapter.setOnCommonRVItemClickListener(new CommonAdapter.OnCommonRVItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Toast.makeText(getActivity(), "显示运营商资费清单", Toast.LENGTH_SHORT).show();
                String txt = UserUtil.readFromRaw(getActivity(),R.raw.ribbontmp);
                Intent intent = new Intent(getActivity(), TxtDisplayActivity.class);
                intent.putExtra("OPERATOR_INFO",txt);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
