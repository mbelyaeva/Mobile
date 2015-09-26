package com.example.maria.homework3;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;



public class MainActivity extends AppCompatActivity {

    public ImageButton skipBack;
    public ImageButton play;
    public ImageButton skipForward;
    public TextView current;
    public TextView left;
    public Song song;
    public boolean isPlaying;
    public boolean isLooping;
    public String[] songNames;
    private SeekBar mSeekBar;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Definitely took this from the list view demo done in class*/
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        getResources().getStringArray(R.array.song_names));
        // ListViews display data in a scrollable list
        ListView theListView = (ListView) findViewById(R.id.songsListView);
        // Tells the ListView what data to use
        theListView.setAdapter(theAdapter);

        song = new Song(this);

        //attaching buttons and textviews
        skipBack = (ImageButton) findViewById(R.id.skipBackButton);
        play = (ImageButton) findViewById(R.id.playButton);
        skipForward = (ImageButton) findViewById(R.id.skipForwardButton);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        current = (TextView) findViewById(R.id.currentTime);
        left = (TextView) findViewById(R.id.endTime);

        /*
        *Literally all of my seek stuff is about to come from
        * http://stackoverflow.com/questions/17168215/seekbar-and-media-player-in-android
         */
        songNames = getResources().getStringArray(R.array.song_names);
        mSeekBar.setMax(song.getTime());

        setSongNames();
        isPlaying = true;
        isLooping = false;

        //updates the seek bar while playing
        mHandler = new Handler();
        //Make sure you update Seekbar on UI thread
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (song != null) {
                    int mCurrentPosition = song.getPosition();
                    mSeekBar.setProgress(mCurrentPosition);
                        left.setText(song.timeLeft());
                        current.setText(song.timePassed());
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        //updates the music when you move the seek bar
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                song.setSeek(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                song.setSeek(true);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (song != null && fromUser) {
                    song.seek2(progress);
                }
            }
        });

        //change to the song you tap on
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                song.playThisOne(i);
                setSongNames();
            }
        });

        //play/pause things
        play.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    song.pause();
                    isPlaying = false;
                    play.setImageResource(R.drawable.play);
                } else {
                    song.play();
                    isPlaying = true;
                    play.setImageResource(R.drawable.pause);
                }
            }
        });

        skipBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.playPrev();
                setSongNames();
                mSeekBar.setMax(song.getTime());
            }
        });

        skipForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.playNext();
                setSongNames();
                mSeekBar.setMax(song.getTime());
            }
        });

    }

    //sets the song names at the top
    public void setSongNames(){
        TextView current = (TextView) findViewById(R.id.nowPlayingSong);
        current.setText(songNames[song.songIndex()]);
        TextView next = (TextView) findViewById(R.id.nextSong);
        next.setText(songNames[song.nextSongIndex()]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //goes to settings menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //referenced the flipped activity classroom we did with Intents
        if (id == R.id.action_settings) {
            Intent gotoSettingsIntent = new Intent(this, Settings.class);
            final int result = 1;
            gotoSettingsIntent.putExtra("isLooping", isLooping);
            startActivityForResult(gotoSettingsIntent, result);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //called when you're done with settings
    //sets the looping var
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (data.hasExtra("isLooping")) {
            Bundle args = data.getExtras();
            isLooping = (boolean) args.get("isLooping");
            song.isLooping(isLooping);
        }
    }

}
