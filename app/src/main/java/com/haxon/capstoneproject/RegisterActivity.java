package com.haxon.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText inputName, inputPhoneNumber, inputPassword;
    private ProgressDialog progressDialog;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = findViewById(R.id.register_btn);
        inputName = findViewById(R.id.register_username_input);
        inputPhoneNumber = findViewById(R.id.register_phone_number_input);
        inputPassword = findViewById(R.id.register_password_input);
        progressDialog = new ProgressDialog(this);

        //click listener to create an account
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    //This function creates an account in the firebase by checking the credentials
    private void createAccount() {

        String name = inputName.getText().toString();
        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();

        //Checking the emptiness of the fields and the end it will create an account by showing a dialog box
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name...",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please write your phone number...",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please write your password...",Toast.LENGTH_LONG).show();
        }else {
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Please wait, while we are checking the credentials.");
            progressDialog.setCanceledOnTouchOutside(false); //when user touch the screen it will not let the dialogBox disappear
            progressDialog.show();

            //validate the phone number if it is already exist or not.
            validatePhoneNumber(name,phone,password);
        }
    }

    //This method checks the already existing credentials so that same number doesn't register again and again.
    private void validatePhoneNumber(final String name, final String phone, final String password){

        final DatabaseReference rootRef;                                //reference the location you want it to write/read
        rootRef = FirebaseDatabase.getInstance().getReference();        //retrieve an instance of the database and then getting the reference

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {   //Read and listen for changes to the entire contents of a path.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  //dataSnapshot contains the data at the specified location in the database
                if (!(dataSnapshot.child(parentDbName).child(phone).exists())){  //if this number does not exist it will create a new account
                    HashMap<String,Object> userdataMap = new HashMap<>();   //storing the values using the HashMap class
                    userdataMap.put("phone",phone);
                    userdataMap.put("name",name);
                    userdataMap.put("password",password);

                    //Complete listener it tells the completion of the data i.e to know when the data has been committed
                    rootRef.child(parentDbName).child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Account has been created", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                Intent intent = new Intent(RegisterActivity.this,loginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Network Error! Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }else{  //the number already exist statements
                    Toast.makeText(RegisterActivity.this, "This " + phone + " already exists!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try using another phone number", Toast.LENGTH_SHORT).show();
                    //Sending the user to the main screen if the credentials are being already used.
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
