package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    Context context = this;
    /*Button button_test;*/
    Button button_submit;
    Button button_dangky;
    EditText editText_taikhoan;
    EditText editText_matkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        /*button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);

            }
        });*/

        button_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });
        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                button_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String taikhoan = editText_taikhoan.getText().toString();
                        String matkhau = editText_matkhau.getText().toString();
                        for (DataSnapshot snapshot : dataSnapshot.child("NguoiDung").getChildren())
                        {
                            if(snapshot.child("TaiKhoan").getValue(String.class).equals(taikhoan) && snapshot.child("MatKhau").getValue(String.class).equals(matkhau))
                            {
                                Intent intent = new Intent(context, HomeActivity.class);
                                StorageData.id_user = snapshot.child("Id").getValue(Integer.class);
                                startActivity(intent);
                            }

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
        //button_test = findViewById(R.id.button_test);
        button_submit = findViewById(R.id.button_submit);
        editText_taikhoan = findViewById(R.id.editText_taikhoan);
        editText_matkhau = findViewById(R.id.editText_matkhau);
        button_dangky = findViewById(R.id.button_dangky);
    }
}