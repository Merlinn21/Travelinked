package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyNumber extends SignUp {

    String verficationCode;
    Button verifyButton;
    TextView verificationTextField;
    FirebaseAuth mAuth;
    String manualVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        verifyButton = findViewById(R.id.Continue_Landing_Page);
        mAuth = FirebaseAuth.getInstance();
        verificationTextField = findViewById(R.id.Input_Verification_Number);


        String noHp = getIntent().getStringExtra("noHp");
        ToastMaker(noHp);
        sendCode(noHp);


        verificationTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String verCode = verificationTextField.getText().toString().trim();
                ToastMaker("onTextChanged");
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
                        manualVerify = code;
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
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intentLandingPage = new Intent(VerifyNumber.this,LandingPage.class);
                    intentLandingPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    ToastMaker("signn");
                    startActivity(intentLandingPage);
                }
            }
        });
    }

    private void ToastMaker(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
