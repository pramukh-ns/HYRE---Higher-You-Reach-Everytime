package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class companydetails extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    TextView cdname,cdusername,cddesc,cdptitle,cdpdesc,cdprole,cdpskills,cdpduratiion;
    ImageView cdimg;
    Button accept;
    String cddesc1,cdimg1,cdusername1,cuid,name,fuid,cdptitle1,cdpdesc1,cdprole1,cdpskills1,cdpduratiion1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent z=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetails);

        BottomNavigationView fn7=findViewById(R.id.freelancer_bottom_navigation);
        fn7.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.freelancer_navigation_search:{
                        Intent e1=new Intent(companydetails.this,companysearch.class);
                        startActivity(e1);
                        break;
                    }
                    case R.id.freelancer_navigation_home:{
                        Intent e2=new Intent(companydetails.this,freelancerhomepage.class);
                        startActivity(e2);
                        break;
                    }
                    case R.id.freelancer_navigation_notifications:{
                        Intent e3=new Intent(companydetails.this,freelancernotification.class);
                        startActivity(e3);
                        break;
                    }
                }
            }
        });

        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        cdname=(TextView)findViewById(R.id.cdname);
        cddesc=(TextView)findViewById(R.id.cddesc);
        cdusername=(TextView)findViewById(R.id.cdusername);
        cdimg=(ImageView)findViewById(R.id.cdpimg);
        cdptitle=(TextView)findViewById(R.id.cdptitle);
        cdpdesc=(TextView)findViewById(R.id.cdpdesc);
        cdprole=(TextView)findViewById(R.id.cdprole);
        cdpskills=(TextView)findViewById(R.id.cdpskills);
        cdpduratiion=(TextView)findViewById(R.id.cdpduratiion);
        accept=(Button)findViewById(R.id.b15) ;

        Intent d=getIntent();
        String cdname1=d.getStringExtra("Name");
        cuid =d.getStringExtra("CUID");
        DocumentReference df=fstore.collection("Company Profile").document(cuid);
        df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                cddesc1=value.getString("Description");
                cdimg1=value.getString("Img_URL");
                cdusername1=value.getString("Username");
                cdname.setText(cdname1);
                cddesc.setText(cddesc1);
                cdusername.setText(cdusername1);
                Picasso.get().load(cdimg1).into(cdimg);
            }
        });
        DocumentReference df1=fstore.collection("Projects").document(cuid);
        df1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                cdptitle1=value.getString("Project_Title");
                cdpdesc1=value.getString("Project_Description");
                cdprole1=value.getString("Project_Role");
                cdpskills1=value.getString("Project_Skills");
                cdpduratiion1=value.getString("Project_Duration");
                cdptitle.setText(cdptitle1);
                cdpskills.setText(cdpskills1);
                cdpdesc.setText(cdpdesc1);
                cdprole.setText(cdprole1);
                cdpduratiion.setText(cdpduratiion1);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept.setText("Accepted");
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Accepted")
                        .child(cuid)
                        .child("Company Accepted").child(firebaseAuth.getCurrentUser().getUid().toString());
                Map<String,Object> accinfo=new HashMap<>();
                DocumentReference df2=fstore.collection("Freelancer Profile").document(firebaseAuth.getCurrentUser()
                .getUid());
                df2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        name=value.getString("Name");
                        fuid=value.getString("FUID");

                    }
                });
                accinfo.put("Name",name);
                accinfo.put("FUID",fuid);

                ref.setValue(accinfo);

            }
        });
    }
}