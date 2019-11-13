package com.example.testthis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signUpButtonId;
    private EditText singUpEmailEditTextId, signUnPasswordEditTextId;
    private TextView signInTextViewId;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.setTitle("Sign Up");

        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBarId);


        signUpButtonId = (Button) findViewById(R.id.signUpButtonId);
        singUpEmailEditTextId = (EditText) findViewById(R.id.singUpEmailEditTextId) ;
        signUnPasswordEditTextId = (EditText) findViewById(R.id.signInPasswordEditTextId);
        signInTextViewId = (TextView) findViewById(R.id.signInTextViewId);



        progressDialog = new ProgressDialog(this);
        signInTextViewId.setOnClickListener(this);
        signUpButtonId.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUpButtonId:
                userRegister();
                break;
            case R.id.signInTextViewId:
                Intent intent = new Intent (getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userRegister() {
        String email = singUpEmailEditTextId.getText().toString().trim();
        String password = signUnPasswordEditTextId.getText().toString().trim();

        if(email.isEmpty()){
            singUpEmailEditTextId.setError("Enter an email address");
            singUpEmailEditTextId.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            singUpEmailEditTextId.setError("Enter a valid email address");
            singUpEmailEditTextId.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signUnPasswordEditTextId.setError("Enter a password");
            signUnPasswordEditTextId.requestFocus();
            return;
        }

        if(password.length()<6){
            signUnPasswordEditTextId.setError("Minimum length must be 6");
            signUnPasswordEditTextId.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), AccountSetting.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Email is already registered", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });


    }
}
