package com.team.kulkson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by xxxx on 2015-05-26.
 */
public class KScore extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.wyniki);


        final KEngine silnik = new KEngine();



        // stowrzenie obiektów dla przycisków


        Button powrot1 = (Button) findViewById(R.id.powrot1);


        powrot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(getApplicationContext(), MainMenu.class);
                int pid2=android.os.Process.myPid();
                android.os.Process.killProcess(pid2);

                KScore.this.startActivity(game);


            }


        });

    }
}
