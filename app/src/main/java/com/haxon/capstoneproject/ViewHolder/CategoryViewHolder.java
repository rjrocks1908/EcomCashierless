package com.haxon.capstoneproject.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haxon.capstoneproject.Interface.ItemClickListner;
import com.haxon.capstoneproject.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtCategoryProductName, txtCategoryDescription, txtCategoryPrice;
    public ImageView imgCategoryImage;
    public ItemClickListner listener;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCategoryProductName = itemView.findViewById(R.id.category_display_item_name);
        txtCategoryDescription = itemView.findViewById(R.id.category_display_item_description);
        txtCategoryPrice = itemView.findViewById(R.id.category_display_item_price);
        imgCategoryImage = itemView.findViewById(R.id.category_display_item_image);

    }

    public void setItemClickListener (ItemClickListner listener){

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        listener.onClick(v, getAdapterPosition(), false);

    }
}
