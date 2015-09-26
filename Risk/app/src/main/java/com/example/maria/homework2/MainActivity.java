package com.example.maria.homework2;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean startGame;
    private EditText aEdit;
    private EditText dEdit;
    private int aSoldiers;
    private int dSoldiers;
    private int max;
    private ProgressBar attackProgress;
    private ProgressBar defenseProgress;
    protected Toast mainToast;

    RedDie attack1;
    RedDie attack2;
    RedDie attack3;

    WhiteDie defend1;
    WhiteDie defend2;

    //I definitely copy/pasted this from the flipped classroom activity
    protected void doToast(String message) {
        if(this.mainToast != null) {
            this.mainToast.cancel();
        }
        this.mainToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        this.mainToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up the million buttons that I have
        Button mStart = (Button) findViewById(R.id.start);
        ImageButton ma1 = (ImageButton) findViewById(R.id.attack1);
        ImageButton ma2 = (ImageButton) findViewById(R.id.attack2);
        ImageButton ma3 = (ImageButton) findViewById(R.id.attack3);
        ImageButton md1 = (ImageButton) findViewById(R.id.defend1);
        ImageButton md2 = (ImageButton) findViewById(R.id.defend2);

        //makes new dice SET TO TRUE
        attack1 = new RedDie(this.getApplicationContext(), true, 1);
        attack2 = new RedDie(this.getApplicationContext(), true, 1);
        attack3 = new RedDie(this.getApplicationContext(), true, 1);
        defend1 = new WhiteDie(this.getApplicationContext(), true, 1);
        defend2 = new WhiteDie(this.getApplicationContext(), true, 1);

        attackProgress = (ProgressBar) findViewById(R.id.progressAttack);
        defenseProgress = (ProgressBar) findViewById(R.id.progressDefend);
        aEdit = (EditText) findViewById(R.id.attackingSoldiers);
        dEdit = (EditText) findViewById(R.id.defendingSoldiers);
        startGame = false;

        //sets up the gameeeeeeeeeeeeee
        mStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String ax = aEdit.getText().toString();
                String dx = dEdit.getText().toString();
                if(ax.equals("") || dx.equals("")) {
                    doToast("Please enter numbers for both fields");
                }
                else {
                    aSoldiers = Integer.parseInt(aEdit.getText().toString());
                    dSoldiers = Integer.parseInt(dEdit.getText().toString());
                        if (aSoldiers > 0 && dSoldiers > 0) {
                            attack1.setDisabled(false);
                            attack2.setDisabled(false);
                            attack3.setDisabled(false);
                            defend1.setDisabled(false);
                            defend2.setDisabled(false);
                            max = Math.max(aSoldiers, dSoldiers);
                            attackProgress.setMax(max);
                            defenseProgress.setMax(max);
                            updateProgress();
                            startGame = true;
                        }
                    else
                            doToast("Please enter numbers for both fields");
                }
                //System.out.println("I'M HERE AND I'M SAFE");
            }
        });
        //bunch of buttons that could probably be done better
        ma1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        ma2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        ma3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        md1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        md2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

    }

    public void disableAll(){
        attack1.setDisabled(true);
        attack2.setDisabled(true);
        attack3.setDisabled(true);
        defend1.setDisabled(true);
        defend2.setDisabled(true);

        attack1.setDie(1);
        attack2.setDie(1);
        attack3.setDie(1);
        defend1.setDie(1);
        defend2.setDie(1);

        attack1.setClickable(false);
        attack2.setClickable(false);
        attack3.setClickable(false);
        defend1.setClickable(false);
        defend2.setClickable(false);

        updateImage();
    }

    //starts the game
    public void startGame(){
        if (startGame) {
            attack1.setClickable(true);
            attack2.setClickable(true);
            attack3.setClickable(true);
            defend1.setClickable(true);
            defend2.setClickable(true);
            gameLoop();
        }
    }

    //updates progress bar
    public void updateProgress(){
        attackProgress.setProgress(aSoldiers);
        defenseProgress.setProgress(dSoldiers);
    }

    //gameloop - exactly what it sounds like
    public void gameLoop(){
            //If the die is not disabled it gives a new number
            if (!attack1.isDisabled())
                attack1.roll();
            if (!attack2.isDisabled())
                attack2.roll();
            if (!attack3.isDisabled())
                attack3.roll();
            if (!defend1.isDisabled())
                defend1.roll();
            if (!defend2.isDisabled())
                defend2.roll();

            updateImage();
            soldierDeaths();
            updateProgress();

            if (aSoldiers < 1 || dSoldiers < 1){
                startGame = false;
                winner();
            }
    }

    //decides the winner based on remaining soldiers - has toast
    public void winner(){
        if (aSoldiers > dSoldiers) {
//            System.out.println("ATTACK WON");
            doToast("Attack Won");
        }
        else if (aSoldiers < dSoldiers) {
//            System.out.println("DEFENSE WON");
            doToast("Defense Won");
        }
        else {
//            System.out.println("TIE");
            doToast("There was a tie");
        }
        disableAll();
        //System.out.println("IDK WHAT HAPPENS HERE");
        startGame = false;
        //System.out.println("DED");
    }

    //updates the dice
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void updateImage(){
        String temp = attack1.updateDieName();
        //System.out.println("Attack 1: " + temp);
        int imageResource = getResources().getIdentifier(temp, null, getPackageName());
        ImageButton mDie = (ImageButton) findViewById(R.id.attack1);
        Drawable res = getResources().getDrawable(imageResource, getApplicationContext().getTheme());
        mDie.setImageDrawable(res);

        temp = attack2.updateDieName();
        //System.out.println("Attack 2: " + temp);
        imageResource = getResources().getIdentifier(temp, null, getPackageName());
        mDie = (ImageButton) findViewById(R.id.attack2);
        res = getResources().getDrawable(imageResource, getApplicationContext().getTheme());
        mDie.setImageDrawable(res);

        temp = attack3.updateDieName();
        //System.out.println("Attack 3: " + temp);
        imageResource = getResources().getIdentifier(temp, null, getPackageName());
        mDie = (ImageButton) findViewById(R.id.attack3);
        res = getResources().getDrawable(imageResource, getApplicationContext().getTheme());
        mDie.setImageDrawable(res);

        temp = defend1.updateDieName();
        //System.out.println("Defense 1: " + temp);
        imageResource = getResources().getIdentifier(temp, null, getPackageName());
        mDie = (ImageButton) findViewById(R.id.defend1);
        res = getResources().getDrawable(imageResource, getApplicationContext().getTheme());
        mDie.setImageDrawable(res);

        temp = defend2.updateDieName();
        //System.out.println("Defense 2: " + temp);
        imageResource = getResources().getIdentifier(temp, null, getPackageName());
        mDie = (ImageButton) findViewById(R.id.defend2);
        res = getResources().getDrawable(imageResource, getApplicationContext().getTheme());
        mDie.setImageDrawable(res);
    }

    //kills off some soldiers using some helper methods, also disables dice
    public void soldierDeaths(){
        logic();
        setToZero(attack1.currentDie, attack2.currentDie, attack3.currentDie, defend1.currentDie, defend2.currentDie);
        logic();
        if (aSoldiers < 3) {
            attack3.setDisabled(true);
            attack3.setDie(1);
            attack3.setClickable(false);
        }
        if (aSoldiers < 2){
            attack2.setDisabled(true);
            attack2.setDie(1);
            attack2.setClickable(false);
        }
        if (dSoldiers < 2){
            defend2.setDisabled(true);
            defend2.setDie(1);
            defend2.setClickable(false);
        }
    }

    //finds max and subtracts soldiers
    public void logic(){
        int rounda = Math.max(attack1.currentDie, attack2.currentDie);
        rounda = Math.max(rounda, attack3.currentDie);
        int roundb = Math.max(defend1.currentDie, defend2.currentDie);
        if (rounda > roundb){
            dSoldiers --;
        }
        else if(rounda < roundb){
            aSoldiers --;
        }
        else {
            aSoldiers --;
        }
    }

    //sets the highest ones temporarily to zero
    public void setToZero(int a1, int a2, int a3, int d1, int d2){
        if(a1 > a2 && a1 > a3){
            attack1.setDie(0);
        }
        else if (a2 > a1 && a2 > a3) {
            attack2.setDie(0);
        }
        else
            attack3.setDie(0);
        if(d1 > d2){
            defend1.setDie(0);
        }
        else
            defend2.setDie(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
