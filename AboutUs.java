package com.example.hossein.chess;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AboutUs extends AppCompatActivity {


    AppCompatButton return_to_main_menu;
    LinearLayout third ;
    ImageView telegram ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
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

        init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                telegram.animate().alpha(1f).setDuration(4000L).start();
            }
        }, 1500L);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                third.animate().alpha(1f).setDuration(4000L).start();
            }
        }, 2000L);

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppInstalled()){
                    Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/ui97_sajjad_hossein"));
                    startActivity(telegram);
                }
                else
                    Toast.makeText(AboutUs.this,getString(R.string.telegramNotInstalled),Toast.LENGTH_LONG).show();
            }
        });
        return_to_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
    private void init()
    {
        return_to_main_menu = (AppCompatButton) findViewById(R.id.return_to_main_menu);
        third = (LinearLayout) findViewById(R.id.aboutus_third);
        telegram = (ImageView) findViewById(R.id.about_us_telegram);
    }

    private boolean AppInstalled(){
        PackageManager pm = this.getPackageManager();
        try
        {
            pm.getPackageInfo("org.telegram.messenger", PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }

    }
}
