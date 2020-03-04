package com.anfaas.bigeater20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextView email,password;
    public  static String IDID;
    public  static String NAME;
    CardView login;
   public static FirebaseAuth myAuth;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myREf=database.getReference();
     User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.btnLogin);
        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPwd);
           myAuth=FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS=email.getText().toString();
                final String passwordS=password.getText().toString();
              //  String emailS=email.getText().toString();
                myAuth.signInWithEmailAndPassword(emailS,passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "login sucess", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser=myAuth.getCurrentUser();
                            user=new User(firebaseUser.getDisplayName().toString(),
                                           firebaseUser.getEmail().toString(),
                                            passwordS,
                                             firebaseUser.getUid(),0);
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            Intent intent = new Intent(getBaseContext(), start.class);
                            IDID=currentFirebaseUser.getUid();
                            NAME=currentFirebaseUser.getDisplayName();
                            SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
                            SharedPreferences login = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                            SharedPreferences name_saved = getSharedPreferences("NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit_name=name_saved.edit();
                            edit_name.putString("NAME",NAME);
                            edit_name.commit();
                            SharedPreferences.Editor editor1 = login.edit();
                            editor1.putInt("LOGIN", 1);
                            editor1.commit();
                            SharedPreferences UID = getSharedPreferences("UID", Context.MODE_PRIVATE);
                            SharedPreferences.Editor EDIT = UID.edit();
                            EDIT.putString("UID", user.Uid);
                            EDIT.commit();
                            int highScore = settings.getInt("HIGH_SCORE", 0);
                            if (highScore==0) {
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putInt("HIGH_SCORE", user.score);
                                editor.commit();

                            }
                            intent.putExtra("EXTRA_SESSION_ID", currentFirebaseUser.getUid());
                            startActivity(intent);


                        }
                    }
                });
            }
        });
    }
}
