package com.anfaas.bigeater20;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Timer;
import java.util.TimerTask;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class main extends AppCompatActivity {

    Intent intent;
    public final static String USERNAME = "com.example.myfirstapp.MESSAGE";
    public final static String EMAIL = "com.example.myfirstapp.EMAIL";
    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;


    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;


    private int boxY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;


    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;


    public static boolean isBlackHit=false;
    boolean adseen=false;

   public boolean isdone=true;
  private boolean isRewardCollected=false;
    private int score = 0;

    private  LinearLayout layout;


    public RewardedAd rewardedAd;

    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;



    private boolean action_flg = false;
    private boolean start_flg = false;
    public static   int speed=30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        loadad();



        layout=findViewById(R.id.layoutmainlinear);

        sound = new SoundPlayer(this);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        box = (ImageView) findViewById(R.id.box);
        orange = (ImageView) findViewById(R.id.orange);
        pink = (ImageView) findViewById(R.id.pink);
        black = (ImageView) findViewById(R.id.black);


        // Get screen size.
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;



        boxSpeed = Math.round(screenHeight / 60);
        orangeSpeed = Math.round(screenWidth / 60);
        pinkSpeed = Math.round(screenWidth / 36);
        blackSpeed = Math.round(screenWidth / 45);




        // Move to out of screen.
        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        black.setX(-80);
        black.setY(-80);

        scoreLabel.setText("Score : 0");


    }


    public void changePos() {


        if (score>10&&isdone==true) {

    layout.setAlpha(0.5f);
    layout.animate()
            .alpha(0f)
            .setDuration(1000)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    layout.setVisibility(View.INVISIBLE);
                }
            });

   layout.setBackground(ContextCompat.getDrawable(main.this, R.drawable.good_morning_img));
    layout.setAlpha(0f);
    layout.setVisibility(View.VISIBLE);
    layout.animate()
            .alpha(1)
            .setDuration(1000)
            .setListener(null);
    isdone=false;
}     hitCheck();
        orangeX -= orangeSpeed;
        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        blackX -= blackSpeed;
        if (blackX < 0) {
            blackX = screenWidth + 10;
            blackY = (int) Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);



        pinkX -= pinkSpeed;
        if (pinkX < 0) {
            pinkX = screenWidth + 5000;
            pinkY = (int) Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);



        if (action_flg == true) {

            boxY -= boxSpeed;

        } else {

            boxY += boxSpeed;
        }


        if (boxY < 0) boxY = 0;

        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);

        scoreLabel.setText("Score : " + score);

    }


    public void hitCheck() {

        if (MenuGameOver.isRewardCollected)
        {

        }
        int orangeCenterX = orangeX + orange.getWidth() / 2;
        int orangeCenterY = orangeY + orange.getHeight() / 2;


        if (0 <= orangeCenterX && orangeCenterX <= boxSize &&
                boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {

            score += 10;
            orangeX = -10;
            sound.playHitSound();

        }

        int pinkCenterX = pinkX + pink.getWidth() / 2;
        int pinkCenterY = pinkY + pink.getHeight() / 2;

        if (0 <= pinkCenterX && pinkCenterX <= boxSize &&
                boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {

            score += 30;
            pinkX = -10;
            sound.playHitSound();

        }


        int blackCenterX = blackX + black.getWidth() / 2;
        int blackCenterY = blackY + black.getHeight() / 2;

        if (0 <= blackCenterX && blackCenterX <= boxSize &&
                boxY <= blackCenterY && blackCenterY <= boxY + boxSize&& !isBlackHit) {
            isBlackHit=true;
            sound.playOverSound();
            blackX=-80;
            blackY=-80;
            timer.cancel();
            timer=null;
            timer =new Timer();
            speed =300000;
            StartTimer();
             final MenuGameOver gameOver = new MenuGameOver(main.this,score,score,main.this);
             gameOver.show();
         if (isRewardCollected) {
             gameOver.watchRewardAd.setVisibility(View.GONE);
             Intent intent = new Intent(getApplicationContext(), LeaderBoardActivity.class);
             intent.putExtra("SCORE", score);
             startActivity(intent);
             finish();
         }
            gameOver.tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LeaderBoardActivity.class);
                    intent.putExtra("SCORE", score);
                    startActivity(intent);
                    finish();
                }
            });
            gameOver.watchRewardAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Log.i("sss", "damn 2 "+v.getId());
                    gameOver.dismiss();
                    showad();
                }
            });
//            Intent intent = new Intent(getApplicationContext(), LeaderBoardActivity.class);
//            intent.putExtra("SCORE", score);
//            startActivity(intent);
        }
    }
    public boolean onTouchEvent(MotionEvent me) {
               if (adseen)
               {  StartTimer();
               adseen=false;
               }
        if (start_flg == false) {

            start_flg = true;

            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            boxY = (int)box.getY();

            boxSize = box.getHeight();


            startLabel.setVisibility(View.GONE);
            StartTimer();




        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;

            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }

        return true;
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
void StartTimer ()
{
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    changePos();
                }
            });
        }
    }, 0, speed);
}
void  loadad(){
        rewardedAd =new RewardedAd(main.this,"ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback loadCallback =new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
                //todo add here watch ad
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(),loadCallback);

    }
    void showad()
    {

        if (rewardedAd.isLoaded())
        {
            Activity act= this;
            RewardedAdCallback rewardedAdCallback=new RewardedAdCallback() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Log.i(TAG, "onUserEarnedReward: "+"got the ad");

                    isRewardCollected=true;
                    MenuGameOver.isAdLoaded=true;
                   isBlackHit=false;
                    timer.cancel();
                    timer=null;
                    speed=30;
                   timer=new Timer();


                 adseen=true;

                }
            };
            rewardedAd.show(this,rewardedAdCallback);

        }
        else Log.i(TAG, "showad: "+"nope no ad");
        isBlackHit=false;
    }
}