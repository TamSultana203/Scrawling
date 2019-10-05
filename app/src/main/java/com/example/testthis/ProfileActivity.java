package com.example.testthis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;
    private DatabaseReference databaseReference;

    private EditText editTextName, editTextAddress;
    private Button buttonSavepeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextFullName);
        buttonSavepeople = (Button) findViewById(R.id.buttonAddPeope);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        textViewUserEmail = (TextView) findViewById(R.id.textureViewUserEmail);
        textViewUserEmail.setText("Welcome"+user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);

        buttonSavepeople.setOnClickListener(this);

    }

    private  void SaveUserInformation(){
        String name = editTextName.getText().toString().trim();
        String add = editTextAddress.getText().toString().trim();
        UserInformation userInformation = new UserInformation(name, add);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information  saved...",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        if(view==buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        }

        if(view == buttonSavepeople){
            SaveUserInformation();
        }
    }
}
