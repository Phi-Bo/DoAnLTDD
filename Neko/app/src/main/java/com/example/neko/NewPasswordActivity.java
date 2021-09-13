package com.example.neko;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class NewPasswordActivity extends AppCompatActivity {

    private TextView txtWarningPass, txtWarningConfirmPass;
    private TextInputEditText editTxtNewPass, editTxtConfirmPass;
    private Button btnSubmit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        // TextView
        txtWarningPass = findViewById(R.id.txtWarningPassword);
        txtWarningConfirmPass = findViewById(R.id.txtConfirmWarning);
        // EditText
        editTxtNewPass = findViewById(R.id.txtInputEditPass);
        editTxtConfirmPass = findViewById(R.id.txtInputEditConfirmPass);
        // Button
        btnSubmit = findViewById(R.id.btnSubmitNewPass);
        mAuth = FirebaseAuth.getInstance();


        btnSubmit.setOnClickListener(view -> {
            String txt_pass = editTxtNewPass.getText().toString();

            if (txt_pass.isEmpty()) {
                txtWarningPass.setVisibility(View.VISIBLE);
                txtWarningPass.setText(R.string.fill_the_blank);
            } else if (txt_pass.length() < 6) {
                txtWarningPass.setVisibility(View.VISIBLE);
                txtWarningPass.setText(R.string.warning_password_character);
            } else if (!editTxtNewPass.getText().toString().equals(editTxtConfirmPass.getText().toString())) {
                txtWarningConfirmPass.setVisibility(View.VISIBLE);
                txtWarningConfirmPass.setText(R.string.warning_confirm_password);
            } else {
                txtWarningPass.setVisibility(View.INVISIBLE);
                txtWarningConfirmPass.setVisibility(View.INVISIBLE);
            }

        });
    }
}