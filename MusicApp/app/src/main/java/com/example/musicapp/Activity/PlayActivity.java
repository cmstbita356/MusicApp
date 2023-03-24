package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicapp.Adapter.PlayAdapter;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    ImageView imV_Song;
    MediaPlayer mediaPlayer;
    ImageButton bt_play;
    ImageButton bt_next;
    ImageButton bt_previous;
    ImageButton bt_nexttime;
    ImageButton bt_previoustime;
    SeekBar seekBar_song;
    RecyclerView recyclerView;
    ImageButton bt_repeat;
    TextView textView_name;
    TextView textView_singer;

    Song song;
    ArrayList<Song> ListSong;
    Handler mHandler = new Handler();
    Context context = this;
    boolean isRepeat = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        init();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);


        seekBar_song.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        FirebaseHelper.getData("BaiHat", new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                song = SongData.getSongById(id, dataSnapshot);

                textView_name.setText(song.getTen());
                textView_singer.setText(song.getCaSi());

                ListSong = SongData.getSongByLanguage(song.getNgonNgu(), dataSnapshot);
                PlayAdapter adapter = new PlayAdapter(ListSong, context, mediaPlayer);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);

                Picasso.get().load(song.getHinh()).into(imV_Song);
                playSong(song.getLink());

                bt_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                        } else {
                            mediaPlayer.start();
                        }
                    }
                });

                bt_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            song = ListSong.get(song.getId()+1);

                            Picasso.get().load(song.getHinh()).into(imV_Song);
                            playSong(song.getLink());
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            song = ListSong.get(0);
                            Picasso.get().load(song.getHinh()).into(imV_Song);
                            playSong(song.getLink());
                        }

                    }
                });

                bt_previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            song = ListSong.get(song.getId()-1);

                            Picasso.get().load(song.getHinh()).into(imV_Song);
                            playSong(song.getLink());
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            song = ListSong.get(ListSong.size()-1);
                            Picasso.get().load(song.getHinh()).into(imV_Song);
                            playSong(song.getLink());
                        }
                    }
                });

                bt_nexttime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int nextTime = mediaPlayer.getCurrentPosition() + 10000;
                        mediaPlayer.seekTo(nextTime);
                        seekBar_song.setProgress(nextTime);
                    }
                });

                bt_previoustime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int previousTime = mediaPlayer.getCurrentPosition() - 10000;
                        mediaPlayer.seekTo(previousTime);
                        seekBar_song.setProgress(previousTime);
                    }
                });

                bt_repeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isRepeat)
                        {
                            isRepeat = false;
                        }
                        else
                        {
                            isRepeat = true;
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void init()
    {
        imV_Song = findViewById(R.id.imV_song);
        mediaPlayer = new MediaPlayer();
        bt_play = findViewById(R.id.bt_play);
        bt_next = findViewById(R.id.bt_next);
        bt_previous = findViewById(R.id.bt_previous);
        seekBar_song = findViewById(R.id.seekBar_song);
        bt_nexttime = findViewById(R.id.bt_nexttime);
        bt_previoustime = findViewById(R.id.bt_previoustime);
        recyclerView = findViewById(R.id.recyclerView);
        bt_repeat = findViewById(R.id.bt_repeat);
        textView_singer = findViewById(R.id.textView_singer);
        textView_name = findViewById(R.id.textView_name);
    }
    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            int currentPosition = mediaPlayer.getCurrentPosition();
            seekBar_song.setProgress(currentPosition);
            mHandler.postDelayed(this, 1000);
        }
    };
    private void playSong(String songLink) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songLink);
            mediaPlayer.prepare();
            mediaPlayer.start();

            seekBar_song.setMax(mediaPlayer.getDuration());
            mHandler.postDelayed(updateSeekBarTime, 1000);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(isRepeat)
                    {
                        mp.seekTo(0);
                        mp.start();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}