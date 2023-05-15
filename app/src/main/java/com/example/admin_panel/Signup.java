package com.example.admin_panel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private Button btn_signup,btn_loginPage;
    private TextInputEditText signup_email,signup_phone,signup_name,signup_password;
    FirebaseFirestore db;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        signup_email=findViewById(R.id.signup_email);
        signup_phone=findViewById(R.id.signup_phone);
        signup_name=findViewById(R.id.signup_name);
        signup_password=findViewById(R.id.signup_password);
        btn_signup=findViewById(R.id.signup_btn);
        btn_loginPage=findViewById(R.id.signup_login_page);
        btn_loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Signup.this,login_page.class);
                startActivity(intent);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createuser();
                addUserData();
                signup_phone.setText("");
                signup_email.setText("");
                signup_name.setText("");
                signup_password.setText("");
            }
        });
    }
    private void addUserData()
    {
        Map<String, Object> user = new HashMap<>();
        user.put("email", signup_email.getText().toString());
        user.put("name", signup_name.getText().toString());
        user.put("phone", signup_phone.getText().toString());db.collection("shopkeeper").document(signup_email.getText().toString()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Signup.this, "USER REGISTERED", Toast.LENGTH_SHORT).show();
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createuser()
    {
        String email=signup_email.getText().toString();
        String password=signup_password.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            signup_email.setError("Enter Email");
            signup_email.requestFocus();

        }
        else if (TextUtils.isEmpty(password))
        {
            signup_password.setError("Enter Password");
            signup_password.requestFocus();
        }
        else
        {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {

                                    Toast.makeText(Signup.this, "Email Sent please verify your email", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(Signup.this, "Error failure" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Toast.makeText(Signup.this, "User Created", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Signup.this, "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }
    }
}