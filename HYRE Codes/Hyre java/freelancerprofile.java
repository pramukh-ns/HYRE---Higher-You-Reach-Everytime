package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class freelancerprofile extends AppCompatActivity {

    TextView pp1,pp2,pp3,pp4,pp5,pp6,pp7,pp8;
    Button pedit;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    ImageView fprofpic;
    StorageReference sr,profref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent z = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancerprofile);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        BottomNavigationView fn3=findViewById(R.id.freelancer_bottom_navigation);
        fn3.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.freelancer_navigation_search:{
                        Intent c1=new Intent(freelancerprofile.this,companysearch.class);
                        startActivity(c1);
                        break;
                    }
                    case R.id.freelancer_navigation_home:{
                        Intent c2=new Intent(freelancerprofile.this,freelancerhomepage.class);
                        startActivity(c2);
                        break;
                    }
                    case R.id.freelancer_navigation_notifications:{
                        Intent c3=new Intent(freelancerprofile.this,freelancernotification.class);
                        startActivity(c3);
                        break;
                    }
                }
            }
        });


        pp1=(TextView)findViewById(R.id.pp1);
        pp2=(TextView)findViewById(R.id.pp2);
        pp3=(TextView)findViewById(R.id.pp3);
        pp4=(TextView)findViewById(R.id.pp4);
        pp5=(TextView)findViewById(R.id.pp5);
        pp6=(TextView)findViewById(R.id.pp6);
        pp7=(TextView)findViewById(R.id.pp7);

        FirebaseUser user1=firebaseAuth.getCurrentUser();
        DocumentReference df = fstore.collection("Users").document(user1.getUid());
        df.addSnapshotListener(freelancerprofile.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                pp1.setText(value1.getString("NAME"));
                pp2.setText(value1.getString("USERNAME"));
            }
        });
        DocumentReference docref = fstore.collection("Freelancer Profile").document(user1.getUid());
        docref.addSnapshotListener(freelancerprofile.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                pp3.setText(value.getString("Date of Birth"));
                pp4.setText(value.getString("Freelancer Phone No"));
                pp5.setText(value.getString("Address"));
                pp6.setText(value.getString("Description"));
                pp7.setText(value.getString("Skills"));
            }
        });
        pedit=(Button)findViewById(R.id.b8);
        pedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pname=pp1.getText().toString();
                String pusername=pp2.getText().toString();
                String pdob=pp3.getText().toString();
                String pno=pp4.getText().toString();
                String paddress=pp5.getText().toString();
                String pexp=pp6.getText().toString();
                String pskills=pp7.getText().toString();
                Intent i = new Intent(freelancerprofile.this,editfreelancerprofile.class);
                i.putExtra("Name",pname);
                i.putExtra("Username",pusername);
                i.putExtra("Date of Birth",pdob);
                i.putExtra("Freelancer Phone No",pno);
                i.putExtra("Address",paddress);
                i.putExtra("Description",pexp);
                i.putExtra("Skills",pskills);
                startActivity(i);
            }
        });
        fprofpic=findViewById(R.id.pimg);
        sr= FirebaseStorage.getInstance().getReference();
        profref=sr.child("Users/"+firebaseAuth.getCurrentUser().getUid()+"/profile pic");
        profref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(fprofpic);
            }
        });
    }

}
