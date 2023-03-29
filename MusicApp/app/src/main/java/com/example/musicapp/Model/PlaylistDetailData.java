package com.example.musicapp.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class PlaylistDetailData {
    public static int getQuantitySong(DataSnapshot dataSnapshot, int id_playlist)
    {
        int count = 0;
        for (DataSnapshot snapshot : dataSnapshot.child("PlaylistDetail").getChildren()) {
            if(snapshot.child("Id_Playlist").getValue(Integer.class).equals(id_playlist))
            {
                count = count + 1;
            }
        }
        return count;
    }
    public static ArrayList<Integer> getListQuantitySong(DataSnapshot dataSnapshot, ArrayList<Integer> listIdPlaylist)
    {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i : listIdPlaylist)
        {
            result.add(getQuantitySong(dataSnapshot, i));
        }
        return result;
    }
}
