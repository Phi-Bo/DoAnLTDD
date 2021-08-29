    package com.example.neko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
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

    public TextView txtforgotpsd;

    private FirebaseDatabase mFirebase= FirebaseDatabase.getInstance();
    private DatabaseReference mRef;
    private FirebaseDatabase avatarbase= FirebaseDatabase.getInstance();
    private  DatabaseReference avtRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        /*btnFogetPassword = (Button) findViewById(R.id.btnForGetpassWord);*/
        txtforgotpsd = findViewById(R.id.txtforgotpsd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
       /* btnClose = (Button) findViewById(R.id.btnClose);*/
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
    }

    // hàm gọi activity dăng kí tài khoản
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
        //Báo cho người dùng nếu khi nó quên nhập số điên thoại sau đó kết thúc thực thi
        if ( phoneNumber.isEmpty())
        {
            txtPhoneNumber.setError("Chưa nhập số điên thoại");
            txtPhoneNumber.requestFocus();
            return;
        }
        // tiếp theo kiểm tra mật khẩu xem đã nhập hay chưa
        if(password.isEmpty())
        {
            txtPassword.setError("Chưa nhập mật khẩu");
            txtPassword.requestFocus();
            return;
        }
        if(!Patterns.PHONE.matcher(phoneNumber).matches())
        {
            txtPhoneNumber.setError("Sai định dạng số điện thoại");
            txtPhoneNumber.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            txtPassword.setError("Mật khẩu phải lớn hơn 5 kí tự");
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
                // kiểm tra từng phần tử con trong userInfo
                for (DataSnapshot st : snapshot.getChildren())
                {
                    // nếu 1 trong hàng triệu tài khoàn băng vs sdt thì tài khoản đó tồn tại là đúng
                    if( st.getKey().toString().equals(phoneNumber)){
                        check=true;
                    }
                }
                if(check) {
                    // khiểm tra mật khẩu của tài khoản vừa nhập
                    if (snapshot.child(phoneNumber).child("password").getValue().toString().equals(password))
                    {
                        name=snapshot.child(phoneNumber).child("username").getValue().toString();
                        Intent intent = new Intent(MainActivity.this, Main.class);
                        startActivity(intent);
                    }
                    else { //nếu sai mật khẩu
                        txtPassword.setError("Sai mật khẩu");
                        txtPassword.requestFocus();
                        return;
                    }
                }
                // khi không có tài khoản nào trong data
                else {
                    txtPhoneNumber.setError("Tài khoản này không tồn tại");
                    txtPhoneNumber.requestFocus();
                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // đéo biết cũng đéo hiểu khi nào hàm này thực thi

            }
        });
    }
}




















