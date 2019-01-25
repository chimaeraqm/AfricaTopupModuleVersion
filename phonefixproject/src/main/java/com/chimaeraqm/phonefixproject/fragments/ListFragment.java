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

        String[] phonetypes = {"HUAWEI Mate20 X","HUAWEI Mate20 Pro","HUAWEI Mate20 Pro(UD)","HUAWEI Mate20",
                "HUAWEI Mate10 Pro","HUAWEI P20 Pro","HUAWEI P20",
                "HUAWEI Mate20 X","HUAWEI Mate20 Pro","HUAWEI Mate20 Pro(UD)","HUAWEI Mate20",
                "HUAWEI Mate10 Pro","HUAWEI P20 Pro","HUAWEI P20",};
        Integer[] ids = {R.drawable.hw_mate20_x,R.drawable.hw_mate20_pro,R.drawable.hw_mate20_pro_ud,R.drawable.hw_mate20,
                R.drawable.hw_mate10,R.drawable.hw_p20_pro,R.drawable.hw_p20,
                R.drawable.hw_mate20_x,R.drawable.hw_mate20_pro,R.drawable.hw_mate20_pro_ud,R.drawable.hw_mate20,
                R.drawable.hw_mate10,R.drawable.hw_p20_pro,R.drawable.hw_p20,};
        TypesAdapter typesAdapter = new TypesAdapter(phonetypes,ids,200,R.color.colorTrans);
        mRecyclerview.setAdapter(typesAdapter);
    }
}
