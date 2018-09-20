package com.crazydwarf.africatopup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.crazydwarf.africatopup.R;
import com.crazydwarf.africatopup.view.HistoryItemAdapter;
import com.crazydwarf.africatopup.view.SimpleToolBar;

public class HistoryActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        SimpleToolBar toolBar = findViewById(R.id.top_menu);
        setSupportActionBar(toolBar);
        toolBar.setBackIconClickListener(new SimpleToolBar.BackIconClickListener() {
            @Override
            public void OnClick() {
                Intent intent = new Intent(HistoryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RecyclerView mRecyclerview = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(HistoryActivity.this,DividerItemDecoration.VERTICAL));


        String[] newdates = {"09/20/2018","09/20/2018","09/20/2018","09/20/2018","09/20/2018","09/20/2018","09/20/2018","09/20/2018",
                "09/20/2018","09/20/2018","09/20/2018","09/20/2018","09/20/2018","09/20/2018","09/20/2018","09/20/2018"};
        String[] newfees = {"99.99","99.99","99.99","99.99","99.99","99.99","99.99","99.99",
                "99.99","99.99","99.99","99.99","99.99","99.99","99.99","99.99"};
        String[] newids = {"12345678","12345678","12345678","12345678","12345678","12345678","12345678","12345678",
                "12345678","12345678","12345678","12345678","12345678","12345678","12345678","12345678"};
        String[] newstatus = {"completed","completed","completed","completed","completed","completed","completed","completed",
                "completed","completed","completed","completed","completed","completed","completed","completed"};

        HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(newdates,newfees,newids,newstatus);
        mRecyclerview.setAdapter(historyItemAdapter);
    }
}
