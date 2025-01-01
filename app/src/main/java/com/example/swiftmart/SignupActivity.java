package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {
    private TextView signUpSignInTxt;
    private ScrollView signUpScrollView;
    private EditText signUpUserNameInput, signUpEmailInput, signUpPasswordInput, signUpConfirmPasswordInput;
    private MaterialButton signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialization();
        inputValidation();
        handleSignInTextClick();
        handleSignUpButtonClick();
    }

    // All the id initialize
    private void initialization(){
        signUpSignInTxt = findViewById(R.id.signUpSignInTxt);
        signUpScrollView = findViewById(R.id.signUpScrollView);
        signUpUserNameInput = findViewById(R.id.signUpUserNameInput);
        signUpEmailInput = findViewById(R.id.signUpEmailInput);
        signUpPasswordInput = findViewById(R.id.signUpPasswordInput);
        signUpConfirmPasswordInput = findViewById(R.id.signUpConfirmPasswordInput);
        signUpButton = findViewById(R.id.signUpButton);

        signUpScrollView.setVerticalScrollBarEnabled(false);
    }

    // validate the input
    private void inputValidation(){
        // Username input
        signUpUserNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signUpUserNameInput.getText().toString().isEmpty()) {
                    signUpUserNameInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                } else {
                    signUpUserNameInput.setBackgroundResource(R.drawable.rounded_edit_text_success);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Email input
        signUpEmailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signUpEmailInput.getText().toString().isEmpty()) {
                    signUpEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                } else {
                    signUpEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_success);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Password input
        signUpPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signUpPasswordInput.getText().toString().isEmpty()) {
                    signUpPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                } else {
                    signUpPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_success);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Confirm Password input
        signUpConfirmPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signUpConfirmPasswordInput.getText().toString().isEmpty()) {
                    signUpConfirmPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                } else {
                    signUpConfirmPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_success);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // handle sign in text click
    private void handleSignInTextClick() {
        signUpSignInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // handle sign up button click
    private void handleSignUpButtonClick(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = signUpUserNameInput.getText().toString();
                String txtEmail = signUpEmailInput.getText().toString();
                String txtPassword = signUpPasswordInput.getText().toString();
                String txtConfirmPassword = signUpConfirmPasswordInput.getText().toString();

                if(txtUsername.isEmpty()){
                    signUpUserNameInput.setError("Please provide your username");
                    signUpUserNameInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                }

                if(txtEmail.isEmpty()){
                    signUpEmailInput.setError("Please provide your email");
                    signUpEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                }

                if(txtPassword.isEmpty()){
                    signUpPasswordInput.setError("Please provide your password");
                    signUpPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                }

                if(txtConfirmPassword.isEmpty()){
                    signUpConfirmPasswordInput.setError("Please provide confirm password");
                    signUpConfirmPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                }

                if(!txtPassword.equals(txtConfirmPassword)){
                    signUpConfirmPasswordInput.setError("Password not match");
                    signUpConfirmPasswordInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                }

            }
        });
    }

}