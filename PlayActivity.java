package com.example.hossein.chess;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.datatype.Duration;

public class PlayActivity extends AppCompatActivity {

    final static String black = "black";
    final static String white = "white";
    final static String Soldier = "Soldier";
    final static String Castle = "Castle";
    final static String Horse = "Horse";
    final static String Elephant = "Elephant";
    final static String Minister = "Minister";
    final static String King = "King";
    MediaPlayer tickSound , backGroundMusic1 , backGroundMusic2;

    String saveMusicInfo;

    String playMusic;
    SharedPreferences mSharedPreferences;

    TextView playerBlack , playerWhite;
    Cell [][] board;
    ImageButton [][] guiBoard ;
    Boolean clickedFirst ;
    List<Pair<Integer , Integer >> backlighted;
    List<Move> undo;



    Pair<Integer , Integer> soldierInEnd;
    Pair<Integer , Integer> kishedKingPosition;
    Pair<Integer , Integer> matedKingPosition;
    Pair<Integer , Integer> kishKonItem;
    Pair<Integer , Integer> deletedKishKonItem;


    boolean play = true ;

    AppCompatImageButton music_setting_btn,previos_music_btn , pause_music_btn , next_music_btn, castleForChange , ministerForChange , elephantForChange;

    int xForFirst;
    int yForFirst;

    int counterForwhite, counterForBlack;

    String player1_name , player2_name , player1_color , player2_color;

    boolean whiteTurn , blackTurn;

    Dialog endchange , musicSetting;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();


        backGroundMusic2 = MediaPlayer.create(this , R.raw.marde_montazer);
        backGroundMusic1 = MediaPlayer.create(this , R.raw.ghasemabadi);

