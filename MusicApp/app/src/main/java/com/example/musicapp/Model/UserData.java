package com.example.musicapp.Model;

import com.google.firebase.database.DataSnapshot;

public class UserData {
    public static User getUserById(DataSnapshot dataSnapshot, int id)
    {
        User user = new User();
        for (DataSnapshot snapshot : dataSnapshot.getChildren())
        {
            if(snapshot.child("Id").getValue(Integer.class).equals(id))
            {
                return  snapshot.getValue(User.class);
            }
        }
        return null;
    }
}
