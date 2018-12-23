package com.ashugo.wikitap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.Transformation;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        TextView welcomeTV = findViewById(R.id.welcome_TV);
        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in_welcome);
        welcomeTV.startAnimation(animZoomIn);
    }
}
