package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Adapter.DialogPlaylistAdapter;
import com.example.musicapp.Adapter.PlayAdapter;
import com.example.musicapp.Model.FavoriteSongData;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.PlaylistData;
import com.example.musicapp.Model.PlaylistDetailData;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.Model.UserData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayActivity extends AppCompatActivity {
    ImageView imV_Song;
    ImageButton bt_play;
    ImageButton bt_next;
    ImageButton bt_previous;
    ImageButton bt_nexttime;
    ImageButton bt_previoustime;
    ImageButton imageButton_back;
    ImageButton bt_favorite;
    ImageButton bt_repeat;
    ImageButton bt_add;
    SeekBar seekBar_song;
    Button button_songList;
    TextView textView_name;
    TextView textView_singer;
    TextView textView_currentTime;
    TextView textView_maxTime;
    Song song;
    ArrayList<Song> ListSong;
    Handler mHandler = new Handler();
    Context context = this;
    boolean isRepeat = false;
    boolean isFavorite = false;
    boolean isAdded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        init();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);


        seekBar_song.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    StorageData.mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                song = SongData.getSongById(id, dataSnapshot);

                textView_name.setText(song.getTen());
                textView_singer.setText(song.getCaSi());

                String string_id_playlist = intent.getStringExtra("id_playlist");
                if(isNumeric(string_id_playlist))
                {
                    ListSong = PlaylistDetailData.getSongsByIdPlaylist(dataSnapshot, Integer.parseInt(string_id_playlist));
                }
                else
                {
                    if (string_id_playlist.equals("favorite"))
                    {
                        ListSong = FavoriteSongData.getListFavoriteSong(dataSnapshot, StorageData.id_user);
                    }
                    else
                    {
                        ListSong = SongData.getSongByLanguage(song.getNgonNgu(), dataSnapshot);
                    }

                }


                Picasso.get().load(song.getHinh()).into(imV_Song);
                playSong(song.getLink());

                if (savedInstanceState != null) {
                    int currentPosition = savedInstanceState.getInt("currentPosition");
                    StorageData.mediaPlayer.seekTo(currentPosition);
                }

                bt_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (StorageData.mediaPlayer.isPlaying()){
                            StorageData.mediaPlayer.pause();
                            bt_play.setImageResource(R.drawable.ic_play);
                        } else {
                            StorageData.mediaPlayer.start();
                            bt_play.setImageResource(R.drawable.ic_pause);
                        }
                    }
                });

                bt_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            song = ListSong.get(song.getId()+1);

                            Picasso.get().load(song.getHinh()).into(imV_Song);
                            playSong(song.getLink());
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            song = ListSong.get(0);
                            Picasso.get().load(song.getHinh()).into(imV_Song);
                            playSong(song.getLink());
                        }

                    }
                });

                bt_previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            song = ListSong.get(song.getId()-1);

                            Picasso.get().load(song.getHinh()).into(imV_Song);
                            playSong(song.getLink());
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            song = ListSong.get(ListSong.size()-1);
                            Picasso.get().load(song.getHinh()).into(imV_Song);
                            playSong(song.getLink());
                        }
                    }
                });

                bt_nexttime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int nextTime = StorageData.mediaPlayer.getCurrentPosition() + 10000;
                        StorageData.mediaPlayer.seekTo(nextTime);
                        seekBar_song.setProgress(nextTime);
                    }
                });

                bt_previoustime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int previousTime = StorageData.mediaPlayer.getCurrentPosition() - 10000;
                        StorageData.mediaPlayer.seekTo(previousTime);
                        seekBar_song.setProgress(previousTime);
                    }
                });

                bt_repeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isRepeat)
                        {
                            isRepeat = false;
                            bt_repeat.setImageResource(R.drawable.ic_loop);
                        }
                        else
                        {
                            isRepeat = true;
                            bt_repeat.setImageResource(R.drawable.ic_loop_selected);
                        }
                    }
                });

                button_songList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_layout_play, null);

                        RecyclerView recyclerView = mView.findViewById(R.id.recyclerView);
                        Button button_cancel = mView.findViewById(R.id.button_cancel);

                        PlayAdapter adapter = new PlayAdapter(ListSong, context, StorageData.mediaPlayer);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);

                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();

                        button_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });

                FirebaseHelper.getDataChange(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isFavorite = FavoriteSongData.check(snapshot, StorageData.id_user, id);
                        if(isFavorite)
                        {
                            bt_favorite.setImageResource(R.drawable.ic_favorite_selected);
                        }
                        else
                        {
                            bt_favorite.setImageResource(R.drawable.ic_favorite);
                        }
                        bt_favorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isFavorite)
                                {
                                    bt_favorite.setImageResource(R.drawable.ic_favorite);
                                    String path = "YeuThich/" + FavoriteSongData.getKeyFavoriteSong(snapshot, StorageData.id_user, id);
                                    FirebaseHelper.deleteData(path);
                                    isFavorite = false;
                                }
                                else
                                {
                                    bt_favorite.setImageResource(R.drawable.ic_favorite_selected);
                                    Map<String, Object> childValues = new HashMap<>();
                                    childValues.put("Id_BaiHat", id);
                                    childValues.put("Id_NguoiDung", StorageData.id_user);
                                    FirebaseHelper.addData("YeuThich", childValues);
                                    isFavorite = true;
                                }
                            }
                        });

                        isAdded = PlaylistDetailData.checkSongAdded(snapshot, StorageData.id_user, id);
                        if(isAdded)
                        {
                            bt_add.setImageResource(R.drawable.ic_add_selected);
                        }
                        else
                        {
                            bt_add.setImageResource(R.drawable.ic_add);
                        }
                        bt_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                                View mView = getLayoutInflater().inflate(R.layout.dialog_layout_playlist, null);

                                RecyclerView dialog_listView = mView.findViewById(R.id.dialog_recyclerView);
                                Button button_cancel = mView.findViewById(R.id.button_cancel);
                                ArrayList<Playlist> playlists = PlaylistData.getPlaylist(snapshot, StorageData.id_user);

                                DialogPlaylistAdapter dialogPlaylistAdapter = new DialogPlaylistAdapter(playlists, id, context);
                                dialog_listView.setLayoutManager(new LinearLayoutManager(context));
                                dialog_listView.setAdapter(dialogPlaylistAdapter);


                                mBuilder.setView(mView);
                                AlertDialog dialog = mBuilder.create();
                                dialog.show();

                                button_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageData.mediaPlayer.reset();
                finish();
            }
        });

    }
    private void init()
    {
        imV_Song = findViewById(R.id.imV_song);
        bt_play = findViewById(R.id.bt_play);
        bt_next = findViewById(R.id.bt_next);
        bt_previous = findViewById(R.id.bt_previous);
        seekBar_song = findViewById(R.id.seekBar_song);
        bt_nexttime = findViewById(R.id.bt_nexttime);
        bt_previoustime = findViewById(R.id.bt_previoustime);
        bt_repeat = findViewById(R.id.bt_repeat);
        textView_singer = findViewById(R.id.textView_singer);
        textView_name = findViewById(R.id.textView_name);
        imageButton_back = findViewById(R.id.imageButton_back);
        bt_favorite = findViewById(R.id.bt_favorite);
        bt_add = findViewById(R.id.bt_add);
        textView_currentTime = findViewById(R.id.textView_currentTime);
        textView_maxTime = findViewById(R.id.textView_maxTime);
        button_songList = findViewById(R.id.button_songList);
    }

    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            int currentPosition = StorageData.mediaPlayer.getCurrentPosition();
            seekBar_song.setProgress(currentPosition);
            textView_currentTime.setText(milliSecondsToTimer(currentPosition));
            mHandler.postDelayed(this, 1000);
        }
    };
    private void playSong(String songLink) {
        try {
            StorageData.mediaPlayer.reset();
            StorageData.mediaPlayer.setDataSource(songLink);
            StorageData.mediaPlayer.prepare();
            StorageData.mediaPlayer.start();

            seekBar_song.setMax(StorageData.mediaPlayer.getDuration());
            textView_maxTime.setText(String.valueOf(milliSecondsToTimer(StorageData.mediaPlayer.getDuration())));
            mHandler.postDelayed(updateSeekBarTime, 1000);

            StorageData.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(isRepeat)
                    {
                        mp.seekTo(0);
                        mp.start();
                    }
                    else
                    {
                        bt_play.setImageResource(R.drawable.ic_play);
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    private String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        return finalTimerString;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        int current = StorageData.mediaPlayer.getCurrentPosition();
        outState.putInt("currentPosition", current);
    }
}