package com.example.hossein.chess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Intro extends AppCompatActivity {

    MediaPlayer mp;
    String playMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        mp = MediaPlayer.create(this , R.raw.sheyheh);

        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();


        View decordview = getWindow().getDecorView();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT)
            decordview.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        SharedPreferences mSharedPreferences = getSharedPreferences("save" , MODE_PRIVATE);
        if (mSharedPreferences.contains("playMusic")) {
            playMusic = mSharedPreferences.getString("playMusic", "yes");
        }
        else
        {
            SharedPreferences.Editor editor = mSharedPreferences.edit() ;
            editor.putString("playMusic","yes");
            editor.apply();
            playMusic = "yes" ;
        }



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mp.start();
            }
        },2500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                go_to_main_menu();
            }
        },5000);


    }


    public void go_to_main_menu()
    {
        Intent goMainMenu = new Intent(Intro.this , IntroSliderActivity.class);
        this.finish();
        mp.stop();
        Intro.this.startActivity(goMainMenu);
    }
}
