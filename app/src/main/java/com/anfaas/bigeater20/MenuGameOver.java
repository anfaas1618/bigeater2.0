package com.anfaas.bigeater20;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anfaas.bigeater20.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MenuGameOver extends Dialog  {
    Button ok;
    int score,highscore;
    TextView scoreText,highScoreText;
    public  Activity c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_game_over);
        ok=findViewById(R.id.okbtn);
        scoreText=findViewById(R.id.scoreLabelDialog);
        highScoreText=findViewById(R.id.highScoreLabelDialog);
        Log.i(TAG, "scorecheck: "+score+highscore);
        highScoreText.setText("HighScore:"+highscore);
        scoreText.setText(""+score);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sss", "damn 2 "+v.getId());
                dismiss();
            }
        });

    }

    public MenuGameOver(Activity a, int score, int highScore) {
        super(a);
        this.c=a;
        this.score=score;
        this.highscore=highScore;
    }


}
