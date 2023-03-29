package com.example.musicapp.Model;

import com.google.firebase.database.DataSnapshot;

public class UserData {
    public static User getUserById(DataSnapshot dataSnapshot, int id)
    {
        User user = new User();
        for (DataSnapshot snapshot : dataSnapshot.child("NguoiDung").getChildren())
        {
            if(snapshot.child("Id").getValue(Integer.class).equals(id))
            {
                return  snapshot.getValue(User.class);
            }
        }
        return null;
    }
    public static boolean CheckExisted(DataSnapshot dataSnapshot, String username)
    {
        for (DataSnapshot snapshot : dataSnapshot.child("NguoiDung").getChildren())
        {
            if(snapshot.child("TaiKhoan").getValue(String.class).equals(username))
            {
                return  false;
            }
        }
        return true;
    }
    public static int getMaxId(DataSnapshot dataSnapshot)
    {
        int maxid = 0;
        for (DataSnapshot snapshot : dataSnapshot.child("NguoiDung").getChildren())
        {
            maxid = snapshot.child("Id").getValue(Integer.class);
        }
        return maxid;
    }
}
