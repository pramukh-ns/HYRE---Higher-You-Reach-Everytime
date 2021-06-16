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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class companymessage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    DatabaseReference dr;
    RecyclerView cmlis;
    FirebaseRecyclerAdapter adt;
    String img,fuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companymessage);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        BottomNavigationView cn11 = findViewById(R.id.company_bottom_navigation);
        cn11.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.company_navigation_home:{
                        Intent w1=new Intent(companymessage.this,companyhomepage.class);
                        startActivity(w1);
                        break;
                    }
                    case R.id.company_navigation_search: {
                        Intent w2 = new Intent(companymessage.this, freelancersearch.class);
                        startActivity(w2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent w3 = new Intent(companymessage.this, newproject.class);
                        startActivity(w3);
                        break;
                    }
                    case R.id.company_navigation_notifications:{
                        Intent w4 = new Intent(companymessage.this, companynotification.class);
                        startActivity(w4);
                        break;
                    }
                }
            }
        });
        cmlis=(RecyclerView)findViewById(R.id.cmlist);
        dr=FirebaseDatabase.getInstance().getReference().child("Accepted")
                .child(firebaseAuth.getCurrentUser().getUid().toString())
                .child("Company Accepted");
        FirebaseRecyclerOptions<cmlist> options1=new FirebaseRecyclerOptions.Builder<cmlist>().
                setQuery(dr,cmlist.class).build();
        adt= new FirebaseRecyclerAdapter<cmlist, acceptedviewholder>(options1) {
            @NonNull
            @NotNull
            @Override
            public acceptedviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
               View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cmlist_item,parent,false);
               return new acceptedviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull acceptedviewholder holder, int position, @NonNull @NotNull cmlist model) {
                DocumentReference df=fstore.collection("Freelancer Profile").document(model.getFUID());
                df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        holder.cmname.setText(model.getName()+" has accepted your connection request.");
                        img=value.getString("Img_URL");
                        
                        Picasso.get().load(img).into(holder.cmprofimg);
                        holder.cmlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent xx=new Intent(companymessage.this,freelancercommunicate.class);
                                xx.putExtra("Name",model.getName());
                                xx.putExtra("FUID",model.getFUID().toString());
                                startActivity(xx);
                            }
                        });
                    }
                });

            }
        } ;
        cmlis.setHasFixedSize(true);
        cmlis.setLayoutManager(new LinearLayoutManager(this));
        cmlis.setAdapter(adt);

    }

    private class acceptedviewholder extends RecyclerView.ViewHolder{
        TextView cmname;
        ImageView cmprofimg;
        LinearLayout cmlayout;
        public acceptedviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            cmname=itemView.findViewById(R.id.cmname);
            cmprofimg=itemView.findViewById(R.id.cmprofimg);
            cmlayout=itemView.findViewById(R.id.cmlayout);
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



