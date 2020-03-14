package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView password;
    Button nextPage;
    String nomorHpPindah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = findViewById(R.id.First_Name);
        lastName = findViewById(R.id.Last_Name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        noHp = findViewById(R.id.Phone_Number);

        nextPage = findViewById(R.id.Sign_Up);

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
                nomorHpPindah = "+62"+nomorHp.substring(1);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.toString().trim().equals("") || lastName.toString().trim().equals("") ||
                    noHp.toString().trim().equals("") || password.toString().trim().equals("") || email.toString().trim().equals("")){
                    Toast.makeText(SignUp.this, "Please fill all the ...", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent nextPageIntent = new Intent(SignUp.this,VerifyNumber.class);
                nextPageIntent.putExtra("noHp",nomorHpPindah);

                startActivity(nextPageIntent);
            }
        });


    }
}
