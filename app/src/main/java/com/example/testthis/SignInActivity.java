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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signInButton;
    private EditText singInEmailEdittext, signInPasswordEditText;
    private TextView signUpTextView;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.setTitle("Sign In");

        firebaseAuth = FirebaseAuth.getInstance();



        signInButton = (Button) findViewById(R.id.signInbuttonId);
        singInEmailEdittext = (EditText) findViewById(R.id.signInEmailEditTextId) ;
        signInPasswordEditText = (EditText) findViewById(R.id.signInPasswordEditTextId);
        signUpTextView = (TextView) findViewById(R.id.signUpTextViewId);

        progressBar = findViewById(R.id.progressBarId);

        progressDialog = new ProgressDialog(this);
        signUpTextView.setOnClickListener(this);
        signInButton.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInbuttonId:
                userLogin();

                break;
            case R.id.signUpTextViewId:
                Intent intent = new Intent (getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userLogin() {
        String email = singInEmailEdittext.getText().toString().trim();
        String password = signInPasswordEditText.getText().toString().trim();

        if(email.isEmpty()){
            singInEmailEdittext.setError("Enter an email address");
            singInEmailEdittext.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            singInEmailEdittext.setError("Enter a valid email address");
            singInEmailEdittext.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signInPasswordEditText.setError("Enter a password");
            signInPasswordEditText.requestFocus();
            return;
        }

        if(password.length()<6){
            signInPasswordEditText.setError("Minimum length must be 6");
            signInPasswordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
