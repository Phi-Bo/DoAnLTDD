package com.example.neko;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.neko.Utills.Comments;
import com.example.neko.Utills.FriendsProfile;
import com.example.neko.Utills.Poster;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Profile extends AppCompatActivity {
    private ImageView imAvatar;
    private  String phoneNumber= MainActivity.phoneNumber;
    private String name= MainActivity.name;
    private TextView txtName2,txtTheNumber;
    private EditText addPost;
    private ImageView imsendPoster;
    private ImageView imPicture;
    private ImageView imavatar4;
    private ImageView imAddfriends,imchat;
    private RecyclerView recyclerPoster;
    private Uri uriImagePoster;
    private FirebaseRecyclerAdapter<Poster,MyViewHoder> recyclerAdapter;
    private FirebaseRecyclerOptions<Poster> options ;
    private FirebaseRecyclerOptions <Comments> commentOption;
    private FirebaseRecyclerAdapter<Comments,CommentsViewHoder> commentAdapter;
    int count=0;
    String friendsNumber = Main.friendsNumber;
    String nameFriends= Main.nameFriends;
    String tempName,tempPhone;
    boolean friends = TheFriends.friends;
    public  static String friendsUriImage;
    boolean added= Main.check;
    private ProgressDialog pd;

    public String userId;

    // firebase
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference("imPosters");
    private DatabaseReference posterRef= FirebaseDatabase.getInstance().getReference("userPoster");

    private DatabaseReference friendsRef,likeRef,commentRef,userRef;

    private static String  uriAvatarString = Main.uriAvatarString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // ????nh ngh?? c??c box
        userRef= FirebaseDatabase.getInstance().getReference("userInfo");
        txtName2= findViewById(R.id.txtName2);
        imAvatar= findViewById(R.id.imAvatar);
        addPost= findViewById(R.id.txtaddPost);
        imchat= findViewById(R.id.imChat);
        txtName2.setText(name);
        imavatar4 = findViewById(R.id.imAvatar4);
        imPicture= findViewById(R.id.imAddPicture);
        imsendPoster= findViewById(R.id.imaddPoster);
        imAddfriends= findViewById(R.id.imAddFriends);
        txtTheNumber= findViewById(R.id.txtTheNumber);
        tempPhone= phoneNumber;
        recyclerPoster = findViewById(R.id.recyterPoster);
        pd= new ProgressDialog(this);
        recyclerPoster.setLayoutManager(new LinearLayoutManager(this));

//        constraintLayout= findViewById(R.id.parent_constrain);
//        constraintSet =new ConstraintSet();
//        constraintSet.clone(constraintLayout);


        friendsRef= FirebaseDatabase.getInstance().getReference("listFriends").child(tempPhone);
        likeRef= FirebaseDatabase.getInstance().getReference("likePoster");
        commentRef= FirebaseDatabase.getInstance().getReference("comment");
        // g???n gi?? tr??? c?? s???n
        txtName2.setText(name);
        Picasso.get().load(uriAvatarString).into(imAvatar);
        imsendPoster.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pd.setTitle("please wait");
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("??ang ????ng b??i vi???t");
                pd.show();
                if(uriImagePoster!= null)
                {
                    // l???y time hi???n t???i
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                    String strDate = formatter.format(date);


                    posterRef.child(phoneNumber).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists())
                            {
                                count= (int)snapshot.getChildrenCount();
                            }
                            else
                                count=0;
                        }
                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {
                            Toast.makeText(Profile.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    String temp = System.currentTimeMillis()+"."+ getsExtension(uriImagePoster);
                    storageReference.child(phoneNumber).child(temp)
                            .putFile(uriImagePoster).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()) {

                                storageReference.child(phoneNumber).child(temp)
                                        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        userImage userImage = new userImage(uri.toString(), strDate, addPost.getText().toString(), String.valueOf(count + 1));

                                        HashMap hashMap = new HashMap();

                                        hashMap.put("discription", addPost.getText().toString());
                                        hashMap.put("imPoster", uri.toString());
                                        hashMap.put("updatetime", strDate);
                                        hashMap.put("idPostor", String.valueOf(count + 1));
                                        posterRef.child(phoneNumber).child(String.valueOf(count + 1)).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                if (task.isSuccessful()) {
                                                    pd.dismiss();
                                                    Toast.makeText(Profile.this, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                                                    addPost.setText("");
                                                    imPicture.setImageURI(null);
                                                    addPost.clearFocus();
                                                } else {
                                                    pd.dismiss();
                                                    Toast.makeText(Profile.this, "C???p nh???t th???t  b???i", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                            else {
                                pd.dismiss();
                                Toast.makeText(Profile.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else {
                    pd.dismiss();
                    Toast.makeText(Profile.this, "khong co h??nh ???nh", Toast.LENGTH_SHORT).show();

                }
            }
        });
        imPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPicterPoster();
            }
        });
        imAddfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendsProfile friendsProfile= new FriendsProfile(name,phoneNumber,uriAvatarString);
                friendsRef.child(phoneNumber).setValue(friendsProfile);
                Toast.makeText(Profile.this, "???? th??m v??o danh s??ch b???n b??", Toast.LENGTH_SHORT).show();
                imAddfriends.setVisibility(View.GONE);
            }
        });
        loadPoster();
        imchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Profile.this,ChatActivity.class);
                intent.putExtra("userkey",userId);
                startActivity(intent);
            }
        });
    }
    private void loadPoster()
    {
        DatabaseReference tempRef=posterRef.child(phoneNumber);// k???t n???i t???m v??o c??i b???ng pposter treen firebase

        options = new FirebaseRecyclerOptions.Builder<Poster>().setQuery(tempRef,Poster.class).build();
        recyclerAdapter = new FirebaseRecyclerAdapter<Poster, MyViewHoder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  MyViewHoder holder, int position,  Poster model) {
                final String postkey= getRef(position).getKey();
                holder.txtDiscription.setText(model.getDiscription());
                String timeAgo= TimeAgo(model.getUpdatetime());
                holder.txtTime.setText(timeAgo);
                holder.txtNamePoster.setText(name);
                holder.countLike(postkey,phoneNumber,likeRef);
                holder.countComment(postkey,phoneNumber,commentRef);

                Picasso.get().load(model.getImPoster()).into(holder.imPoster);
                Picasso.get().load(uriAvatarString).into(holder.imavatar4);
                holder.imLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeRef.child(postkey).child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {
                                    likeRef.child(postkey).child(phoneNumber).removeValue();
                                    holder.imLike.setColorFilter(Color.GRAY);
                                    notifyDataSetChanged();
                                }
                                else
                                {
                                    likeRef.child(postkey).child(phoneNumber).setValue("like");
                                    holder.imLike.setColorFilter(Color.GREEN);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull  DatabaseError error) {
                                Toast.makeText(Profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                holder.imsendComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String comment= holder.txtinputcomment.getText().toString();
                        if (comment.isEmpty())
                        {
                            holder.txtinputcomment.setError("ch??a b??nh lu???n");
                        }
                        else
                        {
                            Addcomment(holder, postkey,commentRef,phoneNumber,comment);
                        }
                    }
                });
                LoadComments(postkey);
            }
            @NonNull
            @Override
            public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.poster,parent,false);
                return new MyViewHoder(view);

            }
        };
        recyclerAdapter.startListening();
        recyclerPoster.setAdapter(recyclerAdapter);

    }

    private void LoadComments(String postkey) {
        MyViewHoder.recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        commentOption=new  FirebaseRecyclerOptions.Builder<Comments>().setQuery(commentRef.child(postkey),Comments.class).build();
        commentAdapter = new FirebaseRecyclerAdapter<Comments, CommentsViewHoder>(commentOption) {
            @Override
            protected void onBindViewHolder(@NonNull  CommentsViewHoder holder, int position, Comments model) {
                holder.txtperson_commentName.setText(model.getUserName());
                holder.txtnameCommenter.setText(model.getComment());
                Picasso.get().load(model.getUserImage()).into(holder.imperson_comment);
            }

            @NonNull

            @Override
            public CommentsViewHoder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_comment,parent,false);
                return new CommentsViewHoder(view);
            }
        };
        commentAdapter.startListening();
        MyViewHoder.recyclerViewComments.setAdapter(commentAdapter);
    }

    private void Addcomment(MyViewHoder holder, String postkey, DatabaseReference commentRef, String phoneNumber, String comment) {
        HashMap hashMap = new HashMap();
        hashMap.put("userName",name);
        hashMap.put("userImage",uriAvatarString);
        hashMap.put("comment",comment);
         commentRef.child(postkey).child(phoneNumber).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
             @Override
             public void onComplete(@NonNull Task task) {
                 if(task.isSuccessful())
                 {
                     Toast.makeText(Profile.this, "xong", Toast.LENGTH_SHORT).show();
                     recyclerAdapter.notifyDataSetChanged();
                     holder.txtinputcomment.setText(null);
                 }
                 else {
                     Toast.makeText(Profile.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    // chuy???n ?????i ng??y ????ng th??nh s??? ng??y ????ng
    private String  TimeAgo(String temptime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse(temptime).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    void addPicterPoster()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    // ????y l?? h??m l???y th??ng tin h??nh ???nh th??ng qua 1 ?????i t?????ng t??n uriImposter
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null);
        {
            uriImagePoster= data.getData();
            imPicture.setImageURI(uriImagePoster);
        }
    }
    // ki???m tra xem ng?????i d??ng ???? nh???p h??nh ???nh hay ckt0h??a
    private boolean check()
    {
        if(addPost.getText().equals("") && uriImagePoster== null)
        {
            addPost.setError("ch??a c?? b??i vi???t");
            return false;
        }
        return true;
    }
    private String getsExtension(Uri muri)
    {
        ContentResolver contentResolver= getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(muri));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (added) {
            friends = added;
            imAddfriends.setVisibility(View.VISIBLE);
            userId = getIntent().getStringExtra("userkey");
            userRef.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        tempName = name;
                        name = snapshot.child("username").getValue().toString();
                        phoneNumber = userId;
                        uriAvatarString = snapshot.child("uriAvatar").getValue().toString();
                        imPicture.setVisibility(View.GONE);
                        imsendPoster.setVisibility(View.GONE);
                        addPost.setVisibility(View.GONE);
                        loadPoster();
                        txtName2.setText(name);
                        txtTheNumber.setText(phoneNumber);
                        Picasso.get().load(uriAvatarString).into(imAvatar);
                    } else {
                        Toast.makeText(Profile.this, "??", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {

            if (friends) {
                 userId = getIntent().getStringExtra("userkey");
                userRef.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            tempName = name;
                            name = snapshot.child("username").getValue().toString();
                            phoneNumber = userId;
                            uriAvatarString = snapshot.child("uriAvatar").getValue().toString();
                            imPicture.setVisibility(View.GONE);
                            imsendPoster.setVisibility(View.GONE);
                            addPost.setVisibility(View.GONE);
                            loadPoster();
                            txtName2.setText(name);
                            txtTheNumber.setText(phoneNumber);
                            Picasso.get().load(uriAvatarString).into(imAvatar);
                        } else {
                            Toast.makeText(Profile.this, "??", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                txtTheNumber.setVisibility(View.GONE);
                imchat.setVisibility(View.GONE);
            }
        }

    }
}