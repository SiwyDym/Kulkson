package com.team.kulkson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


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
    Button opcje=(Button) findViewById(R.id.opcje);
    Button wyniki=(Button) findViewById(R.id.wyniki);


    //obsługa przycisku nowa gra

    start.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            KEngine.aktualny=0;
            KEngine.PLAYER_BANK_SPEED=0.17f; //prędkośc poruszania
            KEngine.PLAYER_BANK_SPEED2=0.12f;
            KEngine.KOLCE_SPEED = 0.03f;//predkość poruszania pierwszego wroga
            KEngine.BLOK_SPEED=0.036f;//predkość poruszania drugiego wroga
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




    opcje.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {



            Intent MainMenu=new Intent(getApplicationContext(), KOpcje.class);
            MainMenu.this.startActivity(MainMenu);

        }
    });
    wyniki.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent MainMenu= new Intent(getApplicationContext(), KScore.class);
            MainMenu.this.startActivity(MainMenu);




            //setContentView(R.layout.wyniki);
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
