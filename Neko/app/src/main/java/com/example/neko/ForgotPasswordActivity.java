package com.example.neko;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnSubmitEmail;
    private EditText editTxtForgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnSubmitEmail = findViewById(R.id.btnSubmitEmail);
        editTxtForgotPass = findViewById(R.id.editTextEmail);

        btnSubmitEmail.setOnClickListener(view -> {

            String userEmail = editTxtForgotPass.getText().toString();
            if (TextUtils.isEmpty(userEmail)) {
                Toast.makeText(ForgotPasswordActivity.this, "Are you writing your correct email!?", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Please check your email!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(ForgotPasswordActivity.this, "Error Occured" + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    private void reload() {

    }
}





















