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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import static androidx.constraintlayout.widget.Constraints.TAG;
public class MenuGameOver extends Dialog  {
    Button ok;
     RewardedAd rewardedAd;
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

        ok=findViewById(R.id.okbtn);
        scoreText=findViewById(R.id.scoreLabelDialog);
        highScoreText=findViewById(R.id.highScoreLabelDialog);
        Log.i(TAG, "scorecheck: "+score+highscore);
        highScoreText.setText("HighScore:"+highscore);
        scoreText.setText(""+score);





    }
    public MenuGameOver(Activity a, int score, int highScore,Activity activity) {
        super(a);
        this.c=a;
        this.score=score;
        this.highscore=highScore;
        this.activity=activity;

    }

void LoadAdInterstetial()
{

}
}
