package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class freelancerfeedback extends AppCompatActivity {
    TextView ffname,ffexp,ffskills,ffusername,ffrate;
    ImageView ffpimg;
    Button rate;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    String l,a,b,c,d,e,s,f,g,k,fuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancerfeedback);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        BottomNavigationView cn9 = findViewById(R.id.company_bottom_navigation);
        cn9.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.company_navigation_home:{
                        Intent v1=new Intent(freelancerfeedback.this,companyhomepage.class);
                        startActivity(v1);
                        break;
                    }
                    case R.id.company_navigation_search: {
                        Intent v2 = new Intent(freelancerfeedback.this, freelancersearch.class);
                        startActivity(v2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent v3 = new Intent(freelancerfeedback.this, newproject.class);
                        startActivity(v3);
                        break;
                    }
                    case R.id.company_navigation_notifications:{
                        Intent v4 = new Intent(freelancerfeedback.this, companynotification.class);
                        startActivity(v4);
                        break;
                    }
                }
            }
        });
        ffname=(TextView)findViewById(R.id.ffname);
        ffexp=(TextView)findViewById(R.id.ffexp);
        ffskills=(TextView)findViewById(R.id.ffskills);
        ffrate=(TextView)findViewById(R.id.ffrate);
        ffusername=(TextView)findViewById(R.id.ffusername);
        ffpimg=(ImageView)findViewById(R.id.ffpimg);
        rate=(Button)findViewById(R.id.b13) ;
        Intent d=getIntent();
        String ffname1=d.getStringExtra("Name");
        fuid=d.getStringExtra("FUID");

        if(fuid!=null){
            DocumentReference df1=fstore.collection("Freelancer Profile").document(fuid.toString());
            df1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains
                        .annotations.Nullable FirebaseFirestoreException error) {
                    a=value.getString("Freelancer Phone No");
                    b=value.getString("Address");
                    c=value.getString("Date of Birth");
                    s=value.getString("Img_URL");
                    e=value.getString("Description");
                    f=value.getString("Skills");
                    g=value.getString("Username");

                    ffname.setText(ffname1);
                    ffexp.setText(e);
                    ffskills.setText(f);
                    ffusername.setText(g);
                    Picasso.get().load(s).into(ffpimg);
                }
            });



            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i=new Intent(freelancerfeedback.this,editfreelancerfeedback.class);
                    i.putExtra("Name",ffname1);
                    i.putExtra("FUID",fuid);
                    startActivity(i);

                }
            });
            DocumentReference df=fstore.collection("Freelancer Profile").document(fuid);
            df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                    l=value.getString("Ratings");
                    ffrate.setText(l);

                }
            });

        }




    }
}