package com.haxon.capstoneproject.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.haxon.capstoneproject.HomeActivity;
import com.haxon.capstoneproject.MainActivity;
import com.haxon.capstoneproject.R;

public class adminCategoryActivity extends AppCompatActivity {

    private ImageView foodItems, footWear, books, grooming;
    private ImageView sports, walletsBagPurses, hatsCaps, shoes;
    private ImageView headphonesHandFree, laptops, watches, mobilePhones;
    private Button logoutBtn, checkOrderBtn, maintainProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        logoutBtn = findViewById(R.id.admin_logout_btn);
        checkOrderBtn = findViewById(R.id.check_order_btn);
        maintainProductsBtn = findViewById(R.id.maintain_btn);

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        checkOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });

        foodItems = findViewById(R.id.foodItems);
        footWear = findViewById(R.id.footWear);
        books = findViewById(R.id.Books);
        grooming = findViewById(R.id.Grooming);
        sports = findViewById(R.id.sports);
        walletsBagPurses = findViewById(R.id.purses_bag_wallets);
        hatsCaps = findViewById(R.id.hats_caps);
        shoes = findViewById(R.id.shoes);
        headphonesHandFree = findViewById(R.id.headphones_handfree);
        laptops = findViewById(R.id.laptops_pc);
        watches = findViewById(R.id.watches);
        mobilePhones = findViewById(R.id.mobilephones);

        foodItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Food Items");
                startActivity(intent);
            }
        });

        footWear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Foot Wear");
                startActivity(intent);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Books");
                startActivity(intent);
            }
        });

        grooming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Grooming");
                startActivity(intent);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Sports");
                startActivity(intent);
            }
        });

        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Hats Caps");
                startActivity(intent);
            }
        });

        walletsBagPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Wallets Bags Purses");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });

        headphonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","HeadPhones HandFree");
                startActivity(intent);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Watches");
                startActivity(intent);
            }
        });

        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Mobile Phones");
                startActivity(intent);
            }
        });
    }
}
