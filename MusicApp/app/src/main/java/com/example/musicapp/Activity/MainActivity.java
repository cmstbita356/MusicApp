package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.musicapp.Adapter.HomeAdapter;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;

import java.io.IOException;
import java.util.ArrayList;

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
        SongData.getAllSongs(this);
        SongData.getLanguageSong("Anh", this);
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
        songList = songs;
        HomeAdapter adapter = new HomeAdapter(songs, this);

        RecyclerView recyclerView_TatCa = findViewById(R.id.recyclerView_TatCa);
        recyclerView_TatCa.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_TatCa.setAdapter(adapter);

        RecyclerView recyclerView_NuocNgoai = findViewById(R.id.recyclerView_NuocNgoai);
        recyclerView_NuocNgoai.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_NuocNgoai.setAdapter(adapter);

        RecyclerView recyclerView_VietNam = findViewById(R.id.recyclerView_VietNam);
        recyclerView_VietNam.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_VietNam.setAdapter(adapter);
    }

    @Override
    public void onSongDataReceived(Song song) {
        mediaPlayer.stop();
        playSong(song.getLink());
    }

    @Override
    public void onLanguageSongDataReceived(ArrayList<Song> songs) {


    }

    @Override
    public void onSongDataError(String message) {
        // Handle error
    }
}