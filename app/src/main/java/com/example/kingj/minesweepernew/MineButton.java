package com.example.kingj.minesweepernew;

import android.content.Context;
import android.widget.Button;

public class MineButton extends Button {
    private boolean isMine;
    public boolean isreveal;
    private int value;
    public int bi,bj;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean flag;

    public boolean isIsreveal() {
        return isreveal;
    }

    public void setIsreveal(boolean isreveal)
    {
        this.isreveal = isreveal;
    }

    public MineButton(Context context) {
        super(context);
    }

    public boolean isMine() {

        return isMine;
    }

    public void setMine(boolean mine)
    {
        isMine = mine;
    }



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
