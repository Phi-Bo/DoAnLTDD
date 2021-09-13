package com.example.neko;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatViewHoder extends RecyclerView.ViewHolder {
    CircleImageView imfruser,imscuser;
    TextView txtfruser,txtscuser,txtScSeen,txtFrSeen;
    String phoneNumber = MainActivity.phoneNumber;

    DatabaseReference chatRef= FirebaseDatabase.getInstance().getReference("userInfo");
    DatabaseReference  userRef= FirebaseDatabase.getInstance().getReference("chat");

    ValueEventListener seenListener ;

    public ChatViewHoder(@NonNull View itemView) {
        super(itemView);
        imfruser= itemView.findViewById(R.id.imfrUser);
        imscuser= itemView.findViewById(R.id.imScUser);
        txtfruser= itemView.findViewById(R.id.txtscChat);
        txtscuser= itemView.findViewById(R.id.txtfrChat);
        txtScSeen= itemView.findViewById(R.id.txtScSeen);
        txtFrSeen= itemView.findViewById(R.id.txtFrSeen);

    }
    private void SeenMess(String userId,String friendId)
    {

        chatRef = chatRef.child(userId).child(friendId);
        seenListener = chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
nhat
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
