package com.anfaas.bigeater20;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);




    }


    public void displayInterstitial() {



    }

    public void startGame(View view) {
         String uid = getIntent().getStringExtra("EXTRA_SESSION_ID");
        Intent intent = new Intent(getApplicationContext(), main.class);
        intent.putExtra("EXTRA_SESSION_ID", uid);
        startActivity(intent);
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
