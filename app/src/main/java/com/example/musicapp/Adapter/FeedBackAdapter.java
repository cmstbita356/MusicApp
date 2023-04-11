package com.example.musicapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.FeedBack;
import com.example.musicapp.Model.UserData;
import com.example.musicapp.R;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.ViewHolder>{
    public FeedBackAdapter(ArrayList<FeedBack> data, DataSnapshot dataSnapshot, Context context) {
        this.data = data;
        this.dataSnapshot = dataSnapshot;
        this.context = context;
    }

    ArrayList<FeedBack> data;
    DataSnapshot dataSnapshot;
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_settings, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedBack feedBack = data.get(position);
        holder.textView_userName.setText("Tài khoản: "+UserData.getUserById(dataSnapshot,feedBack.getId_NguoiDung()).getTaiKhoan());
        holder.textView_feedback.setText("Nhận xét: "+feedBack.getFeedback());
        switch (feedBack.getStar_vote()){
            case 1:
                holder.star2.setImageResource(R.drawable.ic_star);
                holder.star3.setImageResource(R.drawable.ic_star);
                holder.star4.setImageResource(R.drawable.ic_star);
                holder.star5.setImageResource(R.drawable.ic_star);
                break;
            case 2:
                holder.star3.setImageResource(R.drawable.ic_star);
                holder.star4.setImageResource(R.drawable.ic_star);
                holder.star5.setImageResource(R.drawable.ic_star);
                break;
            case 3:
                holder.star4.setImageResource(R.drawable.ic_star);
                holder.star5.setImageResource(R.drawable.ic_star);break;
            case 4:
                holder.star5.setImageResource(R.drawable.ic_star);break;
            case 5:
                break;
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_userName;
        ImageView star1,star2,star3,star4,star5;
        TextView textView_feedback;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_userName = itemView.findViewById(R.id.tV_username);
            textView_feedback = itemView.findViewById(R.id.tV_feedback);
            star1 = itemView.findViewById(R.id.iV_starr1);
            star2 = itemView.findViewById(R.id.iV_starr2);
            star3 = itemView.findViewById(R.id.iV_starr3);
            star4 = itemView.findViewById(R.id.iV_starr4);
            star5 = itemView.findViewById(R.id.iV_starr5);
        }

    }
}
