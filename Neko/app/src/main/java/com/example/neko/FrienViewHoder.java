package com.example.neko;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FrienViewHoder extends RecyclerView.ViewHolder {

    public CircleImageView imFriendsAvatar1;
    public TextView txtFriendsName,txtHoureAgo;
    public FrienViewHoder(@NonNull  View itemView) {
        super(itemView);

        imFriendsAvatar1= itemView.findViewById(R.id.imfriendsAvatar1);
        txtFriendsName = itemView.findViewById(R.id.txtStrangername);
        txtHoureAgo = itemView.findViewById(R.id.txtHoureAgo2);
    }
}
