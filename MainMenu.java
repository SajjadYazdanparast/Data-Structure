package com.example.hossein.chess;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.view.WindowManager;

public class MainMenu extends AppCompatActivity {


    AppCompatButton start_btn , setting_btn , exit_btn , about_us_btn   , confirm_btn , cancel_btn;

    Context context;

    Dialog dialog;

    String player1_color , player2_color , player1_name , player2_name;
    AppCompatButton player1_white_choose , player2_white_choose , player1_black_choose, player2_black_choose;



    TextInputEditText player1_name_et , player2_name_et;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menuu);

        getSupportActionBar().hide();
        player1_color = "white";
        player2_color = "black";

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


        context = this;


        dialog = new Dialog(context , R.style.FullHeightDialog);
        dialog.setContentView(R.layout.ask_dialog);



        player1_name_et = (TextInputEditText) dialog.findViewById(R.id.player1_name_et);
        player2_name_et = (TextInputEditText) dialog.findViewById(R.id.player2_name_et);

        player1_white_choose = (AppCompatButton) dialog.findViewById(R.id.player1_white_choose);
        player2_white_choose = (AppCompatButton) dialog.findViewById(R.id.player2_white_choose);
        player1_black_choose = (AppCompatButton) dialog.findViewById(R.id.player1_black_choose);
        player2_black_choose = (AppCompatButton) dialog.findViewById(R.id.player2_black_choose);

        start_btn = (AppCompatButton) findViewById(R.id.start_game_btn);
        setting_btn = (AppCompatButton) findViewById(R.id.setting_btn);
        exit_btn = (AppCompatButton) findViewById(R.id.exit_btn);
        about_us_btn = (AppCompatButton) findViewById(R.id.about_us_btn);




        confirm_btn = (AppCompatButton) dialog.findViewById(R.id.confirm_btn);
        cancel_btn = (AppCompatButton) dialog.findViewById(R.id.cancel_btn);



        player1_white_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1_color = "white";
                player2_color = "black";
                player1_white_choose.setEnabled(false);
                player2_black_choose.setEnabled(false);

                if(!player1_black_choose.isEnabled())
                {
                    player1_black_choose.setEnabled(true);
                }
                if(!player2_white_choose.isEnabled())
                {
                    player2_white_choose.setEnabled(true);
                }
            }
        });

        player1_black_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1_color = "black";
                player2_color = "white";
                player1_black_choose.setEnabled(false);
                player2_white_choose.setEnabled(false);
                if(!player1_white_choose.isEnabled())
                {
                    player1_white_choose.setEnabled(true);
                }
                if(!player2_black_choose.isEnabled())
                {
                    player2_black_choose.setEnabled(true);
                }
            }
        });

        player2_white_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player2_color = "white";
                player1_color = "black";
                player2_white_choose.setEnabled(false);
                player1_black_choose.setEnabled(false);

                if(!player1_white_choose.isEnabled())
                {
                    player1_white_choose.setEnabled(true);
                }
                if(!player2_black_choose.isEnabled())
                {
                    player2_black_choose.setEnabled(true);
                }
            }
        });


        player2_black_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player2_color = "black";
                player1_color = "white";
                player2_black_choose.setEnabled(false);
                player1_white_choose.setEnabled(false);

                if(!player1_black_choose.isEnabled())
                {
                    player1_black_choose.setEnabled(true);
                }
                if(!player2_white_choose.isEnabled())
                {
                    player2_white_choose.setEnabled(true);
                }
            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                player1_name = player1_name_et.getText().toString();
                player2_name = player2_name_et.getText().toString();
                GoPlay(v);
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

    void GoPlay (View view)
    {

        Intent goPlayActivity = new Intent(MainMenu.this , PlayActivity.class);
        goPlayActivity.putExtra("player1_color" , player1_color);
        goPlayActivity.putExtra("player2_color" , player2_color);
        goPlayActivity.putExtra("player1_name" , player1_name);
        goPlayActivity.putExtra("player2_name" , player2_name);
        MainMenu.this.startActivity(goPlayActivity);
    }
}
