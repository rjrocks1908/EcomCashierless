package com.haxon.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class adminCategoryActivity extends AppCompatActivity {

    private ImageView tShirts, sportsTShirts, femaleDresses, sweaters;
    private ImageView glasses, walletsBagPurses, hatsCaps, shoes;
    private ImageView headphonesHandFree, laptops, watches, mobilePhones;
    private Button logoutBtn, checkOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        logoutBtn = findViewById(R.id.admin_logout_btn);
        checkOrderBtn = findViewById(R.id.check_order_btn);

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

        tShirts = findViewById(R.id.t_shirts);
        sportsTShirts = findViewById(R.id.sports_t_shirts);
        femaleDresses = findViewById(R.id.female_dresses);
        sweaters = findViewById(R.id.sweathers);
        glasses = findViewById(R.id.glasses);
        walletsBagPurses = findViewById(R.id.purses_bag_wallets);
        hatsCaps = findViewById(R.id.hats_caps);
        shoes = findViewById(R.id.shoes);
        headphonesHandFree = findViewById(R.id.headphones_handfree);
        laptops = findViewById(R.id.laptops_pc);
        watches = findViewById(R.id.watches);
        mobilePhones = findViewById(R.id.mobilephones);

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","TShirts");
                startActivity(intent);
            }
        });

        sportsTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Sports TShirts");
                startActivity(intent);
            }
        });

        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Female Dresses");
                startActivity(intent);
            }
        });

        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Sweaters");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryActivity.this, adminAddNewProductActivity.class);
                intent.putExtra("category","Glasses");
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
