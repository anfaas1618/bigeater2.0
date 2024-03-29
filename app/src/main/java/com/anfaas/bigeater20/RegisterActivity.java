package com.anfaas.bigeater20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    String nameS,emailS,passS;
TextView name,email,password;
Button loginBtn,guestBtn;
ProgressBar signUpProgressBar;
FirebaseDatabase database= FirebaseDatabase.getInstance();
DatabaseReference myRef=database.getReference();
public  static FirebaseAuth myAuth;
String uID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);
        final SharedPreferences login = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);

        int loginInt = login.getInt("LOGIN", 0);
        if (loginInt==1)
            startActivity(new Intent(this,start.class));
        name=findViewById(R.id.txtname);
        email=findViewById(R.id.txtemail);
        password=findViewById(R.id.txtpwd);
        loginBtn=findViewById(R.id.btnLogin);
        guestBtn=findViewById(R.id.guest);
        signUpProgressBar=findViewById(R.id.signUpProgressBar);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setVisibility(View.GONE);
                signUpProgressBar.setVisibility(View.VISIBLE);
                final String nameS,emailS,passS;
                nameS=name.getText().toString();
                emailS=email.getText().toString();
                passS=password.getText().toString();
                myAuth =FirebaseAuth.getInstance();
                myAuth.createUserWithEmailAndPassword(emailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("HIGH_SCORE", 0);
                            editor.commit();

                            Toast.makeText(RegisterActivity.this, "registerd", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser;
                           firebaseUser= myAuth.getCurrentUser();

                            uID=firebaseUser.getUid();
                            UserProfileChangeRequest request=new UserProfileChangeRequest.Builder().setDisplayName(nameS).
                                                                  build();
                          firebaseUser.updateProfile(request);
                            addUser();
                            login();

                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"check your email and password" , Toast.LENGTH_LONG).show();//todo add a proper error dialog
                            loginBtn.setVisibility(View.VISIBLE);
                            signUpProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

        });
        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.ISGUEST=true;
                startActivity(new Intent(RegisterActivity.this,start.class));
            }
        });
    }
    public  void addUser()
    {

        nameS=name.getText().toString();
        emailS=email.getText().toString();
        passS=password.getText().toString();
        User user=new User(nameS,emailS,passS,uID,0);
        myRef.child(uID).setValue(user);
    }
    public void test(View view)
    {
          startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
    public void login()
    {
        myAuth.signInWithEmailAndPassword(emailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {signUpProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "login sucess", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser=myAuth.getCurrentUser();

                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                    Intent intent = new Intent(getBaseContext(), SetImage.class);

                    SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
                    SharedPreferences login = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                    SharedPreferences name_saved = getSharedPreferences("NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit_name=name_saved.edit();
                    edit_name.putString("NAME",currentFirebaseUser.getDisplayName());
                    edit_name.commit();
                    SharedPreferences.Editor editor1 = login.edit();
                    editor1.putInt("LOGIN", 1);
                    editor1.commit();
                    SharedPreferences UID = getSharedPreferences("UID", Context.MODE_PRIVATE);
                    SharedPreferences.Editor EDIT = UID.edit();
                    EDIT.putString("UID", currentFirebaseUser.getUid());
                    EDIT.commit();
                    int highScore = settings.getInt("HIGH_SCORE", 0);

                    intent.putExtra("EXTRA_SESSION_ID", currentFirebaseUser.getUid());
                    Helper.ISGUEST=false;
                    startActivity(intent);
                }
            }
        });
    }
}
