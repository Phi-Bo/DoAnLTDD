    package com.example.neko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister;
    private  Button btnLogin;
    private  Button btnFogetPassword;
    private Button btnClose;
    private TextView txtPhoneNumber;
    private TextView txtPassword;
    public static String phoneNumber = "";
    private  String password="";
    private  TextView txtName3;
    private boolean t = false;
    public static String name;
    private ImageView imEye;
    private TextView textView;

    private FirebaseDatabase mFirebase= FirebaseDatabase.getInstance();
    private DatabaseReference mRef;
    private FirebaseDatabase avatarbase= FirebaseDatabase.getInstance();
    private  DatabaseReference avtRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = (Button) findViewById(R.id.btnRegister);
       // btnFogetPassword = (Button) findViewById(R.id.btnForGetpassWord);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnClose = (Button) findViewById(R.id.btnClose);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtPassword = findViewById(R.id.txtPassword);

        imEye= findViewById(R.id.imEye);
        //txtName3= findViewById(R.id.txtName3);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Register();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_list();
            }
        });
        imEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int set= txtPassword.getInputType();
               if (!t)
               {

                   txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                   t=true;
               }
               else
               {
                   //txtPassword.setInputType(set);
                   t=false;
                   txtPassword.setInputType(129);

               }
            }
        });

        //// FORGOT PASSWORD ///////
        btnFogetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassIntent = new Intent(MainActivity.this, NewPasswordActivity.class);
                startActivity(forgotpassIntent);
            }
        });
    }



    // h??m g???i activity d??ng k?? t??i kho???n
    public  void openActivity_Register()
    {
        Intent intent= new Intent(this, RegisterName.class);
        startActivity(intent);
    }

    public void openActivity_list()
    {
        //
        phoneNumber = txtPhoneNumber.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        //B??o cho ng?????i d??ng n???u khi n?? qu??n nh???p s??? ??i??n tho???i sau ???? k???t th??c th???c thi
        if ( phoneNumber.isEmpty())
        {
            txtPhoneNumber.setError("Ch??a nh???p s??? ??i??n tho???i");
            txtPhoneNumber.requestFocus();
            return;
        }
        // ti???p theo ki???m tra m???t kh???u xem ???? nh???p hay ch??a
        if(password.isEmpty())
        {
            txtPassword.setError("Ch??a nh???p m???t kh???u");
            txtPassword.requestFocus();
            return;
        }
        if(!Patterns.PHONE.matcher(phoneNumber).matches())
        {
            txtPhoneNumber.setError("Sai ?????nh d???ng s??? ??i???n tho???i");
            txtPhoneNumber.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            txtPassword.setError("M???t kh???u ph???i l???n h??n 5 k?? t???");
            txtPassword.requestFocus();
            return;
        }
        setBtnLogin();
    }
    private void setBtnLogin(){
        mRef= mFirebase.getReference("userInfo");
        avtRef = avatarbase.getReference("userImage");
        avtRef.child(phoneNumber).child("avatarImage").getKey().hashCode();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean check= false;
                // ki???m tra t???ng ph???n t??? con trong userInfo
                for (DataSnapshot st : snapshot.getChildren())
                {
                    // n???u 1 trong h??ng tri???u t??i kho??n b??ng vs sdt th?? t??i kho???n ???? t???n t???i l?? ????ng
                    if( st.getKey().toString().equals(phoneNumber)){
                        check=true;
                    }
                }
                if(check) {
                    // khi???m tra m???t kh???u c???a t??i kho???n v???a nh???p
                    if (snapshot.child(phoneNumber).child("password").getValue().toString().equals(password))
                    {
                        name=snapshot.child(phoneNumber).child("username").getValue().toString();
                        Intent intent = new Intent(MainActivity.this, Main.class);
                        startActivity(intent);
                    }
                    else { //n???u sai m???t kh???u
                        txtPassword.setError("Sai m???t kh???u");
                        txtPassword.requestFocus();
                        return;
                    }
                }
                // khi kh??ng c?? t??i kho???n n??o trong data
                else {
                    txtPhoneNumber.setError("T??i kho???n n??y kh??ng t???n t???i");
                    txtPhoneNumber.requestFocus();
                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // ????o bi???t c??ng ????o hi???u khi n??o h??m n??y th???c thi

            }
        });
    }
}




















