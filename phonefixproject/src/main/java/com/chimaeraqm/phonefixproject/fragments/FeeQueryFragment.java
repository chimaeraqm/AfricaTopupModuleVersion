package com.chimaeraqm.phonefixproject.fragments;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chimaeraqm.phonefixproject.R;
import com.chimaeraqm.phonefixproject.adapter.TypesAdapter;

public class FeeQueryFragment extends Fragment
{
    private RecyclerView mRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_feequery,container,false);
        mRecyclerview = view.findViewById(R.id.list_fees);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        //初始化时选择第一个Egypt
        String[] phonetypes = {"价位6000元以上机型","价位5000~6000元机型","价位4000~5000元机型",
                "价位3000~4000元机型","价位2000~3000元机型","价位1000~2000元机型","价位1000元以下机型"};
        Integer[] ids = new Integer[7];
        for(int i=0;i<phonetypes.length;i++)
        {
            ids[i] = R.color.colorWhite;
        }
        TypesAdapter typesAdapter = new TypesAdapter(phonetypes,ids,60, Color.BLACK);
        mRecyclerview.setAdapter(typesAdapter);
    }
}
