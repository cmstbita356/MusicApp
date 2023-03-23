package com.example.musicapp.Model;

import android.media.MediaPlayer;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SongData {
    public static void getAllSongs(final SongDataCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Song> songs = new ArrayList<>();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("BaiHat");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Song song = snapshot.getValue(Song.class);
                            songs.add(song);
                        }
                        callback.onAllSongDataReceived(songs);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Firebase Link", "Lỗi khi đọc dữ liệu từ Firebase.", databaseError.toException());
                        callback.onSongDataError(databaseError.getMessage());
                    }
                });
            }
        });
    }
    public static void getSongById(int id, final SongDataCallback callback)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("BaiHat");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.child("Id").getValue(Integer.class).equals(id))
                            {
                                callback.onSongDataReceived(snapshot.getValue(Song.class));
                                return;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Firebase Link", "Lỗi khi đọc dữ liệu từ Firebase.", databaseError.toException());
                        callback.onSongDataError(databaseError.getMessage());
                    }
                });
            }
        });
    }
    public static void getLanguageSong(String language, final SongDataCallback callback)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Song> songs = new ArrayList<>();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("BaiHat");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.child("NgonNgu").getValue(String.class).equals(language))
                            {
                                songs.add(snapshot.getValue(Song.class));
                            }
                        }
                        callback.onLanguageSongDataReceived(songs);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Firebase Link", "Lỗi khi đọc dữ liệu từ Firebase.", databaseError.toException());
                        callback.onSongDataError(databaseError.getMessage());
                    }
                });
            }
        });
    }

    public interface SongDataCallback {
        void onAllSongDataReceived(ArrayList<Song> songs);
        void onSongDataReceived(Song song);
        void onLanguageSongDataReceived(ArrayList<Song> songs);
        void onSongDataError(String message);
    }
}

