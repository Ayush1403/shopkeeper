package com.example.admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myoreder extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference orderRef;
    LinearLayout llcv;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myoreder);
        database = FirebaseDatabase.getInstance();
        orderRef = database.getReference("ORDERS");
        llcv=findViewById(R.id.llcv);
        productfetching();
    }

    private void productfetching(){
//place order button
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                llcv.removeAllViews();

                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
//                     Iterate through the child nodes inside each order
                    for (DataSnapshot emailSnapshot : orderSnapshot.getChildren()) {
                        String emailKey = emailSnapshot.getKey();
                        String emailuser = orderSnapshot.getKey();
                        // Access the child nodes inside each email
                        String about = emailSnapshot.child("about").getValue(String.class);
                        String name = emailSnapshot.child("name").getValue(String.class);
                        String amount= emailSnapshot.child("amount").getValue(String.class);
                        category= emailSnapshot.child("category").getValue(String.class);

                        TextView tvname=new TextView(myoreder.this);
                        TextView tvamount=new TextView(myoreder.this);
                        TextView tvabout=new TextView(myoreder.this);
                        tvabout.setText(about);
                        tvname.setText(name);
                        tvamount.setText(amount);
                        tvamount.setTextSize(16);
                        tvname.setTextSize(16);
                        tvabout.setTextSize(16);
                        Button b=new Button(myoreder.this);
                        b.setText("ACCEPT ORDER");
                        b.setWidth(25);
                        b.setHeight(10);
//                        Remove child code from cart
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences sp=getSharedPreferences("myuser",MODE_PRIVATE);
                                String userid = sp.getString("username", "na");
                                //removing value
                                orderRef.child(emailuser.replace(".",",")).child(emailKey).removeValue();
                                Toast.makeText(myoreder.this, "ORDER ACCEPTED", Toast.LENGTH_SHORT).show();
                            }
                        });

                        CardView cardView = new CardView(myoreder.this);
                        int cardMargin = getResources().getDimensionPixelSize(R.dimen.card_margin);
                        int cardElevation = getResources().getDimensionPixelSize(R.dimen.card_elevation);
                        cardView.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT


                        ));
//                    Margin to cardView

//                    cardView.setCardBackgroundColor(Color.WHITE);



                        cardView.setCardElevation(cardElevation);



                        cardView.setContentPadding(cardMargin, cardMargin, cardMargin, cardMargin);


//

                        LinearLayout parentLayout = new LinearLayout(myoreder.this);
                        parentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
                        parentLayout.setWeightSum(2); // Set weight sum of 2 for the parent layout

// Create the first inner LinearLayout
                        LinearLayout innerLayout1 = new LinearLayout(myoreder.this);
                        innerLayout1.setLayoutParams(new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1 // Set weight of 1 for the first inner layout
                        ));
                        innerLayout1.setOrientation(LinearLayout.VERTICAL);


// Set Button properties and content

// Add the TextViews and Button to the first inner LinearLayout
                        innerLayout1.addView(tvname);
                        innerLayout1.addView(tvamount);
                        innerLayout1.addView(tvabout);


                        innerLayout1.addView(b);

// Create the second inner LinearLayout
                        LinearLayout innerLayout2 = new LinearLayout(myoreder.this);
                        innerLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                1 // Set weight of 1 for the second inner layout
                        ));

                        innerLayout2.setOrientation(LinearLayout.VERTICAL);

// Create the ImageView

                        ImageView imageView = new ImageView(myoreder.this);
// Set ImageView properties and content
                        if(category.equals("PEN")){
                            imageView.setImageResource(R.drawable.pen);
                        }
                        else if(category.equals("REGISTER")){
                            imageView.setImageResource(R.drawable.register); // Set default image resource
                        }
                        else if(category.equals("COPY")){
                            imageView.setImageResource(R.drawable.copyy); // Set default image resource
                        }
                        else if(category.equals("PAPER")){
                            imageView.setImageResource(R.drawable.paper); // Set default image resource
                        }
                        else if(category.equals("MANUAL")){
                            imageView.setImageResource(R.drawable.manual); // Set default image resource
                        }
                        else {
                            imageView.setImageResource(R.drawable.images); // Set default image resource

                        }


// Add the ImageView to the second inner LinearLayout
                        innerLayout2.addView(imageView);

// Add both inner LinearLayouts to the parent LinearLayout

                        parentLayout.addView(innerLayout2);
                        parentLayout.addView(innerLayout1);



                        cardView.addView(parentLayout);
                        llcv.addView(cardView);


//                        pb.setVisibility(View.GONE);
                        // Do something with the child data


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(myoreder.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}