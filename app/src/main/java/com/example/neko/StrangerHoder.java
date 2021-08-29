package com.example.neko;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class StrangerHoder extends RecyclerView.ViewHolder {

    public TextView txtStrangerName,txtHoureAgo2;
    public CircleImageView imStranger;
    public StrangerHoder(@NonNull View itemView) {
        super(itemView);

        txtStrangerName= itemView.findViewById(R.id.txtStrangername);
        txtHoureAgo2 = itemView.findViewById(R.id.txtHoureAgo2);
        imStranger= itemView.findViewById(R.id.imStrangeperson);

    }
}
