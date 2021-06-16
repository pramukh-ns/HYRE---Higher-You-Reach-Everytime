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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class companyprofile extends AppCompatActivity {
    TextView cp1,cp2,cp3,cp4,cp5;
    Button save,edit;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    StorageReference sr,profref;
    ImageView profimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyprofile);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        BottomNavigationView cn3=findViewById(R.id.company_bottom_navigation);
        cn3.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.company_navigation_search: {
                        Intent r1 = new Intent(companyprofile.this, freelancersearch.class);
                        startActivity(r1);
                        break;
                    }
                    case R.id.company_navigation_home: {
                        Intent r2 = new Intent(companyprofile.this, companyhomepage.class);
                        startActivity(r2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent r3 = new Intent(companyprofile.this, newproject.class);
                        startActivity(r3);
                        break;
                    }
                    case R.id.company_navigation_notifications: {
                        Intent r4 = new Intent(companyprofile.this, companynotification.class);
                        startActivity(r4);
                        break;
                    }
                }
            }
        });

        cp1=(TextView)findViewById(R.id.cp1);
        cp2=(TextView)findViewById(R.id.cp2);
        cp3=(TextView)findViewById(R.id.cp3);
        cp4=(TextView)findViewById(R.id.cp4);
        cp5=(TextView)findViewById(R.id.cp5);


                    FirebaseUser user1=firebaseAuth.getCurrentUser();
                    DocumentReference df = fstore.collection("Users").document(user1.getUid());
                    df.addSnapshotListener(companyprofile.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                            cp1.setText(value1.getString("NAME"));
                            cp2.setText(value1.getString("USERNAME"));
                        }
                    });
                    DocumentReference docref = fstore.collection("Company Profile").document(user1.getUid());
                    docref.addSnapshotListener(companyprofile.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            cp3.setText(value.getString("Company Phone No"));
                            cp4.setText(value.getString("Address"));
                            cp5.setText(value.getString("Description"));
                        }
                    });


        edit=(Button)findViewById(R.id.b6);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname=cp1.getText().toString();
                String cusername=cp2.getText().toString();
                String cno=cp3.getText().toString();
                String caddress=cp4.getText().toString();
                String cdesc=cp5.getText().toString();
                Intent i = new Intent(companyprofile.this,editcompanyprofile.class);
                i.putExtra("Name",cname);
                i.putExtra("Username",cusername);
                i.putExtra("Company Phone No",cno);
                i.putExtra("Address",caddress);
                i.putExtra("Description",cdesc);
                startActivity(i);

            }
        });
        profimg=findViewById(R.id.img);
        sr= FirebaseStorage.getInstance().getReference();
        profref=sr.child("Users/"+firebaseAuth.getCurrentUser().getUid()+"/profile pic");
        profref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profimg);
            }
        });
    }

}