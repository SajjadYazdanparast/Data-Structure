package com.yazdanparast.sajjad.chess;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
    
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    MediaPlayer tickSound;
    TextView player1;
    Cell [][] board;
    ImageButton [][] guiBoard ;
    Boolean clickedFirst ;
    int xForFirst , yForFirst;
    List<Pair<Integer,Integer>> backligted;
    List<Move> undo;
    Button undobtn;

    final static String black = "black";
    final static String white = "white";
    final static String soldier = "Soldier";
    final static String castle = "Castle";
    final static String horse = "Horse";
    final static String elephant = "Elephant";
    final static String minister = "Minister";
    final static String king = "King";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        if (getSupportActionBar()!=null)    getSupportActionBar().hide();
        showCounterDialoge();
        init();
        setBeadsFor_first();
        animatingBeads();
        player1 =(TextView) findViewById(R.id.playground_upper_tv);

    }

    public void init() {
        board = new Cell[8][8];
        backligted = new ArrayList<>();
        undo = new ArrayList<>();
        undobtn = (Button) findViewById(R.id.undo_btn);
        clickedFirst = true ;
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++)
            {
                board[i][j] = new Cell();
            }
        }

        guiBoard = new ImageButton[8][8];
        imageButtonInitializer();

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
        board[0][3].setObject(new Bead(black ,1,0,3 ,"Minister",R.drawable.kingb));
        board[0][4].setObject(new Bead(black ,1,0,4 ,"King",R.drawable.ministerb));
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
            }
        }

        board[7][0].setObject(new Bead(white ,1,7,0 ,"Castle",R.drawable.castlew));
        board[7][1].setObject(new Bead(white ,1,7,1 ,"Horse",R.drawable.horsew));
        board[7][2].setObject(new Bead(white ,1,7,2 ,"Elephant",R.drawable.elephantw));
        board[7][3].setObject(new Bead(white ,1,7,3 ,"Minister",R.drawable.ministerw));
        board[7][4].setObject(new Bead(white ,1,7,4 ,"King",R.drawable.kingw));
        board[7][5].setObject(new Bead(white ,2,7,5 ,"Elephant",R.drawable.elephantw));
        board[7][6].setObject(new Bead(white ,2,7,6 ,"Horse",R.drawable.horsew));
        board[7][7].setObject(new Bead(white ,2,7,7 ,"Castle",R.drawable.castlew));
        board[6][0].setObject(new Bead(white ,1,6,0 ,"Soldier",R.drawable.soldierw));
        board[6][1].setObject(new Bead(white ,2,6,1 ,"Soldier",R.drawable.soldierw));
        board[6][2].setObject(new Bead(white ,3,6,2 ,"Soldier",R.drawable.soldierw));
        board[6][3].setObject(new Bead(white ,4,6,3 ,"Soldier",R.drawable.soldierw));
        board[6][4].setObject(new Bead(white ,5,6,4 ,"Soldier",R.drawable.soldierw));
        board[6][5].setObject(new Bead(white ,6,6,5 ,"Soldier",R.drawable.soldierw));
        board[6][6].setObject(new Bead(white ,7,6,6 ,"Soldier",R.drawable.soldierw));
        board[6][7].setObject(new Bead(white ,8,6,7 ,"Soldier",R.drawable.soldierw));

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

    private void btnSetDisable(boolean gholmorad) {
        for(int i=0 ;i<8 ;i++)
            for(int j=0 ;j <8 ;j++)
                guiBoard[i][j].setEnabled(gholmorad);
    }

    public void cellsClicked(View view){
        String tag = view.getTag().toString();
        int x , y;
        x = ToInt(tag , 0);
        y = ToInt(tag , 1);

        if(!board[x][y].is_empty && clickedFirst)
        {
            btnSetDisable(false);
            undobtn.setEnabled(false);
            decision(x,y);
        }
        else if(!clickedFirst)
        {
            decision(x,y);
            undobtn.setEnabled(true);
        }
    }

    public void decision(int x , int y) {
        if (clickedFirst){
            switch (board[x][y].getObject().getName())
            {
                case "Castle":
                {
                    player1.setText(castle);
                }
                break;

                case horse:
                {
                    moveHorse(x,y);
                }
                break;

                case "Elephant":
                {
                    player1.setText(elephant);
                }
                break;

                case minister:
                {
                    moveMinister(x,y);
                }
                break;

                case "King":
                {
                    player1.setText(king);
                }
                break;

                case soldier:
                {
                    if(board[x][y].getObject().getColor().equals(black) && x==7)  {
                        Toast.makeText(PlayActivity.this,getString(R.string.wrong_choose),Toast.LENGTH_LONG).show();
                        btnSetDisable(true) ;
                        return ;
                    }
                    if(board[x][y].getObject().getColor().equals(white) && x==0)  {
                        Toast.makeText(PlayActivity.this,R.string.wrong_choose,Toast.LENGTH_LONG).show();
                        btnSetDisable(true) ;
                        return ;
                    }
                    moveSoldier(x,y);
                }
                break;
            }
        }
        else{
            secondMove(x,y);
            clickedFirst = true ;
        }
    }

    private void moveSoldier(int x , int y) {
            xForFirst = x ;
            yForFirst = y ;
            clickedFirst = false ;
        if (board[x][y].getObject().getColor().equals(black)) {
            if (board[x + 1][y].is_empty)
            {
                guiBoard[x + 1][y].setEnabled(true);
                guiBoard[x + 1][y].setBackgroundResource(R.drawable.blue_backlight);
                backligted.add(new Pair<>(x + 1, y));
            }
            if (y != 7 && !board[x + 1][y + 1].is_empty && board[x + 1][y + 1].getObject().getColor().equals(white))
            {
                guiBoard[x + 1][y + 1].setEnabled(true);
                guiBoard[x + 1][y + 1].setBackgroundResource(R.drawable.red_backlight);
                backligted.add(new Pair<>(x + 1, y + 1));
            }
            if (y != 0 && !board[x + 1][y - 1].is_empty && board[x + 1][y - 1].getObject().getColor().equals(white))
            {
                guiBoard[x + 1][y - 1].setEnabled(true);
                guiBoard[x + 1][y - 1].setBackgroundResource(R.drawable.red_backlight);
                backligted.add(new Pair<>(x + 1, y - 1));
            }
            if (board[x][y].getObject().first_move)
            {
                board[x][y].getObject().first_move = false;
                    if (board[x + 2][y].is_empty)
                    {
                        guiBoard[x + 2][y].setEnabled(true);
                        guiBoard[x + 2][y].setBackgroundResource(R.drawable.blue_backlight);
                        backligted.add(new Pair<>(x + 2, y));
                    }

            }
        }

        if(board[x][y].getObject().getColor().equals(white))
        {
            if(board[x-1][y].is_empty)
            {
                guiBoard[x-1][y].setEnabled(true);
                guiBoard[x-1][y].setBackgroundResource(R.drawable.blue_backlight);
                backligted.add(new Pair<>(x-1,y));
            }
            if(y!=7 && !board[x-1][y+1].is_empty && board[x-1][y+1].getObject().getColor().equals(black))
            {
                guiBoard[x-1][y+1].setEnabled(true);
                guiBoard[x-1][y+1].setBackgroundResource(R.drawable.red_backlight);
                backligted.add(new Pair<>(x-1,y+1));
            }
            if(y!=0 && !board[x-1][y-1].is_empty && board[x-1][y-1].getObject().getColor().equals(black))
            {
                guiBoard[x-1][y-1].setEnabled(true);
                guiBoard[x-1][y-1].setBackgroundResource(R.drawable.red_backlight);
                backligted.add(new Pair<>(x-1,y-1));
            }
            if(board[x][y].getObject().first_move)
            {
                board[x][y].getObject().first_move = false  ;
                guiBoard[x-2][y].setEnabled(true);
                guiBoard[x-2][y].setBackgroundResource(R.drawable.blue_backlight);
                backligted.add(new Pair<>(x-2,y));
            }
        }
        }

    private void moveHorse(int x, int y) {
        boolean certificated = false ;
        xForFirst = x ;
        yForFirst = y ;
        clickedFirst = false ;
        if(board[x][y].getObject().getColor().equals(black))
        {
            if(x<6 && y<7 )
            {
                if(!board[x+2][y+1].is_empty &&board[x+2][y+1].getObject().getColor().equals(white))
                {
                    certificated = true;
                    guiBoard[x+2][y+1].setEnabled(true);
                    guiBoard[x+2][y+1].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(x+2,y+1));

                }
                else if(board[x+2][y+1].is_empty)
                {
                    certificated = true;
                    guiBoard[x+2][y+1].setEnabled(true);
                    guiBoard[x+2][y+1].setBackgroundResource(R.drawable.blue_backlight);
                    backligted.add(new Pair<>(x+2,y+1));
                }
            }

            if(x<6 && y>0)
            {
                if(!board[x+2][y-1].is_empty && board[x+2][y-1].getObject().getColor().equals(white))
                {
                    certificated = true;
                    guiBoard[x+2][y-1].setEnabled(true);
                    guiBoard[x+2][y-1].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(x+2,y-1));
                }
                else if(board[x+2][y-1].is_empty)
                {
                    certificated = true;
                    guiBoard[x+2][y-1].setEnabled(true);
                    guiBoard[x+2][y-1].setBackgroundResource(R.drawable.blue_backlight);
                    backligted.add(new Pair<>(x+2,y-1));
                }
            }

            if(x<7 && y<6)
            {
                if(!board[x+1][y+2].is_empty &&board[x+1][y+2].getObject().getColor().equals(white))
                {
                    certificated = true;
                    guiBoard[x+1][y+2].setEnabled(true);
                    guiBoard[x+1][y+2].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(x+1,y+2));
                }
                if(board[x+1][y+2].is_empty)
                {
                    certificated = true;
                    guiBoard[x+1][y+2].setEnabled(true);
                    guiBoard[x+1][y+2].setBackgroundResource(R.drawable.blue_backlight);
                    backligted.add(new Pair<>(x+1,y+2));
                }
            }

            if(x<7 && y>1)
            {
                if(!board[x+1][y-2].is_empty && board[x+1][y-2].getObject().getColor().equals(white))
                {
                    certificated = true;
                    guiBoard[x+1][y-2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x+1][y-2].setEnabled(true);
                    backligted.add(new Pair<>(x+1,y-2));
                }
                if(board[x+1][y-2].is_empty)
                {
                    certificated = true;
                    guiBoard[x+1][y-2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x+1][y-2].setEnabled(true);
                    backligted.add(new Pair<>(x+1,y-2));
                }
            }

            if(x>1 && y<7)
            {
                if(!board[x-2][y+1].is_empty && board[x-2][y+1].getObject().getColor().equals(white))
                {
                    certificated = true;
                    guiBoard[x-2][y+1].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-2][y+1].setEnabled(true);
                    backligted.add(new Pair<>(x-2,y+1));
                }
                if(board[x-2][y+1].is_empty)
                {
                    certificated = true;
                    guiBoard[x-2][y+1].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-2][y+1].setEnabled(true);
                    backligted.add(new Pair<>(x-2,y+1));
                }
            }

            if(x>1 && y>0)
            {
                if(!board[x-2][y-1].is_empty && board[x-2][y-1].getObject().getColor().equals(white))
                {
                    certificated = true;
                    guiBoard[x-2][y-1].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-2][y-1].setEnabled(true);
                    backligted.add(new Pair<>(x-2,y-1));
                }
                if(board[x-2][y-1].is_empty)
                {
                    certificated = true;
                    guiBoard[x-2][y-1].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-2][y-1].setEnabled(true);
                    backligted.add(new Pair<>(x-2,y-1));
                }
            }

            if(x>0 && y<6)
            {
                if(!board[x-1][y+2].is_empty && board[x-1][y+2].getObject().getColor().equals(white))
                {
                    certificated = true;
                    guiBoard[x-1][y+2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-1][y+2].setEnabled(true);
                    backligted.add(new Pair<>(x-1,y+2));
                }
                if(board[x-1][y+2].is_empty)
                {
                    certificated = true;
                    guiBoard[x-1][y+2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-1][y+2].setEnabled(true);
                    backligted.add(new Pair<>(x-1,y+2));
                }
            }

            if(x>0 && y>1)
            {
                if(!board[x-1][y-2].is_empty && board[x-1][y-2].getObject().getColor().equals(white))
                {
                    certificated = true;
                    guiBoard[x-1][y-2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-1][y-2].setEnabled(true);
                    backligted.add(new Pair<>(x-1,y-2));
                }
                if(board[x-1][y-2].is_empty)
                {
                    certificated = true;
                    guiBoard[x-1][y-2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-1][y-2].setEnabled(true);
                    backligted.add(new Pair<>(x-1,y-2));
                }
            }
        }


        if(board[x][y].getObject().getColor().equals(white))
        {
            if(x<6 && y<7 )
            {
                if(!board[x+2][y+1].is_empty &&board[x+2][y+1].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x+2][y+1].setEnabled(true);
                    guiBoard[x+2][y+1].setBackgroundResource(0);
                    guiBoard[x+2][y+1].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(x+2,y+1));
                }
                else if(board[x+2][y+1].is_empty)
                {
                    certificated = true;
                    guiBoard[x+2][y+1].setEnabled(true);
                    guiBoard[x+2][y+1].setBackgroundResource(0);
                    guiBoard[x+2][y+1].setBackgroundResource(R.drawable.blue_backlight);
                    backligted.add(new Pair<>(x+2,y+1));
                }
            }

            if(x<6 && y>0)
            {
                if(!board[x+2][y-1].is_empty && board[x+2][y-1].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x+2][y-1].setEnabled(true);
                    guiBoard[x+2][y-1].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(x+2,y-1));
                }
                else if(board[x+2][y-1].is_empty)
                {
                    certificated = true;
                    guiBoard[x+2][y-1].setEnabled(true);
                    guiBoard[x+2][y-1].setBackgroundResource(R.drawable.blue_backlight);
                    backligted.add(new Pair<>(x+2,y-1));
                }
            }

            if(x<7 && y<6)
            {
                if(!board[x+1][y+2].is_empty &&board[x+1][y+2].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x+1][y+2].setEnabled(true);
                    guiBoard[x+1][y+2].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(x+1,y+2));
                }
                if(board[x+1][y+2].is_empty)
                {
                    certificated = true;
                    guiBoard[x+1][y+2].setEnabled(true);
                    guiBoard[x+1][y+2].setBackgroundResource(R.drawable.blue_backlight);
                    backligted.add(new Pair<>(x+1,y+2));
                }
            }

            if(x<7 && y>1)
            {
                if(!board[x+1][y-2].is_empty && board[x+1][y-2].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x+1][y-2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x+1][y-2].setEnabled(true);
                    backligted.add(new Pair<>(x+1,y-2));
                }
                if(board[x+1][y-2].is_empty)
                {
                    certificated = true;
                    guiBoard[x+1][y-2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x+1][y-2].setEnabled(true);
                    backligted.add(new Pair<>(x+1,y-2));
                }
            }

            if(x>1 && y<7)
            {
                if(!board[x-2][y+1].is_empty && board[x-2][y+1].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x-2][y+1].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-2][y+1].setEnabled(true);
                    backligted.add(new Pair<>(x-2,y+1));
                }
                if(board[x-2][y+1].is_empty)
                {
                    certificated = true;
                    guiBoard[x-2][y+1].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-2][y+1].setEnabled(true);
                    backligted.add(new Pair<>(x-2,y+1));
                }
            }

            if(x>1 && y>0)
            {
                if(!board[x-2][y-1].is_empty && board[x-2][y-1].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x-2][y-1].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-2][y-1].setEnabled(true);
                    backligted.add(new Pair<>(x-2,y-1));
                }
                if(board[x-2][y-1].is_empty)
                {
                    certificated = true;
                    guiBoard[x-2][y-1].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-2][y-1].setEnabled(true);
                    backligted.add(new Pair<>(x-2,y-1));
                }
            }

            if(x>0 && y<6)
            {
                if(!board[x-1][y+2].is_empty && board[x-1][y+2].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x-1][y+2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-1][y+2].setEnabled(true);
                    backligted.add(new Pair<>(x-1,y+2));
                }
                if(board[x-1][y+2].is_empty)
                {
                    certificated = true;
                    guiBoard[x-1][y+2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-1][y+2].setEnabled(true);
                    backligted.add(new Pair<>(x-1,y+2));
                }
            }

            if(x>0 && y>1)
            {
                if(!board[x-1][y-2].is_empty && board[x-1][y-2].getObject().getColor().equals(black))
                {
                    certificated = true;
                    guiBoard[x-1][y-2].setBackgroundResource(R.drawable.red_backlight);
                    guiBoard[x-1][y-2].setEnabled(true);
                    backligted.add(new Pair<>(x-1,y-2));
                }
                if(board[x-1][y-2].is_empty)
                {
                    certificated = true;
                    guiBoard[x-1][y-2].setBackgroundResource(R.drawable.blue_backlight);
                    guiBoard[x-1][y-2].setEnabled(true);
                    backligted.add(new Pair<>(x-1,y-2));
                }
            }
        }
        if(!certificated){
            clickedFirst=true;
            Toast.makeText(PlayActivity.this,getString(R.string.wrong_choose),Toast.LENGTH_LONG).show();
            btnSetDisable(true);
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
                    backligted.add(new Pair<>(i, y));
                }
                if (!board[i][y].is_empty && board[i][y].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(i, y));
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
                    backligted.add(new Pair<>(i, y));
                }
                if (!board[i][y].is_empty && board[i][y].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][y].setEnabled(true);
                    guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(i, y));
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
                    backligted.add(new Pair<>(x, i));
                }
                if (!board[x][i].is_empty && board[x][i].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(x, i));
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
                    backligted.add(new Pair<>(x, i));
                }
                if (!board[x][i].is_empty && board[x][i].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[x][i].setEnabled(true);
                    guiBoard[x][i].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(x, i));
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
                    backligted.add(new Pair<>(i, j));
                }
                if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(i, j));
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
                    backligted.add(new Pair<>(i, j));
                }
                if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(i, j));
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
                    backligted.add(new Pair<>(i, j));
                }
                if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(i, j));
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
                    backligted.add(new Pair<>(i, j));
                }
                if (!board[i][j].is_empty && board[i][j].getObject().getColor().equals(white)) {
                    certificated = true;
                    guiBoard[i][j].setEnabled(true);
                    guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                    backligted.add(new Pair<>(i, j));
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
                        backligted.add(new Pair<>(i,y));
                    }
                    if(!board[i][y].is_empty && board[i][y].getObject().getColor().equals(black))
                    {
                        certificated = true;
                        guiBoard[i][y].setEnabled(true);
                        guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                        backligted.add(new Pair<>(i,y));
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
                        backligted.add(new Pair<>(i,y));
                    }
                    if(!board[i][y].is_empty && board[i][y].getObject().getColor().equals(black))
                    {
                        certificated = true;
                        guiBoard[i][y].setEnabled(true);
                        guiBoard[i][y].setBackgroundResource(R.drawable.red_backlight);
                        backligted.add(new Pair<>(i,y));
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
                        backligted.add(new Pair<>(x,i));
                    }
                    if(!board[x][i].is_empty && board[x][i].getObject().getColor().equals(black))
                    {
                        certificated = true;
                        guiBoard[x][i].setEnabled(true);
                        guiBoard[x][i].setBackgroundResource(R.drawable.red_backlight);
                        backligted.add(new Pair<>(x,i));
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
                        backligted.add(new Pair<>(x,i));
                    }
                    if(!board[x][i].is_empty && board[x][i].getObject().getColor().equals(black))
                    {
                        certificated = true;
                        guiBoard[x][i].setEnabled(true);
                        guiBoard[x][i].setBackgroundResource(R.drawable.red_backlight);
                        backligted.add(new Pair<>(x,i));
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
                        backligted.add(new Pair<>(i,j));
                    }
                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                    {
                        certificated = true;
                        guiBoard[i][j].setEnabled(true);
                        guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                        backligted.add(new Pair<>(i,j));
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
                        backligted.add(new Pair<>(i,j));
                    }
                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                    {
                        certificated = true;
                        guiBoard[i][j].setEnabled(true);
                        guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                        backligted.add(new Pair<>(i,j));
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
                        backligted.add(new Pair<>(i,j));
                    }
                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                    {
                        certificated = true;
                        guiBoard[i][j].setEnabled(true);
                        guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                        backligted.add(new Pair<>(i,j));
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
                        backligted.add(new Pair<>(i,j));
                    }
                    if(!board[i][j].is_empty && board[i][j].getObject().getColor().equals(black))
                    {
                        certificated = true;
                        guiBoard[i][j].setEnabled(true);
                        guiBoard[i][j].setBackgroundResource(R.drawable.red_backlight);
                        backligted.add(new Pair<>(i,j));
                        break;
                    }
                    if(!board[i][j].is_empty)    break;
                }
            }
        if(!certificated){
            Toast.makeText(PlayActivity.this,getString(R.string.wrong_choose),Toast.LENGTH_LONG).show();
            btnSetDisable(true);
            clickedFirst = true;
        }
    }

    private void secondMove(int x , int y) {
        //FOR BOARD
        undo.add(new Move(board[xForFirst][yForFirst].getObject() , board[x][y].getObject() ,
                         xForFirst , yForFirst , x , y)) ;
        board[x][y].setObject(board[xForFirst][yForFirst].getObject());
        board[x][y].is_empty = false ;
        board[xForFirst][yForFirst].setObject(null);
        board[xForFirst][yForFirst].is_empty = true ;
        //TODO add to ommited list
        //FOR BACKLIGHT
        for(int i=0 ; i<backligted.size(); i++){
            guiBoard[backligted.get(i).first][backligted.get(i).second].setBackgroundResource(0);
//            guiBoard[backligted.get(i).first][backligted.get(i).second].setBackgroundResource(android.R.color.transparent);
            player1.setText("avaz shod");
        }
        backligted.clear();

        //FOR ANIMATING

        guiBoard[xForFirst][yForFirst].animate().alpha(0f).setDuration(750L).start();
        guiBoard[x][y].setAlpha(0f);
        guiBoard[x][y].setImageResource(board[x][y].getObject().getImageId());
        guiBoard[x][y].animate().alpha(1f).setDuration(750L).start();

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                guiBoard[xForFirst][yForFirst].setImageResource(0);
                guiBoard[xForFirst][yForFirst].setAlpha(1f);
                btnSetDisable(true);
            }
        },750L);
    }

    private void showCounterDialoge() {

        tickSound = MediaPlayer.create(getApplicationContext(),R.raw.tick_tock_sound);
        tickSound.start();

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
        }, 500L);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d1.dismiss();
                d2.show();
            }
        }, 1500L);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d2.dismiss();
                d3.show();
            }
        }, 2500L);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d3.dismiss();
            }
        }, 3500L);

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
        Move move = undo.get(undo.size()-1);

        if(move.getFirstBead().getColor().equals(black) && move.getFirstBead().getX()==1){
            move.getFirstBead().setFirst_move(true);
        }
        if(move.getFirstBead().getColor().equals(white) && move.getFirstBead().getX()==6){
            move.getFirstBead().setFirst_move(true);
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
}


