package com.anfaas.bigeater20;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import static androidx.constraintlayout.widget.Constraints.TAG;
public class MenuGameOver extends Dialog  {
    Button watchRewardAd,tryAgainBtn;

   public   static  boolean isRewardCollected=false;
     Activity activity;
    int score,highscore;
    TextView scoreText,highScoreText;

  public static   boolean isAdLoaded=false;
    public  Activity c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_game_over);
        tryAgainBtn =findViewById(R.id.try_again_btn);
        watchRewardAd =findViewById(R.id.okbtn);
        scoreText=findViewById(R.id.scoreLabelDialog);
       // highScoreText=findViewById(R.id.highScoreLabelDialog);
        Log.i(TAG, "scorecheck: "+score+highscore);
//        highScoreText.setText("HighScore:"+highscore);
        scoreText.setText(""+score);





    }
    public MenuGameOver(Activity a, int score, int highScore,Activity activity) {
        super(a);
        this.c=a;
        this.score=score;
        this.highscore=highScore;
        this.activity=activity;
        this.setCanceledOnTouchOutside(false);

    }

void LoadAdInterstetial()
{

}

    @Override
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

