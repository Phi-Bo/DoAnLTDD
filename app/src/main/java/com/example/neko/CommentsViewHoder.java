package com.example.neko;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsViewHoder extends RecyclerView.ViewHolder {
    CircleImageView imperson_comment;
    TextView txtperson_commentName, txtnameCommenter;
    public CommentsViewHoder(@NonNull  View itemView) {
        super(itemView);
        imperson_comment= itemView.findViewById(R.id.ImPerson_comment);
        txtperson_commentName=itemView.findViewById(R.id.txtNameComnenter);
        txtnameCommenter =itemView.findViewById(R.id.txtCommented);
    }
}
