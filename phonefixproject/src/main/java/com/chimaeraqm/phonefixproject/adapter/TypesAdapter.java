package com.chimaeraqm.phonefixproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chimaeraqm.phonefixproject.R;

public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.TypesViewHolder>
{
    private String[] texts = null;
    private Integer[] imageIds = null;

    public void setTexts(String[] texts) {
        this.texts = texts;
    }

    public void setImageIds(Integer[] imageIds) {
        this.imageIds = imageIds;
    }

    public TypesAdapter(String[] texts, Integer[] imageIds) {
        this.texts = texts;
        this.imageIds = imageIds;
    }

    public static class TypesViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView type_im;
        public TextView type_tv;
        public TypesViewHolder(View itemView) {
            super(itemView);
            type_im = itemView.findViewById(R.id.im_phone);
            type_tv = itemView.findViewById(R.id.tv_info);
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
