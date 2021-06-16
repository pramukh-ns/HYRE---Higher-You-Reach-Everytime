package com.example.hyreeee;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.HashMap;
import java.util.Map;


public class freelancerhomepage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    RecyclerView clist;
    FirestoreRecyclerAdapter adapter;
    String a,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent z = getIntent();
        super.onCreate(savedInstanceState);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_freelancerhomepage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView fn1=findViewById(R.id.freelancer_bottom_navigation);
        fn1.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.freelancer_navigation_search:{
                        Intent a1=new Intent(freelancerhomepage.this,companysearch.class);
                        startActivity(a1);
                        break;
                    }
                    case R.id.freelancer_navigation_notifications:{
                        Intent a3=new Intent(freelancerhomepage.this,freelancernotification.class);
                        startActivity(a3);
                        break;
                    }
                }
            }
        });


        clist=(RecyclerView)findViewById(R.id.clist);

        Query query = fstore.collection("Projects");
        FirestoreRecyclerOptions <cpost> options=new FirestoreRecyclerOptions.Builder<cpost>().setQuery(query,cpost.class).build();
        adapter= new FirestoreRecyclerAdapter<cpost, ProjectViewHolder>(options) {
            @NonNull
            @Override
            public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cpost_item,parent,false);
                return new ProjectViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProjectViewHolder holder, int position, @NonNull cpost model) {
                DocumentReference qq=fstore.collection("Company Profile").document(model.getPID());
                qq.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        img=value.getString("Img_URL");
                        Picasso.get().load(img).into(holder.profimg1);
                        holder.cname.setText(model.getName());
                        holder.fhptitle.setText(model.getProject_Title());
                        holder.fhpdesc.setText(model.getProject_Description());
                        holder.fhprole.setText(model.getProject_Role());
                        holder.fhpskills.setText(model.getProject_Skills());
                        holder.fhpduration.setText(model.getProject_Duration());
                    }
                });


                holder.aply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.aply.setText("Applied");
                        FirebaseUser user1=firebaseAuth.getCurrentUser();
                        DocumentReference df1 = fstore.collection("Freelancer Profile").document(user1.getUid());
                        df1.addSnapshotListener(freelancerhomepage.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                                a=value1.getString("Name");
                            }
                        });
                        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("Applied")
                                .child(model.getPID().toString()).child("Freelancers").child(firebaseAuth.getCurrentUser().getUid().toString());
                        Map<String,Object> appinfo=new HashMap<>();
                        appinfo.put("Name",a);
                        appinfo.put("FUID",firebaseAuth.getCurrentUser().getUid().toString());

                        dr.setValue(appinfo);


                    }
                });
            }
        };

        clist.setHasFixedSize(true);
        clist.setLayoutManager(new LinearLayoutManager(this));
        clist.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater1 = getMenuInflater();
        inflater1.inflate(R.menu.freelancertoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile: {
                Intent intent = new Intent(freelancerhomepage.this, freelancerprofile.class);
                startActivity(intent);
                return true;
            }
            case R.id.signout: {
                Intent intent = new Intent(freelancerhomepage.this, MainActivity2.class);
                startActivity(intent);
                return true;
            }
            case R.id.message:{
                Intent intent = new Intent(freelancerhomepage.this, freelancermessage.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private class ProjectViewHolder extends RecyclerView.ViewHolder{
        private TextView fhptitle,fhpdesc,fhprole,fhpskills,fhpduration,cname;
        private ImageView profimg1;
        private Button aply;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            profimg1=itemView.findViewById(R.id.fhcprofimg);
            cname=itemView.findViewById(R.id.fhcname);
            fhptitle=itemView.findViewById(R.id.fhptitle);
            fhpdesc=itemView.findViewById(R.id.fhpdesc);
            fhprole=itemView.findViewById(R.id.fhprole);
            fhpskills=itemView.findViewById(R.id.fhpskills);
            fhpduration=itemView.findViewById(R.id.fhpduration);
            aply=itemView.findViewById(R.id.b10);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    private class imageViewHolder  extends RecyclerView.ViewHolder{
        public imageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}