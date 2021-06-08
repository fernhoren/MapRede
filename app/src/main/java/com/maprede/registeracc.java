package com.maprede;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maprede.ModelUser.UserModel;

import java.util.HashMap;


public class registeracc extends AppCompatActivity {

    private EditText edt_nome_register,edt_sobrenome_register,edt_email_register,edt_senha_register,edt_confirmar_senha_register;
    private CheckBox ckb_privacidade;
    private Button btn_login_register;
    private ProgressBar loginprogressBar_register;
    private FirebaseAuth mAuth;
    private Object e;
    //private static final String TAG = "sendemail";
    //private DatabaseReference reference;
    boolean Privatecheck;
    //private TextView policies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView t = findViewById(R.id.policies);
        t.setMovementMethod(LinkMovementMethod.getInstance());

        mAuth = FirebaseAuth.getInstance();

        edt_nome_register = findViewById(R.id.edt_nome_register);
        edt_sobrenome_register = findViewById(R.id.edt_sobrenome_register);
        edt_email_register = findViewById(R.id.edt_email_register);
        edt_senha_register = findViewById(R.id.edt_senha_register);
        edt_confirmar_senha_register = findViewById(R.id.edt_confirmar_senha_register);
        CheckBox ckb_mostrar_senha_register = findViewById(R.id.ckb_mostrar_senha_register);
        ckb_privacidade = findViewById(R.id.ckb_privacidade);
        Button btn_registrar_register = findViewById(R.id.btn_registrar_register);
        btn_login_register = findViewById(R.id.btn_login_register);

        loginprogressBar_register = findViewById(R.id.loginprogressBar_register);

        ckb_mostrar_senha_register.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edt_senha_register.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_confirmar_senha_register.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    edt_senha_register.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_confirmar_senha_register.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_registrar_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserModel userModel = new UserModel();

                userModel.setEmail(edt_email_register.getText().toString());
                userModel.setUsername(edt_nome_register.getText().toString());
                userModel.setSobrenome(edt_sobrenome_register.getText().toString());

                Privatecheck = false;
                String senha = edt_senha_register.getText().toString();
                String confirmarSenha = edt_confirmar_senha_register.getText().toString();


                if (!TextUtils.isEmpty(userModel.getUsername()) && !TextUtils.isEmpty(userModel.getSobrenome()) && !TextUtils.isEmpty(userModel.getEmail()) && !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(confirmarSenha))  {
                    if (senha.equals(confirmarSenha))  {
                        if (ckb_privacidade.isChecked()){
                            loginprogressBar_register.setVisibility(View.VISIBLE);
                            mAuth.createUserWithEmailAndPassword(userModel.getEmail(), senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Toast.makeText(registeracc.this,"Registro realizado. Confira seu email para verificar a conta",Toast.LENGTH_LONG).show();
                                                            userModel.setId(mAuth.getUid());
                                                            userModel.salvar();
                                                            edt_nome_register.setText("");
                                                            edt_sobrenome_register.setText("");
                                                            edt_email_register.setText("");
                                                            edt_senha_register.setText("");
                                                            edt_confirmar_senha_register.setText("");
                                                            Intent intent = new Intent(registeracc.this, signin.class);
                                                            startActivity(intent);
                                                            finish();

                                                        }else{
                                                            Toast.makeText(registeracc.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });

                                    } else {

                                        String error;
                                        try{
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e){
                                            error = "A senha deve conter no mínimo 6 caracteres";
                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            error = "E-mail inválido";
                                        }catch (FirebaseAuthUserCollisionException e){
                                            error = "Usuário já existe";
                                        }catch (Exception e){
                                            error = "Erro no cadastro";
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(registeracc.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    loginprogressBar_register.setVisibility(View.INVISIBLE);
                                }
                            });
                        }else{
                            Toast.makeText(registeracc.this,"checar as políticas de privacidade",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(registeracc.this,"a senhas não coincidem",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(registeracc.this,"todos os campos devem ser preenchidos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registeracc.this, signin.class);
                startActivity(intent);
                finish();
            }
        });

    }


}