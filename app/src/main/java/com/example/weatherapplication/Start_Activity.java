package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Start_Activity extends AppCompatActivity {
    TextView t1;
    Intent wt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        t1=findViewById(R.id.fi);

        wt=new Intent(Start_Activity.this,MainActivity.class);

        Animation scale= AnimationUtils.loadAnimation(this,R.anim.scale);
        t1.startAnimation(scale);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(wt);
              finish();
            }
        },3000);





    }
}