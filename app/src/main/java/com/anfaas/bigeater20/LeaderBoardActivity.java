package com.anfaas.bigeater20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {
    private static final String TAG = "LeaderBoardActivity";
    List<PlayerScore> playerScoreList;
 //   String uid;
 int scoree=0;
 Button avatarchoosebtn;
    ListView listView;
    String uid_saved;
    String name_Saved;
         String  imageid;
    List <UserImage> userImageslist;
    FirebaseUser userAuth;
   FirebaseDatabase database=FirebaseDatabase.getInstance();
   FirebaseDatabase userimage=FirebaseDatabase.getInstance();
   DatabaseReference imageref=userimage.getReference("uploads");
   DatabaseReference myRef=database.getReference("scores");
   LoginActivity activity=new LoginActivity();
    User user1;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences UID= getSharedPreferences("UID", Context.MODE_PRIVATE);
        uid_saved = UID.getString("UID", "0");
        SharedPreferences namePref=getSharedPreferences("NAME",Context.MODE_PRIVATE);
        name_Saved=namePref.getString("NAME","user");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        avatarchoosebtn=findViewById(R.id.avatarchange);
        avatarchoosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //not working
                startActivity( new Intent(LeaderBoardActivity.this,SetImage.class));
            }
        });
        final SharedPreferences imageurll = getSharedPreferences("IMAGEURL", Context.MODE_PRIVATE);
         final   SharedPreferences.Editor aeditor=   imageurll.edit();
        Log.i(TAG, "onCreate: error here"+uid_saved);
        imageref.child(uid_saved).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                imageid=    dataSnapshot.child("image_reference").getValue(String.class);
                Log.i(TAG, "onDataChange: "+imageid);
                aeditor.putString("IMAGEURL",imageid);
                aeditor.commit();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            final    String IMAGEurl = imageurll.getString("IMAGEURL", "null");
     scoree = getIntent().getIntExtra("SCORE", 0);
        SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if (scoree > highScore) {

           //  MenuGameOver gameOver= new MenuGameOver(LeaderBoardActivity.this, scoree,scoree,LeaderBoardActivity.this);
           // gameOver.show();
            // Update High Score
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", scoree);
            editor.commit();

        } else {
      //       MenuGameOver gameOver= new MenuGameOver(LeaderBoardActivity.this, scoree,highScore,LeaderBoardActivity.this);
       //     gameOver.show();
        }
      FirebaseAuth auth= LoginActivity.myAuth;
        if (uid_saved=="0") {
try {
     userAuth = auth.getCurrentUser();
    FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userRef = userDatabase.getReference(userAuth.getUid());
    userRef.child("score").setValue(highScore);

}
catch (Exception e)
{
    Log.i("error",e.toString());
}

        }
else {


    FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userRef = userDatabase.getReference(uid_saved);
            userRef.child("score").setValue(highScore);
}
        listView=findViewById(R.id.list);
        playerScoreList=new ArrayList<PlayerScore>();
        userImageslist =new ArrayList<>();
 //  String Uid=     activity.user.Uid;


           FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
           final DatabaseReference user=firebaseDatabase.getReference();

              user.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      PlayerScore playerScore;
                       user1=dataSnapshot.getValue(User.class);
                      SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
                      int highScore = settings.getInt("HIGH_SCORE", 0);
                      if (uid_saved=="0") {
                          if (scoree > highScore) {
                              playerScore = new PlayerScore(LoginActivity.NAME, scoree, user1.Uid,imageid);

                          } else
                              playerScore = new PlayerScore(LoginActivity.NAME, highScore, user1.Uid,imageid);
                      }
                      else
                      {
                          if (scoree > highScore) {
                              playerScore = new PlayerScore(name_Saved, scoree, user1.Uid,imageid);
                          } else {
                              playerScore = new PlayerScore(name_Saved, highScore, user1.Uid, imageid);
                          }
                      }
                      FirebaseDatabase database=FirebaseDatabase.getInstance();
//                      Log.i("am",user1.Nane);
                      DatabaseReference scoreSet=database.getReference("scores");
                      if (uid_saved=="0") {
                          try {
                              scoreSet.child(LoginActivity.IDID).setValue(playerScore);
                          } catch (Exception e) {

                          }

                      }

                      else   scoreSet.child(uid_saved).setValue(playerScore);

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {
                  }
              });
      myRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              playerScoreList.clear();
              for (DataSnapshot snap:dataSnapshot.getChildren())
              {     PlayerScore scores = snap.getValue(PlayerScore.class);
                  playerScoreList.add(scores);
              }


              Collections.sort(playerScoreList);
              Collections.reverse(playerScoreList);
              ScoreAdapter fruitsAdapter=new ScoreAdapter(LeaderBoardActivity.this,playerScoreList);

              listView.setAdapter(fruitsAdapter);



          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

    }
    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), main.class));
    }
    public   void name(View view)
    {


    }



    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }


}
