package com.chimaeraqm.phonefixproject.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chimaeraqm.phonefixproject.R;
import com.crazydwarf.comm_library.Utilities.UserUtil;

public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.TypesViewHolder>
{
    private String[] texts = null;
    private Integer[] imageIds = null;
    private float mLayoutHeight;
    private int mTextColor;

    public void setTexts(String[] texts) {
        this.texts = texts;
    }

    public void setImageIds(Integer[] imageIds) {
        this.imageIds = imageIds;
    }

    public TypesAdapter(String[] texts, Integer[] imageIds,float layoutHeight,int textColor) {
        this.texts = texts;
        this.imageIds = imageIds;
        this.mLayoutHeight = layoutHeight;
        this.mTextColor = textColor;
    }

    public class TypesViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView type_im;
        public TextView type_tv;
        public TypesViewHolder(View itemView) {
            super(itemView);
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.height = UserUtil.dip2px(itemView.getContext(),mLayoutHeight);
            itemView.setLayoutParams(params);
            type_im = itemView.findViewById(R.id.im_phone);
            type_tv = itemView.findViewById(R.id.tv_info);
            type_tv.setTextColor(mTextColor);
        }
    }

    @NonNull
    @Override
    public TypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_type,parent,false);
        TypesViewHolder typesViewHolder = new TypesViewHolder(view);
        return typesViewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull TypesViewHolder holder, int position) {
        holder.type_tv.setText(texts[position]);
        holder.type_im.setImageResource(imageIds[position]);

    }

    @Override
    public int getItemCount() {
        return texts.length;
    }
}
