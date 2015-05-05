package com.team.kulkson;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

//trzeba skojarzyc ten plik z android manifest: w zakładce applikacje->application nodes
/**
 * Created by Marco on 2015-04-12.
 */
public class KMusic extends Service{

    public static boolean isRunning = false;
    MediaPlayer player; //odtwarza muzyke

    @Override
    public IBinder onBind(Intent arg0){

        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        setMusicOptions(this, KEngine.LOOP_BACKGROUND_MUSIC,KEngine.R_VOLUME,KEngine.L_VOLume,KEngine.SPLASH_SCREEN_MUSIC);

    }
    public void setMusicOptions(Context context, boolean isLooped, int rVolume, int lVolume, int soundFile){

        player = MediaPlayer.create(context, soundFile);
        player.setLooping(isLooped);
        player.setVolume(rVolume, lVolume);

    }
    public int onStartCommand(Intent intent, int flags, int startId){

        try{
            player.start();
            isRunning = true;
        } catch (Exception e){
            isRunning =false;
            player.stop();
        }
        return 1;
    }
    public void onStart(Intent intent, int startId){

    }
    public void onStop(){
        isRunning = false;
    }
    public IBinder onUnBind(Intent arg0){
        return null;
    }

    public void onPause(){

    }

    @Override
    public void onDestroy(){
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory(){
        player.stop();
    }

}
