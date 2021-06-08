package com.maprede.ModelUser;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class UserModel {
    private String id;
    private String username;
    private String sobrenome;
    private String email;
    private String emailProviderID;
    private String fbProviderID;
    private String googleProviderID;
    //public String privatecheck;



    public UserModel() {
    }

    public UserModel (String id,  String username, String sobrenome, String email, String googleProviderID) {
        this.id = id;
        this.username = username;
        this.sobrenome = sobrenome;
        this.email = email;
        this.emailProviderID = id;
        this.fbProviderID = id;
        this.googleProviderID = id;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailProviderID() {
        return emailProviderID;
    }

    public void setEmailProviderID(String emailProviderID) {
        this.emailProviderID = emailProviderID;
    }

    public String getFbProviderID() {
        return fbProviderID;
    }

    public void setFbProviderID(String fbProviderID) {
        this.fbProviderID = fbProviderID;
    }

    public void salvar(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(getId()).setValue(this);

    }

 /*   public void removeDB(DatabaseReference.CompletionListener completionListener) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(getId()).setValue(null, completionListener);
    }

    public void contextDataDB( Context context ){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(getId()).removeValue();
        reference.addListenerForSingleValueEvent( (ValueEventListener) context );
    }*/
}
