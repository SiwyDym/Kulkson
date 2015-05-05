package com.team.kulkson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

/**
 * Created by xxxx on 2015-04-04.
 */
public class MainMenu extends ActionBarActivity {
@Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//wyświetlenie menu





   final KEngine silnik= new KEngine();

    // stowrzenie obiektów dla przycisków
    Button start=(Button) findViewById(R.id.nowa_gra);
    Button exit=(Button) findViewById(R.id.wyjscie);

   // start.setHapticFeedbackEnabled(Silnik.HAPTIC_BUTTON_FEEDBACK);
   // exit.setHapticFeedbackEnabled(Silnik.HAPTIC_BUTTON_FEEDBACK);

    //obsługa przycisku nowa gra

    start.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent game= new Intent(getApplicationContext(), KGame.class);
            MainMenu.this.startActivity(game);
        }
    });

    //obsługa przycisku wyjście

    exit.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean clean=false;
            clean= silnik.onExit(v);
            if(clean)
            {
                int pid=android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        }
    });

    KEngine.musicThread = new Thread(){
        public void run(){
            Intent bgmusic = new Intent(getApplicationContext(), KMusic.class);
            startService(bgmusic);
            KEngine.context = getApplicationContext();
        }

    };
    KEngine.musicThread.start();


}
}
