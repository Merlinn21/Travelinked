package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyNumber extends SignUp {

    String verficationCode;
    Button verifyButton;
    TextView verificationTextField;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String manualVerify;
    TextView resendCode;
    ImageView prevPage;

    DatabaseReference reff;
    User user;
    String noHp;
    String fName;
    String LName;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        verifyButton = findViewById(R.id.Continue_Landing_Page);
        mAuth = FirebaseAuth.getInstance();
        verificationTextField = findViewById(R.id.Input_Verification_Number);
        resendCode = findViewById(R.id.Resend_Code);
        prevPage = findViewById(R.id.back_arrow);

        user = new User();
        reff = FirebaseDatabase.getInstance().getReference().child("User");

        noHp = getIntent().getStringExtra("noHp");
        fName = getIntent().getStringExtra("Fname");
        LName = getIntent().getStringExtra("Lname");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("pass");
        ToastMaker(email);
        sendCode(noHp);


        verificationTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String verCode = verificationTextField.getText().toString().trim();
                if(verCode.length() == 6){
                    verifyButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verificationTextField.getText().toString().trim();
                verifyCode(code);
            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode(manualVerify);
            }
        });

        prevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prevPageIntent = new Intent(VerifyNumber.this,SignUp.class);
                startActivity(prevPageIntent);
            }
        });
    }


    private void sendCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verficationCode = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        String code = phoneAuthCredential.getSmsCode();
                        manualVerify = code;
                        if(code!=null){
                            verifyCode(code);
                        }
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        ToastMaker("gagal");
                    }
                }
        );
    }


    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationCode, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    user.setFirst_name(fName);
                    user.setLast_name(LName);
                    user.setEmail(email);
                    user.setGuide(false);
                    user.setNoHp(noHp);

                    reff.push().setValue(user);

                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().
                            setDisplayName(fName).build();
                    firebaseUser.updateProfile(profileUpdate);

                    firebaseUser.updateEmail(email);
                    firebaseUser.updatePassword(password);

                    Intent intentLandingPage = new Intent(VerifyNumber.this,LandingPage.class);
                    startActivity(intentLandingPage);
                }
            }
        });
    }

    private void ToastMaker(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void createAcc(String email, String pass){
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else{
                            ToastMaker("Failed to create account");
                        }
                    }
                });
    }

}
