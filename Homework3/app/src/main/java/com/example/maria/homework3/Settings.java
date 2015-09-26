package com.example.maria.homework3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

/**
 * Created by Maria on 9/23/2015.
 */
public class Settings extends Activity {

    public Switch looper;
    public Button okay;
    public Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        looper = (Switch) findViewById(R.id.switch1);
        okay = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.button2);

        //sets looping switch
        Intent loop = getIntent();
        if (loop.hasExtra("isLooping")){
            Bundle args = loop.getExtras();
            boolean isLooping = (boolean) args.get("isLooping");
            looper.setChecked(isLooping);
        }

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent();
                settingsIntent.putExtra("isLooping", looper.isChecked());
                setResult(RESULT_OK, settingsIntent);
                finish();

            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent();
                setResult(RESULT_OK, settingsIntent);
                finish();
            }
        });
    }
}
