package com.example.neko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterName extends AppCompatActivity {

    public  static String userName= "";
    private Button btnNext;
    private TextView txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);

        btnNext= (Button) findViewById(R.id.btnNext);
        txtName= findViewById(R.id.txtName);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName= txtName.getText().toString().trim();
                if(userName.isEmpty())
                {
                    txtName.setError("Bạn chưa nhập tên");
                    txtName.requestFocus();
                    return;
                }

                Intent intent= new Intent(RegisterName.this,Register.class);
                startActivity(intent);
            }
        });

    }
}