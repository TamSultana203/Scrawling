package com.example.testthis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class AccountSetting extends AppCompatActivity {

    private EditText profileFullName,userName, profileStatus;
    private CircleImageView userProfileImage;

    private DatabaseReference profileUserRef;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private Uri mainImageURI = null;

    private EditText setupName;
    private Button setupBtn;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    private Bitmap compressedImageFile;
    private Button saveButton;

    String getCurrentUserId;
    final static int GalleryPic =1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        //this.setTitle("Profile Page");

        progressDialog = new ProgressDialog(this);
        userName = (EditText) findViewById(R.id.userNameId);
        profileFullName = (EditText) findViewById(R.id.profileFullNameId);
        profileStatus = (EditText) findViewById(R.id.profileStatusId);
        setupBtn = findViewById(R.id.saveId);
        userProfileImage = findViewById(R.id.profilePicId);
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");

        setupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountSetUpInformation();
            }
        });

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("Image/*");
                startActivityForResult(galleryIntent, GalleryPic);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPic && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                progressDialog.setTitle("Profile Image");
                progressDialog.setMessage("Please wait while we update your account");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();

                StorageReference filePath = storageReference.child(currentUserId + ".jpg");


                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(AccountSetting.this, "Profile saved..", Toast.LENGTH_SHORT).show();

                            final String downloadUri = task.getResult().getStorage().getDownloadUrl().toString();

                            profileUserRef.child("ProfileImage").setValue(downloadUri)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Intent setUpInt = new Intent(AccountSetting.this, AccountSetting.class);
                                                startActivity(setUpInt);
                                                Toast.makeText(AccountSetting.this, "Profile image stored to firebase", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();

                                            } else {
                                                String message = task.getException().getMessage();

                                                Toast.makeText(AccountSetting.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });


                        }
                    }
                });


            } else {
                Toast.makeText(AccountSetting.this, "Error: Image cant be cropped. Try Again", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

    }

    private void SaveAccountSetUpInformation() {
        String username = userName.getText().toString();
        String fullname = profileFullName.getText().toString();
        String profilestatus = profileStatus.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "please write your user name", Toast.LENGTH_SHORT).show();

        }

        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this, "please write your full name", Toast.LENGTH_SHORT).show();

        }

        if(TextUtils.isEmpty(profilestatus)){
            Toast.makeText(this, "please write a status", Toast.LENGTH_SHORT).show();

        }
        else {

            progressDialog.setTitle("Saving Information");
            progressDialog.setMessage("Please wait while we create your account");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(true);

            HashMap userMap = new HashMap();
            userMap.put("username", username);
            userMap.put("Full name", fullname);
            userMap.put("Status", "Hey there! I am rocking Scrawler");

            profileUserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()){
                        
                        SendUseToMainActvity();
                        Toast.makeText(AccountSetting.this, "Your information has been saved", Toast.LENGTH_LONG).show();;
                        progressDialog.dismiss();
                    }
                    else {
                        String message = task.getException().getMessage();
                        Toast.makeText(AccountSetting.this, "Error Occured", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });

        }
    }

    private void SendUseToMainActvity() {

        Intent intent = new Intent(AccountSetting.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}

/*(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String myProfileFullName = dataSnapshot.child("profileFullName").getValue().toString();
                        String myuserNamee = dataSnapshot.child("userName").getValue().toString();
                        String myprofileStatus = dataSnapshot.child("profileStatus").getValue().toString();
                        String myuserProfileImage = dataSnapshot.child("userProfileImage").getValue().toString();


                        //Picasso.with(AccountSetting.this).load(myuserProfileImage).placeholder(R.drawable.profile).into.(userProfileImage);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/