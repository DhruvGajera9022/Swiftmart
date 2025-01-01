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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ScrollView forgotPasswordScrollView;
    private EditText forgotPasswordEmailInput;
    private MaterialButton forgotPasswordButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initialization();
        inputValidation();
        handleForgotPasswordButtonClick();

    }

    // All the id initialize
    private void initialization(){
        forgotPasswordEmailInput = findViewById(R.id.forgotPasswordEmailInput);
        forgotPasswordScrollView = findViewById(R.id.forgotPasswordScrollView);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        forgotPasswordScrollView.setVerticalScrollBarEnabled(false);

    }

    // validate the input
    private void inputValidation(){
        // Email input
        forgotPasswordEmailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (forgotPasswordEmailInput.getText().toString().isEmpty()) {
                    forgotPasswordEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                } else {
                    forgotPasswordEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_success);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // handle forgot password button
    private void handleForgotPasswordButtonClick(){
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = forgotPasswordEmailInput.getText().toString();
                boolean isValid = true;

                if(txtEmail.isEmpty()){
                    forgotPasswordEmailInput.setError("Please provide your email");
                    forgotPasswordEmailInput.setBackgroundResource(R.drawable.rounded_edit_text_error);
                    isValid = false;
                }

                if (isValid){
                    mAuth.sendPasswordResetEmail(txtEmail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Password reset email failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

}