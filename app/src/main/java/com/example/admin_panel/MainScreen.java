package com.example.admin_panel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainScreen extends AppCompatActivity {
    private Button product_upload;
    private EditText about,amount,link,name;
    Spinner category;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //Shared prefrence
        SharedPreferences sp=getSharedPreferences("myshopkeeper",MODE_PRIVATE);
        userid=sp.getString("username", "na");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("PRODUCT");
        product_upload=findViewById(R.id.upload_button);
        about=findViewById(R.id.product_about);
        amount=findViewById(R.id.product_amount);
        link=findViewById(R.id.product_image_link);
        name=findViewById(R.id.product_name);
        category=findViewById(R.id.product_category);


        product_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              uploadproduct();

            }
        });


    }

    private void uploadproduct() {
        Map<String, Object> product = new HashMap<>();
        product.put("name",name.getText().toString());
        product.put("amount", amount.getText().toString());
        product.put("about", about.getText().toString());
        product.put("imagelink",link.getText().toString());
        product.put("shopkeeper",userid);
        product.put("category",category.getSelectedItem().toString());
        String pid = myRef.push().getKey();
        String email=userid.toString().replace(".",",");
        myRef.child(pid).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainScreen.this, "Data Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainScreen.this, "error"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom,menu);
        return super.onCreateOptionsMenu(menu);
    }
}