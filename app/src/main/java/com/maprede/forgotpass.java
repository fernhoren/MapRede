package com.maprede;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class forgotpass extends AppCompatActivity {

    //private DatabaseReference reference;

    ProgressBar progressBar_rec;
    EditText edt_email_rec;
    Button btn_recuperar_senha;
    Button btn_login_back;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        progressBar_rec = findViewById(R.id.progressBar_rec);
        edt_email_rec = findViewById(R.id.edt_email_rec);
        btn_recuperar_senha = findViewById(R.id.btn_recuperar_senha);
        btn_login_back = findViewById(R.id.btn_login_back);

        mAuth = FirebaseAuth.getInstance();

        btn_recuperar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginEmail = edt_email_rec.getText().toString();

                if (!TextUtils.isEmpty(loginEmail)){
                    progressBar_rec.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(loginEmail.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar_rec.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                Toast.makeText(forgotpass.this,"Senha enviada ao email",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(forgotpass.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }


            }
        });

        btn_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgotpass.this, signin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}