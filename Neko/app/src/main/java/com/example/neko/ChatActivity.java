    package com.example.neko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neko.Utills.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

    public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewchat;
    TextView txtchatMess,txtNameFriends;
    ImageView imSendChat,imPicture;
    ImageView imUriFriends,imAvatar;
    String orderUserId,orderUserName,orderUserIm;

    DatabaseReference chatRef,userRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseRecyclerOptions<Chat> options;
    FirebaseRecyclerAdapter<Chat,ChatViewHoder> chatAdapter;

    String myNumber= MainActivity.phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar= findViewById(R.id.app_bar_3);
        setSupportActionBar(toolbar);

        orderUserId= getIntent().getStringExtra("userkey");
        recyclerViewchat= findViewById(R.id.recycleChat);
        recyclerViewchat.setLayoutManager(new LinearLayoutManager(this));

        userRef= FirebaseDatabase.getInstance().getReference("userInfo");
        chatRef= FirebaseDatabase.getInstance().getReference("chat");
        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        txtchatMess= findViewById(R.id.txtChatSms);
        txtNameFriends= findViewById(R.id.txtName2);
        imAvatar= findViewById(R.id.imAvatar);
        imPicture= findViewById(R.id.imPicture);
        imSendChat= findViewById(R.id.imSendSms);

        LoadOrderUser();
        imSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSms();
            }


        });
        LoadSms();
    }
        private void LoadSms()
        {

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String strDate = formatter.format(date);

            HashMap hashMap = new HashMap();
            hashMap.put("statusMess","đã xem");
            hashMap.put("viewOn",strDate);



            options= new FirebaseRecyclerOptions.Builder<Chat>().setQuery(chatRef.child(myNumber).child(orderUserId),Chat.class).build();
            chatAdapter= new FirebaseRecyclerAdapter<Chat, ChatViewHoder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull  ChatViewHoder holder, int position, Chat model) {
                    if (model.getUserid().equals(myNumber))
                    {
                        holder.txtfruser.setVisibility(View.GONE);
                        holder.imfruser.setVisibility(View.GONE);
                        holder.txtscuser.setVisibility(View.VISIBLE);
                        holder.imscuser.setVisibility(View.VISIBLE);
                        holder.txtscuser.setText(model.sms);
                    }
                    else
                    {
                        holder.txtfruser.setVisibility(View.VISIBLE);
                        holder.imfruser.setVisibility(View.VISIBLE);
                        holder.txtscuser.setVisibility(View.GONE);
                        holder.imscuser.setVisibility(View.GONE);

                        holder.txtfruser.setText(model.sms);
                        Picasso.get().load(orderUserIm).into(holder.imfruser);
                    }

                }

                @NonNull
                @Override
                public ChatViewHoder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singele_view_sms,parent,false);
                    return new  ChatViewHoder(view)  ;
                }
            };
            chatAdapter.startListening();
            recyclerViewchat.setAdapter(chatAdapter);
        }

        private void SendSms() {
            String sms= txtchatMess.getText().toString();
            if (sms.isEmpty())
            {
                txtchatMess.setError("chưa viết");
                txtchatMess.requestFocus();
            }
            else {
                // thêm ngày giờ
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String strDate = formatter.format(date);

                HashMap hashMap = new HashMap();
                hashMap.put("sms",txtchatMess.getText().toString());
                hashMap.put("userid",myNumber);
                hashMap.put("sendTime",strDate);
                hashMap.put("statusMess","đã gửi");
                hashMap.put("isDeleted","false");
                hashMap.put("viewOn","");

                chatRef.child(orderUserId).child(myNumber).push().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull  Task task) {
                        if (task.isSuccessful())
                        {

                            chatRef.child(myNumber).child(orderUserId).push().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull  Task task) {
                                    if (task.isSuccessful())
                                    {
                                        txtchatMess.setText("");
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
        private void LoadOrderUser() {
        userRef.child(orderUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    orderUserName= snapshot.child("username").getValue().toString();
                    orderUserIm= snapshot.child("uriAvatar").getValue().toString();
                    Picasso.get().load(orderUserIm).into(imAvatar);
                    txtNameFriends.setText(orderUserName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }
    }