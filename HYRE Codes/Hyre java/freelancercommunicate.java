package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class freelancercommunicate extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    TextView fcname,fcexp,fcskills,fcusername,fcrate,fcpno;
    ImageView fcpimg;
    String r,fcusername1,fcpimg1,fcskills1,fcexp1,fcrate1,fcpno1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancercommunicate);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        BottomNavigationView cn12 = findViewById(R.id.company_bottom_navigation);
        cn12.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.company_navigation_home:{
                        Intent x1=new Intent(freelancercommunicate.this,companyhomepage.class);
                        startActivity(x1);
                        break;
                    }
                    case R.id.company_navigation_search: {
                        Intent x2 = new Intent(freelancercommunicate.this, freelancersearch.class);
                        startActivity(x2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent x3 = new Intent(freelancercommunicate.this, newproject.class);
                        startActivity(x3);
                        break;
                    }
                    case R.id.company_navigation_notifications:{
                        Intent x4 = new Intent(freelancercommunicate.this, companynotification.class);
                        startActivity(x4);
                        break;
                    }
                }
            }
        });
        fcname=(TextView)findViewById(R.id.fcname);
        fcpimg=(ImageView)findViewById(R.id.fcpimg);
        fcskills=(TextView)findViewById(R.id.fcskills);
        fcexp=(TextView)findViewById(R.id.fcexp);
        fcusername=(TextView)findViewById(R.id.fcusername);
        fcrate=(TextView)findViewById(R.id.fcrate);
        fcpno=(TextView)findViewById(R.id.fcpno);
        Intent d=getIntent();
        String fcname1=d.getStringExtra("Name");
        r=d.getStringExtra("FUID");
        DocumentReference df=fstore.collection("Freelancer Profile").document(r);
        df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                fcusername1=value.getString("Username");
                fcpimg1=value.getString("Img_URL");
                fcexp1=value.getString("Description");
                fcskills1=value.getString("Skills");
                fcrate1=value.getString("Ratings");
                fcpno1=value.getString("Freelancer Phone No");
                fcusername.setText(fcusername1);
                fcname.setText(fcname1);
                Picasso.get().load(fcpimg1).into(fcpimg);
                fcexp.setText(fcexp1);
                fcskills.setText(fcskills1);
                fcrate.setText(fcrate1);
                fcpno.setText(fcpno1);
            }
        });
        
    }
}