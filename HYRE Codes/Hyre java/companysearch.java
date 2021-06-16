package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class companysearch extends AppCompatActivity {
    EditText search;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    RecyclerView csearchlist;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<cslist> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent z = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companysearch);
        firebaseAuth = firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        search = (EditText) findViewById(R.id.csearch);
        BottomNavigationView fn2 = findViewById(R.id.freelancer_bottom_navigation);
        fn2.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.freelancer_navigation_home: {
                        Intent b1 = new Intent(companysearch.this, freelancerhomepage.class);
                        startActivity(b1);
                        break;
                    }
                    case R.id.freelancer_navigation_notifications: {
                        Intent b2 = new Intent(companysearch.this, freelancernotification.class);
                        startActivity(b2);
                        break;
                    }
                }
            }
        });

        csearchlist = (RecyclerView) findViewById(R.id.csearchlist);
        Query query = fstore.collection("Company Profile").orderBy("Name").startAt(search.getText().toString()) .endAt(search.getText().toString()+"\uf8ff");
        options = new FirestoreRecyclerOptions.Builder<cslist>().setQuery(query, cslist.class).build();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Query query = fstore.collection("Company Profile").orderBy("Name").startAt(search.getText().toString())
                        .endAt(search.getText().toString()+"\uf8ff");
                options = new FirestoreRecyclerOptions.Builder<cslist>().setQuery(query, cslist.class).build();
                adapter.updateOptions(options);
            }
        });

        adapter = new FirestoreRecyclerAdapter<cslist, csearchviewholder>(options) {
            @NonNull
            @NotNull
            @Override
            public csearchviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.csearch_item, parent, false);
                return new csearchviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull csearchviewholder holder, int position, @NonNull @NotNull cslist model) {
                holder.csname.setText(model.getName());

            }
        };
        csearchlist.setHasFixedSize(true);
        csearchlist.setLayoutManager(new LinearLayoutManager(this));
        csearchlist.setAdapter(adapter);


    }

    public class csearchviewholder extends RecyclerView.ViewHolder {
        TextView csname;
        LinearLayout cslayout;

        public csearchviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            csname = itemView.findViewById(R.id.csname);
            cslayout=itemView.findViewById(R.id.cslayout);
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