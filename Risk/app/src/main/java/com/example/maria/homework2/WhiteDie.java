package com.example.maria.homework2;

import android.content.Context;

/**
 * Created by Maria on 9/13/2015.
 */
public class WhiteDie extends Die {

    public String color = "@drawable/white_die_";
    private String die;


    public WhiteDie(Context context, boolean disabled, int currentDie) {
        super(context, disabled, currentDie);
    }

    public String updateDieName (){
        if(this.isDisabled()){
            die = color + "1" + this.dis;
        }
        else{
            die = color + this.currentDie;
        }
        return die;
    }
}
