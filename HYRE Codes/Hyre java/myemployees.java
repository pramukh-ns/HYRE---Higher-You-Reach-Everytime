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

public class myemployees extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    RecyclerView myem;
    FirebaseRecyclerAdapter adt;
    DatabaseReference dr;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myemployees);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        BottomNavigationView cn8 = findViewById(R.id.company_bottom_navigation);
        cn8.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.company_navigation_home:{
                        Intent v1=new Intent(myemployees.this,companyhomepage.class);
                        startActivity(v1);
                        break;
                    }
                    case R.id.company_navigation_search: {
                        Intent v2 = new Intent(myemployees.this, freelancersearch.class);
                        startActivity(v2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent v3 = new Intent(myemployees.this, newproject.class);
                        startActivity(v3);
                        break;
                    }
                    case R.id.company_navigation_notifications:{
                        Intent v4 = new Intent(myemployees.this, companynotification.class);
                        startActivity(v4);
                        break;
                    }
                }
            }
        });
        myem=(RecyclerView)findViewById(R.id.myemp);
        dr= FirebaseDatabase.getInstance().getReference().child("Company Hired").child(firebaseAuth
        .getCurrentUser().getUid().toString()).child("Freelancers Hired");
        FirebaseRecyclerOptions<myemp> options=new FirebaseRecyclerOptions.Builder<myemp>().setQuery(dr,myemp.class).build();
        adt= new FirebaseRecyclerAdapter<myemp, myempviewholder>(options) {
            @NonNull
            @NotNull
            @Override
            public myempviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myemp_item,parent,false);
            return new myempviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull myempviewholder holder, int position, @NonNull @NotNull myemp model) {
                holder.myempname.setText(model.getName());
                DocumentReference df=fstore.collection("Freelancer Profile").document(model.getFUID().toString());
                df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        img=value.getString("Img_URL");

                        Picasso.get().load(img).into(holder.myempprofimg);
                    }
                });
                holder.myemplayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent d=new Intent(myemployees.this,freelancerfeedback.class);
                        d.putExtra("Name",model.getName());
                        d.putExtra("FUID",model.getFUID().toString());
                        startActivity(d);
                    }
                });
            }
        };
        myem.setHasFixedSize(true);
        myem.setLayoutManager(new LinearLayoutManager(this));
        myem.setAdapter(adt);

    }

    private class myempviewholder extends RecyclerView.ViewHolder{
        TextView myempname;
        LinearLayout myemplayout;
        ImageView myempprofimg;
        public myempviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            myempname=itemView.findViewById(R.id.myempname);
            myemplayout=itemView.findViewById(R.id.myemplayout);
            myempprofimg=itemView.findViewById(R.id.myempprofimg);
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