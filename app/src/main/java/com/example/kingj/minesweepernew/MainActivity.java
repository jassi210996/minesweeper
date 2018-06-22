package com.example.kingj.minesweepernew;

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
    int r, c,count=0;
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
        size = 6;
        setupBoard();

        show();


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
            size = 6;
            setupBoard();
            setMines();

        } else if (id == R.id.medium) {
            size = 12;
            setupBoard();
            msetMines();

        } else if (id == R.id.hard) {
            size = 15;
            setupBoard();
            hsetMines();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupBoard() {
        board = new MineButton[size][size];
        rows = new ArrayList<>();
        layout.removeAllViews();

        for (int i = 0; i < size; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
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
        while (x < 10) {
            r = rn.nextInt(size);
            c = rn.nextInt(size);
            MineButton button = board[r][c];

            if (!button.isMine()) {
                button.setMine(true);
                button.setValue(-1);
                Log.i("MainActivity", "Mine set on i = " + r + " j = " + c);
                x++;
                countMine(r, c);
            }
        }
    }
    public void msetMines() {

        Log.i("MainActivity", "level = " + size + " j = ");
        x=0;
        while (x < 20) {
            r = rn.nextInt(size);
            c = rn.nextInt(size);
            MineButton button = board[r][c];

            if (!button.isMine()) {
                button.setMine(true);
                button.setValue(-1);
                Log.i("MainActivity", "Mine set on i = " + r + " j = " + c);
                x++;
                countMine(r, c);
            }
        }
    }

    public void hsetMines() {

        Log.i("MainActivity", "level = " + size + " j = ");
        x=0;
        while (x < 25) {
            r = rn.nextInt(size);
            c = rn.nextInt(size);
            MineButton button = board[r][c];

            if (!button.isMine()) {
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
            if (newI >= 0 && newI < size && newJ >= 0 && newJ < size && !board[newI][newJ].isMine()) {
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

        if (value == 0) {

            b1.setIsreveal(true);
            b1.setEnabled(false);
            int newI, newJ;

            for (int i = 0; i < 8; i++)
            {
                newI = b1.bi + ia[i];
                newJ = b1.bj + ja[i];

                if (newI >= 0 && newI < size && newJ >= 0 && newJ < size )
                {
                    Log.i("MainActivity", " newi value = " + newI + "" + newJ);

                    if ((!board[newI][newJ].isIsreveal()) && (!gt.equals("F")))
                    {
                        int nv = board[newI][newJ].getValue();

                        if (nv == 0)
                        {

                            board[newI][newJ].setIsreveal(true);
                            board[newI][newJ].setEnabled(false);
                            reveal(board[newI][newJ]);
                        }
                        else if (nv > 0)
                        {
                            board[newI][newJ].setIsreveal(true);
                            board[newI][newJ].setEnabled(false);
                            board[newI][newJ].setText(nv + "");
                        }
                    }
                }
            }
        }
            if (value == -1 ) {
                b1.setText(value+"");
                for(int i=0;i<size;i++)
                {
                    for(int j=0;j<size;j++)
                    {   int mv = board[i][j].getValue();

                        if(mv==-1)
                        {
                            board[i][j].setText(mv+"");
                        }
                        board[i][j].setEnabled(false);
                    }
                }
                Toast.makeText(this, "Game over", Toast.LENGTH_LONG).show();
            }
            else if(value>0)
            {   b1.setIsreveal(true);
                b1.setEnabled(false);
                b1.setText(value + "");
            }

        }

        @Override
        public void onClick (View view){

            if(count==0)
            {
                setMines();
                show();
            }
            MineButton bt = (MineButton) view;
            String gt = bt.getText().toString();

            if(!gt.equals("F")) {
                reveal(bt);
            }
            count++;
        }

    @Override
    public boolean onLongClick(View view) {
        MineButton bt = (MineButton) view;

        if(bt.isFlag())
        {
            bt.setText("");
            bt.flag=false;
        }
        else
        {
            bt.setText("F");
            bt.flag=true;

        }
                return true;
    }

}
