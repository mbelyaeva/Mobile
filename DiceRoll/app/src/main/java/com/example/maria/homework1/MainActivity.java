package com.example.maria.homework1;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mReset;
    private ImageButton mDie;
    private int[] numbers;
    private TextView[] countViews;
    private int flickerCount;
    private String dieColor;
    private int currentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReset = (Button)findViewById(R.id.reset);
        mDie = (ImageButton)findViewById(R.id.dice);
        numbers = new int[6];
        dieColor = "@drawable/red_die_";
        currentNum = 1;
        flickerCount = 0;
        countViews = new TextView[6];
        countViews[0] = (TextView)findViewById(R.id.one);
        countViews[1] = (TextView)findViewById(R.id.two);
        countViews[2] = (TextView)findViewById(R.id.three);
        countViews[3] = (TextView)findViewById(R.id.four);
        countViews[4] = (TextView)findViewById(R.id.five);
        countViews[5] = (TextView)findViewById(R.id.six);
        resetNums();
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetNums();
            }
        });
        mDie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                flickerCount = 10;
                MediaPlayer sounds = MediaPlayer.create(MainActivity.this, R.raw.diceroll);
                sounds.start();
                changeDie();
            }
        });
        ToggleButton color = (ToggleButton) findViewById(R.id.color);
        color.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                dieColor = isChecked ? "@drawable/white_die_" : "@drawable/red_die_";
                String temp = dieColor + String.valueOf(currentNum);
                int imageResource = getResources().getIdentifier(temp, null, getPackageName());
                mDie = (ImageButton)findViewById(R.id.dice);
                Drawable res = getResources().getDrawable(imageResource, getApplicationContext().getTheme());
                mDie.setImageDrawable(res);
            }
        });

    }

    public void resetNums(){
        for(int i = 0; i < 6; i++){
            numbers[i] = 0;
            countViews[i].setText(String.valueOf(numbers[i]));
        }

    }

    public void changeDie (){
        Handler flickerHandler = new Handler();
        flickerHandler.postDelayed(new Runnable(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run(){
                Random rand = new Random();
                int r = rand.nextInt(6);
                currentNum = r+1;
                String temp = dieColor + String.valueOf(currentNum);
                System.out.println(temp);
                int imageResource = getResources().getIdentifier(temp, null, getPackageName());
                System.out.println(imageResource);
                mDie = (ImageButton)findViewById(R.id.dice);
                Drawable res = getResources().getDrawable(imageResource, getApplicationContext().getTheme());
                mDie.setImageDrawable(res);
                if(flickerCount > 0){
                    changeDie();
                    flickerCount --;
                }
                else{
                    numbers[r]++;
                    countViews[r].setText(String.valueOf(numbers[r]));
                    currentNum = r+1;
            }
            }
        }, 50);
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
