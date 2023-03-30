package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicapp.Adapter.PlayAdapter;
import com.example.musicapp.Adapter.PlaylistDetailAdapter;
import com.example.musicapp.Model.PlaylistDetail;
import com.example.musicapp.Model.PlaylistDetailData;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistDetailActivity extends AppCompatActivity {
    ImageView imageView_playlist;
    TextView textView_playlistName;
    TextView textView_quantitySong;
    RecyclerView recyclerView;
    ImageButton imageButton_back;
    Context context = this;
    ArrayList<Song> songs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);
        init();
        String linkHinh = "https://cdn1.vectorstock.com/i/1000x1000/14/35/playlist-black-icon-button-logo-symbol-vector-14551435.jpg";
        Picasso.get().load(linkHinh).into(imageView_playlist);

        Intent intent = getIntent();
        textView_playlistName.setText(intent.getStringExtra("ten_playlist"));
        String textQuantity = String.valueOf(intent.getIntExtra("soluong_baihat", 0)) + " songs";
        textView_quantitySong.setText(textQuantity);
        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int id_playlist = intent.getIntExtra("id_Playlist", 0);
                songs = PlaylistDetailData.getSongsByIdPlaylist(dataSnapshot, id_playlist);
                PlaylistDetailAdapter adapter = new PlaylistDetailAdapter(songs, id_playlist, context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init()
    {
        imageView_playlist = findViewById(R.id.imageView_playlist);
        textView_playlistName = findViewById(R.id.textView_playlistName);
        textView_quantitySong = findViewById(R.id.textView_quantitySong);
        recyclerView = findViewById(R.id.recyclerView);
        imageButton_back = findViewById(R.id.imageButton_back);
    }
}