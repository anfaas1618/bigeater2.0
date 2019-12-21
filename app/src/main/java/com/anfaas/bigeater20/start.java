package com.anfaas.bigeater20;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;

public class start extends AppCompatActivity {

  //  private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // Create the interstitial.
      //  interstitial = new InterstitialAd(this);

        // Set your unit id. THIS IS TEST ID!!
     //   interstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        // Create request.
    //    AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading...
    //    interstitial.loadAd(adRequest);

        // Once request is loaded, display ad.
     //   interstitial.setAdListener(new AdListener() {
        //    public void onAdLoaded() {
                displayInterstitial();
      //      }
    //    });

    }


    public void displayInterstitial() {

     //   if (interstitial.isLoaded()) {
     //       interstitial.show();
      //  }

    }

    public void startGame(View view) {
        startActivity(new Intent(getApplicationContext(), main.class));
    }

    // Disable Return Button
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
