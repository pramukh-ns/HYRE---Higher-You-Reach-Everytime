package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class freelancerdetails extends AppCompatActivity {
    TextView fdname,fdexp,fdskills,fdusername,fdrate,fdpno;
    ImageView fdpimg;
    Button hire;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    StorageReference sr,profref;
    String p,q,r,x,y,h,fdusername1,fdpimg1,fdskills1,fdexp1,fdrate1,fdpno1;
    List<String> list;
    boolean test = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent z=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancerdetails);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        BottomNavigationView cn7 = findViewById(R.id.company_bottom_navigation);
        cn7.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.company_navigation_home:{
                        Intent v1=new Intent(freelancerdetails.this,companyhomepage.class);
                        startActivity(v1);
                        break;
                    }
                    case R.id.company_navigation_search: {
                        Intent v2 = new Intent(freelancerdetails.this, freelancersearch.class);
                        startActivity(v2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent v3 = new Intent(freelancerdetails.this, newproject.class);
                        startActivity(v3);
                        break;
                    }
                    case R.id.company_navigation_notifications:{
                        Intent v4 = new Intent(freelancerdetails.this, companynotification.class);
                        startActivity(v4);
                        break;
                    }
                }
            }
        });
        fdname=(TextView)findViewById(R.id.fdname);
        fdpimg=(ImageView)findViewById(R.id.fdpimg);
        fdskills=(TextView)findViewById(R.id.fdskills);
        fdexp=(TextView)findViewById(R.id.fdexp);
        fdusername=(TextView)findViewById(R.id.fdusername);
        fdrate=(TextView)findViewById(R.id.fdrate);
        fdpno=(TextView)findViewById(R.id.fdpno);
        Intent d=getIntent();
        String fdname1=d.getStringExtra("Name");
        r=d.getStringExtra("FUID");
        DocumentReference df=fstore.collection("Freelancer Profile").document(r);
        df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                fdusername1=value.getString("Username");
                fdpimg1=value.getString("Img_URL");
                fdexp1=value.getString("Description");
                fdskills1=value.getString("Skills");
                fdrate1=value.getString("Ratings");
                fdpno1=value.getString("Freelancer Phone No");
                fdusername.setText(fdusername1);
                fdname.setText(fdname1);
                Picasso.get().load(fdpimg1).into(fdpimg);
                fdexp.setText(fdexp1);
                fdskills.setText(fdskills1);
                fdrate.setText(fdrate1);
                fdpno.setText(fdpno1);
            }
        });

        hire=(Button)findViewById(R.id.b12);
        list = new ArrayList<>();
        fstore.collection("Hired").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    Log.d( "DOClist",list.toString());
                    for(String idl:list) {
                        if(idl.equals(r)){
                            test = true;
                            Log.d("DOCTrue",list.toString());
                            break;
                        }
                    }
                    if(test==true){
                        hire.setEnabled(false);
                    }
                    else{
                        hire.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hire.setText("Hired");
                                FirebaseUser user1=firebaseAuth.getCurrentUser();
                                DocumentReference df1 = fstore.collection("Company Profile").document(user1.getUid());
                                df1.addSnapshotListener(freelancerdetails.this, new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                                        p=value1.getString("Name");
                                        q=value1.getString("Img_URL");
                                        x=value1.getString("Description");
                                        y=value1.getString("Username");
                                    }
                                });

                                DocumentReference tf=fstore.collection("Hired").document(r);
                                Map<String,Object> hireinfo=new HashMap<>();
                                hireinfo.put("Name",p);
                                hireinfo.put("CUID",firebaseAuth.getCurrentUser().getUid().toString());
                                tf.set(hireinfo);

                                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Company Hired")
                                        .child(firebaseAuth.getCurrentUser().getUid().toString())
                                        .child("Freelancers Hired").child(r.toString());
                                Map<String,Object> chireinfo=new HashMap<>();
                                chireinfo.put("Name",fdname1);
                                chireinfo.put("FUID",r);
                                ref.setValue(chireinfo);

                            }
                        });
                    }
                } else {
                    Log.d( "Error getting documents: ", String.valueOf(task.getException()));
                }
            }
        });




    }
}



