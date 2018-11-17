package com.example.hossein.chess;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Setting extends AppCompatActivity {


    AppCompatButton return_to_main_menu;
    CheckBox music_switch;
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

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


        return_to_main_menu = (AppCompatButton) findViewById(R.id.return_to_main_menu);


        music_switch = (CheckBox) findViewById(R.id.song_switch);

        if(getSharedPreferences("save",MODE_PRIVATE).getString("playMusic","yes").equals("yes")){
            music_switch.setChecked(true);
        }
        else
            music_switch.setChecked(false);
        return_to_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSharedPreferences = getSharedPreferences("save" , MODE_PRIVATE);
        music_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(music_switch.isChecked())
                {
                    SharedPreferences.Editor editor = mSharedPreferences.edit() ;
                    editor.putString("playMusic","yes");
                    editor.apply();
                }
                else
                {
                    SharedPreferences.Editor editor = mSharedPreferences.edit() ;
                    editor.putString("playMusic","no");
                    editor.apply();
                }
            }
        });


    }

}
