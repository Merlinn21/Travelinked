package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class LandingPage extends AppCompatActivity {
    TextView pack;
    FirebaseAuth mAuth;
    ImageView bali;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        pack = findViewById(R.id.textBag);
        bali = findViewById(R.id.imageBali);


        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Destination").child("Bali").child("Guide");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String test = dataSnapshot.child("FirstName").getValue().toString();
                ToastMaker(test);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGuidePage();
            }
        });
    }

    public void openGuidePage() {
        Intent intent = new Intent(this, GuideList.class);
        startActivity(intent);
    }
    private void ToastMaker(String text){
        Toast.makeText(LandingPage.this,text, Toast.LENGTH_SHORT).show();
    }


}
