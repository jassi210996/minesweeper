package com.example.kingj.minesweepernew;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    LinearLayout layout;
    MineButton button;
    int size, x = 0;
    int r, c,min,countRevealed,buttonsToberevealed;
    public boolean firstclick=false;
    int ia[] = {-1, -1, -1, 0, 0, 1, 1, 1};
    int ja[] = {-1, 0, 1, 1, -1, -1, 0, 1};
    Random rn = new Random();

    MineButton[][] board;
    ArrayList<LinearLayout> rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.root);
        countRevealed=0;
        size = 10;
        min=30;
        buttonsToberevealed=((size*size)+1)-min;
        setupBoard();
        setMines();
      //  show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.easy) {
            min=30;
            firstclick=false;
            countRevealed=0;
            buttonsToberevealed=((size*size)+1)-min;
            setupBoard();
            setMines();
//            show();

        } else if (id == R.id.medium) {
//            size = 8;
            min=40;
            firstclick=false;
            countRevealed=0;
            buttonsToberevealed=((size*size)+1)-min;
            setupBoard();
            setMines();
        } else if (id == R.id.hard) {
//            size = 10;
            min=55;
            firstclick=false;
            countRevealed=0;
            buttonsToberevealed=((size*size)+1)-min;
            setupBoard();
            setMines();
        }
        else if(id==R.id.reset)
        {
            resetBoard();
            min=30;
            firstclick=false;
            countRevealed=0;
            buttonsToberevealed=((size*size)+1)-min;
            setupBoard();
            setMines();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupBoard() {
        board = new MineButton[size][size];
        rows = new ArrayList<>();
        layout.removeAllViews();
       // layout.setBackgroundColor(Color.BLUE);

        for (int i = 0; i < size; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);
            layout.addView(linearLayout);
            rows.add(linearLayout);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button = new MineButton(this);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
                LinearLayout row = rows.get(i);
                row.addView(button);
//              button.setBackgroundResource(R.drawable.newbutton);
                button.setTextSize(20);
                //button.setBottom(10);
                button.isreveal = false;
                button.setMine(false);
                button.setValue(0);
                button.setFlag(false);
                button.bi = i;
                button.bj = j;
                board[i][j] = button;
            }
        }

    }

    public void setMines() {

        Log.i("MainActivity", "level = " + size + " j = ");
        x=0;
        while (x < min) {
            r = rn.nextInt(size);
            c = rn.nextInt(size);
            MineButton button = board[r][c];

            if (!button.isMine()&&!button.isIsreveal()) {
                button.setMine(true);
                button.setValue(-1);
                Log.i("MainActivity", "Mine set on i = " + r + " j = " + c);
                x++;
                countMine(r, c);
            }
        }
    }

    public void countMine(int x, int y) {
        int newI, newJ;
        for (int i = 0; i < 8; i++) {
            newI = x + ia[i];
            newJ = y + ja[i];
            if (newI >= 0 && newI < size && newJ >= 0 && newJ < size && !board[newI][newJ].isMine())
            {
                board[newI][newJ].setValue(board[newI][newJ].getValue() + 1);
                Log.i("MainActivity", " ijj value = " + newI + "" + newJ);
                Log.i("MainActivity", " i value = " + board[newI][newJ].getValue());
            }
        }
    }

    public void show() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int t = board[i][j].getValue();

                board[i][j].setText(t + "");
            }
        }
    }

    public void reveal(MineButton b1) {
        String gt = b1.getText().toString();
        int value = b1.getValue();
        countRevealed++;

        if (gameStatus(countRevealed)) {

            if (value == 0) {

                b1.setIsreveal(true);
                b1.setEnabled(false);
                int newI, newJ;

                for (int i = 0; i < 8; i++) {
                    newI = b1.bi + ia[i];
                    newJ = b1.bj + ja[i];

                    if (newI >= 0 && newI < size && newJ >= 0 && newJ < size)
                    {
                        Log.i("MainActivity", " newi value = " + newI + "" + newJ);

                        if ((!board[newI][newJ].isIsreveal()) && board[newI][newJ].flag == false)
                        {
                            int nv = board[newI][newJ].getValue();
                                countRevealed++;
                           if(gameStatus(countRevealed)) {
                               if (nv == 0) {
//                            board[newI][newJ].setBackgroundColor(Color.DKGRAY);
                                   board[newI][newJ].setBackgroundResource(R.drawable.button_bg);
                                   board[newI][newJ].setIsreveal(true);
                                   board[newI][newJ].setEnabled(false);
                                   reveal(board[newI][newJ]);
                               } else if (nv > 0) {
                                   board[newI][newJ].setIsreveal(true);
                                   board[newI][newJ].setEnabled(false);
                                   textColor(nv, board[newI][newJ]);
                                   board[newI][newJ].setText(nv + "");
                               }
                           }
                        }
                    }
                }
            }
            if (value == -1) {
                b1.setText(value + "");
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        int mv = board[i][j].getValue();

                        if (mv == -1) {
                            board[i][j].setBackgroundResource(R.drawable.mine);

//                            board[i][j].setText(mv+"");
                        } else if (mv > 0) {

                            textColor(mv, board[i][j]);
                        } else
                            board[i][j].setBackgroundResource(R.drawable.button_bg);

                        board[i][j].setEnabled(false);
                    }
                }
                Toast.makeText(this, "Game over", Toast.LENGTH_LONG).show();
            } else if (value > 0) {
                b1.setIsreveal(true);
                b1.setEnabled(false);
                textColor(value, b1);

                b1.setText(value + "");
            }

        }
        else
        {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int mv = board[i][j].getValue();

                    if (mv == -1) {
                        board[i][j].setBackgroundResource(R.drawable.mine);

//                            board[i][j].setText(mv+"");
                    }
                    board[i][j].setEnabled(false);
                }
            }
            Toast.makeText(this, "Game Won", Toast.LENGTH_LONG).show();
        }
    }

        public void textColor(int value,MineButton b1)
        {
            b1.setText(value+"");
            if(value==1)
            {

                b1.setBackgroundResource(R.drawable.button_bg);
                b1.setTextColor(Color.BLUE);
            }
            else if(value==2)
            { b1.setBackgroundResource(R.drawable.button_bg);
                b1.setTextColor(Color.GREEN);
            }
            else if(value==3)
            { b1.setBackgroundResource(R.drawable.button_bg);
                b1.setTextColor(Color.RED);
            }
            else if(value==4)
            { b1.setBackgroundResource(R.drawable.button_bg);
                b1.setTextColor(Color.MAGENTA);
            }
            else if(value==5)
            { b1.setBackgroundResource(R.drawable.button_bg);
                b1.setTextColor(Color.DKGRAY);
            }
            else if(value==6)
            { b1.setBackgroundResource(R.drawable.button_bg);
                b1.setTextColor(Color.BLACK);
            }
            else if(value==7)
            { b1.setBackgroundResource(R.drawable.button_bg);
                b1.setTextColor(Color.BLUE);
            }
            else if(value==8)
            { b1.setBackgroundResource(R.drawable.button_bg);
                b1.setTextColor(Color.RED);
            }

        }

        public void resetBoard(){
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                MineButton button=board[i][j];
                button.isreveal=false;
                button.setValue(0);
                button.setMine(false);
            }
        }
        }

        public boolean gameStatus(int countRevealed)
        {
            if(countRevealed==buttonsToberevealed)
            {
                return false;
            }
            else
                return true;
        }

        @Override
        public void onClick (View view){


            MineButton bt = (MineButton) view;
            String gt = bt.getText().toString();
            int vl = bt.getValue();

            if(firstclick==false && vl==-1)
            {
               // bt.setText(" ");
                //bt.setValue(0);
                //x=x-1;
                resetBoard();
                firstclick=true;
                bt.setIsreveal(true);
                bt.setBackgroundResource(R.drawable.button_bg);
                setMines();
                reveal(bt);
                //show();
            }
            else if(firstclick==false)
            {
                firstclick=true;
                bt.setIsreveal(true);
             //   countRevealed++;
                bt.setBackgroundResource(R.drawable.button_bg);
                reveal(bt);
            }
            else if (!gt.equals("F")) {

                bt.setBackgroundResource(R.drawable.button_bg);
                reveal(bt);
            }
        }

    @Override
    public boolean onLongClick(View view) {
        MineButton bt = (MineButton) view;

        if(bt.isFlag())
        {
            bt.setBackgroundResource(android.R.drawable.btn_default_small);
        }
        else
        {
            bt.setBackgroundResource(R.drawable.flag);
            bt.flag=true;
        }
                return true;
    }

}
