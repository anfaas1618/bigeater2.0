package com.anfaas.bigeater20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class start extends AppCompatActivity {

    private static final String TAG = "start";
    public InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

       displayInterstitial();

    }


    public void displayInterstitial() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                //TODO add here on sdk loaded
                Log.i(TAG, "onInitializationComplete: "+initializationStatus.toString());
                Context context;
                interstitialAd =new InterstitialAd(start.this);
                interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
                interstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });



    }

    public void startGame(View view) {

     if (interstitialAd.isLoaded())
            interstitialAd.show();
        else
        {
            Toast.makeText(start.this, "please enable your internet it helps us!!", Toast.LENGTH_SHORT).show();
            String uid = getIntent().getStringExtra("EXTRA_SESSION_ID");

            Intent intent = new Intent(getApplicationContext(), main.class);
            intent.putExtra("EXTRA_SESSION_ID", uid);
            startActivity(intent);
        }
        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Toast.makeText(start.this, "please enable your internet it helps us!!", Toast.LENGTH_SHORT).show();
                String uid = getIntent().getStringExtra("EXTRA_SESSION_ID");

                Intent intent = new Intent(getApplicationContext(), main.class);
                intent.putExtra("EXTRA_SESSION_ID", uid);
                startActivity(intent);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                String uid = getIntent().getStringExtra("EXTRA_SESSION_ID");

                Intent intent = new Intent(getApplicationContext(), main.class);
                intent.putExtra("EXTRA_SESSION_ID", uid);
                startActivity(intent);
            }
        });

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
