package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.musicapp.R;

public class SettingActivity extends AppCompatActivity {
    ImageButton iB_home;
    ImageButton iB_search;
    ImageButton iB_library;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();


        iB_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(getApplicationContext(), LibraryActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init()
    {
        iB_home = findViewById(R.id.iB_home);
        iB_search = findViewById(R.id.iB_search);
        iB_library = findViewById(R.id.iB_library);
    }
}