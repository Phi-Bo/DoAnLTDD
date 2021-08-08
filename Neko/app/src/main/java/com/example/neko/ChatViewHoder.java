package com.example.neko;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatViewHoder extends RecyclerView.ViewHolder {
    CircleImageView imfruser,imscuser;
    TextView txtfruser,txtscuser;

    public ChatViewHoder(@NonNull View itemView) {
        super(itemView);
        imfruser= itemView.findViewById(R.id.imfrUser);
        imscuser= itemView.findViewById(R.id.imScUser);
        txtfruser= itemView.findViewById(R.id.txtscChat);
        txtscuser= itemView.findViewById(R.id.txtfrChat);
    }
}
