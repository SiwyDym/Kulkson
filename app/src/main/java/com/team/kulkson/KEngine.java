package com.team.kulkson;

import android.content.Context;
import android.content.Intent;
import android.view.View;

//heb he

/**
 * Created by Marco on 2015-04-11.
 */
public class KEngine {
    //zmiana nowa

    public static final int GAME_THREAD_DELAY = 4000;
    public static final int MENU_BUTTON_ALPHA = 0;
    public static final boolean HAPTIC_BUTTON_FEEDBACK = true;
    public static final int SPLASH_SCREEN_MUSIC = R.raw.test; //pliczek muzyczny, rozszerzenie .ogg
    public static final int R_VOLUME = 100;
    public static final int L_VOLume = 100;
    public static final boolean LOOP_BACKGROUND_MUSIC = true;
    public static int GAME_THREA_FPS_SLEEP = (1000/60); //odpowiedzialna za wyświetlanie się 60kl/sekunde
    public static Context context;
    public static Thread musicThread;
    public static final int BACKGROUND_LAYER_ONE = R.drawable.background;// backgroundstars-nasze tło umieszczone w folderze drawable

    public static float SCROLL_BACKGROUND_1 = .002f;

    public static int playerFlightAction=0; //śledzenie akcji, które gracz wykonał, aby odpowiednio na nie odpowiedziec  w petli gry
    public static final int BALL=R.drawable.kula; // wskazanie na obrazek bohatera
    public static final int PLAYER_BANK_JUMP=1; //akcje postaci- skok
    public static final float PLAYER_BANK_SPEED=.1f; //prędkośc poruszania
    public static final float PLAYER_BANK_SPEED2=0.05f;
    public static float playerBankPosY=0f;
    public static boolean boolflag;



    public static  int CHARACTER_SHEET1=R.drawable.blok;// wskazanie na obrazek przeciwnika1
    public static  int CHARACTER_SHEET2=R.drawable.blok;// wskazanie na obrazek przeciwnika1



    public static float KOLCE_SPEED = SCROLL_BACKGROUND_1*8f;//predkość poruszania pierwszego wroga
    public static float BLOK_SPEED=SCROLL_BACKGROUND_1*10f;//predkość poruszania drugiego wroga

    public static final int TYPE_KOLCE=1;
    public static final int TYPE_BLOK=2;


    //zamknij wątek gry i wyjdź z niej
    public boolean onExit(View v){
        try{
            Intent bgmusic = new Intent(context, KMusic.class); //trzeba stworzyć klase KMusic
            context.stopService(bgmusic);
            //musicThread.stop();
            return true;
        } catch (Exception e){
            return false;
        }
    }



}
