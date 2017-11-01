package com.example.gabriel.mypain;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(InitialScreen.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#ffffff"))
                .withBackgroundResource(R.drawable.splash)
                .withAfterLogoText("")
                .withFooterText("Fatec São Caetano do Sul - 2017");

        config.getAfterLogoTextView().setTextColor(Color.BLACK);
        config.getFooterTextView().setTextColor(Color.BLACK);

        View view = config.create();

        setContentView(view);
    }
}
