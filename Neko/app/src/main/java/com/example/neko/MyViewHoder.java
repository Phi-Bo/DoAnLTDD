package com.example.neko;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MyViewHoder extends RecyclerView.ViewHolder {

    public ImageView imavatar4,imLike,imPoster,imComment,imsendComment;
    public TextView txtCountlike,txtCountcomment,txtTime,txtDiscription,txtNamePoster, txtinputcomment;
    public static RecyclerView recyclerViewComments;
    public MyViewHoder(@NonNull View itemView) {
        super(itemView);

        imavatar4= itemView.findViewById(R.id.imAvatar4);
        imComment= itemView.findViewById(R.id.imComment);
        imLike= itemView.findViewById(R.id.imLike);
        imPoster= itemView.findViewById(R.id.imPoster);
        txtCountlike= itemView.findViewById(R.id.txtcountLike);
        txtCountcomment= itemView.findViewById(R.id.txtcoutComment);
        txtTime= itemView.findViewById(R.id.txtTime);
        txtDiscription= itemView.findViewById(R.id.txtDiscription);
        txtNamePoster= itemView.findViewById(R.id.txtNamePoster);
        txtinputcomment=itemView.findViewById(R.id.txtInputcomment);
        imsendComment= itemView.findViewById(R.id.imsendComment);
        recyclerViewComments=itemView.findViewById(R.id.recyclerViewComment);

    }

    public void countLike(String postkey, String phoneNumber, DatabaseReference likeRef) {
        likeRef.child(postkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int count = (int) snapshot.getChildrenCount();
                    txtCountlike.setText(count+"");
                }
                else {
                    txtCountlike.setText("0");
                }
                if (snapshot.child(phoneNumber).exists())
                {
                    imLike.setColorFilter(Color.GREEN);
                }
                else {
                    imLike.setColorFilter(Color.GRAY);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void countComment(String postkey, String phoneNumber, DatabaseReference commentRef) {
        commentRef.child(postkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int count = (int) snapshot.getChildrenCount();
                    txtCountcomment.setText(count+"");
                }
                else {
                    txtCountcomment.setText("0");
                }
                if (snapshot.child(phoneNumber).exists())
                {
                    imComment.setColorFilter(Color.GREEN);
                }
                else {
                    imComment.setColorFilter(Color.GRAY);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
