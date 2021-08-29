package com.example.neko;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.neko.Utills.FriendsProfile;
import com.example.neko.Utills.Userprofile;
import com.example.neko.Utills.userInfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    private ImageButton btnFind;
    private ArrayList<String> listPhone = new ArrayList<>();
    private TextView txtSearch;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ImageView imMenu;
    private String phoneNumber=MainActivity.phoneNumber;
    private TextView txtName3;
    private CircleImageView imAvarta;
    public static String uriAvatarString="";
    private String imString;
    private Uri imageUri;
    private ProgressDialog pd;
    public static String friendsNumber;
    public static String nameFriends;
    public static boolean  check= false;
    public static String friendsUriImage="";
    public static boolean friends = false;

    private DatabaseReference reference= FirebaseDatabase.getInstance().getReference("userInfo");
    public DatabaseReference strangerRef;

    private DatabaseReference imfef;
    private DatabaseReference listFriendsRef= FirebaseDatabase.getInstance().getReference("listFriends");
    private FirebaseStorage storage=FirebaseStorage.getInstance();
    private StorageReference storageReference=storage.getReference();

    private FirebaseRecyclerAdapter<FriendsProfile,FrienViewHoder> recyclerAdapter;
    private FirebaseRecyclerOptions<FriendsProfile> options ;

    private FirebaseRecyclerAdapter<Userprofile,FrienViewHoder> strangeAdapter;
    private FirebaseRecyclerOptions<Userprofile> useroptions ;
    private RecyclerView recyclerchatFriends;

    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imMenu= findViewById(R.id.imMenu);
        drawerLayout= findViewById(R.id.drawer);
        storage= FirebaseStorage.getInstance();
        imfef= FirebaseDatabase.getInstance().getReference("userImage");
        strangerRef=FirebaseDatabase.getInstance().getReference("userInfo");

        recyclerchatFriends= findViewById(R.id.recyclerchatFriends);
        recyclerchatFriends.setLayoutManager(new LinearLayoutManager(this));

        navigationView = findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.inflateHeaderView(R.layout.draw_header);// gắn cái navi vào view
        txtName3 = (TextView) view.findViewById(R.id.txtName3);
        imAvarta = view.findViewById(R.id.imAvarta);
        //btnFind= (ImageButton) findViewById(R.id.imbtnFind);


        imAvarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tìm Kiếm");
        LoadListFriends("");
    }

    public void LoadListFriends(String s)
    {

            Query que = reference.orderByChild("number").startAt(s).endAt(s+"\uf8ff");
            useroptions = new FirebaseRecyclerOptions.Builder<Userprofile>().setQuery(que,Userprofile.class).build();
            strangeAdapter = new FirebaseRecyclerAdapter<Userprofile, FrienViewHoder>(useroptions) {
                @Override
                protected void onBindViewHolder(@NonNull  FrienViewHoder holder, @SuppressLint("RecyclerView") int position, @NonNull  Userprofile model) {

                    holder.txtFriendsName.setText(model.getUsername());
                    Picasso.get().load(model.getUriAvatar()).into(holder.imFriendsAvatar1);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Main.this,Profile.class);
                            intent.putExtra("userkey",getRef(position).getKey().toString());
                            check= true;
                            startActivity(intent);
                        }
                    });
                }
                @NonNull
                @Override
                public FrienViewHoder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendslist,parent,false);
                    return new FrienViewHoder(view);
                }
            };
            strangeAdapter.startListening();
            recyclerchatFriends.setAdapter(strangeAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LoadListFriends(newText);
                return false;
            }
        });
        return  true;
    }
    public  void  onUpNameOnAp()
    {
       txtName3.setText(MainActivity.name);
        imfef.child(phoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String strImage= snapshot.child("avatarUri").getValue().toString();
                    Picasso.get().load(strImage).into(imAvarta);
                    uriAvatarString= strImage;
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(Main.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setup:
                Toast.makeText(this, " Cài đặt", Toast.LENGTH_SHORT).show();
                break;
            case R.id.friends:
                Intent intent1= new Intent(Main.this, TheFriends.class);
                startActivity(intent1);                break;
            case R.id.profile:
                Intent intent= new Intent(Main.this, Profile.class);
                startActivity(intent);
                break;
        }
        return false;
    }
    public void choosePicture()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null);
        {
            imageUri= data.getData();
            imAvarta.setImageURI(imageUri);
            updatePictureTofireStoreage(imageUri);
        }
    }
    private void updatePictureTofireStoreage(Uri muri)
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);
        storageReference= storageReference.child("PostImage");
        // nếu không muốn tạo forder thì dung dòng này - System.currentTimeMillis()+"."+ getsExtension(uri)
        storageReference.child(phoneNumber).putFile(muri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                      storageReference.child(phoneNumber).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                                HashMap hashMap= new HashMap();
                                hashMap.put("avatarUri", uri.toString());
                                hashMap.put("timeUp", strDate);
                                hashMap.put("phonenumber",phoneNumber);
                                imfef.child(phoneNumber).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(Main.this, "cập nhật thành công", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(Main.this, "Bi lol gì đó", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                HashMap hashMap1= new HashMap();
                                hashMap1.put("uriAvatar",uri.toString());
                                reference.child(phoneNumber).updateChildren(hashMap1);
                          }
                      });
                }
                else
                    Toast.makeText(Main.this, ""+ task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

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
        onUpNameOnAp();
        friends= false;
       check= false;
    }

}