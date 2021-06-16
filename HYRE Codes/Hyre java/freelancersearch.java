package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class freelancersearch extends AppCompatActivity {

    EditText search;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    RecyclerView fsearchlist;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<fslist> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancersearch);
        firebaseAuth = firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        BottomNavigationView cn2 =findViewById(R.id.company_bottom_navigation);
        cn2.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.company_navigation_home: {
                        Intent q1 = new Intent(freelancersearch.this, companyhomepage.class);
                        startActivity(q1);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent q2 = new Intent(freelancersearch.this, newproject.class);
                        startActivity(q2);
                        break;
                    }
                    case R.id.company_navigation_notifications: {
                        Intent q3 = new Intent(freelancersearch.this, companynotification.class);
                        startActivity(q3);
                        break;
                    }
                }
            }
        });
        search = (EditText) findViewById(R.id.fsearch);
        fsearchlist = (RecyclerView) findViewById(R.id.fsearchlist);
        Query query = fstore.collection("Freelancer Profile").orderBy("Name").startAt(search.getText().toString()).endAt(search.getText().toString()+"\uf8ff");
        options = new FirestoreRecyclerOptions.Builder<fslist>().setQuery(query, fslist.class).build();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Query query = fstore.collection("Freelancer Profile").orderBy("Name").startAt(search.getText().toString())
                        .endAt(search.getText().toString()+"\uf8ff");
                options = new FirestoreRecyclerOptions.Builder<fslist>().setQuery(query, fslist.class).build();
                adapter.updateOptions(options);
            }
        });

        adapter = new FirestoreRecyclerAdapter<fslist, freelancersearch.fsearchviewholder>(options) {
            @NonNull
            @NotNull
            @Override
            public freelancersearch.fsearchviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fsearch_item, parent, false);
                return new freelancersearch.fsearchviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull freelancersearch.fsearchviewholder holder, int position, @NonNull @NotNull fslist model) {
                holder.fsname.setText(model.getName());

            }
        };
        fsearchlist.setHasFixedSize(true);
        fsearchlist.setLayoutManager(new LinearLayoutManager(this));
        fsearchlist.setAdapter(adapter);


    }

    public class fsearchviewholder extends RecyclerView.ViewHolder {
        TextView fsname;
        LinearLayout fslayout;

        public fsearchviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            fsname = itemView.findViewById(R.id.fsname);
            fslayout=itemView.findViewById(R.id.fslayout);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}