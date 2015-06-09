package com.team.kulkson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;


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

    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        gameView = new KGameView(this, new KGameView.KGameInterface() {
            @Override
            public void onColisionDetection() {
                finish();
                Intent intent = new Intent(KGame.this, KDeath.class);
                startActivity(intent);

            }
        });

        setContentView(gameView);
    }

    @Override
    public void onResume(){
        super.onResume();
        gameView.onResume();

    }

    @Override
    public void onPause(){ //zatrzymuje wykonywanie czynności
        super.onPause();
    }
}
