package com.haxon.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.haxon.capstoneproject.Models.Products;
import com.haxon.capstoneproject.ViewHolder.CategoryViewHolder;
import com.squareup.picasso.Picasso;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;
    String category = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);



        Toolbar toolbar = findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);

        category = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //show back arrow on action bar

        productRef = FirebaseDatabase.getInstance().getReference().child("ProductsByCategory").child(category);

        categoryRecyclerView = findViewById(R.id.category_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        categoryRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(productRef,Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, CategoryViewHolder> adapter = new FirebaseRecyclerAdapter<Products, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i, @NonNull final Products products) {

//                if (category.equals(products.getCategory())){
//
                    categoryViewHolder.txtCategoryProductName.setText(products.getpName());
                    categoryViewHolder.txtCategoryDescription.setText(products.getDescription());
                    categoryViewHolder.txtCategoryPrice.setText("Price = Rs." + products.getPrice());
                    Picasso.get().load(products.getImage()).into(categoryViewHolder.imgCategoryImage);

                    categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(CategoryActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", products.getPid());
                            intent.putExtra("image",products.getImage());
                            startActivity(intent);

                        }
                    });
//
//                }

            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_display_item_layout, parent, false);
                CategoryViewHolder holder = new CategoryViewHolder(view);
                return holder;
            }
        };
        categoryRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionBar_search_icon){
            Intent intent = new Intent(CategoryActivity.this,SearchProductsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}