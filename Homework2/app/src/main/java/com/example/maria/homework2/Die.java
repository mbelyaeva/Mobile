package com.example.maria.homework2;

import android.content.Context;
import android.widget.ImageButton;

import java.util.Random;

/**
 * Created by Maria on 9/13/2015.
 */
public class Die extends ImageButton {

    boolean disabled;
    public int currentDie;
    final public String dis = "_disabled";

    public Die(Context context, boolean disabled, int currentDie) {
        super(context);
        this.disabled = disabled;
        this.currentDie = currentDie;
    }

    public void roll (){
        if (!disabled){
            Random rand = new Random();
            int r = rand.nextInt(6);
            r += 1;
            this.currentDie = r;
        }

    }

    public void setDie(int x){
        this.currentDie = x;
    }

    public void setDisabled (boolean b){
        this.disabled = b;
    }

    public boolean isDisabled (){
        return disabled;
    }

}
