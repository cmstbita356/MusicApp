package com.example.musicapp.Service;

import com.example.musicapp.Model.Song;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {
    public static void getData(ValueEventListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(listener);
    }
    public static void getData(String path, ValueEventListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(path);
        mDatabase.addListenerForSingleValueEvent(listener);
    }



}
