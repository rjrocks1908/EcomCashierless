package com.haxon.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haxon.capstoneproject.Models.Users;
import com.haxon.capstoneproject.Prevalent.Prevalent;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton, loginButton;
    private RelativeLayout rellay1;
    private TextView appSlogan;
    Handler handler = new Handler(); //for the splash screen
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {       //changing items for the splash screen
//            rellay1.setVisibility(View.VISIBLE);
//            appSlogan.setVisibility(View.VISIBLE);
//        }
//    };
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rellay1 = findViewById(R.id.rellay1);
        appSlogan = findViewById(R.id.app_slogan);

        //handler.postDelayed(runnable,2000);  //delay for the splash screen

        joinNowButton = findViewById(R.id.main_join_now_btn);
        loginButton = findViewById(R.id.main_login_btn);
        progressDialog = new ProgressDialog(this);
        Paper.init(this);

        //linking two activities main and login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });

        //linking activities main and register
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //this reads the values of the stored phone and password for the remember me feature
        String usersPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String usersPasswordKey = Paper.book().read(Prevalent.userPasswordKey);
        //checking the values for emptiness
        if (usersPhoneKey != "" && usersPasswordKey != ""){
            if (!TextUtils.isEmpty(usersPhoneKey) && !TextUtils.isEmpty(usersPasswordKey)){

                progressDialog.setTitle("Login Account");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                //going to directly home activity after opening the application
                allowAccess(usersPhoneKey,usersPasswordKey);
            }
        }
    }

    private void allowAccess(final String phone, final String password) {

        //reference of location where to read/write the data in database
        //retrieve an instance of the database and getting the reference
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //dataSnapshot contains the data of the database
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()){
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "logged in successfully...", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Account does not exits!!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
