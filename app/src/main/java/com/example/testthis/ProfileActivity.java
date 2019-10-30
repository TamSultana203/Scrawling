package com.example.testthis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileFullName,userName, profileStatus;
    private CircleImageView userProfileImage;

    private DatabaseReference profileUserRef;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        this.setTitle("Profile Page");


        userName = (TextView) findViewById(R.id.userNameId);
        profileFullName = (TextView) findViewById(R.id.profileFullNameId);
        profileStatus = (TextView) findViewById(R.id.profileStatusId);

        userProfileImage = (CircleImageView) findViewById(R.id.profilePicId);

        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String myProfileFullName = dataSnapshot.child("profileFullName").getValue().toString();
                    String myuserNamee = dataSnapshot.child("userName").getValue().toString();
                    String myprofileStatus = dataSnapshot.child("profileStatus").getValue().toString();
                    String myuserProfileImage = dataSnapshot.child("userProfileImage").getValue().toString();


                    //Picasso.with(ProfileActivity.this).load(myuserProfileImage).placeholder(R.drawable.profile).into.(userProfileImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
