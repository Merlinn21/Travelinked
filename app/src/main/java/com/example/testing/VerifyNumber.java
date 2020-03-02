package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyNumber extends SignUp {

    String verficationCode;
    Button verifyButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_number);

        verifyButton = findViewById(R.id.verify_button);
        mAuth = FirebaseAuth.getInstance();


        String noHp = getIntent().getStringExtra("noHp");
        ToastMaker(noHp);
        sendCode(noHp);


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerifyNumber.this,LandingPage.class));
                ToastMaker("Clicked");
            }
        });
    }

    private void sendCode(String number){
        ToastMaker("sendCode");
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
                        ToastMaker("onCodeSent");
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        String code = phoneAuthCredential.getSmsCode();
                        ToastMaker("onVerificationCompleted");
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
        ToastMaker("verifyCode");
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intentLandingPage = new Intent(VerifyNumber.this,LandingPage.class);
                    //intentLandingPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    ToastMaker("signIn");
                    startActivity(intentLandingPage);
                }
            }
        });
    }

    private void ToastMaker(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
