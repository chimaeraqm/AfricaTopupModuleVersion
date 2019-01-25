package com.chimaeraqm.phonefixproject.fragments;

import android.content.res.TypedArray;
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

public class ListFragment extends Fragment
{
    private RecyclerView mRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        mRecyclerview = view.findViewById(R.id.list_types);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        //初始化时选择第一个Egypt
        final TypedArray operatorArray = getActivity().getResources().obtainTypedArray(R.array.operator_egypt_20);

        String[] phonetypes = {"价位6000元以上机型","价位6000元及以上机型","价位6000元及以上机型",
                "价位5000~6000元机型", "价位5000~6000元机型",
                "价位4000~5000元机型","价位4000~5000元机型",
                "价位3000~4000元机型","价位3000~4000元机型",
                "价位2000~3000元机型","价位2000~3000元机型","价位2000~3000元机型",
                "价位1000~2000元机型",
                "价位1000元以下机型",};
        Integer[] ids = {R.drawable.huawei_mate20_pro,R.drawable.huawei_mate20_pro_ud,R.drawable.huawei_mate20_pro,
                R.drawable.huawei_mate20, R.drawable.huawei_p20_pro,
                R.drawable.huawei_p20,R.drawable.huawei_mate10_pro,
                R.drawable.huawei_nova4,R.drawable.huawei_nova3,
                R.drawable.huawei_nova3i, R.drawable.huawei_cx9p, R.drawable.huawei_cxmax,
                R.drawable.huawei_cx9,
                R.drawable.huawei_cx8e};
        TypesAdapter typesAdapter = new TypesAdapter(phonetypes,ids,200,R.color.colorBlue);
        mRecyclerview.setAdapter(typesAdapter);
    }
}
