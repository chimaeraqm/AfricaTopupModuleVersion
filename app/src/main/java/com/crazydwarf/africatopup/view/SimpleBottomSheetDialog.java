package com.crazydwarf.africatopup.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazydwarf.africatopup.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleBottomSheetDialog extends BottomSheetDialog
{
    private LinearLayout mPanelItems;
    private DialogAdapter adapter;
    private Context mContext;
    private View contentView;

    public SimpleBottomSheetDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        contentView = View.inflate(getContext(), R.layout.dialog_bottomsheet, null);
        setContentView(contentView);
        ((View)contentView.getParent()).setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorTrans));
        mPanelItems = findViewById(R.id.panelItems);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public void addItems(List<DialogListItem> items) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView.LayoutManager manager;
        adapter = new DialogAdapter(mContext,items);
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(params);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        mPanelItems.addView(recyclerView);
    }

    public void setItemClick(OnItemClickListener onItemClickListener) {
        adapter.setItemClick(onItemClickListener);
    }

    public void setItemSelected(String key) {
        adapter.setItemSelected(key);
    }

    public void setHeight(int height) {
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View)contentView.getParent());
        mBehavior.setPeekHeight(height);
    }

    private class DialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<DialogListItem> mItems = Collections.emptyList();
        private Context mContext;
        private OnItemClickListener itemClickListener;
        private String itemKey;


        public DialogAdapter(Context context, List<DialogListItem> mItems) {
            setList(mItems);
            mContext = context;
        }

        private void setList(List<DialogListItem> items) {
            mItems = items == null ? new ArrayList<DialogListItem>() : items;
        }

        public void setItemClick(OnItemClickListener onItemClickListener) {
            this.itemClickListener = onItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = new DialogListItemView(mContext);
            return new DialogListItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((DialogListItemView) holder.itemView).setListItem(mItems.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if (itemClickListener != null)
                    {
                        itemClickListener.click(mItems.get(position));
                    }
                }
            });
        }

        public void setItemSelected(String key) {
            itemKey = key;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class DialogListItemHolder extends RecyclerView.ViewHolder {
            public DialogListItemHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public  class DialogListItemView extends FrameLayout {

        public DialogListItemView(Context context) {
            super(context);
            init();
        }

        public DialogListItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public DialogListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private TextView tvItem;
        private ImageView imItem;

        private DialogListItem listItem;

        private void init() {
            LayoutInflater.from(getContext()).inflate(R.layout.listview_bottomsheet, this);
            tvItem = findViewById(R.id.tv_item);
            imItem = findViewById(R.id.im_item);
        }

        public void setListItem(DialogListItem listItem) {
            this.listItem = listItem;
            tvItem.setText(listItem.title == null ? "" : listItem.title);
        }

        public DialogListItem getListItem()
        {
            return listItem;
        }

        @Override
        public void setOnClickListener(OnClickListener l) {
            findViewById(R.id.layout_item).setOnClickListener(l);
        }
    }

    public interface OnItemClickListener {
        void click(DialogListItem item);
    }
}
