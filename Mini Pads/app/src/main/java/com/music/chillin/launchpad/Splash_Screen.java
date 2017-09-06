package com.music.chillin.launchpad;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Splash_Screen extends AppCompatActivity {

    ImageView logo;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        logo = (ImageView)findViewById(R.id.logo);
        animateButtonsZoomOut(logo);
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen.this,Startup_Menu.class);
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable,3000);

    }
    public void animateButtonsZoomOut(View v){
        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out_splash));
    }
}
