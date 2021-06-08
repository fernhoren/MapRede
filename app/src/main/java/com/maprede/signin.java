package com.maprede;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class signin extends AppCompatActivity {

    private EditText edt_email,edt_senha;
    private FirebaseAuth mAuth;
    private ProgressBar loginprogressBar;
    private CallbackManager mCallbackManager;
    private static final String TAG = "FacebookAuthentication";
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        TextView t = (TextView) findViewById(R.id.policies);
        t.setMovementMethod(LinkMovementMethod.getInstance());

        mCallbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();
        edt_email = findViewById(R.id.edt_email);
        edt_senha = findViewById(R.id.edt_senha);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_recuperar = findViewById(R.id.btn_recuperar);
        LoginButton login_FB = findViewById(R.id.login_FB);
        login_FB.setReadPermissions(Arrays.asList("email","public_profile"));
        Button btn_registrar = findViewById(R.id.btn_registrar);
        TextView policies = findViewById(R.id.policies);
        loginprogressBar = findViewById(R.id.loginprogressBar);
        CheckBox ckb_mostrar_senha = findViewById(R.id.ckb_mostrar_senha);
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        login_FB.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String email = object.getString("email");
                            mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if (task.getResult().getSignInMethods().isEmpty()){
                                        handleFacebookAccessToken(loginResult.getAccessToken());
                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(signin.this);
                                        builder.setCancelable(true);
                                        builder.setTitle("Não registrado");
                                        builder.setMessage("Por favor registrar sua conta");
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();

                                    }
                                }
                            });
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,profile_user");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginEmail = edt_email.getText().toString();
                String loginsenha = edt_senha.getText().toString();

                if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginsenha)){
                    loginprogressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(loginEmail,loginsenha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        if (mAuth.getCurrentUser().isEmailVerified()){
                                            abrirActividade();
                                        }else{
                                            Toast.makeText(signin.this, "Confira seu email", Toast.LENGTH_SHORT).show();
                                            loginprogressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(signin.this, ""+error, Toast.LENGTH_SHORT).show();
                                        loginprogressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                }else{
                    Toast.makeText(signin.this,"todos os campos devem ser preenchidos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signin.this, registeracc.class);
                startActivity(intent);
                finish();
            }
        });

        btn_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signin.this,forgotpass.class));
            }
        });

        ckb_mostrar_senha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edt_senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    edt_senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()){
                    abrirActividade();
                }
            }
        };

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    mAuth.signOut();
                }
            }
        };


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser rUser = task.getResult().getUser();
                            assert  rUser != null;
                            final String userId = rUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("usuarios").child(userId);
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username", rUser.getDisplayName());
                            hashMap.put("email", rUser.getEmail());
                            for (UserInfo user: FirebaseAuth.getInstance().getCurrentUser().getProviderData()){
                                if(user.getProviderId().equals("facebook.com")){
                                    hashMap.put("fbProviderID", user.getProviderId());
                                    //hashMap.put("emailProviderID","default");
                                    //hashMap.put("googleProviderID","default");
                                }else if (user.getProviderId().equals("edt_senha")){
                                    //hashMap.put("fbProviderID", "default");
                                    hashMap.put("emailProviderID",user.getProviderId());
                                    //hashMap.put("googleProviderID","default");
                                }
                            }
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        abrirActividade();
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            AlertDialog.Builder builder = new AlertDialog.Builder(signin.this);
                            builder.setCancelable(true);
                            builder.setTitle("Não registrado");
                            builder.setMessage("Por favor registrar sua conta");
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            Toast.makeText(signin.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signin.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirActividade() {
        Intent intent = new Intent(signin.this, UserIniActivity.class);
        startActivity(intent);
        finish();
    }
}