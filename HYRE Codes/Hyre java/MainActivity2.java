package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    DatabaseHelper hdb;
    EditText et1,et2;
    Button login;
    TextView tvsignup,tvforgot;
    private FirebaseAuth firebaseAuth1;
    private FirebaseFirestore fstore;
    Boolean valid=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        tvsignup=(TextView)findViewById(R.id.tvsignup);
        tvforgot=(TextView)findViewById(R.id.tvforgot);
        firebaseAuth1=firebaseAuth1.getInstance();
        fstore = FirebaseFirestore.getInstance();
        login=(Button)findViewById(R.id.b1);
        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         if (validate1()) {
                                              firebaseAuth1.signInWithEmailAndPassword(et1.getText().toString(), et2.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                 @Override
                                                 public void onSuccess(AuthResult authResult) {
                                                     checkUser(authResult.getUser().getUid());
                                                 }
                                             });
                                         }
                                         else{
                                             et1.setError("Invalid Username and Password");
                                         }
                                     }
                                 });

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(MainActivity2.this, MainActivity.class);
               startActivity(intent);
            }

        });
        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this, forgotpassword.class);
                startActivity(intent);
            }
        });
    }



    private void checkUser(String uid) {
        DocumentReference df=fstore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","OnSuccess: "+ documentSnapshot.getData());
                if(documentSnapshot.getString("isFreelancer")!=null){
                    Intent intent=new Intent(MainActivity2.this, freelancerhomepage.class);
                    startActivity(intent);
                    finish();
                }
                if(documentSnapshot.getString("isCompany")!=null){
                    Intent intent=new Intent(MainActivity2.this, companyhomepage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private boolean validate1() {
        boolean res=true;
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        String username=et1.getText().toString();
        String password=et2.getText().toString();
        if(username.isEmpty()){
            et1.setError("Enter Username");
            res=false;
        }
        else if(password.length() < 6 || password.isEmpty() ){
            et2.setError("Invalid Password");
            res=false;
        }

        return res;
    }

}
