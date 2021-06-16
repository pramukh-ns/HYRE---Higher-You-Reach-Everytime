package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper hdb;
    EditText et3,et4,et5;
    Button signup;
    TextView tvlogin;
    CheckBox c1,c2;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        setContentView(R.layout.activity_main);
        firebaseAuth=firebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        c1 = (CheckBox)findViewById(R.id.c1);
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    c2.setChecked(false);
                }
            }
        });
        c2 = (CheckBox)findViewById(R.id.c2);
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    c1.setChecked(false);
                }
            }
        });
        signup = (Button) findViewById(R.id.b2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    String username=et3.getText().toString();
                    String password=et4.getText().toString();
                    String name=et5.getText().toString();

                 firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser user=firebaseAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                                DocumentReference df=fstore.collection("Users").document(user.getUid());
                                Map<String,Object>userinfo=new HashMap<>();
                                userinfo.put("USERNAME",username);
                                userinfo.put("PASSWORD",password);
                                userinfo.put("NAME",name);
                                if(c1.isChecked()) {
                                    userinfo.put("isFreelancer", "1");
                                }
                                else if(c2.isChecked()){
                                    userinfo.put("isCompany", "1");
                                }
                                df.set(userinfo);
                                Intent intent=new Intent(MainActivity.this, MainActivity2.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        tvlogin=(TextView)findViewById(R.id.tvlogin);
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    public boolean validate(){
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        Boolean result = true;
        String username=et3.getText().toString();
        String password=et4.getText().toString();
        String name=et5.getText().toString();
        if(username.isEmpty() ) {
            et3.setError("Enter Username");
            result=false;
        }
        else if(password.length()<6 || password.isEmpty()){
            et4.setError("Invalid Password");
            result=false;
        }
        else if(name.isEmpty()){
            et5.setError("Enter name");
            result=false;
        }
        return result;
    }
}


