package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.musicapp.Adapter.HomeAdapter;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ImageButton iB_search;
    ImageButton iB_library;
    RecyclerView recyclerView_Anh;
    RecyclerView recyclerView_VietNam;
    RecyclerView recyclerView_Khac;

    MediaPlayer mediaPlayer;
    ArrayList<Song> songList = new ArrayList<>();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(1f, 1f);

        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                songList = SongData.getAllSong(dataSnapshot);

                HomeAdapter adapter_Anh = new HomeAdapter(SongData.getSongByLanguage("Anh", dataSnapshot), context);
                recyclerView_Anh.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                recyclerView_Anh.setAdapter(adapter_Anh);

                HomeAdapter adapter_VietNam = new HomeAdapter(SongData.getSongByLanguage("Viet", dataSnapshot), context);
                recyclerView_VietNam.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                recyclerView_VietNam.setAdapter(adapter_VietNam);

                HomeAdapter adapter_Khac = new HomeAdapter(SongData.getSongByLanguage("Khac", dataSnapshot), context);
                recyclerView_Khac.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                recyclerView_Khac.setAdapter(adapter_Khac);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        iB_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(intent);
            }
        });

        iB_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init()
    {
        iB_search = findViewById(R.id.iB_search);
        recyclerView_Anh = findViewById(R.id.recyclerView_Anh);
        recyclerView_VietNam = findViewById(R.id.recyclerView_VietNam);
        recyclerView_Khac = findViewById(R.id.recyclerView_Khac);
        iB_library = findViewById(R.id.iB_library);
    }
}