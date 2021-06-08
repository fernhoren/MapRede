package com.maprede;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maprede.ModelUser.UserModel;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


public class UserIniActivity extends AppCompatActivity implements DatabaseReference.CompletionListener{

    //private AuthStateListener authStateListener;
    private AppBarConfiguration mAppBarConfiguration;
    //private FirebaseAuth mAuth;
    //private FirebaseUser mUser;
    private DatabaseReference reference;
   // private TextView edt_nome, edt_email;
   // private Button btn_remove;
    private ProgressBar loginprogressBar_remove;
    //private String TAG;
    //private String usuario;
    //private String userId;
    private UserModel userModel;



    OvershootInterpolator interpolator = new OvershootInterpolator();



    public void onFragmentViewCreated(View view) {

        loginprogressBar_remove = findViewById(R.id.loginprogressBar_remove);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Button btn_remove = findViewById(R.id.btn_remove);
        TextView usernome =  findViewById(R.id.edt_nome);
        assert user != null;
        usernome.setText(user.getDisplayName());
        TextView useremail =  findViewById(R.id.edt_email);
        useremail.setText(user.getEmail());
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteuser(usernome.toString(),useremail.toString());
            }
        });
    }

    private void deleteuser(String usernome, String useremail){
        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase reference = FirebaseDatabase.getInstance();
        final DatabaseReference idref = reference.getReference("usuario").child(mUser.getUid());
        AuthCredential credential = EmailAuthProvider.getCredential(usernome,useremail);

        if(mUser != null){
            mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UserIniActivity.this,"Conta deletada",Toast.LENGTH_SHORT).show();
                                signin_ac();
                            }else {
                                Toast.makeText(UserIniActivity.this,"A conta não foi apagada",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ini);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseauth = getInstance();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView textView1 =  headerview.findViewById(R.id.textView1);
        textView1.setText(firebaseauth.getCurrentUser().getDisplayName());
        TextView textView2 = headerview.findViewById(R.id.textView2);
        textView2.setText(firebaseauth.getCurrentUser().getEmail());    //email id of logged in user.

       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_instruction, R.id.nav_novo_proj, R.id.nav_salvar_proj,R.id.map_user,R.id.remove_user)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int menuId = destination.getId();
                switch (menuId){
                    case R.id.map_user:
                        Toast.makeText(UserIniActivity.this, "Projeto em andamento",Toast.LENGTH_LONG).show();
                        //b_btn_pr_atual.show();
                        break;
                    default:
                        //b_btn_pr_atual.hide();
                        break;
                }
            }
        });
    }





        /** final ImageView img1 = (ImageView) headerview.findViewById(R.id.imageView);
         Glide.with(getApplicationContext())
         .load(firebaseauth.getCurrentUser().getPhotoUrl()).asBitmap().atMost().error(R.id.imageView)   //asbitmap after load always.
         .into(new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        img1.setImageBitmap(resource);
        }
        });**/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_ini, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this,"Item 1 selecionado",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.confirmar_logout:
                getInstance().signOut();
                LoginManager.getInstance().logOut();

                Intent intent = new Intent(UserIniActivity.this, signin.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
        Toast.makeText(UserIniActivity.this, "Conta removida", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void signin_ac() {
        Intent intent = new Intent(UserIniActivity.this, signin.class);
        startActivity(intent);
        finish();
    }
}