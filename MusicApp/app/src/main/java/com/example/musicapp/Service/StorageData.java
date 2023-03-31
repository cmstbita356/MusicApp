package com.example.musicapp.Service;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.musicapp.R;

public class StorageData {
    public static int id_user;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static CountDownTimer timer;
//    public static void miniLayout_Play(LinearLayout layout, ImageButton playButton)
//    {
//        layout.setVisibility(View.GONE);
//        if(mediaPlayer.getAudioSessionId() != -1 )
//        {
//            layout.setVisibility(View.VISIBLE);
//            if(!mediaPlayer.isPlaying())
//            {
//                playButton.setImageResource(R.drawable.ic_play);
//            }
//            playButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mediaPlayer.isPlaying())
//                    {
//                        mediaPlayer.stop();
//                        playButton.setImageResource(R.drawable.ic_play);
//                    }
//                    else
//                    {
//                        mediaPlayer.start();
//                        playButton.setImageResource(R.drawable.ic_pause);
//                    }
//                }
//            });
//        }

//    }
}
