package com.example.neko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.neko.Utills.FriendsProfile;
import com.example.neko.Utills.Userprofile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class TheFriends extends AppCompatActivity {
    private Button btnBack;
    private RecyclerView recyclerTheFriends;
    private String friendsName;
    private String friendNumber;
    private String friendsUri;
    private TextView txtsearchFeriends;

    Toolbar toolbar;

    private String  myNumber =MainActivity.phoneNumber ;
    private FirebaseRecyclerOptions<FriendsProfile> options ;

    DatabaseReference frndsRef;
    DatabaseReference userRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseRecyclerAdapter<FriendsProfile,FrienViewHoder> recyclerAdapter;
    private FirebaseRecyclerAdapter<Userprofile,FrienViewHoder> strangeAdapter;
    private FirebaseRecyclerOptions<Userprofile> useroptions ;
    public static boolean friends= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_friends);

        recyclerTheFriends= findViewById(R.id.RecyclerTheFriends);
        recyclerTheFriends.setLayoutManager(new LinearLayoutManager(this)); // quan trong
//        txtsearchFeriends= findViewById(R.id.txtSearchFriends);
        frndsRef= FirebaseDatabase.getInstance().getReference("listFriends").child(myNumber);
        userRef=FirebaseDatabase.getInstance().getReference("userInfo");
        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();



        toolbar = findViewById(R.id.app_bar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tìm Kiếm");
        LoadListFriends("");

    }

    public void LoadListFriends(String s)
    {

        Query query=frndsRef.orderByChild("friendsName").startAt(s).endAt(s+"\uf8ff");
        options= new FirebaseRecyclerOptions.Builder<FriendsProfile>().setQuery(query,FriendsProfile.class).build();
        recyclerAdapter = new FirebaseRecyclerAdapter<FriendsProfile, FrienViewHoder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FrienViewHoder holder, int position, @NonNull FriendsProfile model) {
                holder.txtFriendsName.setText(model.friendsName);
                //holder.txtHoureAgo.setText(model.);
                Picasso.get().load(model.urifriends).into(holder.imFriendsAvatar1);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TheFriends.this,Profile.class);
                        intent.putExtra("userkey",getRef(position).getKey().toString());
                        intent.putExtra("userIm",model.urifriends);
                        friends= true;
                        startActivity(intent);
                    }
                });
            }
            @NonNull
            @Override
            public FrienViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.friendslist,parent,false);
                return new FrienViewHoder(view);
            }

        };
        recyclerAdapter.startListening();
        recyclerTheFriends.setAdapter(recyclerAdapter);



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

    @Override
    protected void onStart() {
        super.onStart();
        friends= false;
    }
}