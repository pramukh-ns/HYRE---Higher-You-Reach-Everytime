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

import java.util.HashMap;
import java.util.Map;

public class editfreelancerprofile extends AppCompatActivity {
    EditText pp11,pp13,pp14,pp15,pp16,pp17,pp18;
    TextView pp12;
    Button psave;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    ImageView fprofpic11;
    StorageReference sr,profref,profref1;
    String pimgurl,rate,sum,count;
    boolean res=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editfreelancerprofile);
        BottomNavigationView fn4=findViewById(R.id.freelancer_bottom_navigation);
        fn4.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.freelancer_navigation_search:{
                        Intent d1=new Intent(editfreelancerprofile.this,companysearch.class);
                        startActivity(d1);
                        break;
                    }
                    case R.id.freelancer_navigation_home:{
                        Intent d2=new Intent(editfreelancerprofile.this,freelancerhomepage.class);
                        startActivity(d2);
                        break;
                    }
                    case R.id.freelancer_navigation_notifications:{
                        Intent d3=new Intent(editfreelancerprofile.this,freelancernotification.class);
                        startActivity(d3);
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

        pp11=(EditText)findViewById(R.id.pp11);
        pp12=(TextView)findViewById(R.id.pp12);
        pp13=(EditText)findViewById(R.id.pp13);
        pp14=(EditText)findViewById(R.id.pp14);
        pp15=(EditText)findViewById(R.id.pp15);
        pp16=(EditText)findViewById(R.id.pp16);
        pp17=(EditText)findViewById(R.id.pp17);


        String pname11=intent.getStringExtra("Name");
        String pusername11=intent.getStringExtra("Username");
        String pdob11=intent.getStringExtra("Date of Birth");
        String pno11=intent.getStringExtra("Freelancer Phone No");
        String paddress11=intent.getStringExtra("Address");
        String pexp11=intent.getStringExtra("Description");
        String pskills11=intent.getStringExtra("Skills");
        pp11.setText(pname11);
        pp12.setText(pusername11);
        pp13.setText(pdob11);
        pp14.setText(pno11);
        pp15.setText(paddress11);
        pp16.setText(pexp11);
        pp17.setText(pskills11);
        fprofpic11=findViewById(R.id.pimg1);
        FirebaseUser user=firebaseAuth.getCurrentUser();

        fprofpic11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opengalleryintent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengalleryintent,1000);
            }
        });


        profref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(fprofpic11);
                pimgurl=uri.toString();
            }
        });
        DocumentReference df1=fstore.collection("Freelancer Profile").document(user.getUid());
        df1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                rate =value.getString("Ratings");
                sum=value.getString("Sum");
                count=value.getString("Count");
            }
        });


        psave=(Button)findViewById(R.id.b9);
        psave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    String pname1 = pp11.getText().toString();
                    String pusername1 = pp12.getText().toString();
                    String pdob1 = pp13.getText().toString();
                    String pno1 = pp14.getText().toString();
                    String paddress1 = pp15.getText().toString();
                    String pexp1 = pp16.getText().toString();
                    String pskills1 = pp17.getText().toString();

                    DocumentReference df = fstore.collection("Freelancer Profile").document(user.getUid());
                    Map<String, Object> pprofileinfo = new HashMap<>();
                    pprofileinfo.put("Name", pname1);
                    pprofileinfo.put("Username", pusername1);
                    pprofileinfo.put("Date of Birth", pdob1);
                    pprofileinfo.put("Freelancer Phone No", pno1);
                    pprofileinfo.put("Address", paddress1);
                    pprofileinfo.put("Description", pexp1);
                    pprofileinfo.put("Skills", pskills1);
                    pprofileinfo.put("Img_URL", pimgurl);
                    pprofileinfo.put("Ratings", rate);
                    pprofileinfo.put("FUID", user.getUid().toString());
                    if (sum == null) {
                        pprofileinfo.put("Sum", "0");
                        pprofileinfo.put("Count", "0");
                    } else {
                        pprofileinfo.put("Sum", sum);
                        pprofileinfo.put("Count", count);
                    }
                    df.set(pprofileinfo);
                    Intent i = new Intent(editfreelancerprofile.this, freelancerprofile.class);
                    startActivity(i);
                }


            }
        });



    }

    private boolean validate() {
        boolean res=true;
        pp14=(EditText)findViewById(R.id.pp14);
        pp17=(EditText)findViewById(R.id.pp17);
        String pno1 = pp14.getText().toString();
        String pskills1 = pp17.getText().toString();
        if(pno1.isEmpty()){
            pp14.setError("Enter Phone NO");
            res=false;
        }
        if(pskills1.isEmpty()){
            pp17.setError("Enter Phone NO");
            res=false;
        }
        return res;
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
        final ProgressDialog pd=new ProgressDialog(editfreelancerprofile.this);
        pd.setTitle("Uploading Photo");
        pd.show();
        StorageReference fileref=sr.child("Users/"+firebaseAuth.getCurrentUser().getUid()+"/profile pic");
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(editfreelancerprofile.this, "Image Successfully uploaded",Toast.LENGTH_LONG);
                pd.dismiss();
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(fprofpic11);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(editfreelancerprofile.this, "Failed",Toast.LENGTH_LONG);
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