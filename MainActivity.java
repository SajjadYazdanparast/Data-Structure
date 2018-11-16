package com.example.hossein.chess;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    String playMusic;
    SharedPreferences mSharedPreferences;

    MediaPlayer backGroundMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



        mSharedPreferences = getSharedPreferences("save" , MODE_PRIVATE);
        if (mSharedPreferences.contains("playMusic")) {
            playMusic = mSharedPreferences.getString("playMusic", "yes");
        }

        if(playMusic.equals("yes"))
        {
            backGroundMusic = MediaPlayer.create(this , R.raw.tick_tock_sound);
            backGroundMusic.start();
        }



    }
}
