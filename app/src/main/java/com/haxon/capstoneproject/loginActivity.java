package com.haxon.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haxon.capstoneproject.Models.Users;
import com.haxon.capstoneproject.Prevalent.Prevalent;
import com.rey.material.widget.CheckBox;

public class loginActivity extends AppCompatActivity {

    private EditText inputNumber, inputPassword;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private String parentsDbName = "Users";
    private CheckBox checkBoxRememberMe;
    private TextView adminLink, notAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Connecting the UI with the java
        inputNumber = findViewById(R.id.login_phone_number_input);
        inputPassword = findViewById(R.id.login_password_input);
        loginButton = findViewById(R.id.login_btn);
        progressDialog = new ProgressDialog(this);
        adminLink = findViewById(R.id.admin_panel_link);
        notAdminLink = findViewById(R.id.not_admin_panel_link);

        //Connecting the UI of checkBox with the java file.
        checkBoxRememberMe = findViewById(R.id.remember_me_checkBox);
        Paper.init(this);

        //Change the login to admin's login and access the admin database
        //remember me feature doesn't work here
        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentsDbName = "Admins";
                checkBoxRememberMe.setVisibility(View.INVISIBLE);
            }
        });

        //change back to the user login
        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                parentsDbName = "Users";
                checkBoxRememberMe.setVisibility(View.VISIBLE);
            }
        });

        //login the user through the button login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    //This method login the user by checking the credentials
    private void loginUser(){

        String phone = inputNumber.getText().toString();
        String password = inputPassword.getText().toString();

        //checking the number and password for emptiness
        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please write your phone number...",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_LONG).show();
        }else {
            progressDialog.setTitle("Login Account");
            progressDialog.setMessage("Please wait, while we are checking the credentials.");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //allow access to the account.
            allowAccessToAccount(phone,password);
        }

    }

    //This method login the user by checking the credentials and takes to the home activity
    private void allowAccessToAccount(final String phone, final String password) {

        //this checks the remember me is checked or not
        //By using the paper library we are storing or writing the values of phone and password
        if (checkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.userPhoneKey,phone);
            Paper.book().write(Prevalent.userPasswordKey,password);
        }

        //a reference the location you want it to write/read
        //retrieve an instance of the database and then getting the reference
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        //Read and listen for changes to the entire contents of a path.
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //dataSnapshot contains the data at the specified location in the database
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentsDbName).child(phone).exists()){
                    //this statement store the user data from the database to the class Users
                    Users usersData = dataSnapshot.child(parentsDbName).child(phone).getValue(Users.class);

                    //checking the phone number with the database or with the data saved in class Users
                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){

                            //Checking the admin's login
                            if (parentsDbName.equals("Admins")){
                                Toast.makeText(loginActivity.this, "Welcome Admin!!...", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                //starting the adminAddNewProduct activity
                                Intent intent = new Intent(loginActivity.this,adminCategoryActivity.class);
                                startActivity(intent);
                            }   //Checking the users login
                            else if (parentsDbName.equals("Users")){
                                Toast.makeText(loginActivity.this, "logged in successfully...", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                //starting the home activity
                                Intent intent = new Intent(loginActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(loginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(loginActivity.this, "Account does not exits!!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
