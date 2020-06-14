package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class guideProfile2 extends AppCompatActivity {
    TextView guideName;
    TextView price;
    TextView aboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_page);

        guideName = findViewById(R.id.guideName);
        price = findViewById(R.id.guidePrice);
        aboutMe = findViewById(R.id.aboutmetext);
    }
}
