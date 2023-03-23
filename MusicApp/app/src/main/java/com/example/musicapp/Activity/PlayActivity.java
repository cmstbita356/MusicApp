package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity implements SongData.SongDataCallback{
    ImageView imV_Song;
    MediaPlayer mediaPlayer;
    ImageButton bt_play;
    ImageButton bt_next;
    ImageButton bt_previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        init();
        Intent intent = getIntent();
        SongData.getSongById(intent.getIntExtra("id", 0), this);




    }
    private void init()
    {
        imV_Song = findViewById(R.id.imV_song);
        mediaPlayer = new MediaPlayer();
        bt_play = findViewById(R.id.bt_play);
        bt_next = findViewById(R.id.bt_next);
        bt_previous = findViewById(R.id.bt_previous);
    }
    private void playSong(String songLink) {
        // Initialize the media player and start playing the song
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songLink);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onAllSongDataReceived(ArrayList<Song> songs) {

    }

    @Override
    public void onSongDataReceived(Song song) {
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
                Picasso.get().load(song.getHinh()).into(imV_Song);
                playSong(song.getLink());
            }
        });
    }

    @Override
    public void onLanguageSongDataReceived(ArrayList<Song> songs) {

    }

    @Override
    public void onSongDataError(String message) {

    }

}