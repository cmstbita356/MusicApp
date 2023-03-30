package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.musicapp.Adapter.PlaylistAdapter;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.PlaylistData;
import com.example.musicapp.Model.PlaylistDetail;
import com.example.musicapp.Model.PlaylistDetailData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
    GridView gridView;

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        init();

        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Playlist> playlists = PlaylistData.getPlaylist(dataSnapshot, StorageData.id_user);
                ArrayList<Integer> listIdPlaylist = new ArrayList<>();
                for (Playlist item : playlists)
                {
                    listIdPlaylist.add(item.getId());
                }
                ArrayList<Integer> listQuantitySong = PlaylistDetailData.getListQuantitySong(dataSnapshot, listIdPlaylist);
                PlaylistAdapter adapter = new PlaylistAdapter(playlists, listQuantitySong, context);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(context, PlaylistDetailActivity.class);
                        intent.putExtra("id_playlist", playlists.get(position).getId());
                        intent.putExtra("ten_playlist", playlists.get(position).getTen());
                        intent.putExtra("soluong_baihat", listQuantitySong.get(position));
                        startActivity(intent);
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
        gridView = findViewById(R.id.gridView);
    }
}