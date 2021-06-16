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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class editfreelancerfeedback extends AppCompatActivity {
    TextView fffname,fffexp,fffskills,fffusername;
    EditText fffrate;
    ImageView fffpimg;
    Button rate1;
    String a,b,c,s,e,f,g,h,fuid,sum,count,r;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfreelancerfeedback);

        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        BottomNavigationView cn10 = findViewById(R.id.company_bottom_navigation);
        cn10.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.company_navigation_home:{
                        Intent v1=new Intent(editfreelancerfeedback.this,companyhomepage.class);
                        startActivity(v1);
                        break;
                    }
                    case R.id.company_navigation_search: {
                        Intent v2 = new Intent(editfreelancerfeedback.this, freelancersearch.class);
                        startActivity(v2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent v3 = new Intent(editfreelancerfeedback.this, newproject.class);
                        startActivity(v3);
                        break;
                    }
                    case R.id.company_navigation_notifications:{
                        Intent v4 = new Intent(editfreelancerfeedback.this, companynotification.class);
                        startActivity(v4);
                        break;
                    }
                }
            }
        });
        fffname=(TextView)findViewById(R.id.fffname);
        fffexp=(TextView)findViewById(R.id.fffexp);
        fffskills=(TextView)findViewById(R.id.fffskills);
        fffrate=(EditText) findViewById(R.id.fffrate);
        fffusername=(TextView)findViewById(R.id.fffusername);
        fffpimg=(ImageView)findViewById(R.id.fffpimg);
        rate1=(Button)findViewById(R.id.b14) ;

        Intent d=getIntent();
        String fffname1=d.getStringExtra("Name");
        fuid=d.getStringExtra("FUID");
        if(fuid!=null){
            DocumentReference df1=fstore.collection("Freelancer Profile").document(fuid);
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
                    h=value.getString("FUID");
                    sum=value.getString("Sum");
                    count=value.getString("Count");
                    fffname.setText(fffname1);
                    fffexp.setText(e);
                    fffskills.setText(f);
                    fffusername.setText(g);
                    Picasso.get().load(s).into(fffpimg);


                }
            });


            rate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    float x=Float.parseFloat(sum);
                    float y=Float.parseFloat(count);
                    y=y+1;
                    float f11=Float.parseFloat(fffrate.getText().toString());
                    float sum1=f11+x;
                    float avgr=sum1/y;
                    sum=String.valueOf(sum1);
                    count=String.valueOf(y);
                    String ar=String.valueOf(avgr);
                    DocumentReference df=fstore.collection("Freelancer Profile").document(fuid);
                    Map<String,Object> fbinfo=new HashMap<>();
                    fbinfo.put("Name",fffname1);
                    fbinfo.put("Description",e);
                    fbinfo.put("Skills",f);
                    fbinfo.put("Username",g);
                    fbinfo.put("Ratings",ar);
                    fbinfo.put("Freelancer Phone No",a);
                    fbinfo.put("Address",b);
                    fbinfo.put("Date of Birth",c);
                    fbinfo.put("Img_URL",s);
                    fbinfo.put("FUID",h);
                    fbinfo.put("Sum",sum);
                    fbinfo.put("Count",count);

                    df.set(fbinfo);
                    Intent i=new Intent(editfreelancerfeedback.this,freelancerfeedback.class);
                    i.putExtra("Name",fffname1);
                    i.putExtra("FUID",fuid);
                    startActivity(i);

                }
            });

        }

    }
}