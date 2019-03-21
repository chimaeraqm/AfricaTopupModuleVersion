package com.crazydwarf.comm_library.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.dialogs.HistoryDetailDialog;
import com.crazydwarf.comm_library.adapters.HistoryItemAdapter;
import com.crazydwarf.comm_library.view.SimpleToolBar;
import com.crazydwarf.comm_library.view.SmoothCheckBox;

public class HistoryActivity extends BaseActivity
{
    private SmoothCheckBox cb_Recent1m;
    private SmoothCheckBox cb_Recent3m;
    private SmoothCheckBox cb_Recent6m;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        SimpleToolBar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);
        toolBar.setBackIconClickListener(new SimpleToolBar.BackIconClickListener() {
            @Override
            public void OnClick() {
                finish();
            }
        });

        RecyclerView mRecyclerview = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(HistoryActivity.this,DividerItemDecoration.VERTICAL));


        String[] newdates = {"2018/09/20","2018/09/20","2018/09/20","2018/09/20","2018/09/20","2018/09/20","2018/09/20","2018/09/20",
                "2018/09/20","2018/09/20","2018/09/20","2018/09/20","2018/09/20","2018/09/20","2018/09/20","2018/09/20"};
        String[] newfees = {"99.99","99.99","99.99","99.99","99.99","99.99","99.99","99.99",
                "99.99","99.99","99.99","99.99","99.99","99.99","99.99","99.99"};
        String[] newids = {"12345678","12345678","12345678","12345678","12345678","12345678","12345678","12345678",
                "12345678","12345678","12345678","12345678","12345678","12345678","12345678","12345678"};
        String[] newstatus = {"completed","completed","completed","completed","completed","completed","completed","completed",
                "completed","completed","completed","completed","completed","completed","completed","completed"};

        HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(newdates,newfees,newids,newstatus);
        mRecyclerview.setAdapter(historyItemAdapter);

        //TODO: 显示RecycleView点击事件
        historyItemAdapter.setOnHistoryItemRVClickListener(new HistoryItemAdapter.onHistoryItemRVClickListener() {
            @Override
            public void onItemClick(View view) {
                Toast.makeText(HistoryActivity.this, "打开历史充值信息详情对话框", Toast.LENGTH_SHORT).show();
                HistoryDetailDialog historyDetailDialog = new HistoryDetailDialog(HistoryActivity.this);
                historyDetailDialog.show();
            }
        });

        cb_Recent1m = findViewById(R.id.cb_recent1m);
        cb_Recent1m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_Recent1m.setChecked(true);
                if(cb_Recent1m.isChecked())
                {
                    cb_Recent3m.setChecked(false);
                    cb_Recent6m.setChecked(false);
                }
            }
        });

        cb_Recent3m = findViewById(R.id.cb_recent3m);
        cb_Recent3m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_Recent3m.setChecked(true);
                if(cb_Recent3m.isChecked())
                {
                    cb_Recent1m.setChecked(false);
                    cb_Recent6m.setChecked(false);
                }
            }
        });

        cb_Recent6m = findViewById(R.id.cb_recent6m);
        cb_Recent6m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_Recent6m.setChecked(true);
                if(cb_Recent6m.isChecked())
                {
                    cb_Recent3m.setChecked(false);
                    cb_Recent1m.setChecked(false);
                }
            }
        });

        cb_Recent1m.setChecked(true);
        cb_Recent3m.setChecked(false);
        cb_Recent6m.setChecked(false);
    }
}
