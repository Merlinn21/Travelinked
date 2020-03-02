package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LandingPage extends AppCompatActivity {

    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastMaker("Logout");
                FirebaseAuth.getInstance().signOut();
            }
        });
    }
    private void ToastMaker(String text){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
    }
}
