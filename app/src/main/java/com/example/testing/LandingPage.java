package com.example.testing;

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
import com.google.firebase.database.core.Tag;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class LandingPage extends AppCompatActivity {
    TextView pack;
    FirebaseUser user;
    FirebaseAuth mAuth;
    ImageView bali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        reduceChoreographerSkippedFramesWarningThreshold();
        pack = findViewById(R.id.textBag);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        bali = findViewById(R.id.imageBali);

        bali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGuidePage();
            }
        });
    }

    public void openGuidePage() {
        Intent intent = new Intent(this, guideProfile2.class);
        startActivity(intent);
    }
    private void ToastMaker(String text){
        Toast.makeText(LandingPage.this,text, Toast.LENGTH_SHORT).show();
    }

    private void reduceChoreographerSkippedFramesWarningThreshold() {
        if (BuildConfig.DEBUG) {
            Field field = null;
            try {
                field = Choreographer.class.getDeclaredField("SKIPPED_FRAME_WARNING_LIMIT");
                field.setAccessible(true);
                field.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                field.set(null, 5);
            } catch (Throwable e) {
            }
        }
    }
}
