package com.example.hyreeee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {
        EditText et6;
        Button resetpassword;
        private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        et6=(EditText)findViewById(R.id.et6);
        firebaseAuth=firebaseAuth.getInstance();
        resetpassword=(Button)findViewById(R.id.b3);
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=et6.getText().toString();
                if(username.isEmpty()){
                    Toast.makeText(forgotpassword.this,"Please enter your email or username",Toast.LENGTH_LONG);
                }else{
                    firebaseAuth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forgotpassword.this,"Password reset link sent successfully",Toast.LENGTH_LONG);
                                Intent intent=new Intent(forgotpassword.this, MainActivity2.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(forgotpassword.this,"Invalid email or username",Toast.LENGTH_LONG);
                            }
                        }
                    });
                }
            }
        });
    }
}