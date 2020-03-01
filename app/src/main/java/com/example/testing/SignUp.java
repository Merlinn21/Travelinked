package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends MainActivity {

    ImageButton backToStart;
    TextView noHp;
    Button nextPage;
    String nomorHpPindah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page_layout);

        backToStart = findViewById(R.id.back_To_Start);
        noHp = findViewById(R.id.no_hp);
        nextPage = findViewById(R.id.next_Page);

        noHp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nomorHp = noHp.getText().toString().trim();

                if(nomorHp.length()>10 && nomorHp.length()<12  ){
                    nextPage.setEnabled(true);
                }
                nomorHpPindah = "+62"+nomorHp;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(SignUp.this,VerifyNumber.class);
                nextPageIntent.putExtra("noHp",nomorHpPindah);

                startActivity(nextPageIntent);
            }
        });

        backToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,MainActivity.class));
            }
        });


    }
}
