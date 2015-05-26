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
public class KOpcje extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcje);//wyświetlenie opcje





        final KEngine silnik= new KEngine();

        // stowrzenie obiektów dla przycisków

        Button powrot=(Button) findViewById(R.id.powrot);



        powrot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game= new Intent(getApplicationContext(), MainMenu.class);
                KOpcje.this.startActivity(game);
                //setContentView(R.layout.activity_main);


            }


        });















    }
}