        mSharedPreferences = getSharedPreferences("save" , MODE_PRIVATE);
        if (mSharedPreferences.contains("playMusic")) {
            playMusic = mSharedPreferences.getString("playMusic", "yes");
        }
        showCounterDialoge();
        init();
        setBeadsFor_first();
        animatingBeads();
        if(playMusic.equals("yes")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backGroundMusic2.start();
//                    HandleMusic();
                }
            }, 4500L);
        }



        endchange = new Dialog(this , R.style.FullHeightDialog);
        endchange.setContentView(R.layout.change_bead_layout);

        castleForChange  = (AppCompatImageButton) endchange.findViewById(R.id.castle_init);
        ministerForChange  =(AppCompatImageButton) endchange.findViewById(R.id.minister_init);
        elephantForChange  =(AppCompatImageButton) endchange.findViewById(R.id.elephant_init);

        castleForChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (board[soldierInEnd.first][soldierInEnd.second].getObject().getColor().equals(black)) {
                    undo.add(new Move(board[soldierInEnd.first][soldierInEnd.second].getObject(),
                            new Bead(black, counterForBlack, soldierInEnd.first, soldierInEnd.second, "Castle", R.drawable.castleb)
                            ,Integer.valueOf(soldierInEnd.first),Integer.valueOf(soldierInEnd.second)
                            ,Integer.valueOf(soldierInEnd.first),Integer.valueOf(soldierInEnd.second)));
                    board[soldierInEnd.first][soldierInEnd.second].setObject(new Bead(black, counterForBlack, soldierInEnd.first, soldierInEnd.second, "Castle", R.drawable.castleb));
                    counterForBlack++;
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setBackgroundResource(android.R.color.transparent);


                    //FOR ANIMATING

                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(0f).setDuration(750L).start();
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setAlpha(0f);
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setImageResource(board[soldierInEnd.first][soldierInEnd.second].getObject().getImageId());
                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(1f).setDuration(750L).start();

                }

                else if (board[soldierInEnd.first][soldierInEnd.second].getObject().getColor().equals(white)) {
                    board[soldierInEnd.first][soldierInEnd.second].setObject(new Bead(white, counterForBlack, soldierInEnd.first, soldierInEnd.second, "Castle", R.drawable.castle_w));
                    counterForwhite++;
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setBackgroundResource(android.R.color.transparent);


                    //FOR ANIMATING

                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(0f).setDuration(750L).start();
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setAlpha(0f);
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setImageResource(board[soldierInEnd.first][soldierInEnd.second].getObject().getImageId());
                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(1f).setDuration(750L).start();

                }
                endchange.dismiss();
            }
        });

        ministerForChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (board[soldierInEnd.first][soldierInEnd.second].getObject().getColor().equals(black)) {
                    board[soldierInEnd.first][soldierInEnd.second].setObject(new Bead(black, counterForBlack, soldierInEnd.first, soldierInEnd.second, "Minister", R.drawable.ministerb));
                    counterForBlack++;
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setBackgroundResource(android.R.color.transparent);


                    //FOR ANIMATING

                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(0f).setDuration(750L).start();
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setAlpha(0f);
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setImageResource(board[soldierInEnd.first][soldierInEnd.second].getObject().getImageId());
                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(1f).setDuration(750L).start();

                }
                else if (board[soldierInEnd.first][soldierInEnd.second].getObject().getColor().equals(white)) {
                    board[soldierInEnd.first][soldierInEnd.second].setObject(new Bead(white, counterForBlack, soldierInEnd.first, soldierInEnd.second, "Minister", R.drawable.minister_w));
                    counterForwhite++;
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setBackgroundResource(android.R.color.transparent);


                    //FOR ANIMATING

                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(0f).setDuration(750L).start();
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setAlpha(0f);
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setImageResource(board[soldierInEnd.first][soldierInEnd.second].getObject().getImageId());
                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(1f).setDuration(750L).start();

                }
                endchange.dismiss();

            }
        });

        elephantForChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (board[soldierInEnd.first][soldierInEnd.second].getObject().getColor().equals(black)) {
                    board[soldierInEnd.first][soldierInEnd.second].setObject(new Bead(black, counterForBlack, soldierInEnd.first, soldierInEnd.second, "Elephant", R.drawable.elephantb));
                    counterForBlack++;
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setBackgroundResource(android.R.color.transparent);


                    //FOR ANIMATING

                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(0f).setDuration(750L).start();
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setAlpha(0f);
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setImageResource(board[soldierInEnd.first][soldierInEnd.second].getObject().getImageId());
                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(1f).setDuration(750L).start();

                }

                else if (board[soldierInEnd.first][soldierInEnd.second].getObject().getColor().equals(white)) {
                    board[soldierInEnd.first][soldierInEnd.second].setObject(new Bead(black, counterForBlack, soldierInEnd.first, soldierInEnd.second, "Elephant", R.drawable.elephant_w));
                    counterForwhite++;
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setBackgroundResource(android.R.color.transparent);


                    //FOR ANIMATING

                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(0f).setDuration(750L).start();
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setAlpha(0f);
                    guiBoard[soldierInEnd.first][soldierInEnd.second].setImageResource(board[soldierInEnd.first][soldierInEnd.second].getObject().getImageId());
                    guiBoard[soldierInEnd.first][soldierInEnd.second].animate().alpha(1f).setDuration(750L).start();

                }

                endchange.dismiss();
            }
        });


        musicSetting = new Dialog(this , R.style.FullHeightDialog);
        musicSetting.setContentView(R.layout.music_setting_layout);

        previos_music_btn = (AppCompatImageButton) musicSetting.findViewById(R.id.previos_music_btn);
        pause_music_btn = (AppCompatImageButton) musicSetting.findViewById(R.id.pause_music_btn);
        next_music_btn = (AppCompatImageButton) musicSetting.findViewById(R.id.next_music_btn);
        pause_music_btn.setBackgroundResource(R.drawable.pause_icon);

        next_music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playMusic.equals("yes"))
                {
                    if(backGroundMusic1.isPlaying())
                    {
                        backGroundMusic1.stop();
                        backGroundMusic2.start();
                        backGroundMusic1 = MediaPlayer.create(PlayActivity.this , R.raw.ghasemabadi);
                        return;
                    }
                    else if(backGroundMusic2.isPlaying())
                    {
                        backGroundMusic2.stop();
                        backGroundMusic1.start();
                        backGroundMusic2 = MediaPlayer.create(PlayActivity.this , R.raw.marde_montazer);
                    }
                    else{
                        backGroundMusic2 = MediaPlayer.create(PlayActivity.this , R.raw.marde_montazer) ;
                        backGroundMusic2.start();
                    }

                }
            }
        });
        previos_music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(playMusic.equals("yes")) {
                    if (backGroundMusic2.isPlaying())
                    {
                        backGroundMusic2.stop();
                        backGroundMusic1.start();
                        backGroundMusic2 = MediaPlayer.create(PlayActivity.this , R.raw.marde_montazer);
                        return;
                    }
                    else if (backGroundMusic1.isPlaying()) {
                        backGroundMusic1.stop();
                        backGroundMusic2.start();
                        backGroundMusic1 = MediaPlayer.create(PlayActivity.this , R.raw.ghasemabadi);
                    }
                }
            }
        });

        music_setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(backGroundMusic1.isPlaying() || backGroundMusic2.isPlaying())
                {
                    pause_music_btn.setBackgroundResource(R.drawable.pause_icon);
                }
                musicSetting.show();
            }
        });

        pause_music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(backGroundMusic1.isPlaying())
                {
                    pause_music_btn.setBackgroundResource(R.drawable.play_icon);
                    backGroundMusic1.pause();
                    saveMusicInfo = "1";
                    play = false ;
                }
                if(backGroundMusic2.isPlaying())
                {
                    pause_music_btn.setBackgroundResource(R.drawable.play_icon);
                    backGroundMusic2.pause();
                    saveMusicInfo = "2";
                    play = false ;
                }
                else
                {
                    pause_music_btn.setBackgroundResource(R.drawable.pause_icon);
                    if(saveMusicInfo.equals("1"))
                        backGroundMusic1.start();
                    else if(saveMusicInfo.equals("2"))
                        backGroundMusic2.start();
                    play = true ;
                }

            }
        });
    }

    public void init() {

        undo = new ArrayList<>();

        counterForwhite = 3;
        counterForBlack = 3;

        music_setting_btn = (AppCompatImageButton) findViewById(R.id.music_setting_btn);
        if(getSharedPreferences("save",MODE_PRIVATE).getString("playMusic","no").equals("no"))
            music_setting_btn.setVisibility(View.GONE);
        else
            music_setting_btn.setVisibility(View.VISIBLE);
        Intent intent = getIntent();

        player1_name = intent.getStringExtra("player1_name");
        player2_name = intent.getStringExtra("player2_name");
        player1_color = intent.getStringExtra("player1_color");
        player2_color = intent.getStringExtra("player2_color");


        playerBlack =(TextView) findViewById(R.id.playerBlack);
        playerWhite =(TextView) findViewById(R.id.playerWhite);


        if(player1_color.equals("black"))
        {
            playerBlack.setText(player1_name);
            playerWhite.setText(player2_name);
        }

        else if(player1_color.equals("white"))
        {
            playerWhite.setText(player1_name);
            playerBlack.setText(player2_name);

        }

        whiteTurn = true;
        blackTurn = false;

        board = new Cell[8][8];
        clickedFirst = true ;
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++)
            {
                board[i][j] = new Cell();
            }
        }

        backlighted = new ArrayList<>();

        guiBoard = new ImageButton[8][8];
        imageButtonInitializer();

        xForFirst = 0;
        yForFirst = 0;



    }

    public boolean is_kish(String kingsColor) {

        int xx=0;
        int yy = 0;

        boolean breakKon = true;

        if(kingsColor.equals(black))
        {
            for (int i=0;i<8;i++)
            {

                for (int j = 0; j < 8; j++) {

                    if (!board[i][j].is_empty && board[i][j].getObject().getName().equals("King") && board[i][j].getObject().getColor().equals(black))
                    {
                        xx = i;
                        yy = j;
                        kishedKingPosition = new Pair<>(xx, yy);
                        breakKon = false;
                        break;
                    }
                }
                if(!breakKon)
                {
                    break;
                }
            }
        }
        if(kingsColor.equals(white))
        {
            for (int i=0;i<8;i++)
            {
                for(int j=0;j<8;j++)
                {
                    if (!board[i][j].is_empty && board[i][j].getObject().getName().equals("King") && board[i][j].getObject().getColor().equals(white))
                    {
                        xx = i;
                        yy = j;
                        kishedKingPosition = new Pair<>(xx , yy);
                        breakKon = false;
                        break;
                    }
                }
                if(!breakKon)
                {
                    break;
                }
            }
        }


        if(kingsColor.equals(black))
        {
            if(xx<7 && yy<7 &&!board[xx+1][yy+1].is_empty && board[xx+1][yy+1].getObject().getName().equals("Soldier") && board[xx+1][yy+1].getObject().getColor().equals(white))
            {
                //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+1 , yy+1);
                return true;
            }

            if(xx<7 && yy>0 &&!board[xx+1][yy-1].is_empty && board[xx+1][yy-1].getObject().getName().equals("Soldier") && board[xx+1][yy-1].getObject().getColor().equals(white))
            {
                //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+1 , yy-1);
                return true;
            }

            if(xx<7 && yy<5 &&!board[xx+1][yy+2].is_empty && board[xx+1][yy+2].getObject().getName().equals("Horse") && board[xx+1][yy+2].getObject().getColor().equals(white))
            {
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+1 , yy+2);
                return true;
            }

            if(xx<7 && yy>1 &&!board[xx+1][yy-2].is_empty && board[xx+1][yy-2].getObject().getName().equals("Horse") && board[xx+1][yy-2].getObject().getColor().equals(white))
            {
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+1 , yy-2);
                return true;
            }

            if(xx>0 && yy<6 &&!board[xx-1][yy+2].is_empty && board[xx-1][yy+2].getObject().getName().equals("Horse") && board[xx-1][yy+2].getObject().getColor().equals(white))
            {
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-1 , yy+2);
                return true;
            }

            if(xx>0 && yy>1 &&!board[xx-1][yy-2].is_empty && board[xx-1][yy-2].getObject().getName().equals("Horse") && board[xx-1][yy-2].getObject().getColor().equals(white))
            {
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-1 , yy-2);
                return true;
            }

            if(xx<6 && yy<7 &&!board[xx+2][yy+1].is_empty && board[xx+2][yy+1].getObject().getName().equals("Horse") && board[xx+2][yy+1].getObject().getColor().equals(white))
            {
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+2 , yy+1);
                return true;
            }

            if(xx<6 && yy>0 &&!board[xx+2][yy-1].is_empty && board[xx+2][yy-1].getObject().getName().equals("Horse") && board[xx+2][yy-1].getObject().getColor().equals(white))
            {
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+2 , yy-1);
                return true;
            }

            if(xx>1 && yy<7 &&!board[xx-2][yy+1].is_empty && board[xx-2][yy+1].getObject().getName().equals("Horse") && board[xx-2][yy+1].getObject().getColor().equals(white))
            {
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-2 , yy+1);
                return true;
            }

            if(xx>1 && yy>0 &&!board[xx-2][yy-1].is_empty && board[xx-2][yy-1].getObject().getName().equals("Horse") && board[xx-2][yy-1].getObject().getColor().equals(white))
            {
                //board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-2 , yy-1);
                return true;
            }


            for(int i=xx+1;i<8;i++)
            {
                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(black))
                    break;

                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(white) &&
                        (board[i][yy].getObject().getName().equals(Elephant) || board[i][yy].getObject().getName().equals(Soldier) || board[i][yy].getObject().getName().equals(Horse)
                                || board[i][yy].getObject().getName().equals(King)))
                {

                    break;
                }

                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(white) &&
                        (board[i][yy].getObject().getName().equals(Castle) || board[i][yy].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.is_kiblack_king_is_kish),Toast.LENGTH_LONG).show();
                    //board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(i , yy);
                    return true;
                }

            }

            for(int i=xx-1;i>=0;i--)
            {
                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(black))
                    break;

                if(!board[i][yy].is_empty  &&board[i][yy].getObject().getColor().equals(white) &&
                        (board[i][yy].getObject().getName().equals(Elephant) || board[i][yy].getObject().getName().equals(King) || board[i][yy].getObject().getName().equals(Soldier) || board[i][yy].getObject().getName().equals(Horse)))
                {

                    break;
                }

                if(!board[i][yy].is_empty &&board[i][yy].getObject().getColor().equals(white) &&
                        (board[i][yy].getObject().getName().equals(Castle) || board[i][yy].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                    //board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(i , yy);
                    return true;
                }

            }


            for(int i=yy+1;i<8;i++)
            {


                if(!board[xx][i].is_empty)
                    if(board[xx][i].getObject().getColor().equals(black))
                        break;

                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(white) &&
                        (board[xx][i].getObject().getName().equals(Elephant) || board[xx][i].getObject().getName().equals(King) ||  board[xx][i].getObject().getName().equals(Soldier) || board[xx][i].getObject().getName().equals(Horse)))
                {

                    break;
                }

                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(white) &&
                        (board[xx][i].getObject().getName().equals(Castle) || board[xx][i].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                    //board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx  , i);
                    return true;
                }

            }


            for(int i=yy-1;i>=0;i--)
            {
                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(black))
                    break;

                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(white) &&
                        (board[xx][i].getObject().getName().equals(Elephant)|| board[xx][i].getObject().getName().equals(King) || board[xx][i].getObject().getName().equals(Soldier) || board[xx][i].getObject().getName().equals(Horse)))
                {

                    break;
                }

                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(white) &&
                        (board[xx][i].getObject().getName().equals(Castle) || board[xx][i].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                    //board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx  , i);
                    return true;
                }

            }


            //elephant like move


            int i=1;
            while(xx+i<8 && yy+i<8)
            {
                if(!board[xx+i][yy+i].is_empty && board[xx+i][yy+i].getObject().getColor().equals(black))
                    break;

                if(!board[xx+i][yy+i].is_empty && board[xx+i][yy+i].getObject().getColor().equals(white) &&
                        (board[xx+i][yy+i].getObject().getName().equals(Elephant) || board[xx+i][yy+i].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                    //board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx+i  , yy+i);
                    return true;
                }

                if(!board[xx+i][yy+i].is_empty && board[xx+i][yy+i].getObject().getColor().equals(white) &&
                        (board[xx+i][yy+i].getObject().getName().equals(Castle) || board[xx+i][yy+i].getObject().getName().equals(King) || board[xx+i][yy+i].getObject().getName().equals(Horse) || board[xx+i][yy+i].getObject().getName().equals("Soldier")))
                {
                    break;
                }

                i++;
            }

            i=1;

            while(xx+i<8 && yy-i>=0)
            {
                if(!board[xx+i][yy-i].is_empty && board[xx+i][yy-i].getObject().getColor().equals(black))
                    break;

                if(!board[xx+i][yy-i].is_empty && board[xx+i][yy-i].getObject().getColor().equals(white) &&
                        (board[xx+i][yy-i].getObject().getName().equals(Elephant) || board[xx+i][yy-i].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                    //board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx+i, yy-i);
                    return true;
                }

                if(!board[xx+i][yy-i].is_empty && board[xx+i][yy-i].getObject().getColor().equals(white) &&
                        (board[xx+i][yy-i].getObject().getName().equals(Castle) || board[xx+i][yy-i].getObject().getName().equals(King) || board[xx+i][yy-i].getObject().getName().equals(Horse) || board[xx+i][yy-i].getObject().getName().equals(Soldier)))
                {
                    break;
                }

                i++;
            }

            i=1;
            while(xx-i>=0 && yy-i>=0)
            {
                if(!board[xx-i][yy-i].is_empty && board[xx-i][yy-i].getObject().getColor().equals(black))
                    break;

                if(!board[xx-i][yy-i].is_empty && board[xx-i][yy-i].getObject().getColor().equals(white) &&
                        (board[xx-i][yy-i].getObject().getName().equals(Elephant) || board[xx-i][yy-i].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                    //board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx-i, yy-i);
                    return true;
                }

                if(!board[xx-i][yy-i].is_empty && board[xx-i][yy-i].getObject().getColor().equals(white) &&
                        (board[xx-i][yy-i].getObject().getName().equals(Castle) || board[xx-i][yy-i].getObject().getName().equals(King) || board[xx-i][yy-i].getObject().getName().equals(Horse) || board[xx-i][yy-i].getObject().getName().equals(Soldier)))
                {
                    break;
                }

                i++;
            }
            i=1;

            while(xx-i>=0 && yy+i<8)
            {
                if(!board[xx-i][yy+i].is_empty && board[xx-i][yy+i].getObject().getColor().equals(black))
                    break;

                if(!board[xx-i][yy+i].is_empty && board[xx-i][yy+i].getObject().getColor().equals(white) &&
                        (board[xx-i][yy+i].getObject().getName().equals(Elephant) || board[xx-i][yy+i].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
                    //board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx-i, yy+i);
                    return true;
                }

                if(!board[xx-i][yy+i].is_empty && board[xx-i][yy+i].getObject().getColor().equals(white) &&
                        (board[xx-i][yy+i].getObject().getName().equals(Castle) || board[xx-i][yy+i].getObject().getName().equals(King) || board[xx-i][yy+i].getObject().getName().equals(Horse) || board[xx-i][yy+i].getObject().getName().equals(Soldier)))
                {
                    break;
                }

                i++;
            }
        }

        if(kingsColor.equals(white))
        {
            if(xx>0 && yy<7 &&!board[xx-1][yy+1].is_empty && board[xx-1][yy+1].getObject().getName().equals("Soldier") && board[xx-1][yy+1].getObject().getColor().equals(black))
            {
                //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-1, yy+1);
                return true;
            }

            if(xx>0 && yy>0 && !board[xx-1][yy-1].is_empty && board[xx-1][yy-1].getObject().getName().equals("Soldier") && board[xx-1][yy-1].getObject().getColor().equals(black))
            {
                //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-1, yy-1);
                return true;
            }

            if(xx<7 && yy<6 &&!board[xx+1][yy+2].is_empty && board[xx+1][yy+2].getObject().getName().equals("Horse") && board[xx+1][yy+2].getObject().getColor().equals(black))
            {
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+1 , yy+2);
                return true;
            }

            if(xx<7 && yy>1 &&!board[xx+1][yy-2].is_empty && board[xx+1][yy-2].getObject().getName().equals("Horse") && board[xx+1][yy-2].getObject().getColor().equals(black))
            {
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+1 , yy-2);
                return true;
            }

            if(xx>0 && yy<6 &&!board[xx-1][yy+2].is_empty && board[xx-1][yy+2].getObject().getName().equals("Horse") && board[xx-1][yy+2].getObject().getColor().equals(black))
            {
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-1 , yy+2);
                return true;
            }

            if(xx>0 && yy>1 &&!board[xx-1][yy-2].is_empty && board[xx-1][yy-2].getObject().getName().equals("Horse") && board[xx-1][yy-2].getObject().getColor().equals(black))
            {
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-1 , yy-2);
                return true;
            }

            if(xx<6 && yy<7 &&!board[xx+2][yy+1].is_empty && board[xx+2][yy+1].getObject().getName().equals("Horse") && board[xx+2][yy+1].getObject().getColor().equals(black))
            {
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+2 , yy+1);
                return true;
            }

            if(xx<6 && yy>0 &&!board[xx+2][yy-1].is_empty && board[xx+2][yy-1].getObject().getName().equals("Horse") && board[xx+2][yy-1].getObject().getColor().equals(black))
            {
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx+2 , yy-1);
                return true;
            }

            if(xx>1 && yy<7 &&!board[xx-2][yy+1].is_empty && board[xx-2][yy+1].getObject().getName().equals("Horse") && board[xx-2][yy+1].getObject().getColor().equals(black))
            {
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-2 , yy+1);
                return true;
            }

            if(xx>1 && yy>0 &&!board[xx-2][yy-1].is_empty && board[xx-2][yy-1].getObject().getName().equals("Horse") && board[xx-2][yy-1].getObject().getColor().equals(black))
            {
                board[xx][yy].getObject().is_kish = true;
                kishKonItem = new Pair<>(xx-2 , yy-1);
                return true;
            }



            for(int i=xx+1;i<8;i++)
            {
                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(white))
                    break;

                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(black) &&
                        (board[i][yy].getObject().getName().equals(Elephant) || board[i][yy].getObject().getName().equals(King) || board[i][yy].getObject().getName().equals("Soldier") || board[i][yy].getObject().getName().equals("Horse")))
                {

                    break;
                }

                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(black) &&
                        (board[i][yy].getObject().getName().equals("Castle") || board[i][yy].getObject().getName().equals("Minister")))
                {
                    //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                    board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(i , yy);
                    return true;
                }

            }

            for(int i=xx-1;i>=0;i--)
            {
                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(white))
                    break;

                if(!board[i][yy].is_empty && board[i][yy].getObject().getColor().equals(black) &&
                        (board[i][yy].getObject().getName().equals(Elephant) || board[i][yy].getObject().getName().equals(King) || board[i][yy].getObject().getName().equals("Soldier") || board[i][yy].getObject().getName().equals("Horse")))
                {

                    break;
                }

                if(!board[i][yy].is_empty &&board[i][yy].getObject().getColor().equals(black) &&
                        (board[i][yy].getObject().getName().equals("Castle") || board[i][yy].getObject().getName().equals("Minister")))
                {
                    //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                    board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(i , yy);
                    return true;
                }

            }


            for(int i=yy+1;i<8;i++)
            {
                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(white))
                    break;

                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(black) &&
                        (board[xx][i].getObject().getName().equals(Elephant) || board[xx][i].getObject().getName().equals(King) || board[xx][i].getObject().getName().equals("Soldier") || board[xx][i].getObject().getName().equals("Horse")))
                {

                    break;
                }

                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(black) &&
                        (board[xx][i].getObject().getName().equals("Castle") || board[xx][i].getObject().getName().equals("Minister")))
                {
                    //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                    board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx , i);
                    return true;
                }

            }


            for(int i=yy-1;i>=0;i--)
            {
                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(white))
                    break;

                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(black) &&
                        (board[xx][i].getObject().getName().equals(Elephant) || board[xx][i].getObject().getName().equals(King) || board[xx][i].getObject().getName().equals(Soldier) || board[xx][i].getObject().getName().equals(Horse)))
                {

                    break;
                }

                if(!board[xx][i].is_empty && board[xx][i].getObject().getColor().equals(black) &&
                        (board[xx][i].getObject().getName().equals(Castle) || board[xx][i].getObject().getName().equals(Minister)))
                {
                    //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                    board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx , i);
                    return true;
                }

            }


            //elephant like move


            int i=1;
            while(xx+i<8 && yy+i<8)
            {
                if(!board[xx+i][yy+i].is_empty && board[xx+i][yy+i].getObject().getColor().equals(white))
                    break;

                if(!board[xx+i][yy+i].is_empty && board[xx+i][yy+i].getObject().getColor().equals(black) &&
                        (board[xx+i][yy+i].getObject().getName().equals("Elephant") || board[xx+i][yy+i].getObject().getName().equals("Minister")))
                {
                    //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                    board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx+i , yy+i);
                    return true;
                }

                if(!board[xx+i][yy+i].is_empty && board[xx+i][yy+i].getObject().getColor().equals(black) &&
                        (board[xx+i][yy+i].getObject().getName().equals("Castle")  || board[xx+i][yy+i].getObject().getName().equals(King)|| board[xx+i][yy+i].getObject().getName().equals("Horse") || board[xx+i][yy+i].getObject().getName().equals("Soldier")))
                {
                    break;
                }

                i++;
            }

            i=1;

            while(xx+i<8 && yy-i>=0)
            {
                if(!board[xx+i][yy-i].is_empty && board[xx+i][yy-i].getObject().getColor().equals(white))
                    break;

                if(!board[xx+i][yy-i].is_empty && board[xx+i][yy-i].getObject().getColor().equals(black) &&
                        (board[xx+i][yy-i].getObject().getName().equals("Elephant") || board[xx+i][yy-i].getObject().getName().equals("Minister")))
                {
                    //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                    board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx+i , yy-i);
                    return true;
                }
                if(!board[xx+i][yy-i].is_empty && board[xx+i][yy-i].getObject().getColor().equals(black) &&
                        (board[xx+i][yy-i].getObject().getName().equals("Castle")  || board[xx+i][yy-i].getObject().getName().equals(King) || board[xx+i][yy-i].getObject().getName().equals("Horse") || board[xx+i][yy-i].getObject().getName().equals("Soldier")))
                {
                    break;
                }

                i++;
            }

            i=1;
            while(xx-i>=0 && yy-i>=0)
            {
                if(!board[xx-i][yy-i].is_empty && board[xx-i][yy-i].getObject().getColor().equals(white))
                    break;

                if(!board[xx-i][yy-i].is_empty && board[xx-i][yy-i].getObject().getColor().equals(black) &&
                        (board[xx-i][yy-i].getObject().getName().equals("Elephant") || board[xx-i][yy-i].getObject().getName().equals("Minister")))
                {
                    //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                    board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx-i , yy-i);
                    return true;
                }

                if(!board[xx-i][yy-i].is_empty && board[xx-i][yy-i].getObject().getColor().equals(black) &&
                        (board[xx-i][yy-i].getObject().getName().equals("Castle") || board[xx-i][yy-i].getObject().getName().equals(King) || board[xx-i][yy-i].getObject().getName().equals("Horse") || board[xx-i][yy-i].getObject().getName().equals("Soldier")))
                {
                    break;
                }

                i++;
            }
            i=1;

            while(xx-i>=0 && yy+i<8)
            {
                if(!board[xx-i][yy+i].is_empty && board[xx-i][yy+i].getObject().getColor().equals(white))
                    break;

                if(!board[xx-i][yy+i].is_empty && board[xx-i][yy+i].getObject().getColor().equals(black) &&
                        (board[xx-i][yy+i].getObject().getName().equals("Elephant") || board[xx-i][yy+i].getObject().getName().equals("Minister")))
                {
                    //Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();
                    board[xx][yy].getObject().is_kish = true;
                    kishKonItem = new Pair<>(xx-i , yy+i);
                    return true;
                }

                if(!board[xx-i][yy+i].is_empty && board[xx-i][yy+i].getObject().getColor().equals(black) &&
                        (board[xx-i][yy+i].getObject().getName().equals("Castle")  || board[xx-i][yy+i].getObject().getName().equals(King) || board[xx-i][yy+i].getObject().getName().equals("Horse") || board[xx-i][yy+i].getObject().getName().equals("Soldier")))
                {
                    break;
                }

                i++;
            }
        }


        return false;
    }

    public boolean is_mat(String kingsColor)
    {
        int xx=0;
        int yy = 0;
        boolean breakKon = true;
        Bead deletedItem = null;
        if(kingsColor.equals(black))
        {
            for (int i=0;i<8;i++)
            {
                for(int j=0;j<8;j++)
                {
                    if (!board[i][j].is_empty && board[i][j].getObject().getName().equals("King") && board[i][j].getObject().getColor().equals(black))
                    {
                        xx = i;
                        yy = j;
                        matedKingPosition = new Pair<>(xx , yy);
                        breakKon = false;
                        break;

                    }
                }
                if(!breakKon)
                {
                    break;
                }
            }
        }
        if(kingsColor.equals(white))
        {
            for (int i=0;i<8;i++)
            {
                for(int j=0;j<8;j++)
                {
                    if (!board[i][j].is_empty && board[i][j].getObject().getName().equals("King") && board[i][j].getObject().getColor().equals(white))
                    {
                        xx = board[i][j].getObject().getX();
                        yy = board[i][j].getObject().getY();
                        matedKingPosition = new Pair<>(xx , yy);
                        breakKon = false;
                        break;
                    }
                }
                if(!breakKon)
                {
                    break;
                }
            }
        }

        if(kingsColor.equals(black))
        {
            if(xx<7 && (board[xx+1][yy].is_empty ||(!board[xx+1][yy].is_empty && board[xx+1][yy].getObject().getColor().equals(white))))
            {
                if((!board[xx+1][yy].is_empty && board[xx+1][yy].getObject().getColor().equals(white)))
                {
                    deletedItem = board[xx+1][yy].getObject();
                }
                board[xx+1][yy].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx+1][yy].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(black))
                {
                    board[xx][yy].setObject(board[xx+1][yy].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx+1][yy].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy].is_empty = true;
                    }
                    return false ;
                }
            }

            if(xx>0 && (board[xx-1][yy].is_empty ||(!board[xx-1][yy].is_empty && board[xx-1][yy].getObject().getColor().equals(white))))
            {
                if((!board[xx-1][yy].is_empty && board[xx-1][yy].getObject().getColor().equals(white)))
                {
                    deletedItem = board[xx-1][yy].getObject();
                }
                board[xx-1][yy].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx-1][yy].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(black))
                {
                    board[xx][yy].setObject(board[xx-1][yy].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx-1][yy].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy].is_empty = true;
                    }
                    return false ;
                }
            }

            if(yy<7 && (board[xx][yy+1].is_empty ||(!board[xx][yy+1].is_empty && board[xx][yy+1].getObject().getColor().equals(white))))
            {
                if((!board[xx][yy+1].is_empty && board[xx][yy+1].getObject().getColor().equals(white)))
                {
                    deletedItem = board[xx+1][yy].getObject();
                }
                board[xx][yy+1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx][yy+1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(black))
                {
                    board[xx][yy].setObject(board[xx][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx][yy+1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx][yy+1].is_empty = true;
                    }
                    return false ;
                }
            }

            if(yy>0 && (board[xx][yy-1].is_empty ||(!board[xx][yy-1].is_empty && board[xx][yy-1].getObject().getColor().equals(white))))
            {
                if((!board[xx][yy-1].is_empty && board[xx][yy-1].getObject().getColor().equals(white)))
                {
                    deletedItem = board[xx+1][yy].getObject();
                }
                board[xx][yy-1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx][yy-1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(black))
                {
                    board[xx][yy].setObject(board[xx][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx][yy-1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx][yy-1].is_empty = true;
                    }
                    return false ;
                }
            }

            if(xx<7 && yy<7 && (board[xx+1][yy+1].is_empty ||(!board[xx+1][yy+1].is_empty && board[xx+1][yy+1].getObject().getColor().equals(white))))
            {
                if((!board[xx+1][yy+1].is_empty && board[xx+1][yy+1].getObject().getColor().equals(white)))
                {
                    deletedItem = board[xx+1][yy+1].getObject();
                }
                board[xx+1][yy+1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx+1][yy+1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(black))
                {
                    board[xx][yy].setObject(board[xx+1][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy+1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx+1][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy+1].is_empty = true;
                    }
                    return false ;
                }
            }

            if(xx<7 && yy>0 && (board[xx+1][yy-1].is_empty ||(!board[xx+1][yy-1].is_empty && board[xx+1][yy-1].getObject().getColor().equals(white))))
            {
                if((!board[xx+1][yy-1].is_empty && board[xx+1][yy-1].getObject().getColor().equals(white)))
                {
                    deletedItem = board[xx+1][yy-1].getObject();
                }
                board[xx+1][yy-1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx+1][yy-1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(black))
                {
                    board[xx][yy].setObject(board[xx+1][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy-1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx+1][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy-1].is_empty = true;
                    }
                    return false ;
                }
            }
            if(xx>0 && yy>0 && (board[xx-1][yy-1].is_empty ||(!board[xx-1][yy-1].is_empty && board[xx-1][yy-1].getObject().getColor().equals(white))))
            {
                if((!board[xx-1][yy-1].is_empty && board[xx-1][yy-1].getObject().getColor().equals(white)))
                {
                    deletedItem = board[xx-1][yy-1].getObject();
                }
                board[xx-1][yy-1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx-1][yy-1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(black))
                {
                    board[xx][yy].setObject(board[xx-1][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy-1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx-1][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy-1].is_empty = true;
                    }
                    return false ;
                }
            }

            if(xx>0 && yy<7 && (board[xx-1][yy+1].is_empty ||(!board[xx-1][yy+1].is_empty && board[xx-1][yy+1].getObject().getColor().equals(white))))
            {
                if((!board[xx-1][yy+1].is_empty && board[xx-1][yy+1].getObject().getColor().equals(white)))
                {
                    deletedItem = board[xx-1][yy+1].getObject();
                }
                board[xx-1][yy+1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx-1][yy+1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(black))
                {
                    board[xx][yy].setObject(board[xx-1][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy+1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx-1][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy+1].is_empty = true;
                    }
                    return false ;
                }
            }

            // it check the two mode kished

            if(is_kish(black))
            {

                deletedKishKonItem = kishKonItem;
                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                if(is_kish(black))
                {
                    board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);

                    return true;
                }
                board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
            }


            System.out.println("omadam naboodid raftam ke raftammmmmmmmmmmmmmmmmmmmmmmmmmmm");


            if(is_kish(black))
            {
                deletedKishKonItem = kishKonItem;

                if (board[kishKonItem.first][kishKonItem.second].getObject().getName().equals("Horse"))
                {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black)) {
                                if (board[i][j].getObject().getName().equals("Castle"))
                                {

                                    for (int k = i + 1; k < 8; k++)
                                    {
                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(black))
                                            break;

                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(white) && k != deletedKishKonItem.first && j != deletedKishKonItem.second)
                                            break;

                                        if (k == deletedKishKonItem.first && j == deletedKishKonItem.second) {
                                            deletedItem = board[k][j].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = i - 1; k >= 0; k--)
                                    {
                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(black))
                                            break;

                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(white) && k != deletedKishKonItem.first && j != deletedKishKonItem.second)
                                            break;

                                        if (k == deletedKishKonItem.first && j == deletedKishKonItem.second) {
                                            deletedItem = board[k][j].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = j + 1; k < 8; k++)
                                    {
                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(black))
                                            break;

                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(white) && i != deletedKishKonItem.first && k != deletedKishKonItem.second)
                                            break;

                                        if (i == deletedKishKonItem.first && k == deletedKishKonItem.second) {
                                            deletedItem = board[i][k].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = j - 1; k >= 0; k--)
                                    {
                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(black))
                                            break;

                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(white) && i != deletedKishKonItem.first && k != deletedKishKonItem.second)
                                            break;

                                        if (i == deletedKishKonItem.first && k == deletedKishKonItem.second) {
                                            deletedItem = board[i][k].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                    }
                                }

                                // elephant

                                if (board[i][j].getObject().getName().equals("Elephant"))
                                {
                                    int k=1;
                                    while(i+j<8 && j+k<8)
                                    {

                                        if(!board[i+k][j+k].is_empty && board[i+k][j+k].getObject().getColor().equals(black))
                                        {
                                            break;
                                        }
                                        if(!board[i+k][j+k].is_empty && board[i+k][j+k].getObject().getColor().equals(white) && i+k != deletedKishKonItem.first && j+k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i+k == deletedKishKonItem.first && j+k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }


                                    k=1;
                                    while(i+j<8 && j-k>=0)
                                    {

                                        if(!board[i+k][j-k].is_empty && board[i+k][j-k].getObject().getColor().equals(black))
                                        {
                                            break;
                                        }
                                        if(!board[i+k][j-k].is_empty && board[i+k][j-k].getObject().getColor().equals(white) && i+k != deletedKishKonItem.first && j-k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i+k == deletedKishKonItem.first && j-k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }

                                    k=1;
                                    while(i-j>=0 && j-k>=0)
                                    {

                                        if(!board[i-k][j-k].is_empty && board[i-k][j-k].getObject().getColor().equals(black))
                                        {
                                            break;
                                        }
                                        if(!board[i-k][j-k].is_empty && board[i-k][j-k].getObject().getColor().equals(white) && i-k != deletedKishKonItem.first && j-k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i-k == deletedKishKonItem.first && j-k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }
                                    k=1;
                                    while(i-j>=0 && j+k<8)
                                    {

                                        if(!board[i-k][j+k].is_empty && board[i-k][j+k].getObject().getColor().equals(black))
                                        {
                                            break;
                                        }
                                        if(!board[i-k][j+k].is_empty && board[i-k][j+k].getObject().getColor().equals(white) && i-k != deletedKishKonItem.first && j+k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i-k == deletedKishKonItem.first && j+k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                        k++;
                                    }
                                }

                                // minister

                                if (board[i][j].getObject().getName().equals("Minister"))
                                {
                                    for (int k = i + 1; k < 8; k++)
                                    {
                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(black))
                                            break;

                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(white) && k != deletedKishKonItem.first && j != deletedKishKonItem.second)
                                            break;

                                        if (k == deletedKishKonItem.first && j == deletedKishKonItem.second) {
                                            deletedItem = board[k][j].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = i - 1; k >= 0; k--)
                                    {
                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(black))
                                            break;

                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(white) && k != deletedKishKonItem.first && j != deletedKishKonItem.second)
                                            break;

                                        if (k == deletedKishKonItem.first && j == deletedKishKonItem.second) {
                                            deletedItem = board[k][j].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = j + 1; k < 8; k++)
                                    {
                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(black))
                                            break;

                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(white) && i != deletedKishKonItem.first && k != deletedKishKonItem.second)
                                            break;

                                        if (i == deletedKishKonItem.first && k == deletedKishKonItem.second) {
                                            deletedItem = board[i][k].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = j - 1; k >= 0; k--)
                                    {
                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(black))
                                            break;

                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(white) && i != deletedKishKonItem.first && k != deletedKishKonItem.second)
                                            break;

                                        if (i == deletedKishKonItem.first && k == deletedKishKonItem.second) {
                                            deletedItem = board[i][k].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                    }

                                    int k=1;
                                    while(i+j<8 && j+k<8)
                                    {

                                        if(!board[i+k][j+k].is_empty && board[i+k][j+k].getObject().getColor().equals(black))
                                        {
                                            break;
                                        }
                                        if(!board[i+k][j+k].is_empty && board[i+k][j+k].getObject().getColor().equals(white) && i+k != deletedKishKonItem.first && j+k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i+k == deletedKishKonItem.first && j+k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }


                                    k=1;
                                    while(i+j<8 && j-k>=0)
                                    {

                                        if(!board[i+k][j-k].is_empty && board[i+k][j-k].getObject().getColor().equals(black))
                                        {
                                            break;
                                        }
                                        if(!board[i+k][j-k].is_empty && board[i+k][j-k].getObject().getColor().equals(white) && i+k != deletedKishKonItem.first && j-k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i+k == deletedKishKonItem.first && j-k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }

                                    k=1;
                                    while(i-j>=0 && j-k>=0)
                                    {

                                        if(!board[i-k][j-k].is_empty && board[i-k][j-k].getObject().getColor().equals(black))
                                        {
                                            break;
                                        }
                                        if(!board[i-k][j-k].is_empty && board[i-k][j-k].getObject().getColor().equals(white) && i-k != deletedKishKonItem.first && j-k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i-k == deletedKishKonItem.first && j-k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }
                                    k=1;
                                    while(i-j>=0 && j+k<8)
                                    {

                                        if(!board[i-k][j+k].is_empty && board[i-k][j+k].getObject().getColor().equals(black))
                                        {
                                            break;
                                        }
                                        if(!board[i-k][j+k].is_empty && board[i-k][j+k].getObject().getColor().equals(white) && i-k != deletedKishKonItem.first && j+k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i-k == deletedKishKonItem.first && j+k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(black)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                        k++;
                                    }
                                }

                                if(board[i][j].getObject().getName().equals("Horse"))
                                {
                                    if(deletedKishKonItem.first>1 && deletedKishKonItem.second>0 &&
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].getObject().getColor().equals(black) &&
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        if(is_kish(black))
                                        {
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first>1 && deletedKishKonItem.second<7 &&
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].getObject().getColor().equals(black) &&
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        if(is_kish(black))
                                        {
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first<6 && deletedKishKonItem.second>0 &&
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].getObject().getColor().equals(black) &&
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        if(is_kish(black))
                                        {
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first<6 && deletedKishKonItem.second<7 &&
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].getObject().getColor().equals(black) &&
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        if(is_kish(black))
                                        {
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first>0 && deletedKishKonItem.second>1 &&
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].getObject().getColor().equals(black) &&
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        if(is_kish(black))
                                        {
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first>0 && deletedKishKonItem.second<6 &&
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].getObject().getColor().equals(black) &&
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        if(is_kish(black))
                                        {
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first<7 && deletedKishKonItem.second>1 &&
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].getObject().getColor().equals(black) &&
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        if(is_kish(black))
                                        {
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first<7 && deletedKishKonItem.second<6 &&
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-+2].getObject().getColor().equals(black) &&
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second+2].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first + 1][deletedKishKonItem.second+2].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        if(is_kish(black))
                                        {
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second+2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second+2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Minister) ||
                        board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Castle)||
                        board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Elephant))
                {




                    List<Pair<Integer , Integer>> path = new ArrayList<>();


                    //finding path
                    if(board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Castle) ||
                            board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Minister))
                    {
                        if(xx == kishKonItem.first)
                        {
                            if(yy>kishKonItem.second)
                            {
                                for(int i=kishKonItem.second ; i<yy;i++)
                                {
                                    path.add(new Pair<Integer, Integer>(xx , i));
                                }
                            }

                            if(yy<kishKonItem.second)
                            {
                                for(int i=kishKonItem.second ; i>yy;i--)
                                {
                                    path.add(new Pair<Integer, Integer>(xx , i));
                                }
                            }


                        }

                        if(yy == kishKonItem.second)
                        {
                            if(xx>kishKonItem.first)
                            {
                                for(int i=kishKonItem.first ; i<xx;i++)
                                {
                                    path.add(new Pair<Integer, Integer>(i , yy));
                                }
                            }

                            if(xx<kishKonItem.first)
                            {
                                for(int i=kishKonItem.first ; i>xx;i--)
                                {
                                    path.add(new Pair<Integer, Integer>(i , yy));
                                }
                            }


                        }
                    }

                    if(board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Elephant) ||
                            board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Minister))
                    {
                        float tanjant = (float)(kishKonItem.second - yy)/ (float)(kishKonItem.first - xx);
                        if(Math.abs(tanjant) == 1)
                        {
                            //passed
                            if(kishKonItem.first<xx)
                            {
                                if(kishKonItem.second<yy)
                                {
                                    for(int i = kishKonItem.first,j = kishKonItem.second ; i<xx ;i++ , j++)
                                    {
                                        path.add(new Pair<Integer, Integer>(i , j));
                                    }
                                }

                                if(kishKonItem.second>yy)
                                {
                                    for(int i = kishKonItem.first,j = kishKonItem.second ; i<xx ;i++ , j--)
                                    {
                                        path.add(new Pair<Integer, Integer>(i , j));
                                    }
                                }
                            }

                            //passed
                            if(kishKonItem.first>xx)
                            {
                                if(kishKonItem.second<yy)
                                {
                                    for(int i = xx+1,j = yy-1; i<=kishKonItem.first ;i++ , j--)
                                    {
                                        path.add(new Pair<Integer, Integer>(i , j));
                                    }
                                }

                                if(kishKonItem.second>yy)
                                {
                                    for(int i = xx+1,j = yy+1 ; i<=kishKonItem.first ;i++ , j++)
                                    {
                                        path.add(new Pair<Integer, Integer>(i , j));
                                    }
                                }
                            }
                        }

                    }

                    //checking beads to unmat

                    boolean bekeshBiroon = false;
                    Bead temp = null;
                    for(int i=0;i<8;i++)
                    {
                        for(int j=0;j<8;j++)
                        {

                            if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                            {
                                if(board[i][j].getObject().getColor().equals(Castle) || board[i][j].getObject().getColor().equals(Minister))
                                {
                                    for(Pair<Integer , Integer> k:path)
                                    {
                                        if(k.first == i)
                                        {
                                            if(k.second<j)
                                            {
                                                for(int keke = k.second+1 ;keke <j; keke++)
                                                {
                                                    if(!board[i][keke].is_empty)
                                                    {
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                }
                                                board[i][j].is_empty = true;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                if(is_kish(black))
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    bekeshBiroon = true;
                                                    break;
                                                }
                                                else
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    return false;
                                                }
                                            }
                                            if(k.second>j)
                                            {
                                                for(int keke = k.second-1 ;keke >j; keke--)
                                                {
                                                    if(!board[i][keke].is_empty)
                                                    {
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                }
                                                board[i][j].is_empty = true;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                if(is_kish(black))
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    bekeshBiroon = true;
                                                    break;
                                                }
                                                else
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    return false;
                                                }
                                            }

                                        }

                                        if(k.second == j)
                                        {
                                            if(k.first<i)
                                            {
                                                for(int keke = k.first+1 ;keke <i; keke++)
                                                {
                                                    if(!board[keke][j].is_empty)
                                                    {
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                }
                                                board[i][j].is_empty = true;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                if(is_kish(black))
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    bekeshBiroon = true;
                                                    break;
                                                }
                                                else
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    return false;
                                                }
                                            }
                                            if(k.first>i)
                                            {
                                                for(int keke = k.first-1 ;keke >i; keke--)
                                                {
                                                    if(!board[keke][j].is_empty)
                                                    {
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                }
                                                board[i][j].is_empty = true;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                if(is_kish(black))
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    bekeshBiroon = true;
                                                    break;
                                                }
                                                else
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    return false;
                                                }
                                            }
                                        }
                                        if (bekeshBiroon)
                                        {
                                            bekeshBiroon = false;
                                            break;
                                        }
                                    }
                                }

                                if(board[i][j].getObject().getColor().equals(Minister) || board[i][j].getObject().getColor().equals(Elephant))
                                {
                                    float tanjant ;
                                    for (Pair<Integer , Integer>k:path)
                                    {
                                        if(tan(k.second , j , k.first , i) == 1)
                                        {
                                            ////////////////////////////////////////////////////////////////
                                            if(i<k.first)
                                            {
                                                if(j<k.second)
                                                {
                                                    for(int m = i+1,n = j+1 ; m<k.first ;m++ , n++)
                                                    {
                                                        if(!board[m][n].is_empty)
                                                        {
                                                            bekeshBiroon = true;
                                                            break;
                                                        }
                                                    }
                                                    board[i][j].is_empty = true;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    if(is_kish(black))
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                        return false;
                                                    }
                                                }

                                                if(j>k.second)
                                                {
                                                    for(int m = i+1,n = j-1 ; m<k.first ;m++ , n--)
                                                    {
                                                        if(!board[m][n].is_empty)
                                                        {
                                                            bekeshBiroon = true;
                                                            break;
                                                        }
                                                    }
                                                    board[i][j].is_empty = true;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    if(is_kish(black))
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                        return false;
                                                    }
                                                }
                                            }

                                            if(i>k.first)
                                            {
                                                if(j<k.second)
                                                {
                                                    for(int m = i-1,n = j+1 ; m<k.first ;m-- , n++)
                                                    {
                                                        if(!board[m][n].is_empty)
                                                        {
                                                            bekeshBiroon = true;
                                                            break;
                                                        }
                                                    }
                                                    board[i][j].is_empty = true;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    if(is_kish(black))
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                        return false;
                                                    }
                                                }

                                                if(j>k.second)
                                                {
                                                    for(int m = i-1,n = j-1 ; m<k.first ;m-- , n--)
                                                    {
                                                        if(!board[m][n].is_empty)
                                                        {
                                                            bekeshBiroon = true;
                                                            break;
                                                        }
                                                    }
                                                    board[i][j].is_empty = true;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    if(is_kish(black))
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                        return false;
                                                    }
                                                }
                                            }
                                        }
                                        if (bekeshBiroon)
                                        {
                                            bekeshBiroon = false;
                                            break;
                                        }
                                    }
                                }
                                if(board[i][j].getObject().getColor().equals(Horse))
                                {
                                    for(Pair<Integer , Integer>k:path)
                                    {
                                        if(k.first == i-2 && k.second == j-1)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                            if(is_kish(black))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                return false;
                                            }
                                        }

                                        if(k.first == i-2 && k.second == j+1)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                            if(is_kish(black))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                return false;
                                            }
                                        }

                                        if(k.first == i+2 && k.second == j-1)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                            if(is_kish(black))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                return false;
                                            }
                                        }

                                        if(k.first == i+2 && k.second == j+1)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                            if(is_kish(black))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                return false;
                                            }
                                        }

                                        if(k.first == i-1 && k.second == j-2)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                            if(is_kish(black))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                return false;
                                            }
                                        }

                                        if(k.first == i-1 && k.second == j+2)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                            if(is_kish(black))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                return false;
                                            }
                                        }

                                        if(k.first == i+1 && k.second == j-2)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                            if(is_kish(black))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                return false;
                                            }
                                        }

                                        if(k.first == i+1 && k.second == j+2)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                            if(is_kish(black))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                return false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        if(kingsColor.equals(white))
        {
            if(xx<7 && (board[xx+1][yy].is_empty ||(!board[xx+1][yy].is_empty && board[xx+1][yy].getObject().getColor().equals(black))))
            {
                if((!board[xx+1][yy].is_empty && board[xx+1][yy].getObject().getColor().equals(black)))
                {
                    deletedItem = board[xx+1][yy].getObject();
                }
                board[xx+1][yy].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx+1][yy].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(white))
                {
                    board[xx][yy].setObject(board[xx+1][yy].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx+1][yy].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy].is_empty = true;
                    }
                    return false ;
                }
            }

            if(xx>0 && (board[xx-1][yy].is_empty ||(!board[xx-1][yy].is_empty && board[xx-1][yy].getObject().getColor().equals(black))))
            {
                if((!board[xx-1][yy].is_empty && board[xx-1][yy].getObject().getColor().equals(black)))
                {
                    deletedItem = board[xx-1][yy].getObject();
                }
                board[xx-1][yy].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx-1][yy].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(white))
                {
                    board[xx][yy].setObject(board[xx-1][yy].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx-1][yy].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy].is_empty = true;
                    }
                    return false ;
                }
            }

            if(yy<7 && (board[xx][yy+1].is_empty ||(!board[xx][yy+1].is_empty && board[xx][yy+1].getObject().getColor().equals(black))))
            {
                if((!board[xx][yy+1].is_empty && board[xx][yy+1].getObject().getColor().equals(black)))
                {
                    deletedItem = board[xx+1][yy].getObject();
                }
                board[xx][yy+1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx][yy+1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(white))
                {
                    board[xx][yy].setObject(board[xx][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx][yy+1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx][yy+1].is_empty = true;
                    }
                    return false ;
                }
            }

            if(yy>0 && (board[xx][yy-1].is_empty ||(!board[xx][yy-1].is_empty && board[xx][yy-1].getObject().getColor().equals(black))))
            {
                if((!board[xx][yy-1].is_empty && board[xx][yy-1].getObject().getColor().equals(black)))
                {
                    deletedItem = board[xx+1][yy].getObject();
                }
                board[xx][yy-1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx][yy-1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(white))
                {
                    board[xx][yy].setObject(board[xx][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx][yy-1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx][yy-1].is_empty = true;
                    }
                    return false ;
                }
            }

            if(xx<7 && yy<7 && (board[xx+1][yy+1].is_empty ||(!board[xx+1][yy+1].is_empty && board[xx+1][yy+1].getObject().getColor().equals(black))))
            {
                if((!board[xx+1][yy+1].is_empty && board[xx+1][yy+1].getObject().getColor().equals(black)))
                {
                    deletedItem = board[xx+1][yy+1].getObject();
                }
                board[xx+1][yy+1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx+1][yy+1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(white))
                {
                    board[xx][yy].setObject(board[xx+1][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy+1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx+1][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy+1].is_empty = true;
                    }
                    return false ;
                }
            }

            if(xx<7 && yy>0 && (board[xx+1][yy-1].is_empty ||(!board[xx+1][yy-1].is_empty && board[xx+1][yy-1].getObject().getColor().equals(black))))
            {
                if((!board[xx+1][yy-1].is_empty && board[xx+1][yy-1].getObject().getColor().equals(black)))
                {
                    deletedItem = board[xx+1][yy-1].getObject();
                }
                board[xx+1][yy-1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx+1][yy-1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(white))
                {
                    board[xx][yy].setObject(board[xx+1][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy-1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx+1][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx+1][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx+1][yy-1].is_empty = true;
                    }
                    return false ;
                }
            }
            if(xx>0 && yy>0 && (board[xx-1][yy-1].is_empty ||(!board[xx-1][yy-1].is_empty && board[xx-1][yy-1].getObject().getColor().equals(black))))
            {
                if((!board[xx-1][yy-1].is_empty && board[xx-1][yy-1].getObject().getColor().equals(black)))
                {
                    deletedItem = board[xx-1][yy-1].getObject();
                }
                board[xx-1][yy-1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx-1][yy-1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(white))
                {
                    board[xx][yy].setObject(board[xx-1][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy-1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx-1][yy-1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy-1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy-1].is_empty = true;
                    }
                    return false ;
                }
            }

            if(xx>0 && yy<7 && (board[xx-1][yy+1].is_empty ||(!board[xx-1][yy+1].is_empty && board[xx-1][yy+1].getObject().getColor().equals(black))))
            {
                if((!board[xx-1][yy+1].is_empty && board[xx-1][yy+1].getObject().getColor().equals(black)))
                {
                    deletedItem = board[xx-1][yy+1].getObject();
                }
                board[xx-1][yy+1].setObject(board[xx][yy].getObject());
                board[xx][yy].setObject(null);
                board[xx-1][yy+1].is_empty = false;
                board[xx][yy].is_empty = true;



                if(is_kish(white))
                {
                    board[xx][yy].setObject(board[xx-1][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy+1].is_empty = true;
                    }
                }
                else
                {
                    board[xx][yy].setObject(board[xx-1][yy+1].getObject());
                    board[xx][yy].is_empty = false;
                    if(deletedItem != null)
                    {
                        board[xx-1][yy+1].setObject(deletedItem);
                        deletedItem = null;
                    }
                    else
                    {
                        board[xx-1][yy+1].is_empty = true;
                    }
                    return false ;
                }
            }

            // it check the two mode kished

            if(is_kish(white))
            {

                deletedKishKonItem = kishKonItem;
                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                if(is_kish(white))
                {
                    board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);

                    return true;
                }
                board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
            }


            System.out.println("omadam naboodid raftam ke raftammmmmmmmmmmmmmmmmmmmmmmmmmmm");


            if(is_kish(white))
            {
                deletedKishKonItem = kishKonItem;

                if (board[kishKonItem.first][kishKonItem.second].getObject().getName().equals("Horse"))
                {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                                if (board[i][j].getObject().getName().equals("Castle"))
                                {

                                    for (int k = i + 1; k < 8; k++)
                                    {
                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(white))
                                            break;

                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(black) && k != deletedKishKonItem.first && j != deletedKishKonItem.second)
                                            break;

                                        if (k == deletedKishKonItem.first && j == deletedKishKonItem.second) {
                                            deletedItem = board[k][j].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = i - 1; k >= 0; k--)
                                    {
                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(white))
                                            break;

                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(black) && k != deletedKishKonItem.first && j != deletedKishKonItem.second)
                                            break;

                                        if (k == deletedKishKonItem.first && j == deletedKishKonItem.second) {
                                            deletedItem = board[k][j].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = j + 1; k < 8; k++)
                                    {
                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(white))
                                            break;

                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(black) && i != deletedKishKonItem.first && k != deletedKishKonItem.second)
                                            break;

                                        if (i == deletedKishKonItem.first && k == deletedKishKonItem.second) {
                                            deletedItem = board[i][k].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = j - 1; k >= 0; k--)
                                    {
                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(white))
                                            break;

                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(black) && i != deletedKishKonItem.first && k != deletedKishKonItem.second)
                                            break;

                                        if (i == deletedKishKonItem.first && k == deletedKishKonItem.second) {
                                            deletedItem = board[i][k].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                    }
                                }

                                // elephant

                                if (board[i][j].getObject().getName().equals("Elephant"))
                                {
                                    int k=1;
                                    while(i+j<8 && j+k<8)
                                    {

                                        if(!board[i+k][j+k].is_empty && board[i+k][j+k].getObject().getColor().equals(white))
                                        {
                                            break;
                                        }
                                        if(!board[i+k][j+k].is_empty && board[i+k][j+k].getObject().getColor().equals(black) && i+k != deletedKishKonItem.first && j+k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i+k == deletedKishKonItem.first && j+k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }


                                    k=1;
                                    while(i+j<8 && j-k>=0)
                                    {

                                        if(!board[i+k][j-k].is_empty && board[i+k][j-k].getObject().getColor().equals(white))
                                        {
                                            break;
                                        }
                                        if(!board[i+k][j-k].is_empty && board[i+k][j-k].getObject().getColor().equals(black) && i+k != deletedKishKonItem.first && j-k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i+k == deletedKishKonItem.first && j-k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }

                                    k=1;
                                    while(i-j>=0 && j-k>=0)
                                    {

                                        if(!board[i-k][j-k].is_empty && board[i-k][j-k].getObject().getColor().equals(white))
                                        {
                                            break;
                                        }
                                        if(!board[i-k][j-k].is_empty && board[i-k][j-k].getObject().getColor().equals(black) && i-k != deletedKishKonItem.first && j-k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i-k == deletedKishKonItem.first && j-k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }
                                    k=1;
                                    while(i-j>=0 && j+k<8)
                                    {

                                        if(!board[i-k][j+k].is_empty && board[i-k][j+k].getObject().getColor().equals(white))
                                        {
                                            break;
                                        }
                                        if(!board[i-k][j+k].is_empty && board[i-k][j+k].getObject().getColor().equals(black) && i-k != deletedKishKonItem.first && j+k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i-k == deletedKishKonItem.first && j+k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                        k++;
                                    }
                                }

                                // minister

                                if (board[i][j].getObject().getName().equals("Minister"))
                                {
                                    for (int k = i + 1; k < 8; k++)
                                    {
                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(white))
                                            break;

                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(black) && k != deletedKishKonItem.first && j != deletedKishKonItem.second)
                                            break;

                                        if (k == deletedKishKonItem.first && j == deletedKishKonItem.second) {
                                            deletedItem = board[k][j].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = i - 1; k >= 0; k--)
                                    {
                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(white))
                                            break;

                                        if(!board[k][j].is_empty && board[k][j].getObject().getColor().equals(black) && k != deletedKishKonItem.first && j != deletedKishKonItem.second)
                                            break;

                                        if (k == deletedKishKonItem.first && j == deletedKishKonItem.second) {
                                            deletedItem = board[k][j].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = j + 1; k < 8; k++)
                                    {
                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(white))
                                            break;

                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(black) && i != deletedKishKonItem.first && k != deletedKishKonItem.second)
                                            break;

                                        if (i == deletedKishKonItem.first && k == deletedKishKonItem.second) {
                                            deletedItem = board[i][k].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            } else {
                                                return false;
                                            }
                                        }
                                    }

                                    for (int k = j - 1; k >= 0; k--)
                                    {
                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(white))
                                            break;

                                        if(!board[i][k].is_empty && board[i][k].getObject().getColor().equals(black) && i != deletedKishKonItem.first && k != deletedKishKonItem.second)
                                            break;

                                        if (i == deletedKishKonItem.first && k == deletedKishKonItem.second) {
                                            deletedItem = board[i][k].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                    }

                                    int k=1;
                                    while(i+j<8 && j+k<8)
                                    {

                                        if(!board[i+k][j+k].is_empty && board[i+k][j+k].getObject().getColor().equals(white))
                                        {
                                            break;
                                        }
                                        if(!board[i+k][j+k].is_empty && board[i+k][j+k].getObject().getColor().equals(black) && i+k != deletedKishKonItem.first && j+k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i+k == deletedKishKonItem.first && j+k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }


                                    k=1;
                                    while(i+j<8 && j-k>=0)
                                    {

                                        if(!board[i+k][j-k].is_empty && board[i+k][j-k].getObject().getColor().equals(white))
                                        {
                                            break;
                                        }
                                        if(!board[i+k][j-k].is_empty && board[i+k][j-k].getObject().getColor().equals(black) && i+k != deletedKishKonItem.first && j-k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i+k == deletedKishKonItem.first && j-k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }

                                    k=1;
                                    while(i-j>=0 && j-k>=0)
                                    {

                                        if(!board[i-k][j-k].is_empty && board[i-k][j-k].getObject().getColor().equals(white))
                                        {
                                            break;
                                        }
                                        if(!board[i-k][j-k].is_empty && board[i-k][j-k].getObject().getColor().equals(black) && i-k != deletedKishKonItem.first && j-k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i-k == deletedKishKonItem.first && j-k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }

                                        k++;
                                    }
                                    k=1;
                                    while(i-j>=0 && j+k<8)
                                    {

                                        if(!board[i-k][j+k].is_empty && board[i-k][j+k].getObject().getColor().equals(white))
                                        {
                                            break;
                                        }
                                        if(!board[i-k][j+k].is_empty && board[i-k][j+k].getObject().getColor().equals(black) && i-k != deletedKishKonItem.first && j+k != deletedKishKonItem.second)
                                        {
                                            break;
                                        }

                                        if(i-k == deletedKishKonItem.first && j+k == deletedKishKonItem.second)
                                        {
                                            deletedItem = board[deletedKishKonItem.first][deletedKishKonItem.second].getObject();
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(board[i][j].getObject());
                                            board[i][j].setObject(null);
                                            board[i][j].is_empty = true;

                                            if (is_kish(white)) {
                                                //undo
                                                board[i][j].setObject(board[deletedKishKonItem.first][deletedKishKonItem.second].getObject());
                                                board[i][j].is_empty = false;
                                                board[deletedKishKonItem.first][deletedKishKonItem.second].setObject(deletedItem);
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                        k++;
                                    }
                                }

                                if(board[i][j].getObject().getName().equals("Horse"))
                                {
                                    if(deletedKishKonItem.first>1 && deletedKishKonItem.second>0 &&
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].getObject().getColor().equals(white) &&
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        if(is_kish(white))
                                        {
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second-1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first>1 && deletedKishKonItem.second<7 &&
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].getObject().getColor().equals(white) &&
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        if(is_kish(white))
                                        {
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first - 2][deletedKishKonItem.second+1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first<6 && deletedKishKonItem.second>0 &&
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].getObject().getColor().equals(white) &&
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        if(is_kish(white))
                                        {
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second-1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first<6 && deletedKishKonItem.second<7 &&
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].getObject().getColor().equals(white) &&
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        if(is_kish(white))
                                        {
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first + 2][deletedKishKonItem.second+1].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first>0 && deletedKishKonItem.second>1 &&
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].getObject().getColor().equals(white) &&
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        if(is_kish(white))
                                        {
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second-2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first>0 && deletedKishKonItem.second<6 &&
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].getObject().getColor().equals(white) &&
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        if(is_kish(white))
                                        {
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first - 1][deletedKishKonItem.second+2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first<7 && deletedKishKonItem.second>1 &&
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].getObject().getColor().equals(white) &&
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        if(is_kish(white))
                                        {
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                            return false;
                                        }
                                    }

                                    if(deletedKishKonItem.first<7 && deletedKishKonItem.second<6 &&
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second-+2].getObject().getColor().equals(white) &&
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second+2].getObject().getName().equals(Horse))
                                    {
                                        board[deletedKishKonItem.first + 1][deletedKishKonItem.second+2].is_empty = true;
                                        board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(white);
                                        if(is_kish(white))
                                        {
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second+2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                        }
                                        else
                                        {
                                            board[deletedKishKonItem.first + 1][deletedKishKonItem.second+2].is_empty = false;
                                            board[deletedKishKonItem.first][deletedKishKonItem.second].getObject().setColor(black);
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Minister) ||
                        board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Castle)||
                        board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Elephant))
                {




                    List<Pair<Integer , Integer>> path = new ArrayList<>();


                    //finding path
                    if(board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Castle) ||
                            board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Minister))
                    {
                        if(xx == kishKonItem.first)
                        {
                            if(yy>kishKonItem.second)
                            {
                                for(int i=kishKonItem.second ; i<yy;i++)
                                {
                                    path.add(new Pair<Integer, Integer>(xx , i));
                                }
                            }

                            if(yy<kishKonItem.second)
                            {
                                for(int i=kishKonItem.second ; i>yy;i--)
                                {
                                    path.add(new Pair<Integer, Integer>(xx , i));
                                }
                            }
                        }

                        if(yy == kishKonItem.second)
                        {
                            if(xx>kishKonItem.first)
                            {
                                for(int i=kishKonItem.first ; i<xx;i++)
                                {
                                    path.add(new Pair<Integer, Integer>(i , yy));
                                }
                            }

                            if(xx<kishKonItem.first)
                            {
                                for(int i=kishKonItem.first ; i>xx;i--)
                                {
                                    path.add(new Pair<Integer, Integer>(i , yy));
                                }
                            }


                        }
                    }

                    if(board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Elephant) ||
                            board[kishKonItem.first][kishKonItem.second].getObject().getName().equals(Minister))
                    {
                        float tanjant = (float)(kishKonItem.second - yy)/ (float)(kishKonItem.first - xx);
                        if(Math.abs(tanjant) == 1)
                        {
                            //passed
                            if(kishKonItem.first<xx)
                            {
                                if(kishKonItem.second<yy)
                                {
                                    for(int i = kishKonItem.first,j = kishKonItem.second ; i<xx ;i++ , j++)
                                    {
                                        path.add(new Pair<Integer, Integer>(i , j));
                                    }
                                }

                                if(kishKonItem.second>yy)
                                {
                                    for(int i = kishKonItem.first,j = kishKonItem.second ; i<xx ;i++ , j--)
                                    {
                                        path.add(new Pair<Integer, Integer>(i , j));
                                    }
                                }
                            }

                            //passed
                            if(kishKonItem.first>xx)
                            {
                                if(kishKonItem.second<yy)
                                {
                                    for(int i = xx+1,j = yy-1; i<=kishKonItem.first ;i++ , j--)
                                    {
                                        path.add(new Pair<Integer, Integer>(i , j));
                                    }
                                }

                                if(kishKonItem.second>yy)
                                {
                                    for(int i = xx+1,j = yy+1 ; i<=kishKonItem.first ;i++ , j++)
                                    {
                                        path.add(new Pair<Integer, Integer>(i , j));
                                    }
                                }
                            }
                        }

                    }

                    //checking beads to unmat

                    boolean bekeshBiroon = false;
                    Bead temp = null;
                    for(int i=0;i<8;i++)
                    {
                        for(int j=0;j<8;j++)
                        {

                            if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                            {
                                if(board[i][j].getObject().getColor().equals(Castle) || board[i][j].getObject().getColor().equals(Minister))
                                {
                                    for(Pair<Integer , Integer> k:path)
                                    {
                                        if(k.first == i)
                                        {
                                            if(k.second<j)
                                            {
                                                for(int keke = k.second+1 ;keke <j; keke++)
                                                {
                                                    if(!board[i][keke].is_empty)
                                                    {
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                }
                                                board[i][j].is_empty = true;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                if(is_kish(black))
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    bekeshBiroon = true;
                                                    break;
                                                }
                                                else
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    return false;
                                                }
                                            }
                                            if(k.second>j)
                                            {
                                                for(int keke = k.second-1 ;keke >j; keke--)
                                                {
                                                    if(!board[i][keke].is_empty)
                                                    {
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                }
                                                board[i][j].is_empty = true;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                if(is_kish(white))
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    bekeshBiroon = true;
                                                    break;
                                                }
                                                else
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    return false;
                                                }
                                            }

                                        }

                                        if(k.second == j)
                                        {
                                            if(k.first<i)
                                            {
                                                for(int keke = k.first+1 ;keke <i; keke++)
                                                {
                                                    if(!board[keke][j].is_empty)
                                                    {
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                }
                                                board[i][j].is_empty = true;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                if(is_kish(white))
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    bekeshBiroon = true;
                                                    break;
                                                }
                                                else
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    return false;
                                                }
                                            }
                                            if(k.first>i)
                                            {
                                                for(int keke = k.first-1 ;keke >i; keke--)
                                                {
                                                    if(!board[keke][j].is_empty)
                                                    {
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                }
                                                board[i][j].is_empty = true;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                if(is_kish(white))
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    bekeshBiroon = true;
                                                    break;
                                                }
                                                else
                                                {
                                                    board[i][j].is_empty = false;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                    return false;
                                                }
                                            }
                                        }
                                        if (bekeshBiroon)
                                        {
                                            bekeshBiroon = false;
                                            break;
                                        }
                                    }
                                }

                                if(board[i][j].getObject().getColor().equals(Minister) || board[i][j].getObject().getColor().equals(Elephant))
                                {
                                    float tanjant ;
                                    for (Pair<Integer , Integer>k:path)
                                    {
                                        if(tan(k.second , j , k.first , i) == 1)
                                        {
                                            ////////////////////////////////////////////////////////////////
                                            if(i<k.first)
                                            {
                                                if(j<k.second)
                                                {
                                                    for(int m = i+1,n = j+1 ; m<k.first ;m++ , n++)
                                                    {
                                                        if(!board[m][n].is_empty)
                                                        {
                                                            bekeshBiroon = true;
                                                            break;
                                                        }
                                                    }
                                                    board[i][j].is_empty = true;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    if(is_kish(white))
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                        return false;
                                                    }
                                                }

                                                if(j>k.second)
                                                {
                                                    for(int m = i+1,n = j-1 ; m<k.first ;m++ , n--)
                                                    {
                                                        if(!board[m][n].is_empty)
                                                        {
                                                            bekeshBiroon = true;
                                                            break;
                                                        }
                                                    }
                                                    board[i][j].is_empty = true;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    if(is_kish(white))
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                        return false;
                                                    }
                                                }
                                            }

                                            if(i>k.first)
                                            {
                                                if(j<k.second)
                                                {
                                                    for(int m = i-1,n = j+1 ; m<k.first ;m-- , n++)
                                                    {
                                                        if(!board[m][n].is_empty)
                                                        {
                                                            bekeshBiroon = true;
                                                            break;
                                                        }
                                                    }
                                                    board[i][j].is_empty = true;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    if(is_kish(white))
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                        return false;
                                                    }
                                                }

                                                if(j>k.second)
                                                {
                                                    for(int m = i-1,n = j-1 ; m<k.first ;m-- , n--)
                                                    {
                                                        if(!board[m][n].is_empty)
                                                        {
                                                            bekeshBiroon = true;
                                                            break;
                                                        }
                                                    }
                                                    board[i][j].is_empty = true;
                                                    board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                                    if(is_kish(white))
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                        bekeshBiroon = true;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        board[i][j].is_empty = false;
                                                        board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                        return false;
                                                    }
                                                }
                                            }
                                        }
                                        if (bekeshBiroon)
                                        {
                                            bekeshBiroon = false;
                                            break;
                                        }
                                    }
                                }
                                if(board[i][j].getObject().getColor().equals(Horse))
                                {
                                    for(Pair<Integer , Integer>k:path)
                                    {
                                        if(k.first == i-2 && k.second == j-1)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                            if(is_kish(white))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                return false;
                                            }
                                        }

                                        if(k.first == i-2 && k.second == j+1)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                            if(is_kish(white))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                return false;
                                            }
                                        }

                                        if(k.first == i+2 && k.second == j-1)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                            if(is_kish(white))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                return false;
                                            }
                                        }

                                        if(k.first == i+2 && k.second == j+1)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                            if(is_kish(white))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                return false;
                                            }
                                        }

                                        if(k.first == i-1 && k.second == j-2)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                            if(is_kish(white))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                return false;
                                            }
                                        }

                                        if(k.first == i-1 && k.second == j+2)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                            if(is_kish(white))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                return false;
                                            }
                                        }

                                        if(k.first == i+1 && k.second == j-2)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                            if(is_kish(white))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                return false;
                                            }
                                        }

                                        if(k.first == i+1 && k.second == j+2)
                                        {
                                            board[i][j].is_empty = true;
                                            board[kishKonItem.first][kishKonItem.second].getObject().setColor(white);
                                            if(is_kish(white))
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                break;
                                            }
                                            else
                                            {
                                                board[i][j].is_empty = false;
                                                board[kishKonItem.first][kishKonItem.second].getObject().setColor(black);
                                                return false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private float tan(Integer second, int j, Integer first, int i) {
        return Math.abs((float)(second-j) /(float)(first - i));
    }

    private void imageButtonInitializer() {
        int ID ;
        String SID;
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++) {
                SID="_"+i+""+j;
                ID=getResources().getIdentifier(SID,"id",getPackageName());
                guiBoard[i][j] = (ImageButton) findViewById(ID);
            }
    }

    public void setBeadsFor_first() {


        board[0][0].setObject(new Bead(black ,1,0,0 ,"Castle",R.drawable.castleb));
        board[0][1].setObject(new Bead(black ,1,0,1 ,"Horse",R.drawable.horseb));
        board[0][2].setObject(new Bead(black ,1,0,2 ,"Elephant",R.drawable.elephantb));
        board[0][3].setObject(new Bead(black ,1,0,3 ,"King",R.drawable.kingb));
        board[0][4].setObject(new Bead(black ,1,0,4 ,"Minister",R.drawable.ministerb));
        board[0][5].setObject(new Bead(black ,2,0,5 ,"Elephant",R.drawable.elephantb));
        board[0][6].setObject(new Bead(black ,2,0,6 ,"Horse",R.drawable.horseb));
        board[0][7].setObject(new Bead(black ,2,0,7 ,"Castle",R.drawable.castleb));
        board[1][0].setObject(new Bead(black ,1,1,0 ,"Soldier",R.drawable.soldierb));
        board[1][1].setObject(new Bead(black ,2,1,1 ,"Soldier",R.drawable.soldierb));
        board[1][2].setObject(new Bead(black ,3,1,2 ,"Soldier",R.drawable.soldierb));
        board[1][3].setObject(new Bead(black ,4,1,3 ,"Soldier",R.drawable.soldierb));
        board[1][4].setObject(new Bead(black ,5,1,4 ,"Soldier",R.drawable.soldierb));
        board[1][5].setObject(new Bead(black ,6,1,5 ,"Soldier",R.drawable.soldierb));
        board[1][6].setObject(new Bead(black ,7,1,6 ,"Soldier",R.drawable.soldierb));
        board[1][7].setObject(new Bead(black ,8,1,7 ,"Soldier",R.drawable.soldierb));

        for(int i=0;i<2;i++)
        {
            for(int j=0;j<8;j++)
            {
                board[i][j].is_empty = false;
                guiBoard[i][j].setEnabled(false);
            }
        }

        board[7][0].setObject(new Bead(white ,1,7,0 ,"Castle",R.drawable.castle_w));
        board[7][1].setObject(new Bead(white ,1,7,1 ,"Horse",R.drawable.horse_w));
        board[7][2].setObject(new Bead(white ,1,7,2 ,"Elephant",R.drawable.elephant_w));
        board[7][3].setObject(new Bead(white ,1,7,3 ,"Minister",R.drawable.minister_w));
        board[7][4].setObject(new Bead(white ,1,7,4 ,"King",R.drawable.king_w));
        board[7][5].setObject(new Bead(white ,2,7,5 ,"Elephant",R.drawable.elephant_w));
        board[7][6].setObject(new Bead(white ,2,7,6 ,"Horse",R.drawable.horse_w));
        board[7][7].setObject(new Bead(white ,2,7,7 ,"Castle",R.drawable.castle_w));
        board[6][0].setObject(new Bead(white ,1,6,0 ,"Soldier",R.drawable.soldier_w));
        board[6][1].setObject(new Bead(white ,2,6,1 ,"Soldier",R.drawable.soldier_w));
        board[6][2].setObject(new Bead(white ,3,6,2 ,"Soldier",R.drawable.soldier_w));
        board[6][3].setObject(new Bead(white ,4,6,3 ,"Soldier",R.drawable.soldier_w));
        board[6][4].setObject(new Bead(white ,5,6,4 ,"Soldier",R.drawable.soldier_w));
        board[6][5].setObject(new Bead(white ,6,6,5 ,"Soldier",R.drawable.soldier_w));
        board[6][6].setObject(new Bead(white ,7,6,6 ,"Soldier",R.drawable.soldier_w));
        board[6][7].setObject(new Bead(white ,8,6,7 ,"Soldier",R.drawable.soldier_w));

        for(int i=6;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                board[i][j].is_empty = false;

            }
        }

    }

    public int ToInt(String tag , int i) {
        int id = 0;
        id = tag.charAt(i)-48;
        return id;

    }

    public void decision(int x , int y) {
        if (clickedFirst)
        {

            switch (board[x][y].getObject().getName()) {
                case "Castle": {
                    moveCastle(x, y);
                }
                break;

                case "Horse": {
                    moveHorse(x,y);
                }
                break;

                case "Elephant": {
                    moveElephant(x , y);
                }
                break;

                case "Minister": {
                    moveMinister(x,y);
                }
                break;

                case "King": {
                    moveKing(x , y);
                }
                break;

                case "Soldier": {

                    if(board[x][y].getObject().getColor().equals(black) && x==7)  {
                        Toast.makeText(PlayActivity.this,getString(R.string.wrong_choose),Toast.LENGTH_LONG).show();
                        if(!whiteTurn)
                        {
                            for(int i=0;i<8;i++)
                            {
                                for(int j=0;j<8;j++)
                                {
                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                        guiBoard[i][j].setEnabled(false);
                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                                        guiBoard[i][j].setEnabled(true);
                                }
                            }
                        }
                        if(whiteTurn) {
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals("black"))
                                        guiBoard[i][j].setEnabled(false);
                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                        guiBoard[i][j].setEnabled(true);
                                }
                            }
                        }
                        return ;
                    }
                    if(board[x][y].getObject().getColor().equals(white) && x==0)  {
                        Toast.makeText(PlayActivity.this,R.string.wrong_choose,Toast.LENGTH_LONG).show();
                        if(!whiteTurn)
                        {
                            for(int i=0;i<8;i++)
                            {
                                for(int j=0;j<8;j++)
                                {
                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                        guiBoard[i][j].setEnabled(false);
                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                                        guiBoard[i][j].setEnabled(true);
                                }
                            }
                        }
                        if(whiteTurn) {
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals("black"))
                                        guiBoard[i][j].setEnabled(false);
                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                        guiBoard[i][j].setEnabled(true);
                                }
                            }
                        }
                        return ;
                    }
                    moveSoldier(x, y);
                }
                break;
            }
        }
        else
        {
            secondMove(x , y);
        }
    }

    public void cellsClicked(View view){
        int id = view .getId() ;
        String tag = view.getTag().toString();
        int x , y;
        x = ToInt(tag , 0);
        y = ToInt(tag , 1);



        if(!board[x][y].is_empty && clickedFirst)
        {
            btnSetEnabled(false);
            decision(x, y);
        }
        else if(!clickedFirst)
        {
            decision(x , y);
        }
    }

    private void btnSetEnabled(boolean gholmorad) {

        for(int i=0 ;i<8 ;i++)
            for(int j=0 ;j <8 ;j++)
                guiBoard[i][j].setEnabled(gholmorad);

    }

    private void secondMove(int x, int y) {


        undo.add(new Move(board[xForFirst][yForFirst].getObject() , board[x][y].getObject() ,
                xForFirst , yForFirst , x , y)) ;
        boolean check_if_first = false;

        if(xForFirst == 1 || xForFirst == 6)
            check_if_first = true;



        Bead deleted = null;
        if(!board[x][y].is_empty)
        {
            deleted = board[x][y].getObject();
        }

        clickedFirst = true;
        board[x][y].setObject(board[xForFirst][yForFirst].getObject());
        board[x][y].is_empty = false;
        board[xForFirst][yForFirst].setObject(null);
        board[xForFirst][yForFirst].is_empty = true;

        if(whiteTurn && is_kish(white))
        {
            Toast.makeText(this , getString(R.string.not_alowed_movement), Toast.LENGTH_LONG).show();
            board[xForFirst][yForFirst].setObject(board[x][y].getObject());
            board[x][y].setObject(null);
            board[xForFirst][yForFirst].is_empty = false;
            board[x][y].is_empty = true;

            if(check_if_first)
                board[xForFirst][yForFirst].getObject().first_move = true;

            if(deleted !=null)
            {
                board[x][y].setObject(deleted);
                board[x][y].is_empty = false;
            }

            for(int i=0 ; i<backlighted.size();i++)
            {
                guiBoard[backlighted.get(i).first][backlighted.get(i).second].setBackgroundResource(android.R.color.transparent);
            }

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals("black"))
                            guiBoard[i][j].setEnabled(false);
                        if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                            guiBoard[i][j].setEnabled(true);
                    }
                }

            return;
        }

        if (blackTurn && is_kish(black))
        {
            Toast.makeText(this , getString(R.string.not_alowed_movement), Toast.LENGTH_LONG).show();
            board[xForFirst][yForFirst].setObject(board[x][y].getObject());
            board[x][y].setObject(null);
            board[xForFirst][yForFirst].is_empty = false;
            board[x][y].is_empty = true;

            if(check_if_first)
                board[xForFirst][yForFirst].getObject().first_move = true;


            if(deleted !=null)
            {
                board[x][y].setObject(deleted);
                board[x][y].is_empty = false;
            }

            for(int i=0 ; i<backlighted.size();i++)
            {
                guiBoard[backlighted.get(i).first][backlighted.get(i).second].setBackgroundResource(android.R.color.transparent);
            }


                for(int i=0;i<8;i++)
                {
                    for(int j=0;j<8;j++)
                    {
                        if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                            guiBoard[i][j].setEnabled(false);
                        if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                            guiBoard[i][j].setEnabled(true);
                    }
                }


            return;
        }




        for(int i=0 ; i<backlighted.size();i++)
        {
            guiBoard[backlighted.get(i).first][backlighted.get(i).second].setBackgroundResource(android.R.color.transparent);
        }

        /*guiBoard[xForFirst][yForFirst].setImageResource(0);
        guiBoard[x][y].setImageResource(board[x][y].getObject().getImageId());*/

        //FOR ANIMATING

        guiBoard[x][y].setAlpha(0f);
        guiBoard[x][y].setImageResource(board[x][y].getObject().getImageId());
        guiBoard[xForFirst][yForFirst].animate().alpha(0f).setDuration(750L).start();
        guiBoard[x][y].animate().alpha(1f).setDuration(750L).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                guiBoard[xForFirst][yForFirst].setImageResource(0);
                //btnSetEnabled(true);
            }
        }, 750L);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                guiBoard[xForFirst][yForFirst].setAlpha(1f);
            }
        }, 755L);



        if(whiteTurn)
        {
            whiteTurn = false;
            blackTurn = true;
        }
        else
        {
            whiteTurn = true;
            blackTurn = false;
        }

        btnSetEnabled(true);

        if(board[x][y].getObject().getName().equals("Soldier"))
        {
            if(board[x][y].getObject().getColor().equals(white))
            {
                if(x == 0)
                {
                    soldierInEnd = new Pair<>(x , y);
                    endchange.show();
                }
            }
            else if(board[x][y].getObject().getColor().equals(black))
            {
                if(x == 7)
                {
                    soldierInEnd = new Pair<>(x , y);
                    endchange.show();
                }
            }
        }

        if(!whiteTurn)
        {
            for(int i=0;i<8;i++)
            {
                for(int j=0;j<8;j++)
                {
                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                        guiBoard[i][j].setEnabled(false);
                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                        guiBoard[i][j].setEnabled(true);
                }
            }
        }
        if(whiteTurn) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals("black"))
                        guiBoard[i][j].setEnabled(false);
                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                        guiBoard[i][j].setEnabled(true);
                }
            }
        }

        if(is_kish(black))
        {
            if(is_mat(black))
            {
                Toast.makeText(this,getString(R.string.black_kished_and_muted),Toast.LENGTH_LONG).show();
            }
            else
            Toast.makeText(this,getString(R.string.black_king_is_kish),Toast.LENGTH_LONG).show();
        }


        if(is_kish(white))
        {

            if(is_mat(white))
            {
                Toast.makeText(this,getString(R.string.white_kished_and_muted),Toast.LENGTH_LONG).show();
            }

            Toast.makeText(this,getString(R.string.white_king_is_kish),Toast.LENGTH_LONG).show();

        }
    }

    private void moveKing(int x , int y) {
        xForFirst = x;
        yForFirst = y;
        clickedFirst = false;

        if((x<7 && !board[x+1][y].is_empty && board[x+1][y].getObject().getColor().equals(board[x][y].getObject().getColor()) )|| x==7)
        {
            if((x>0 && !board[x-1][y].is_empty && board[x-1][y].getObject().getColor().equals(board[x][y].getObject().getColor())) || x==0)
            {
                if((y<7 && !board[x][y+1].is_empty && board[x][y+1].getObject().getColor().equals(board[x][y].getObject().getColor())) ||y==7 )
                {
                    if((y>0 && !board[x][y-1].is_empty && board[x][y-1].getObject().getColor().equals(board[x][y].getObject().getColor()))||y==0)
                    {
                        if((x<7 &&y<7 && !board[x+1][y+1].is_empty && board[x+1][y+1].getObject().getColor().equals(board[x][y].getObject().getColor()) )|| (x==7 || y==7))
                        {
                            if((x<7 && y>0 && !board[x+1][y-1].is_empty && board[x+1][y-1].getObject().getColor().equals(board[x][y].getObject().getColor())) || (x==7 || y==0))
                            {
                                if((y<7 && x>0 && !board[x-1][y+1].is_empty && board[x][y+1].getObject().getColor().equals(board[x][y].getObject().getColor())) ||(y==7 || x==0) )
                                {
                                    if((y>0 && x>0 && !board[x-1][y-1].is_empty && board[x][y-1].getObject().getColor().equals(board[x][y].getObject().getColor()))||(y==0 || x==0))
                                    {
                                        if(!whiteTurn)
                                        {
                                            for(int i=0;i<8;i++)
                                            {
                                                for(int j=0;j<8;j++)
                                                {
                                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                                        guiBoard[i][j].setEnabled(false);
                                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                                                        guiBoard[i][j].setEnabled(true);
                                                }
                                            }
                                        }
                                        if(whiteTurn) {
                                            for (int i = 0; i < 8; i++) {
                                                for (int j = 0; j < 8; j++) {
                                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals("black"))
                                                        guiBoard[i][j].setEnabled(false);
                                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                                        guiBoard[i][j].setEnabled(true);
                                                }
                                            }
                                        }
                                        clickedFirst = true;
                                        return ;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        if(board[x][y].getObject().getColor().equals(white))
        {

            if (x <7 && board[x + 1][y].is_empty) {
                guiBoard[x + 1][y].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x + 1][y].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x + 1, y));
            }
            if (x <7 && !board[x + 1][y].is_empty && board[x + 1][y].getObject().getColor().equals(black))
            {
                guiBoard[x+1][y].setEnabled(true);
                guiBoard[x+1][y].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x+1 , y));
            }

            if (x>0 && board[x -1][y].is_empty) {
                guiBoard[x - 1][y].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x - 1][y].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x - 1, y));
            }
            if (x>0 && !board[x - 1][y].is_empty && board[x - 1][y].getObject().getColor().equals(black))
            {
                guiBoard[x-1][y].setEnabled(true);
                guiBoard[x-1][y].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x-1 , y));
            }

            if ( y<7 && board[x][y+1].is_empty) {
                guiBoard[x][y+1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x][y+1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x, y+1));
            }
            if (y<7 && !board[x][y+1].is_empty && board[x][y+1].getObject().getColor().equals(black))
            {
                guiBoard[x][y+1].setEnabled(true);
                guiBoard[x][y+1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x , y+1));
            }


            if ( y>0 && board[x][y-1].is_empty) {
                guiBoard[x][y-1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x][y-1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x, y-1));
            }
            if (y>0 && !board[x][y-1].is_empty && board[x][y-1].getObject().getColor().equals(black))
            {
                guiBoard[x][y-1].setEnabled(true);
                guiBoard[x][y-1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x , y-1));
            }


            if (x<7 && y<7 && board[x+1][y+1].is_empty) {
                guiBoard[x+1][y+1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x+1][y+1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x+1, y+1));
            }
            if (x<7 && y<7 && !board[x+1][y+1].is_empty && board[x+1][y+1].getObject().getColor().equals(black))
            {
                guiBoard[x+1][y+1].setEnabled(true);
                guiBoard[x+1][y+1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x+1 , y+1));
            }

            if (x<7 && y>0 && board[x+1][y-1].is_empty) {
                guiBoard[x+1][y-1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x+1][y-1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x+1, y-1));
            }
            if (x<7 && y>0 && !board[x+1][y-1].is_empty && board[x+1][y-1].getObject().getColor().equals(black))
            {
                guiBoard[x+1][y-1].setEnabled(true);
                guiBoard[x+1][y-1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x+1 , y-1));
            }

            if (x>0 && y<7 && board[x-1][y+1].is_empty) {
                guiBoard[x-1][y+1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x-1][y+1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x-1, y+1));
            }
            if (x>0 && y<7 && !board[x-1][y+1].is_empty && board[x-1][y+1].getObject().getColor().equals(black))
            {
                guiBoard[x-1][y+1].setEnabled(true);
                guiBoard[x-1][y+1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x-1 , y+1));

            }

            if (x>0 && y>0 && board[x-1][y-1].is_empty) {
                guiBoard[x-1][y-1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x-1][y-1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x-1, y-1));
            }
            if (x>0 && y>0 && !board[x-1][y-1].is_empty && board[x-1][y-1].getObject().getColor().equals(black))
            {
                guiBoard[x-1][y-1].setEnabled(true);
                guiBoard[x-1][y-1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x-1 , y-1));
            }



        }

        if(board[x][y].getObject().getColor().equals(black))
        {

            if (x <7 && board[x + 1][y].is_empty) {
                guiBoard[x + 1][y].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x + 1][y].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x + 1, y));
            }
            if (x <7 && !board[x + 1][y].is_empty && board[x + 1][y].getObject().getColor().equals(white))
            {
                guiBoard[x+1][y].setEnabled(true);
                guiBoard[x+1][y].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x+1 , y));
            }

            if (x>0 && board[x -1][y].is_empty) {
                guiBoard[x - 1][y].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x - 1][y].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x - 1, y));
            }
            if (x>0 && !board[x - 1][y].is_empty && board[x - 1][y].getObject().getColor().equals(white))
            {
                guiBoard[x-1][y].setEnabled(true);
                guiBoard[x-1][y].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x-1 , y));
            }

            if ( y<7 && board[x][y+1].is_empty) {
                guiBoard[x][y+1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x][y+1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x, y+1));
            }
            if (y<7 && !board[x][y+1].is_empty && board[x][y+1].getObject().getColor().equals(white))
            {
                guiBoard[x][y+1].setEnabled(true);
                guiBoard[x][y+1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x , y+1));
            }


            if ( y>0 && board[x][y-1].is_empty) {
                guiBoard[x][y-1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x][y-1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x, y-1));
            }
            if (y>0 && !board[x][y-1].is_empty && board[x][y-1].getObject().getColor().equals(white))
            {
                guiBoard[x][y-1].setEnabled(true);
                guiBoard[x][y-1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x , y-1));
            }


            if (x<7 && y<7 && board[x+1][y+1].is_empty) {
                guiBoard[x+1][y+1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x+1][y+1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x+1, y+1));
            }
            if (x<7 && y<7 && !board[x+1][y+1].is_empty && board[x+1][y+1].getObject().getColor().equals(white))
            {
                guiBoard[x+1][y+1].setEnabled(true);
                guiBoard[x+1][y+1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x+1 , y+1));
            }

            if (x<7 && y>0 && board[x+1][y-1].is_empty) {
                guiBoard[x+1][y-1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x+1][y-1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x+1, y-1));
            }
            if (x<7 && y>0 && !board[x+1][y-1].is_empty && board[x+1][y-1].getObject().getColor().equals(white))
            {
                guiBoard[x+1][y-1].setEnabled(true);
                guiBoard[x+1][y-1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x+1 , y-1));
            }

            if (x>0 && y<7 && board[x-1][y+1].is_empty) {
                guiBoard[x-1][y+1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x-1][y+1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x-1, y+1));
            }
            if (x>0 && y<7 && !board[x-1][y+1].is_empty && board[x-1][y+1].getObject().getColor().equals(white))
            {
                guiBoard[x-1][y+1].setEnabled(true);
                guiBoard[x-1][y+1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x-1 , y+1));

            }

            if (x>0 && y>0 && board[x-1][y-1].is_empty) {
                guiBoard[x-1][y-1].setBackgroundResource(R.drawable.blue_backlight);
                guiBoard[x-1][y-1].setEnabled(true);
                backlighted.add(new Pair<Integer, Integer>(x-1, y-1));
            }
            if (x>0 && y>0 && !board[x-1][y-1].is_empty && board[x-1][y-1].getObject().getColor().equals(white))
            {
                guiBoard[x-1][y-1].setEnabled(true);
                guiBoard[x-1][y-1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<Integer, Integer>(x-1 , y-1));
            }
        }
    }


    private void moveHorse(int x, int y) {
        xForFirst = x ;
        yForFirst = y ;
        clickedFirst = false ;
        if(board[x][y].getObject().getColor().equals(black))
        {
            if(x<6 && y<7 )
            {
                if(!board[x+2][y+1].is_empty &&board[x+2][y+1].getObject().getColor().equals(white))
                {
                    guiBoard[x+2][y+1].setEnabled(true);
                    guiBoard[x+2][y+1].setBackgroundResource(R.drawable.red_backlight);
                }
                else if(board[x+2][y+1].is_empty)
                {
                    guiBoard[x+2][y+1].setEnabled(true);
                    guiBoard[x+2][y+1].setBackgroundResource(R.drawable.blue_backlight);
                }
                backlighted.add(new Pair<>(x+2,y+1));
            }

            if(x<6 && y>0)
            {
                if(!board[x+2][y-1].is_empty && board[x+2][y-1].getObject().getColor().equals(white))
                {
                    guiBoard[x+2][y-1].setEnabled(true);
                    guiBoard[x+2][y-1].setBackgroundResource(R.drawable.red_backlight);
                }
                else if(board[x+2][y-1].is_empty)
                {
                    guiBoard[x+2][y-1].setEnabled(true);
                    guiBoard[x+2][y-1].setBackgroundResource(R.drawable.blue_backlight);
                }
                backlighted.add(new Pair<>(x+2,y-1));
            }

            if(x<7 && y<6)
            {
                if(!board[x+1][y+2].is_empty &&board[x+1][y+2].getObject().getColor().equals(white))
                {
                    guiBoard[x+1][y+2].setEnabled(true);
                    guiBoard[x+1][y+2].setBackgroundResource(R.drawable.red_backlight);
                }
                if(board[x+1][y+2].is_empty)
                {
                    guiBoard[x+1][y+2].setEnabled(true);
                    guiBoard[x+1][y+2].setBackgroundResource(R.drawable.blue_backlight);
                }
                backlighted.add(new Pair<>(x+1,y+2));
            }

            if(x<7 && y>1)
            {
                if(!board[x+1][y-2].is_empty && board[x+1][y-2].getObject().getColor().equals(white))
                {
                    guiBoard[x+1][y-2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x+1][y-2].setEnabled(true);
                }
                if(board[x+1][y-2].is_empty)
                {
                    guiBoard[x+1][y-2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x+1][y-2].setEnabled(true);
                }
                backlighted.add(new Pair<>(x+1,y-2));
            }

            if(x>1 && y<7)
            {
                if(!board[x-2][y+1].is_empty && board[x-2][y+1].getObject().getColor().equals(white))
                {
                    guiBoard[x-2][y+1].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-2][y+1].setEnabled(true);
                }
                if(board[x-2][y+1].is_empty)
                {
                    guiBoard[x-2][y+1].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-2][y+1].setEnabled(true);
                }
                backlighted.add(new Pair<>(x-2,y+1));
            }

            if(x>1 && y>0)
            {
                if(!board[x-2][y-1].is_empty && board[x-2][y-1].getObject().getColor().equals(white))
                {
                    guiBoard[x-2][y-1].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-2][y-1].setEnabled(true);
                }
                if(board[x-2][y-1].is_empty)
                {
                    guiBoard[x-2][y-1].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-2][y-1].setEnabled(true);
                }
                backlighted.add(new Pair<>(x-2,y-1));
            }

            if(x>0 && y<6)
            {
                if(!board[x-1][y+2].is_empty && board[x-1][y+2].getObject().getColor().equals(white))
                {
                    guiBoard[x-1][y+2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-1][y+2].setEnabled(true);
                }
                if(board[x-1][y+2].is_empty)
                {
                    guiBoard[x-1][y+2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-1][y+2].setEnabled(true);
                }
                backlighted.add(new Pair<>(x-1,y+2));
            }

            if(x>0 && y>1)
            {
                if(!board[x-1][y-2].is_empty && board[x-1][y-2].getObject().getColor().equals(white))
                {
                    guiBoard[x-1][y-2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-1][y-2].setEnabled(true);
                }
                if(board[x-1][y-2].is_empty)
                {
                    guiBoard[x-1][y-2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-1][y-2].setEnabled(true);
                }
                backlighted.add(new Pair<>(x-1,y-2));
            }
        }


        if(board[x][y].getObject().getColor().equals(white))
        {
            if(x<6 && y<7 )
            {
                if(!board[x+2][y+1].is_empty &&board[x+2][y+1].getObject().getColor().equals(black))
                {
                    guiBoard[x+2][y+1].setEnabled(true);
                    guiBoard[x+2][y+1].setBackgroundResource(R.drawable.red_backlight);
                }
                else if(board[x+2][y+1].is_empty)
                {
                    guiBoard[x+2][y+1].setEnabled(true);
                    guiBoard[x+2][y+1].setBackgroundResource(R.drawable.blue_backlight);
                }
                backlighted.add(new Pair<>(x+2,y+1));
            }

            if(x<6 && y>0)
            {
                if(!board[x+2][y-1].is_empty && board[x+2][y-1].getObject().getColor().equals(black))
                {
                    guiBoard[x+2][y-1].setEnabled(true);
                    guiBoard[x+2][y-1].setBackgroundResource(R.drawable.red_backlight);
                }
                else if(board[x+2][y-1].is_empty)
                {
                    guiBoard[x+2][y-1].setEnabled(true);
                    guiBoard[x+2][y-1].setBackgroundResource(R.drawable.blue_backlight);
                }
                backlighted.add(new Pair<>(x+2,y-1));
            }

            if(x<7 && y<6)
            {
                if(!board[x+1][y+2].is_empty &&board[x+1][y+2].getObject().getColor().equals(black))
                {
                    guiBoard[x+1][y+2].setEnabled(true);
                    guiBoard[x+1][y+2].setBackgroundResource(R.drawable.red_backlight);
                }
                if(board[x+1][y+2].is_empty)
                {
                    guiBoard[x+1][y+2].setEnabled(true);
                    guiBoard[x+1][y+2].setBackgroundResource(R.drawable.blue_backlight);
                }
                backlighted.add(new Pair<>(x+1,y+2));
            }

            if(x<7 && y>1)
            {
                if(!board[x+1][y-2].is_empty && board[x+1][y-2].getObject().getColor().equals(black))
                {
                    guiBoard[x+1][y-2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x+1][y-2].setEnabled(true);
                }
                if(board[x+1][y-2].is_empty)
                {
                    guiBoard[x+1][y-2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x+1][y-2].setEnabled(true);
                }
                backlighted.add(new Pair<>(x+1,y-2));
            }

            if(x>1 && y<7)
            {
                if(!board[x-2][y+1].is_empty && board[x-2][y+1].getObject().getColor().equals(black))
                {
                    guiBoard[x-2][y+1].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-2][y+1].setEnabled(true);
                }
                if(board[x-2][y+1].is_empty)
                {
                    guiBoard[x-2][y+1].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-2][y+1].setEnabled(true);
                }
                backlighted.add(new Pair<>(x-2,y+1));
            }

            if(x>1 && y>0)
            {
                if(!board[x-2][y-1].is_empty && board[x-2][y-1].getObject().getColor().equals(black))
                {
                    guiBoard[x-2][y-1].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-2][y-1].setEnabled(true);
                }
                if(board[x-2][y-1].is_empty)
                {
                    guiBoard[x-2][y-1].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-2][y-1].setEnabled(true);
                }
                backlighted.add(new Pair<>(x-2,y-1));
            }

            if(x>0 && y<6)
            {
                if(!board[x-1][y+2].is_empty && board[x-1][y+2].getObject().getColor().equals(black))
                {
                    guiBoard[x-1][y+2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-1][y+2].setEnabled(true);
                }
                if(board[x-1][y+2].is_empty)
                {
                    guiBoard[x-1][y+2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-1][y+2].setEnabled(true);
                }
                backlighted.add(new Pair<>(x-1,y+2));
            }

            if(x>0 && y>1)
            {
                if(!board[x-1][y-2].is_empty && board[x-1][y-2].getObject().getColor().equals(black))
                {
                    guiBoard[x-1][y-2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-1][y-2].setEnabled(true);
                }
                if(board[x-1][y-2].is_empty)
                {
                    guiBoard[x-1][y-2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-1][y-2].setEnabled(true);
                }
                backlighted.add(new Pair<>(x-1,y-2));
            }
        }
    }

    private void moveElephant(int x , int y) {
        xForFirst = x;
        yForFirst = y;
        clickedFirst = false;

        if((x<7 &&y<7 && !board[x+1][y+1].is_empty && board[x+1][y+1].getObject().getColor().equals(board[x][y].getObject().getColor()) )|| (x==7 || y==7))
        {
            if((x<7 && y>0 && !board[x+1][y-1].is_empty && board[x+1][y-1].getObject().getColor().equals(board[x][y].getObject().getColor())) || (x==7 || y==0))
            {
                if((y<7 && x>0 && !board[x-1][y+1].is_empty && board[x-1][y+1].getObject().getColor().equals(board[x][y].getObject().getColor())) ||(y==7 || x==0) )
                {
                    if((y>0 && x>0 && !board[x-1][y-1].is_empty && board[x-1][y-1].getObject().getColor().equals(board[x][y].getObject().getColor()))||(y==0 || x==0))
                    {
                        if(!whiteTurn)
                        {
                            for(int i=0;i<8;i++)
                            {
                                for(int j=0;j<8;j++)
                                {
                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                        guiBoard[i][j].setEnabled(false);
                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                                        guiBoard[i][j].setEnabled(true);
                                }
                            }
                        }
                        if(whiteTurn) {
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals("black"))
                                        guiBoard[i][j].setEnabled(false);
                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                        guiBoard[i][j].setEnabled(true);
                                }
                            }
                        }
                        clickedFirst = true;

                        return ;
                    }
                }
            }
        }

        if(board[x][y].getObject().getColor().equals(black))
        {
            int i=1;
            while(x+i<8 && y+i<8)
            {
                if(board[x+i][y+i].is_empty)
                {
                    guiBoard[x+i][y+i].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x+i][y+i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x+i , y+i));
                }
                if(!board[x+i][y+i].is_empty && board[x+i][y+i].getObject().getColor().equals(white))
                {
                    guiBoard[x+i][y+i].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x+i][y+i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x+i , y+i));
                    break;
                }
                if(!board[x+i][y+i].is_empty && board[x+i][y+i].getObject().getColor().equals(black))
                {
                    break;
                }

                i++;
            }

            i=1;

            while(x+i<8 && y-i>=0)
            {
                if(board[x+i][y-i].is_empty)
                {
                    guiBoard[x+i][y-i].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x+i][y-i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x+i , y-i));
                }

                if(!board[x+i][y-i].is_empty && board[x+i][y-i].getObject().getColor().equals(white))
                {
                    guiBoard[x+i][y-i].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x+i][y-i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x+i , y-i));
                    break;
                }
                if(!board[x+i][y-i].is_empty && board[x+i][y-i].getObject().getColor().equals(black))
                {
                    break;
                }
                i++;
            }

            i=1;
            while(x-i>=0 && y-i>=0)
            {
                if(board[x-i][y-i].is_empty)
                {
                    guiBoard[x-i][y-i].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-i][y-i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x-i , y-i));
                }

                if(!board[x-i][y-i].is_empty && board[x-i][y-i].getObject().getColor().equals(white))
                {
                    guiBoard[x-i][y-i].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-i][y-i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x-i , y-i));
                    break;
                }
                if(!board[x-i][y-i].is_empty && board[x-i][y-i].getObject().getColor().equals(black))
                {
                    break;
                }
                i++;
            }
            i=1;
            while(x-i>=0 && y+i<8)
            {
                if(board[x-i][y+i].is_empty)
                {
                    guiBoard[x-i][y+i].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-i][y+i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x-i , y+i));
                }
                if(!board[x-i][y+i].is_empty && board[x-i][y+i].getObject().getColor().equals(white))
                {
                    guiBoard[x-i][y+i].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-i][y+i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x-i , y+i));
                    break;
                }
                if(!board[x-i][y+i].is_empty && board[x-i][y+i].getObject().getColor().equals(black))
                {
                    break;
                }

                i++;
            }

        }

        if(board[x][y].getObject().getColor().equals(white))
        {
            int i=1;
            while(x+i<8 && y+i<8)
            {
                if(board[x+i][y+i].is_empty)
                {
                    guiBoard[x+i][y+i].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x+i][y+i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x+i , y+i));
                }
                if(!board[x+i][y+i].is_empty && board[x+i][y+i].getObject().getColor().equals(black))
                {
                    guiBoard[x+i][y+i].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x+i][y+i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x+i , y+i));
                    break;
                }
                if(!board[x+i][y+i].is_empty && board[x+i][y+i].getObject().getColor().equals(white))
                {
                    break;
                }

                i++;
            }

            i=1;

            while(x+i<8 && y-i>=0)
            {
                if(board[x+i][y-i].is_empty)
                {
                    guiBoard[x+i][y-i].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x+i][y-i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x+i , y-i));
                }

                if(!board[x+i][y-i].is_empty && board[x+i][y-i].getObject().getColor().equals(black))
                {
                    guiBoard[x+i][y-i].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x+i][y-i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x+i , y-i));
                    break;
                }
                if(!board[x+i][y-i].is_empty && board[x+i][y-i].getObject().getColor().equals(white))
                {
                    break;
                }
                i++;
            }

            i=1;
            while(x-i>=0 && y-i>=0)
            {
                if(board[x-i][y-i].is_empty)
                {
                    guiBoard[x-i][y-i].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-i][y-i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x-i , y-i));
                }

                if(!board[x-i][y-i].is_empty && board[x-i][y-i].getObject().getColor().equals(black))
                {
                    guiBoard[x-i][y-i].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-i][y-i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x-i , y-i));
                    break;
                }
                if(!board[x-i][y-i].is_empty && board[x-i][y-i].getObject().getColor().equals(white))
                {
                    break;
                }
                i++;
            }
            i=1;
            while(x-i>=0 && y+i<8)
            {
                if(board[x-i][y+i].is_empty)
                {
                    guiBoard[x-i][y+i].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-i][y+i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x-i , y+i));
                }
                if(!board[x-i][y+i].is_empty && board[x-i][y+i].getObject().getColor().equals(black))
                {
                    guiBoard[x-i][y+i].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-i][y+i].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x-i , y+i));
                    break;
                }
                if(!board[x-i][y+i].is_empty && board[x-i][y+i].getObject().getColor().equals(white))
                {
                    break;
                }

                i++;
            }

        }
    }

    private void moveMinister(int x, int y) {
        boolean certificated = false;
        xForFirst = x ;
        yForFirst = y ;
        clickedFirst = false ;
        if(board[x][y].getObject().getColor().equals(black)) {
            //Amoodi paein
            for (int i = x + 1; i < 8; i++) {
                if (board[i][y].is_empty) {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i, y));
                }
                if (!board[i][y].is_empty && board[i][y].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i, y));
                    break;
                }
                if (!board[i][y].is_empty) break;

            }

            //Amoodi bala
            for (int i = x - 1; i >= 0; i--) {
                if (board[i][y].is_empty) {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i, y));
                }
                if (!board[i][y].is_empty && board[i][y].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i, y));
                    break;
                }
                if (!board[i][y].is_empty) break;
            }

            //Ofoghi rast
            for (int i = y + 1; i < 8; i++) {
                if (board[x][i].is_empty) {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(x, i));
                }
                if (!board[x][i].is_empty && board[x][i].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(x, i));
                    break;
                }
                if (!board[x][i].is_empty) break;
            }

            //Ofoghi chap
            for (int i = y - 1; i >= 0; i--) {
                if (board[x][i].is_empty) {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(x, i));
                }
                if (!board[x][i].is_empty && board[x][i].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(x, i));
                    break;
                }
                if (!board[x][i].is_empty) break;
            }

            //paeen rast
            for (int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
                if (board[i][j].is_empty) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i, j));
                }
                if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i, j));
                    break;
                }
                if (!board[i][j].is_empty) break;
            }

            //paeen chap
            for (int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
                if (board[i][j].is_empty) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i, j));
                }
                if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i, j));
                    break;
                }
                if (!board[i][j].is_empty) break;
            }

            //bala rast
            for (int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++) {
                if (board[i][j].is_empty) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i, j));
                }
                if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i, j));
                    break;
                }
                if (!board[i][j].is_empty) break;
            }

            //bala chap
            for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
                if (board[i][j].is_empty) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i, j));
                }
                if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i, j));
                    break;
                }
                if (!board[i][j].is_empty) break;
            }
        }
        if(board[x][y].getObject().getColor().equals(white))
        {
            //Amoodi paein
            for(int i=x+1 ; i<8 ;i++)
            {
                if(board[i][y].is_empty)
                {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i,y));
                }
                if(!board[i][y].is_empty && board[i][y].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i,y));
                    break;
                }
                if (!board[i][y].is_empty)  break;

            }

            //Amoodi bala
            for(int i=x-1 ; i>=0 ; i--)
            {
                if(board[i][y].is_empty)
                {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i,y));
                }
                if(!board[i][y].is_empty && board[i][y].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i,y));
                    break;
                }
                if(!board[i][y].is_empty)    break;
            }

            //Ofoghi rast
            for(int i=y+1 ;i <8 ;i++)
            {
                if(board[x][i].is_empty)
                {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(x,i));
                }
                if(!board[x][i].is_empty && board[x][i].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(x,i));
                    break;
                }
                if(!board[x][i].is_empty)    break;
            }

            //Ofoghi chap
            for(int i=y-1 ;i>=0 ; i--)
            {
                if(board[x][i].is_empty)
                {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(x,i));
                }
                if(!board[x][i].is_empty && board[x][i].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(x,i));
                    break;
                }
                if(!board[x][i].is_empty)    break;
            }

            //paeen rast
            for(int i=x+1 ,  j = y+1 ;i<8 && j<8 ;i++ , j++ )
            {
                if(board[i][j].is_empty)
                {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i,j));
                }
                if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i,j));
                    break;
                }
                if(!board[i][j].is_empty)    break;
            }

            //paeen chap
            for(int i=x+1 ,  j = y-1 ;i<8 && j>=0 ;i++ , j-- )
            {
                if(board[i][j].is_empty)
                {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i,j));
                }
                if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i,j));
                    break;
                }
                if(!board[i][j].is_empty)    break;
            }

            //bala rast
            for(int i=x-1 ,  j = y+1 ;i>=0 && j<8 ;i-- , j++)
            {
                if(board[i][j].is_empty)
                {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i,j));
                }
                if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i,j));
                    break;
                }
                if(!board[i][j].is_empty)    break;
            }

            //bala chap
            for(int i=x-1 ,  j = y-1 ;i>=0 && j>=0 ;i-- , j-- )
            {
                if(board[i][j].is_empty)
                {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(i,j));
                }
                if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backlighted.add(new Pair<>(i,j));
                    break;
                }
                if(!board[i][j].is_empty)    break;
            }
        }
        if(!certificated){
            Toast.makeText(PlayActivity.this,getString(R.string.wrong_choose),Toast.LENGTH_LONG).show();
            if(!whiteTurn)
            {
                for(int i=0;i<8;i++)
                {
                    for(int j=0;j<8;j++)
                    {
                        if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                            guiBoard[i][j].setEnabled(false);
                        if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                            guiBoard[i][j].setEnabled(true);
                    }
                }
            }
            if(whiteTurn) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals("black"))
                            guiBoard[i][j].setEnabled(false);
                        if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                            guiBoard[i][j].setEnabled(true);
                    }
                }
            }
            clickedFirst = true;
        }
    }

    private void moveCastle(int x , int y) {
        xForFirst = x;
        yForFirst = y;
        clickedFirst = false;

        if((x<7 && !board[x+1][y].is_empty && board[x+1][y].getObject().getColor().equals(board[x][y].getObject().getColor()) )|| x==7)
        {
            if((x>0 && !board[x-1][y].is_empty && board[x-1][y].getObject().getColor().equals(board[x][y].getObject().getColor())) || x==0)
            {
                if((y<7 && !board[x][y+1].is_empty && board[x][y+1].getObject().getColor().equals(board[x][y].getObject().getColor())) ||y==7 )
                {
                    if((y>0 && !board[x][y-1].is_empty && board[x][y-1].getObject().getColor().equals(board[x][y].getObject().getColor()))||y==0)
                    {
                        if(!whiteTurn)
                        {
                            for(int i=0;i<8;i++)
                            {
                                for(int j=0;j<8;j++)
                                {
                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                        guiBoard[i][j].setEnabled(false);
                                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                                        guiBoard[i][j].setEnabled(true);
                                }
                            }
                        }
                        if(whiteTurn) {
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals("black"))
                                        guiBoard[i][j].setEnabled(false);
                                    if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white))
                                        guiBoard[i][j].setEnabled(true);
                                }
                            }
                        }
                        clickedFirst = true;

                        return ;
                    }
                }
            }
        }

        if(board[x][y].getObject().getColor().equals("black"));
        {

            if(board[x][y].getObject().getColor().equals(white))
                System.out.println("hello");
            else
            {

                for (int i = x + 1; i < 8; i++) {
                    if (board[i][y].is_empty) {
                        guiBoard[i][y].setBackgroundResource(R.drawable.blue_backlight);
                        guiBoard[i][y].setEnabled(true);
                        backlighted.add(new Pair<Integer, Integer>(i, y));
                    }
                    if (!board[i][y].is_empty && board[i][y].getObject().getColor().equals(white)) {
                        guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                        guiBoard[i][y].setEnabled(true);
                        backlighted.add(new Pair<Integer, Integer>(i, y));

                        break;
                    }
                    if (!board[i][y].is_empty && board[i][y].getObject().getColor().equals(black)) {
                        break;
                    }
                }

                for (int i = x - 1; i >= 0; i--) {
                    if (board[i][y].is_empty) {
                        guiBoard[i][y].setBackgroundResource(R.drawable.blue_backlight);
                        guiBoard[i][y].setEnabled(true);
                        backlighted.add(new Pair<Integer, Integer>(i, y));
                    }
                    if (!board[i][y].is_empty && board[i][y].getObject().getColor().equals(white)) {
                        guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                        guiBoard[i][y].setEnabled(true);
                        backlighted.add(new Pair<Integer, Integer>(i, y));
                        System.out.println("didi ridiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
                        break;
                    }
                    if (!board[i][y].is_empty && board[i][y].getObject().getColor().equals(black)) {
                        break;
                    }
                }

                for (int j = y + 1; j < 8; j++) {
                    if (board[x][j].is_empty) {
                        guiBoard[x][j].setBackgroundResource(R.drawable.blue_backlight);
                        guiBoard[x][j].setEnabled(true);
                        backlighted.add(new Pair<Integer, Integer>(x, j));
                    }

                    if (!board[x][j].is_empty && board[x][j].getObject().getColor().equals(white)) {
                        guiBoard[x][j].setBackgroundResource(R.drawable.red_backlight);
                        guiBoard[x][j].setEnabled(true);
                        backlighted.add(new Pair<Integer, Integer>(x, j));
                        break;
                    }

                    if (!board[x][j].is_empty && board[x][j].getObject().getColor().equals(black)) {
                        break;
                    }
                }

                for (int j = y - 1; j >= 0; j--) {
                    if (board[x][j].is_empty) {
                        guiBoard[x][j].setBackgroundResource(R.drawable.blue_backlight);
                        guiBoard[x][j].setEnabled(true);
                        backlighted.add(new Pair<Integer, Integer>(x, j));
                    }

                    if (!board[x][j].is_empty && board[x][j].getObject().getColor().equals(white)) {
                        guiBoard[x][j].setBackgroundResource(R.drawable.red_backlight);
                        guiBoard[x][j].setEnabled(true);
                        backlighted.add(new Pair<Integer, Integer>(x, j));
                        break;
                    }
                    if (!board[x][j].is_empty && board[x][j].getObject().getColor().equals(black)) {
                        break;
                    }
                }
            }
        }

        if(board[x][y].getObject().getColor().equals(white))
        {
            System.out.println("kiram dahanet age dary mikonish");
            for(int i = x+1;i<8 ; i++)
            {
                if(board[i][y].is_empty)
                {
                    guiBoard[i][y].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[i][y].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(i , y));
                }
                if(!board[i][y].is_empty && board[i][y].getObject().getColor().equals(black))
                {
                    guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[i][y].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(i , y));
                    break;
                }
                if(!board[i][y].is_empty && board[i][y].getObject().getColor().equals(white))
                {
                    break;
                }
            }

            for (int i=x-1;i>=0;i--)
            {
                if(board[i][y].is_empty)
                {
                    guiBoard[i][y].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[i][y].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(i , y));
                }
                if(!board[i][y].is_empty && board[i][y].getObject().getColor().equals(black))
                {
                    guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[i][y].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(i , y));
                    break;
                }
                if(!board[i][y].is_empty && board[i][y].getObject().getColor().equals(white))
                {
                    break;
                }
            }


            for(int j=y+1; j <8;j++)
            {
                if(board[x][j].is_empty)
                {
                    guiBoard[x][j].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x][j].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x , j));
                }

                if(!board[x][j].is_empty && board[x][j].getObject().getColor().equals(black))
                {
                    guiBoard[x][j].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x][j].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x , j));
                    break;
                }

                if(!board[x][j].is_empty && board[x][j].getObject().getColor().equals(white))
                {
                    break;
                }
            }

            for (int j = y-1;j>=0;j--)
            {
                if(board[x][j].is_empty)
                {
                    guiBoard[x][j].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x][j].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x , j));
                }

                if(!board[x][j].is_empty && board[x][j].getObject().getColor().equals(black))
                {
                    guiBoard[x][j].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x][j].setEnabled(true);
                    backlighted.add(new Pair<Integer, Integer>(x , j));
                    break;
                }
                if(!board[x][j].is_empty && board[x][j].getObject().getColor().equals(white))
                {
                    break;
                }
            }
        }

    }

    private void moveSoldier(int x , int y) {

        xForFirst = x ;
        yForFirst = y ;
        clickedFirst = false ;
        if (board[x][y].getObject().getColor().equals(black)) {
            if (x != 7 && board[x + 1][y].is_empty)
            {
                guiBoard[x + 1][y].setEnabled(true);
                guiBoard[x + 1][y].setBackgroundResource(R.drawable.blue_backlight);
                backlighted.add(new Pair<>(x + 1, y));
            }
            if (x!=7 && y != 7 && !board[x + 1][y + 1].is_empty && board[x + 1][y + 1].getObject().getColor().equals(white))
            {
                guiBoard[x + 1][y + 1].setEnabled(true);
                guiBoard[x + 1][y + 1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<>(x + 1, y + 1));
            }
            if (x!=7 && y != 0 && !board[x + 1][y - 1].is_empty && board[x + 1][y - 1].getObject().getColor().equals(white))
            {
                guiBoard[x + 1][y - 1].setEnabled(true);
                guiBoard[x + 1][y - 1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<>(x + 1, y - 1));
            }
            if (board[x][y].getObject().first_move)
            {
                board[x][y].getObject().first_move = false;
                if (board[x + 2][y].is_empty)
                {
                    guiBoard[x + 2][y].setEnabled(true);
                    guiBoard[x + 2][y].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(x + 2, y));
                }

            }
        }
        if(board[x][y].getObject().getColor().equals(white))
        {
            if (x != 0 && board[x - 1][y].is_empty)
            {
                guiBoard[x - 1][y].setEnabled(true);
                guiBoard[x - 1][y].setBackgroundResource(R.drawable.blue_backlight);
                backlighted.add(new Pair<>(x - 1, y));
            }
            if (x!=0 && y != 7 && !board[x - 1][y + 1].is_empty && board[x - 1][y + 1].getObject().getColor().equals(black))
            {
                guiBoard[x - 1][y + 1].setEnabled(true);
                guiBoard[x - 1][y + 1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<>(x - 1, y + 1));
            }
            if (x!=0 && y != 0 && !board[x - 1][y - 1].is_empty && board[x - 1][y - 1].getObject().getColor().equals(black))
            {
                guiBoard[x - 1][y - 1].setEnabled(true);
                guiBoard[x - 1][y - 1].setBackgroundResource(R.drawable.red_backlight);
                backlighted.add(new Pair<>(x - 1, y - 1));
            }
            if (board[x][y].getObject().first_move)
            {
                board[x][y].getObject().first_move = false;
                if (board[x - 2][y].is_empty)
                {
                    guiBoard[x - 2][y].setEnabled(true);
                    guiBoard[x - 2][y].setBackgroundResource(R.drawable.blue_backlight);
                    backlighted.add(new Pair<>(x - 2, y));
                }

            }
        }

    }

    private void showCounterDialoge() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tickSound = MediaPlayer.create(getApplicationContext(),R.raw.tick_tock_sound);
                tickSound.start();
            }
        }, 500L);

        final Dialog d1 = new Dialog(PlayActivity.this);
        d1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        d1.setContentView(R.layout.counter_layout);
        ((ImageView)d1.findViewById(R.id.counter_imageview)).setImageResource(R.drawable.three);
        d1.setCancelable(false);


        final Dialog d2 = new Dialog(PlayActivity.this);
        d2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d2.setContentView(R.layout.counter_layout);
        ((ImageView)d2.findViewById(R.id.counter_imageview)).setImageResource(R.drawable.two);
        d2.setCancelable(false);


        final Dialog d3 = new Dialog(PlayActivity.this);
        d3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d3.setContentView(R.layout.counter_layout);
        ((ImageView)d3.findViewById(R.id.counter_imageview)).setImageResource(R.drawable.one);
        d3.setCancelable(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d1.show();
            }
        }, 1000L);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d1.dismiss();
                d2.show();
            }
        }, 2000L);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d2.dismiss();
                d3.show();
            }
        }, 3000L);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d3.dismiss();
            }
        }, 4000L);

    }

    private void animatingBeads() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0 ; i<8 ;i++)
                {
                    final int fi = i ;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            guiBoard[0][fi].animate().alpha(1f).setDuration(1000L).start();
                            guiBoard[1][fi].animate().alpha(1f).setDuration(1000L).start();
                            guiBoard[6][fi].animate().alpha(1f).setDuration(1000L).start();
                            guiBoard[7][fi].animate().alpha(1f).setDuration(1000L).start();
                        }
                    }, (i + 1) * 250L);
                }
            }
        }, 3550L);

    }

    public void Undo(View view){
        if (undo.isEmpty()) {
            Toast.makeText(PlayActivity.this,getString(R.string.emptyUndo),Toast.LENGTH_LONG).show();
            return ;
        }

        if(blackTurn)
        {
            blackTurn = false ;
            whiteTurn = true ;
        }
        else
        {
            blackTurn = true ;
            whiteTurn = false ;
        }
        Move move = undo.get(undo.size()-1);

        if(move.getFirstBead().getColor().equals(black) && move.getFirstBead().getX()==1){
            move.getFirstBead().first_move=true;
        }
        if(move.getFirstBead().getColor().equals(white) && move.getFirstBead().getX()==6){
            move.getFirstBead().first_move = true;
        }

        board[move.getxForFirst()][move.getyForFirst()].setObject(move.getFirstBead());
        board[move.getxFinal()][move.getyFinal()].setObject(move.getSecondBead());
        board[move.getxForFirst()][move.getyForFirst()].is_empty=false ;
        board[move.getxFinal()][move.getyFinal()].is_empty = true ;

        guiBoard[move.getxForFirst()][move.getyForFirst()].setImageResource(0);
        guiBoard[move.getxForFirst()][move.getyForFirst()].setImageResource(move.getFirstBead().getImageId());

        if (move.getSecondBead()==null)
            guiBoard[move.getxFinal()][move.getyFinal()].setImageResource(0);
        else {
            guiBoard[move.getxFinal()][move.getyFinal()].setImageResource(0);
            guiBoard[move.getxFinal()][move.getyFinal()].setImageResource(move.getSecondBead().getImageId());
        }



        undo.remove(undo.size()-1);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setCancelable(false)
                .setTitle(getString(R.string.exitTitle))
                .setMessage(R.string.exitMessage)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (backGroundMusic1.isPlaying())
                            backGroundMusic1.stop();
                        if(backGroundMusic2.isPlaying())
                            backGroundMusic2.stop();
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.nokey), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                })
                .show();
        return;
    }
}