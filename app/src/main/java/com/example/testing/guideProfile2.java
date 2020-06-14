package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class guideProfile2 extends AppCompatActivity {
    Button btn1,btn2,btn3,btn4,btn5;
    TextView tv1,tv2,tv3,guideName,guidePrice;
    String guide,location, firstName,lastName,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_profile2);

        tv1=(TextView) findViewById(R.id.aboutmetext);
        tv2=(TextView) findViewById(R.id.reviewtext);
        tv3=(TextView) findViewById(R.id.tripstext);
        guideName = findViewById(R.id.guideName);
        guidePrice = findViewById(R.id.guidePrice);

        btn1=(Button) findViewById(R.id.aboutmebutton);
        btn2=(Button) findViewById(R.id.reviewbutton);
        btn3=(Button) findViewById(R.id.tripsbutton);
        btn4=(Button) findViewById(R.id.dealbutton);
        btn5=(Button) findViewById(R.id.negotiatebutton);

        final View scrl = findViewById(R.id.readmorescroll);
        final View abt =  findViewById(R.id.About);

        guide = getIntent().getStringExtra("guideId");
        location = getIntent().getStringExtra("Location");

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tv1.setVisibility(view.VISIBLE);
                tv2.setVisibility(view.INVISIBLE);
                tv3.setVisibility(view.INVISIBLE);
                scrl.setVisibility(view.VISIBLE);
                abt.setVisibility(view.VISIBLE);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setVisibility(view.INVISIBLE);
                tv2.setVisibility(view.VISIBLE);
                tv3.setVisibility(view.INVISIBLE);
                scrl.setVisibility(view.GONE);
                abt.setVisibility(view.GONE);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setVisibility(view.INVISIBLE);
                tv2.setVisibility(view.INVISIBLE);
                tv3.setVisibility(view.VISIBLE);
                scrl.setVisibility(view.GONE);
                abt.setVisibility(view.GONE);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDealActivity();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNegotiateActivity();
            }
        });

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Destination").child(location).child(guide);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firstName = dataSnapshot.child("FirstName").getValue().toString();
                lastName = dataSnapshot.child("LastName").getValue().toString();
                guideName.setText(firstName + " " + lastName);

                price = dataSnapshot.child("Price").getValue().toString();
                guidePrice.setText("Rp." + price + ",00");

                String aboutMe = dataSnapshot.child("AboutMe").getValue().toString();
                tv1.setText(aboutMe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openDealActivity() {
        Intent intent = new Intent(this, Deal.class);
        intent.putExtra("name",firstName + " " + lastName);
        intent.putExtra("price",price);
        startActivity(intent);
    }

    public void openNegotiateActivity() {
        Intent intent = new Intent(this, Negotiate.class);
        intent.putExtra("price",price);
        startActivity(intent);
    }

    private void ToastMaker(String text){
        Toast.makeText(guideProfile2.this,text, Toast.LENGTH_SHORT).show();
    }
}
