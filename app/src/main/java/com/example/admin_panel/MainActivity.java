package com.example.admin_panel;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Animation TAm,bAm;
    VideoView view;
    ImageView img;

    private static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TAm = AnimationUtils.loadAnimation(this,R.anim.top);


        img = findViewById(R.id.img);



        img.setAnimation(TAm);


          new Handler().postDelayed(new Runnable() {
@Override
public void run() {
    Intent intent = new Intent(MainActivity.this, com.example.admin_panel.login_page.class);

    Pair[] pairs = new Pair[1];
    pairs[0] = new Pair<View,String>(img,"image");
    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs[0]);
    startActivity(intent,options.toBundle());

}

        }, SPLASH_SCREEN);
    }
}