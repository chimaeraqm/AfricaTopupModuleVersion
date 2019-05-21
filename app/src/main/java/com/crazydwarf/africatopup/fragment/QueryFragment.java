package com.crazydwarf.africatopup.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.view.CommonAdapter;
import com.crazydwarf.comm_library.Utilities.Constants;
import com.crazydwarf.comm_library.Utilities.GVariable;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public class QueryFragment extends SwipeBackFragment
{
    private RecyclerView mRecyclerview;
    private String[] operatorSeq;
    private Integer[] ids;
    private CommonAdapter commonAdapter;
    private int mSelePos = 0;

    public static QueryFragment newInstance()
    {
        Bundle args = new Bundle();
        QueryFragment fragment = new QueryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_query,container,false);
        mRecyclerview = view.findViewById(R.id.list_carriers);
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

        int seleOperator = GVariable.STORED_COUNTRY_OPERATORS;
        TypedArray operatorArray = getActivity().getResources().obtainTypedArray(seleOperator);

        operatorSeq = new String[operatorArray.length()];
        ids = new Integer[operatorArray.length()];
        for(int i=0;i<operatorArray.length();i++)
        {
            operatorSeq[i] = operatorArray.getString(i);
            ids[i] = R.drawable.ic_keyboard_arrow_left_gray_32dp;
        }
        commonAdapter = new CommonAdapter(operatorSeq,ids);
        mRecyclerview.setAdapter(commonAdapter);

        //TODO:添加Recycleview点击事件
        commonAdapter.setOnCommonRVItemClickListener(new CommonAdapter.OnCommonRVItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                String operator_txt = operatorSeq[position];

                String _txt = String.format("This is a info text for Operator %s",operator_txt);
                start(TxtDisplayFragment.newInstance(_txt));
            }

            @Override
            public void onItemLongClick(View view,int position) {

            }
        });
    }
}
