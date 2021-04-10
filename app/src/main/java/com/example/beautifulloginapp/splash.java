package com.example.beautifulloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class splash extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView= (ImageView) findViewById(R.id.imageView2);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.myanimation);
        imageView.startAnimation(myanim);

        Thread mythread= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent i= new Intent(splash.this,MainActivity.class);
                    startActivity(i);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        mythread.start();
    }
}
