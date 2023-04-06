package com.example.musicapp.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
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
    ImageButton iB_setting;
    RecyclerView recyclerView_Anh;
    RecyclerView recyclerView_VietNam;
    RecyclerView recyclerView_Khac;
    LinearLayout miniLayout_Play;
    ImageSlider imageSlider;

    ArrayList<Song> songList = new ArrayList<>();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        //Nhớ xóa
        miniLayout_Play.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int progress = sharedPreferences.getInt("volume", 50);
        float volume = progress / 100f;
        StorageData.mediaPlayer.setVolume(volume, volume);

        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                songList = SongData.getAllSong(dataSnapshot);
                //SliderImage
                SliderImage();


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
                Intent intent = new Intent(getApplicationContext(), LibraryActivity.class);
                startActivity(intent);
            }
        });
        iB_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init()
    {
        imageSlider=findViewById(R.id.imageSlider);
        iB_search = findViewById(R.id.iB_search);
        recyclerView_Anh = findViewById(R.id.recyclerView_Anh);
        recyclerView_VietNam = findViewById(R.id.recyclerView_VietNam);
        recyclerView_Khac = findViewById(R.id.recyclerView_Khac);
        iB_library = findViewById(R.id.iB_library);
        iB_setting = findViewById(R.id.iB_setting);
        miniLayout_Play = findViewById(R.id.miniLayout_Play);
    }
    public void SliderImage(){
        ArrayList<SlideModel> slideModels=new ArrayList<>();
        for(int i=0;i<songList.size();i++){
            slideModels.add(new SlideModel(songList.get(i).getHinh(), ScaleTypes.FIT));
        }
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

//        imageSlider.addOnLayoutChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                SlideModel slideModel = imageSlider.getSlideModelList().get(position);
//                String imageUrl = slideModel.getImageUrl();
//                Log.d("SliderImage URL", imageUrl);
//
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
//
//            @Override
//            public void onPageScrollStateChanged(int state) {}
//        });
//        imageSlider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, PlayActivity.class);
//                intent.putExtra("id", getSongByHinh(imageSlider.,dataSnapshot).getId());
//                intent.putExtra("id_playlist", "không");
//                context.startActivity(intent);
//            }
//        });
    }

}