package com.example.swiftmart;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private TextView signInSignUpTxt, signInForgotPassword;
    private ScrollView signInScrollView;
    private EditText signInEmailInput, signInPasswordInput;
    private MaterialButton signInButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();
        inputValidation();
        handleForgotPasswordClick();
        handleSignUnTextClick();
        handleSignInButtonClick();
    }

    // All the id initialize
    private void initialization(){
        signInSignUpTxt = findViewById(R.id.signInSignUpTxt);
        signInForgotPassword = findViewById(R.id.signInForgotPassword);
        signInScrollView = findViewById(R.id.signInScrollView);
        signInEmailInput = findViewById(R.id.signInEmailInput);
        signInPasswordInput = findViewById(R.id.signInPasswordInput);
        signInButton = findViewById(R.id.signInButton);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signInScrollView.setVerticalScrollBarEnabled(false);

    }

    // validate the input
    private void inputValidation(){
        // Email input
        signInEmailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signInEmailInput.getText().toString().isEmpty()) {
                    signInEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                } else {
                    signInEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_success);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Password input
        signInPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signInPasswordInput.getText().toString().isEmpty()) {
                    signInPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                } else {
                    signInPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_success);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // handle forgot password text click
    private void handleForgotPasswordClick(){
        signInForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    // handle sign un text click
    private void handleSignUnTextClick() {
        signInSignUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    // handle sign in button click
    private void handleSignInButtonClick(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = signInEmailInput.getText().toString().trim();
                String txtPassword = signInPasswordInput.getText().toString().trim();
                boolean isValid = true;

                if(txtEmail.isEmpty()){
                    signInEmailInput.setError("Please provide your email");
                    signInEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                    isValid = false;
                }
                if(txtPassword.isEmpty()){
                    signInPasswordInput.setError("Please provide your password");
                    signInPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                    isValid = false;
                }

                if (isValid){
                    mAuth.signInWithEmailAndPassword(txtEmail, txtPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

}