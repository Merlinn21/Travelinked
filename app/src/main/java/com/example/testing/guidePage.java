package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class guidePage extends AppCompatActivity {
    Button btn1,btn2,btn3,btn4,btn5;
    TextView tv1,tv2,tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        tv1=(TextView) findViewById(R.id.aboutmetext);
        tv2=(TextView) findViewById(R.id.reviewtext);
        tv3=(TextView) findViewById(R.id.tripstext);
        btn1=(Button) findViewById(R.id.aboutmebutton);
        btn2=(Button) findViewById(R.id.reviewbutton);
        btn3=(Button) findViewById(R.id.tripsbutton);
        btn4=(Button) findViewById(R.id.dealbutton);
        btn5=(Button) findViewById(R.id.negotiatebutton);
        final View scrl = findViewById(R.id.readmorescroll);
        final View abt =  findViewById(R.id.About);

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
    }

    public void openDealActivity() {
        Intent intent = new Intent(this, dealActivity.class);
        startActivity(intent);
    }

    public void openNegotiateActivity() {
        Intent intent = new Intent(this, negotiateActivity.class);
        startActivity(intent);
    }
}