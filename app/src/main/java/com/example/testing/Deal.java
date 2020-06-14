package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Deal extends AppCompatActivity {

    TextView guideName, priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);

        guideName = findViewById(R.id.guideName);
        priceText = findViewById(R.id.price);

        String guide = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");

        guideName.setText(guide);
        priceText.setText("Rp." + price + "/day for each person");
    }
}
