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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class companyhomepage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    RecyclerView flist;
    FirestoreRecyclerAdapter adapter1;
    String a,b,c,d,e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_companyhomepage);
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        BottomNavigationView cn1 = findViewById(R.id.company_bottom_navigation);
        cn1.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.company_navigation_search: {
                        Intent p1 = new Intent(companyhomepage.this, freelancersearch.class);
                        startActivity(p1);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent p2 = new Intent(companyhomepage.this, newproject.class);
                        startActivity(p2);
                        break;
                    }
                    case R.id.company_navigation_notifications: {
                        Intent p3 = new Intent(companyhomepage.this, companynotification.class);
                        startActivity(p3);
                        break;
                    }
                }
            }
        });

        flist=findViewById(R.id.flist);
        Query query=fstore.collection("Freelancer Profile");
        FirestoreRecyclerOptions<fpost> opt=new FirestoreRecyclerOptions.Builder<fpost>().setQuery(query,fpost.class).build();
        adapter1= new FirestoreRecyclerAdapter<fpost, FreelancertViewHolder>(opt) {
            @NonNull
            @NotNull
            @Override
            public FreelancertViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fpost_item,parent,false);
                return new FreelancertViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull FreelancertViewHolder holder, int position, @NonNull @NotNull fpost model) {
                Picasso.get().load(model.getImg_URL()).into(holder.profimg);
                holder.chfskills.setText(model.getSkills());
                holder.chfexp.setText(model.getDescription());
                holder.chfname.setText(model.getName());
                holder.chfrate.setText(model.getRatings());
                holder.connect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.connect.setText("Connected");
                        FirebaseUser user1=firebaseAuth.getCurrentUser();
                        DocumentReference df1 = fstore.collection("Company Profile").document(user1.getUid());
                        df1.addSnapshotListener(companyhomepage.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                                a=value1.getString("Name");
                            }
                        });
                        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("Connected")
                                .child(model.getFUID().toString()).child("Company").child(firebaseAuth.getCurrentUser().getUid().toString());
                        Map<String,Object> coninfo=new HashMap<>();
                        coninfo.put("Name",a);
                        coninfo.put("CUID",firebaseAuth.getCurrentUser().getUid().toString());

                        dr.setValue(coninfo);
                        
                    }
                });
            }
        };
        flist.setHasFixedSize(true);
        flist.setLayoutManager(new LinearLayoutManager(this));
        flist.setAdapter(adapter1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.companytoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile1: {
                Intent intent = new Intent(companyhomepage.this, companyprofile.class);
                startActivity(intent);
                return true;
            }
            case R.id.myprojs: {
                Intent intent = new Intent(companyhomepage.this, myemployees.class);
                startActivity(intent);
                return true;
            }
            case R.id.signout1: {
                Intent intent = new Intent(companyhomepage.this, MainActivity2.class);
                startActivity(intent);
                return true;
            }
            case R.id.message1: {
                Intent intent = new Intent(companyhomepage.this, companymessage.class);
                startActivity(intent);
                return true;
            }
            case R.id.settings1: {
                Intent intent = new Intent(companyhomepage.this, settings.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private class FreelancertViewHolder extends RecyclerView.ViewHolder {
        private TextView chfname,chfexp,chfskills,chfrate;
        private ImageView profimg;
        Button connect;
        public FreelancertViewHolder(@NonNull View itemView1) {
            super(itemView1);
            profimg=itemView1.findViewById(R.id.chfprofimg);
            chfname=itemView1.findViewById(R.id.chfname);
            chfrate=itemView1.findViewById(R.id.chfrate);
            chfexp=itemView1.findViewById(R.id.chfexp);
            chfskills=itemView1.findViewById(R.id.chfskills);
            connect=itemView1.findViewById(R.id.b11);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
    }
}