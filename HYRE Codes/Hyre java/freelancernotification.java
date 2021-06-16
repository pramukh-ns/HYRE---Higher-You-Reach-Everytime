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

public class freelancernotification extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    RecyclerView calis;
    FirebaseRecyclerAdapter adt;
    DatabaseReference dr;
    String img,exp,sk,us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancernotification);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView fn5=findViewById(R.id.freelancer_bottom_navigation);
        fn5.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.freelancer_navigation_search:{
                        Intent e1=new Intent(freelancernotification.this,companysearch.class);
                        startActivity(e1);
                        break;
                    }
                    case R.id.freelancer_navigation_home:{
                        Intent e2=new Intent(freelancernotification.this,freelancerhomepage.class);
                        startActivity(e2);
                        break;
                    }
                }
            }
        });


        calis=(RecyclerView)findViewById(R.id.calist);

        dr=FirebaseDatabase.getInstance().getReference("Connected").child(firebaseAuth.
                getCurrentUser().getUid().toString()).child("Company");
        FirebaseRecyclerOptions<calist> options1=new FirebaseRecyclerOptions.Builder<calist>().
                setQuery(dr,calist.class).build();
        adt= new FirebaseRecyclerAdapter<calist, hiredviewholder>(options1) {
            @NonNull
            @NotNull
            @Override
            public hiredviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.calist_item,parent,false);
                return new hiredviewholder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull @NotNull hiredviewholder holder, int position, @NonNull @NotNull calist model) {

                DocumentReference df=fstore.collection("Company Profile").document(model.getCUID().toString());
                df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        holder.caname.setText(model.getName()+" has requested for a connection.");
                        img=value.getString("Img_URL");
                        us=value.getString("Username");
                        Picasso.get().load(img).into(holder.caprofimg);
                    }
                });
                Picasso.get().load(model.getImg_URL()).into(holder.caprofimg);
                holder.caloyout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent d=new Intent(freelancernotification.this,companydetails.class);
                        d.putExtra("Name",model.getName());
                        d.putExtra("CUID",model.getCUID().toString());
                        startActivity(d);
                    }
                });

            }
        } ;
        calis.setHasFixedSize(true);
        calis.setLayoutManager(new LinearLayoutManager(this));
        calis.setAdapter(adt);
    }

    private class hiredviewholder extends RecyclerView.ViewHolder {
        TextView caname;
        ImageView caprofimg;
        LinearLayout caloyout;

        public hiredviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            caname=itemView.findViewById(R.id.caname);
            caprofimg=itemView.findViewById(R.id.caprofimg);
            caloyout=itemView.findViewById(R.id.calayout);
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



