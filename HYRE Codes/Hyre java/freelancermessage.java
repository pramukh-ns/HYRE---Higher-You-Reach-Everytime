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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class freelancermessage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    RecyclerView chlis;
    FirestoreRecyclerAdapter adt;
    String img,name,cuid;
    TextView chname;
    ImageView chprofimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancermessage);
        firebaseAuth = firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        chname=(TextView)findViewById(R.id.chname);
        chprofimg=(ImageView)findViewById(R.id.chprofimg);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView fn6 = findViewById(R.id.freelancer_bottom_navigation);
        fn6.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.freelancer_navigation_search: {
                        Intent f1 = new Intent(freelancermessage.this, companysearch.class);
                        startActivity(f1);
                        break;
                    }
                    case R.id.freelancer_navigation_home: {
                        Intent f2 = new Intent(freelancermessage.this, freelancerhomepage.class);
                        startActivity(f2);
                        break;
                    }
                    case R.id.freelancer_navigation_notifications: {
                        Intent f3 = new Intent(freelancermessage.this, freelancermessage.class);
                        startActivity(f3);
                        break;
                    }
                }
            }
        });
     DocumentReference df=fstore.collection("Hired").document(firebaseAuth.getCurrentUser().getUid());
     df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
         @Override
         public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
             cuid=value.getString("CUID");
             DocumentReference df1=fstore.collection("Company Profile").document(cuid);
             df1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                 @Override
                 public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                     img=value.getString("Img_URL");
                     name=value.getString("Name");
                     chname.setText("Congratulations!! "+name+" has hired you. You will receive the details of the project through mail.");
                     Picasso.get().load(img).into(chprofimg);
                 }
             });
         }
     });

    }
}

