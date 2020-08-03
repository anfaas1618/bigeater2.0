package com.anfaas.bigeater20;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

public class DialogWaitRewardAd extends Dialog {

public  Context context;
public  Button cancelWait;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.watch_ad_loading_dialog);
        cancelWait =findViewById(R.id.cancelWaitTime);
    }

    public DialogWaitRewardAd(@NonNull Context context) {
        super(context);
        this.context= context;
this.setCanceledOnTouchOutside(false);

    }
}
