package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicapp.Adapter.FindAdapter;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindActivity extends AppCompatActivity {
    EditText editText_find;
    Button bt_find;
    RecyclerView recyclerView_find;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        init();

        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bt_find.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Song> listSong = SongData.getSongByName(editText_find.getText().toString(), dataSnapshot);
                        FindAdapter adapter = new FindAdapter(listSong, context);
                        recyclerView_find.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        recyclerView_find.setAdapter(adapter);
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
        editText_find = findViewById(R.id.editText_find);
        bt_find = findViewById(R.id.bt_find);
        recyclerView_find = findViewById(R.id.recyclerView_find);
    }
}