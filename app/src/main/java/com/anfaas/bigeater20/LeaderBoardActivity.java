package com.anfaas.bigeater20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {
    List<PlayerScore> playerScoreList;
 //   String uid;
 int scoree=0;
    ListView listView;
   FirebaseDatabase database=FirebaseDatabase.getInstance();
   DatabaseReference myRef=database.getReference("scores");
   LoginActivity activity=new LoginActivity();
    User user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);

     scoree = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(scoree + "");
        SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if (scoree > highScore) {
            highScoreLabel.setText("High Score : " + scoree);

            // Update High Score
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", scoree);
            editor.commit();

        } else {
            highScoreLabel.setText("High Score : " + highScore);
        }

      FirebaseAuth auth= LoginActivity.myAuth;
        FirebaseUser userAuth=auth.getCurrentUser();
        FirebaseDatabase userDatabase=FirebaseDatabase.getInstance();
        DatabaseReference userRef=userDatabase.getReference(userAuth.getUid());
          userRef.child("score").setValue(highScore);


        listView=findViewById(R.id.list);
        playerScoreList=new ArrayList<PlayerScore>();
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

                      if (scoree>highScore) {
                           playerScore=new PlayerScore(LoginActivity.NAME,scoree,user1.Uid);
                      }
                      else
                          playerScore=new PlayerScore(LoginActivity.NAME,highScore,user1.Uid);
                      FirebaseDatabase database=FirebaseDatabase.getInstance();
//                      Log.i("am",user1.Nane);
                      DatabaseReference scoreSet=database.getReference("scores");

                      scoreSet.child(LoginActivity.IDID).setValue(playerScore);

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
              {     PlayerScore fruits = snap.getValue(PlayerScore.class);
                  playerScoreList.add(fruits);
              }
              ScoreAdapter fruitsAdapter=new ScoreAdapter(LeaderBoardActivity.this,playerScoreList);
              listView.setAdapter(fruitsAdapter);



          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

    }
    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), start.class));
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
