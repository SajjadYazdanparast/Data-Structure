package com.example.hossein.chess;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainMenu extends AppCompatActivity {


    AppCompatButton start_btn , setting_btn , exit_btn , about_us_btn   , confirm_btn , cancel_btn;

    Context context;

    Dialog dialog;

    String player1_color , player2_color , player1_name , player2_name;

    RadioButton player1_black , player1_white , player2_black , player2_white ;

    EditText player1_name_et , player2_name_et;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menuu);

        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();
        player1_color = "black";
        player2_color = "white";

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




        dialog = new Dialog(MainMenu.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.ask_dialog,null,false);
        dialog.setContentView(view);
        dialog.setCancelable(false);



        player1_name_et = (EditText) dialog.findViewById(R.id.player1_name_et);
        player2_name_et = (EditText) dialog.findViewById(R.id.player2_name_et);
        player1_black = (RadioButton) dialog.findViewById(R.id._1black);
        player1_white = (RadioButton) dialog.findViewById(R.id._1white);
        player2_black = (RadioButton) dialog.findViewById(R.id._2black);
        player2_white = (RadioButton) dialog.findViewById(R.id._2white);

        start_btn = (AppCompatButton) findViewById(R.id.start_game_btn);
        setting_btn = (AppCompatButton) findViewById(R.id.setting_btn);
        exit_btn = (AppCompatButton) findViewById(R.id.exit_btn);
        about_us_btn = (AppCompatButton) findViewById(R.id.about_us_btn);




        confirm_btn = (AppCompatButton) dialog.findViewById(R.id.confirm_btn);
        cancel_btn = (AppCompatButton) dialog.findViewById(R.id.cancel_btn);


        player1_black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    player1_color = "black";
                    player2_color = "white";

                    player1_white.setChecked(false);
                    player1_black.setChecked(true);

                    player2_black.setChecked(false);
                    player2_white.setChecked(true);
                }
                else{

                    player1_color = "white";
                    player2_color = "black";

                    player1_black.setChecked(false);
                    player1_white.setChecked(true);

                    player2_white.setChecked(false);
                    player2_black.setChecked(true);
                }
            }
        });

        player1_white.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    player1_color = "white";
                    player2_color = "black";

                    player1_black.setChecked(false);
                    player1_white.setChecked(true);

                    player2_white.setChecked(false);
                    player2_black.setChecked(true);
                }
                else{
                    player1_color = "black";
                    player2_color = "white";

                    player2_black.setChecked(false);
                    player2_white.setChecked(true);

                    player1_white.setChecked(false);
                    player1_black.setChecked(true);
                }
            }
        });

        player2_black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    player1_color = "white";
                    player2_color = "black";

                    player1_black.setChecked(false);
                    player1_white.setChecked(true);

                    player2_white.setChecked(false);
                    player2_black.setChecked(true);
                }
                else{
                    player1_color = "black";
                    player2_color = "white";

                    player2_black.setChecked(false);
                    player2_white.setChecked(true);

                    player1_white.setChecked(false);
                    player1_black.setChecked(true);
                }
            }
        });
        player2_white.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    player1_color = "black";
                    player2_color = "white";

                    player1_white.setChecked(false);
                    player1_black.setChecked(true);

                    player2_black.setChecked(false);
                    player2_white.setChecked(true);
                }
                else{

                    player1_color = "white";
                    player2_color = "black";

                    player1_black.setChecked(false);
                    player1_white.setChecked(true);

                    player2_white.setChecked(false);
                    player2_black.setChecked(true);
                }
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1_name = player1_name_et.getText().toString().trim();
                player2_name = player2_name_et.getText().toString().trim();
                if(player1_name.isEmpty()) {
                    player1_name_et.setError(getString(R.string.EmptyString));
                    player1_name_et.requestFocus();
                }
                else if(player2_name.isEmpty()) {
                    player2_name_et.setError(getString(R.string.EmptyString));
                    player2_name_et.requestFocus();
                }
                else {
                    dialog.dismiss();
                    GoPlay();
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.show();

            }
        });

        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu.this.startActivity(new Intent(MainMenu.this , Setting.class));
            }
        });


        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        about_us_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu.this.startActivity(new Intent(MainMenu.this , AboutUs.class));
            }
        });
    }

    void GoPlay ()
    {

        Intent goPlayActivity = new Intent(MainMenu.this , PlayActivity.class);
        goPlayActivity.putExtra("player1_color" , player1_color);
        goPlayActivity.putExtra("player2_color" , player2_color);
        goPlayActivity.putExtra("player1_name" , player1_name);
        goPlayActivity.putExtra("player2_name" , player2_name);
        MainMenu.this.startActivity(goPlayActivity);
    }
}
