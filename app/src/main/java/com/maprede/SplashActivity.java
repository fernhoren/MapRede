package com.maprede;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(SplashActivity.this,signin.class);
                //startActivity(intent);
                if (firebaseUser != null){
                    //(FirebaseAuth.getInstance().getCurrentUser() == null){
                    startActivity(new Intent(SplashActivity.this, UserIniActivity.class));
                    finish();



                }
                else{
                    startActivity(new Intent(SplashActivity.this,signin.class));
                    finish();
                }
            }
        }, 5000);
    }


}