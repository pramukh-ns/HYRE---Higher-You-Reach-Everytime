package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class companynotification extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    RecyclerView falis;
    FirebaseRecyclerAdapter adt;
    DatabaseReference dr;
    String p,q,r,img,exp,sk,us;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companynotification);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        BottomNavigationView cn6 = findViewById(R.id.company_bottom_navigation);
        cn6.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.company_navigation_home:{
                        Intent u1=new Intent(companynotification.this,companyhomepage.class);
                        startActivity(u1);
                        break;
                    }
                    case R.id.company_navigation_search: {
                        Intent u2 = new Intent(companynotification.this, freelancersearch.class);
                        startActivity(u2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent u3 = new Intent(companynotification.this, newproject.class);
                        startActivity(u3);
                        break;
                    }
                }
            }
        });
        falis=(RecyclerView)findViewById(R.id.falist);

        dr=FirebaseDatabase.getInstance().getReference("Applied").child(firebaseAuth.
                getCurrentUser().getUid().toString()).child("Freelancers");
        FirebaseRecyclerOptions<falist> options=new FirebaseRecyclerOptions.Builder<falist>().
                setQuery(dr,falist.class).build();
        adt= new FirebaseRecyclerAdapter<falist, appliedviewholder>(options) {
            @NonNull
            @NotNull
            @Override
            public appliedviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.falist_item,parent,false);
                return new appliedviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull appliedviewholder holder, int position, @NonNull @NotNull falist model) {
                holder.faname.setText(model.getName()+" has applied.");
                DocumentReference df=fstore.collection("Freelancer Profile").document(model.getFUID().toString());
                df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        img=value.getString("Img_URL");
                        us=value.getString("Username");
                        sk=value.getString("Skills");
                        exp=value.getString("Description");
                        Picasso.get().load(img).into(holder.faprofimg);
                    }
                });

                holder.falayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent d=new Intent(companynotification.this,freelancerdetails.class);
                        d.putExtra("Name",model.getName());
                        d.putExtra("FUID",model.getFUID().toString());
                        startActivity(d);
                    }
                });
            }
        } ;


        falis.setHasFixedSize(true);
        falis.setLayoutManager(new LinearLayoutManager(this));
        falis.setAdapter(adt);

    }

    private class appliedviewholder extends RecyclerView.ViewHolder {
        TextView faname;
        ImageView faprofimg;
        Button hire;
        LinearLayout falayout;

        public appliedviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            faname=itemView.findViewById(R.id.faname);
            faprofimg=itemView.findViewById(R.id.faprofimg);
            hire=itemView.findViewById(R.id.b12);
            falayout=itemView.findViewById(R.id.falayout);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adt.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adt.stopListening();
    }
}