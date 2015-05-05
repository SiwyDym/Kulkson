package com.team.kulkson;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;


//trzeba powiązać Kgame z projektem poprzez AndroidMAnifest

public class KGame extends Activity { //dziedziczenie z klasy Activity

    private KGameView gameView;

    @Override
    public  boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                KEngine.playerFlightAction=KEngine.PLAYER_BANK_JUMP;
               break;

        }
        return false;

    }
    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        gameView = new KGameView(this);
        setContentView(gameView);
        //        super.onCreate(savedInstanceState);   w MARCINA PLIKU TAK BYŁO
        // setContentView(R.layout.plansza_gry_2);
    }

    @Override
    public void onResume(){
        super.onResume();
        gameView.onResume();

    }

    @Override
    public void onPause(){ //zatrzymuje wykonywanie czynności
        super.onPause();
        gameView.onPause();

    }
}
