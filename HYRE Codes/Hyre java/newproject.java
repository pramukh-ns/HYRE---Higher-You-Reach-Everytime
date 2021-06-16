package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class newproject extends AppCompatActivity {
    EditText pt1,pt2,pt3,pt4,pt5;
    TextView tvcname,profimg;
    Button addproj;
    private FirebaseFirestore fstore;
    private FirebaseAuth firebaseAuth;
    StorageReference sr,profref;
    String a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);
        pt1=(EditText)findViewById(R.id.pt1);
        pt2=(EditText)findViewById(R.id.pt2);
        pt3=(EditText)findViewById(R.id.pt3);
        pt4=(EditText)findViewById(R.id.pt4);
        pt5=(EditText)findViewById(R.id.pt5);
        tvcname=(TextView) findViewById(R.id.tvcname);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        BottomNavigationView cn5=findViewById(R.id.company_bottom_navigation);
       cn5.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.company_navigation_search: {
                        Intent t1 = new Intent(newproject.this, freelancersearch.class);
                        startActivity(t1);
                        break;
                    }
                    case R.id.company_navigation_home :{
                        Intent t2 = new Intent(newproject.this, companyhomepage.class);
                        startActivity(t2);
                        break;
                    }
                    case R.id.company_navigation_notifications: {
                        Intent t3 = new Intent(newproject.this, companynotification.class);
                        startActivity(t3);
                        break;
                    }
                }
            }
        });
        FirebaseUser user1=firebaseAuth.getCurrentUser();
        DocumentReference df1 = fstore.collection("Company Profile").document(user1.getUid());
        df1.addSnapshotListener(newproject.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                        tvcname.setText(value1.getString("Name"));
                    }
                });

        addproj= (Button) findViewById(R.id.b4);
        addproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=tvcname.getText().toString();
                String title=pt1.getText().toString();
                String desc=pt2.getText().toString();
                String skills=pt3.getText().toString();
                String role=pt4.getText().toString();
                String duration=pt5.getText().toString();
                if(title.isEmpty()){
                    pt1.setError("Enter Project Title");
                }
                else if(desc.isEmpty()){
                    pt2.setError("Enter Project Description");
                }
                else if(role.isEmpty()){
                    pt3.setError("Enter Project Role");
                }
                else if(skills.isEmpty()){
                    pt4.setError("Enter Project Skills");
                }
                else if(duration.isEmpty()){
                    pt5.setError("Enter Project Duration");
                }
                else{
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    DocumentReference df=fstore.collection("Projects").document(user.getUid());

                    Map<String,Object> projectinfo=new HashMap<>();
                    projectinfo.put("Name",name);
                    projectinfo.put("Project_Title",title);
                    projectinfo.put("Project_Description",desc);
                    projectinfo.put("Project_Role",role);
                    projectinfo.put("Project_Skills",skills);
                    projectinfo.put("Project_Duration",duration);
                    projectinfo.put("PID",user.getUid());
                    df.set(projectinfo);
                    Toast.makeText(newproject.this, "Project added successfully", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}