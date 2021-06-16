package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class editcompanyprofile extends AppCompatActivity {
    EditText cp11,cp13,cp14,cp15;
    TextView cp12;
    Button csave;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    ImageView profimg1;
    FirebaseUser user;
    StorageReference sr,profref,profref1;
    String profurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcompanyprofile);
        BottomNavigationView cn4=findViewById(R.id.company_bottom_navigation);
        cn4.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.company_navigation_search: {
                        Intent s1 = new Intent(editcompanyprofile.this, freelancersearch.class);
                        startActivity(s1);
                        break;
                    }
                    case R.id.company_navigation_home:{
                        Intent s2 = new Intent(editcompanyprofile.this,companyhomepage.class);
                        startActivity(s2);
                        break;
                    }
                    case R.id.company_navigation_newproject: {
                        Intent s3 = new Intent(editcompanyprofile.this, newproject.class);
                        startActivity(s3);
                        break;
                    }
                    case R.id.company_navigation_notifications: {
                        Intent s4 = new Intent(editcompanyprofile.this, companynotification.class);
                        startActivity(s4);
                        break;
                    }
                }
            }
        });
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        sr= FirebaseStorage.getInstance().getReference();
        profref=sr.child("Users/"+firebaseAuth.getCurrentUser().getUid()+"/profile pic");
        profref1=sr.child("Users/"+firebaseAuth.getCurrentUser().getUid()+"/profile pic");
        profimg1=(ImageView)findViewById(R.id.img1);

        cp11=(EditText)findViewById(R.id.cp11);
        cp12=(TextView)findViewById(R.id.cp12);
        cp13=(EditText)findViewById(R.id.cp13);
        cp14=(EditText)findViewById(R.id.cp14);
        cp15=(EditText)findViewById(R.id.cp15);

        String cname11=intent.getStringExtra("Name");
        String cusername11=intent.getStringExtra("Username");
        String cno11=intent.getStringExtra("Company Phone No");
        String caddress11=intent.getStringExtra("Address");
        String cdesc11=intent.getStringExtra("Description");
        cp11.setText(cname11);
        cp12.setText(cusername11);
        cp13.setText(cno11);
        cp14.setText(caddress11);
        cp15.setText(cdesc11);

        profimg1=findViewById(R.id.img1);
        profimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opengalleryintent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengalleryintent,1000);
            }
        });


        profref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profimg1);
                profurl=uri.toString();
            }
        });
        csave=(Button)findViewById(R.id.b7);
        csave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname1=cp11.getText().toString();
                String cusername1=cp12.getText().toString();
                String cno1=cp13.getText().toString();
                String caddress1=cp14.getText().toString();
                String cdesc1=cp15.getText().toString();
                user=firebaseAuth.getCurrentUser();
                DocumentReference df=fstore.collection("Company Profile").document(user.getUid());
                Map<String,Object> cprofileinfo=new HashMap<>();
                cprofileinfo.put("Name",cname1);
                cprofileinfo.put("Username",cusername1);
                cprofileinfo.put("Company Phone No",cno1);
                cprofileinfo.put("Address",caddress1);
                cprofileinfo.put("Description",cdesc1);
                cprofileinfo.put("Img_URL",profurl);
                df.set(cprofileinfo);
                Intent i=new Intent(editcompanyprofile.this,companyprofile.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageuri=data.getData();
                uploadimagetofirebase(imageuri);

            }
        }
    }

    private void uploadimagetofirebase(Uri imageuri) {
        final ProgressDialog pd=new ProgressDialog(editcompanyprofile.this);
        pd.setTitle("Uploading Photo");
        pd.show();
        StorageReference fileref=sr.child("Users/"+firebaseAuth.getCurrentUser().getUid()+"/profile pic");
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(editcompanyprofile.this, "Image Successfully uploaded",Toast.LENGTH_LONG);
                pd.dismiss();
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profimg1);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(editcompanyprofile.this, "Failed",Toast.LENGTH_LONG);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double p=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                pd.setMessage("Uploaded: "+(int)p+"%");
            }
        });

    }
}