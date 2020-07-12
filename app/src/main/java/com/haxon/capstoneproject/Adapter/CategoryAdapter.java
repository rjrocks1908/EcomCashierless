package com.haxon.capstoneproject.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.haxon.capstoneproject.CategoryActivity;
import com.haxon.capstoneproject.Models.CategoryModel;
import com.haxon.capstoneproject.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        int icon = categoryModelList.get(position).getCategoryIconLink();
        String name = categoryModelList.get(position).getCategoryName();

        holder.setCategoryName(name, position);
        holder.setCategoryIcon(icon, position);

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView categoryIcon;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryIcon = itemView.findViewById(R.id.home_category_icon);
            categoryName = itemView.findViewById(R.id.home_category_name);

        }

        private void setCategoryIcon(int icon, int position){

            categoryIcon.setImageResource(icon);

        }

        private void setCategoryName(final String name,final int position){

            categoryName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position != 0){
                        Intent intent = new Intent(itemView.getContext(), CategoryActivity.class);
                        intent.putExtra("CategoryName",name);
                        itemView.getContext().startActivity(intent);
                    }

                }
            });

        }
    }
}
