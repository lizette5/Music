package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button b1,b2,b3,b4;
    private MediaPlayer mediaPlayer;
    private double startTime=0;
    private double finalTime=0;
    private Handler myHandler=new Handler();
    private  SeekBar seekBar;
    private TextView tx1,tx2,tx3;
    int canciones[]={R.raw.coldplay,R.raw.coloresperanza,R.raw.onemoretime,R.raw.runpiano,R.raw.savepiano};
    String can[]={"coldplay.mp3","coloresperanza.mp3","onemoretime.mp3","runpiano.mp3","savepiano.mp3"};
    int index=0;
    public  static int oneTimeOnly=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button2);
        b2 = (Button) findViewById(R.id.button3);
        b3 = (Button) findViewById(R.id.button1);
        b4 = (Button) findViewById(R.id.button4);
        tx1 = (TextView) findViewById(R.id.editTiempo);
        tx2 = (TextView) findViewById(R.id.editTiempoFinal);
        tx3 = (TextView) findViewById(R.id.editMusica);
        tx3.setText(can[index]);
        mediaPlayer = MediaPlayer.create(this, canciones[index]);
        seekBar = (SeekBar) findViewById(R.id.seekBar2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();
                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }
                tx2.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );
                tx1.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );
                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);

            }

        });
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }

        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index > 4) index = 0;
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(getApplication(), canciones[index]);
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();
                tx3.setText(can[index]);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    index--;
                    if (index < 0) index = 4;
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplication(), canciones[index]);
                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();
                    tx3.setText(can[index]);

                }
        });

    }
    private Runnable UpdateSongTime=new Runnable() {
        @Override
        public void run() {
        startTime =mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long)startTime),
                TimeUnit.MILLISECONDS.toSeconds((long)startTime)-
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime)))
                );
            seekBar.setProgress((int)startTime);
            myHandler.postDelayed(this,100);

        }
    };
}