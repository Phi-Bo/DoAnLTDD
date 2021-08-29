package com.example.neko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neko.Utills.userInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {

    private Button btnSentRequest;
    private Button btnResend;
    private Button btnFinish;
    private TextView txtRegisterPhonenumber;
    private  TextView txtRegisterPassword;
    private TextView txtConfirm;
    private String phoneNumber;
    private  String passWord;
    private  String confirm;
    private FirebaseAuth firebaseAuth;
    private String nPhonenumber;


    // nêu code gửi mã xac nhạ ko đc sẽ gửi lại mã thông qua forceResendingToken
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String verifycationcode;    // biến này sẽ lưu lại giá trị củ mã xác nhận

    private static final String TAG = "MAIN_TAG";// biến này đéo biết nó làm lol j tuôn. từ từ sẽ tim hiểu

    DatabaseReference userRef,imref;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private ProgressDialog pd;
    //progress dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    // GẮn đưỡng dẫn chp các nút vừa tạo ra
        btnSentRequest = (Button) findViewById(R.id.btnSentRequest);
        btnResend= (Button) findViewById(R.id.btnResend);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        imref= FirebaseDatabase.getInstance().getReference("userImage");
        txtRegisterPhonenumber =  findViewById(R.id.txtRegisterPhoneNumber);
        txtRegisterPassword= findViewById(R.id.txtRegisterPassword);

        txtConfirm= findViewById(R.id.txtConfirm);
        firebaseAuth= FirebaseAuth.getInstance();

        pd= new ProgressDialog(this);
        pd.setTitle("please wait...");
        pd.setCanceledOnTouchOutside(false);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                SignInWithPhoneAuthCredential(phoneAuthCredential);
            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                pd.dismiss();
                pd.setMessage(e.getMessage());
               /* Toast.makeText(Register.this, "Bị lỗi lol gì đó", Toast.LENGTH_SHORT).show();*/
                pd.show();
            }
            @Override
            public void onCodeSent(@NonNull String verifycationID, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verifycationID, forceResendingToken);
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.e("on code", verifycationID);
                verifycationcode= verifycationID;
                forceResendingToken =token;
                txtConfirm.setVisibility(View.VISIBLE);
                Toast.makeText(Register.this, "Nhập mã xác nhân đi....", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        };
        //nhấn nút gửi mã  xac nhận
        btnSentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = txtRegisterPhonenumber.getText().toString().trim();
                passWord= txtRegisterPassword.getText().toString().trim();
                userRef = firebaseDatabase.getReference("userInfo");
                nPhonenumber= "+84"+phoneNumber.substring(1);

                if(passWord.isEmpty())
                {
                    txtRegisterPassword.setError("Chưa nhập Mật khẩu");
                    txtRegisterPassword.requestFocus();
                    return;
                }
                if (phoneNumber.isEmpty())
                {
                    txtRegisterPhonenumber.setError("Chưa nhập số điện thoại");
                    txtRegisterPhonenumber.requestFocus();
                    return;
                }

                //setdatabase();
                StartVertifycation();
            }
        });
        // thất bại và gủi lại mã xác nhận
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reVerifycation(forceResendingToken);
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code= txtConfirm.getText().toString().trim();
                CheckverifyWithcode(verifycationcode,code);
            }
        });
    }
    private void reVerifycation(PhoneAuthProvider.ForceResendingToken token)
    {
        pd.setMessage("Đang xác minh số điên thoại");
        pd.show();

        PhoneAuthOptions phoneAuthOptions= PhoneAuthOptions
                .newBuilder(firebaseAuth)
                .setPhoneNumber(nPhonenumber)
                .setTimeout(30L, TimeUnit.SECONDS)
                .setActivity(Register.this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(token)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }
    private void setdatabase()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);
        userInfo userInfo= new userInfo(phoneNumber,passWord,RegisterName.userName,"https://firebasestorage.googleapis.com/v0/b/mychat-72662.appspot.com/o/PostImage%2F0866039125?alt=media&token=5c039961-3626-4515-9cab-775b53722ded");
        HashMap hashMap= new HashMap();
        hashMap.put("avatarUri", "https://firebasestorage.googleapis.com/v0/b/mychat-72662.appspot.com/o/PostImage%2F0866039125?alt=media&token=5c039961-3626-4515-9cab-775b53722ded");
        hashMap.put("timeUp", strDate);
        hashMap.put("phonenumber",phoneNumber);

        //gọi api nhập dữ  liệu vào database
        userRef.child(phoneNumber).setValue(userInfo);
        imref.child(phoneNumber).setValue(hashMap);
        //String t= userRef.child(phoneNumber).child("number").setValue("111").toString();
        Toast.makeText(this, "Tạo Tài Khoản Thành công", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    }

    private void StartVertifycation()
    {
        pd.setMessage("Đang xác minh số điên thoại");
        pd.show();
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions
                .newBuilder(firebaseAuth)
                .setPhoneNumber(nPhonenumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }
    private void CheckverifyWithcode(String VertivicationID, String code)
    {
        pd.setMessage("đang xác minh code....");
        pd.show();

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(VertivicationID,code);
        SignInWithPhoneAuthCredential(credential);
    }
    private void SignInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // đăng nhập thành công
                        pd.dismiss();
                        setdatabase();
                        Toast.makeText(Register.this, "ok Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        //nhập thông tin vào firebase realtime database
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
//                        txtConfirm.setError("SAI MÃ XÁC NHẬN");
//                        txtConfirm.requestFocus();
                    }
                });
    }
}