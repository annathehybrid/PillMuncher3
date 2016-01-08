package anna.richarddawkinsalarmclock;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

public class RingtonePlayingService extends Service {

    String richard_name;

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.e("MyActivity", "In the service");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {



        //if (!mMediaPlayer.isPlaying()) {
        //    Log.e("Mediaplayer ", " :we are inside play loop");
        //    mMediaPlayer.isPlaying();
        //    mMediaPlayer.setLooping(false);
        //    mMediaPlayer.start();
        //    //mMediaPlayer.stop();
       // }
        //else {

        //    Log.e("Mediaplayer ", " :we are inside stop loop");
        //    mMediaPlayer.stop();
        //    mMediaPlayer.reset();
        //    mMediaPlayer.release();
        //}

        String example = "R.raw.richard_dawkins_1";




        Log.e("MyActivity", "In the service");


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        // doesn't work
        MediaPlayer mMediaPlayer = new MediaPlayer();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer.stop();
        }
    }


}