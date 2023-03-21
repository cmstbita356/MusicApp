package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SongData.SongDataCallback {
    MediaPlayer mediaPlayer;
    int countIndex = 0;
    ArrayList<Song> songList = new ArrayList<>();
    String s = "không có";
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(1f, 1f);

        //SongData.getSongById(1, this);
        //SongData.getAllSongs(this);
        SongData.getLanguageSong("Viet", this);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int nextIndex = (countIndex + 1) % songList.size();
                playSong(songList.get(nextIndex).getLink());
                countIndex = nextIndex;
            }
        });


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
        mediaPlayer.stop();
        songList = songs;
        playSong(songList.get(1).getLink());
    }

    @Override
    public void onSongDataReceived(Song song) {
        mediaPlayer.stop();
        playSong(song.getLink());
    }

    @Override
    public void onLanguageSongDataReceived(ArrayList<Song> songs) {
        mediaPlayer.stop();

        playSong(songs.get(0).getLink());

    }

    @Override
    public void onSongDataError(String message) {
        // Handle error
    }
}