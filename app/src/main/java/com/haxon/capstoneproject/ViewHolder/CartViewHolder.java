package com.haxon.capstoneproject.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haxon.capstoneproject.Interface.ItemClickListner;
import com.haxon.capstoneproject.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    public ImageView imgProductImage;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        imgProductImage = itemView.findViewById(R.id.cart_product_image);
    }


    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v, getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
