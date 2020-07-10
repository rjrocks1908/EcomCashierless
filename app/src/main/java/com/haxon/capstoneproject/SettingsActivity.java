package com.haxon.capstoneproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.haxon.capstoneproject.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView profileChangeTextBtn, closeTextBtn, saveTextBtn;

    private Uri imageUri;
    private String myUrl = "";
    private StorageReference storageProfilePictureRef;
    private StorageTask uploadTask;
    private String checker = "";
    private Button securityQuestionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        profileImageView = findViewById(R.id.settings_profile_image);
        fullNameEditText = findViewById(R.id.settings_full_name);
        userPhoneEditText = findViewById(R.id.settings_phone_number);
        addressEditText = findViewById(R.id.settings_address);
        profileChangeTextBtn = findViewById(R.id.profile_image_change_btn);
        closeTextBtn = findViewById(R.id.close_settings_btn);
        saveTextBtn = findViewById(R.id.update_account_settings_btn);
        securityQuestionBtn = findViewById(R.id.security_questions_btn);

        userInfoDisplay(profileImageView, fullNameEditText, userPhoneEditText, addressEditText);

        securityQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "settings");
                startActivity(intent);

            }
        });

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checker.equals("clicked")){

                    //when the user updated profile pic as well as info so this function will get called
                    userInfoSaved();

                }else{

                    //when the user only edited the info not the profile pic then this method will get called
                    updateOnlyUserInfo();
                }
            }
        });

        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checker = "clicked";
                CropImage.activity(imageUri).setAspectRatio(1,1).start(SettingsActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }else{

            Toast.makeText(this, "Error, Try again later", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void updateOnlyUserInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", fullNameEditText.getText().toString());
        userMap.put("address", addressEditText.getText().toString());
        userMap.put("phoneOrder", userPhoneEditText.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);


        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
        Toast.makeText(SettingsActivity.this, "Profile info updated successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void userInfoSaved() {

        if (TextUtils.isEmpty(fullNameEditText.getText().toString())){

            Toast.makeText(this, "Name is mandatory...", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(addressEditText.getText().toString())){

            Toast.makeText(this, "address is mandatory...", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(userPhoneEditText.getText().toString())){

            Toast.makeText(this, "Phone is mandatory...", Toast.LENGTH_SHORT).show();

        }else if (checker.equals("clicked")){

            uploadImage();
        }

    }

    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePictureRef.child(Prevalent.currentOnlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap. put("name", fullNameEditText.getText().toString());
                                userMap. put("address", addressEditText.getText().toString());
                                userMap. put("phoneOrder", userPhoneEditText.getText().toString());
                                userMap. put("image", myUrl);
                                ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                                progressDialog.dismiss();

                                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                                Toast.makeText(SettingsActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(SettingsActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }
//    private void uploadImage() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Update Profile");
//        progressDialog.setMessage("Please wait, while we are updating your profile information");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        if (imageUri != null){
//
//            final StorageReference fileRef = storageProfilePictureRef.child(Prevalent.currentOnlineUser.getPhone() + ".jpg");
//
//            uploadTask = fileRef.putFile(imageUri);
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//
//                    if (!task.isSuccessful()){
//
//                        throw task.getException();
//                    }
//
//                    return fileRef.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//
//                    if (task.isSuccessful()){
//
//                        Uri downloadUrl = task.getResult();
//                        myUrl = downloadUrl.toString();
//
//                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
//
//                        HashMap<String, Object> userMap = new HashMap<>();
//                        userMap.put("name", fullNameEditText.getText().toString());
//                        userMap.put("address", addressEditText.getText().toString());
//                        userMap.put("phoneOrder", userPhoneEditText.getText().toString());
//                        userMap.put("image", myUrl);
//                        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);
//
//                        progressDialog.dismiss();
//
//                        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
//                        Toast.makeText(SettingsActivity.this, "Profile info updated successfully", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }else{
//
//                        progressDialog.dismiss();
//                        Toast.makeText(SettingsActivity.this, "Error.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }else{
//
//            Toast.makeText(this, "Image is not selected!", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText) {

        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    if (dataSnapshot.child("image").exists()){

                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}