package com.team.kulkson;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
        <<<<<<<Updated upstream


        =======
        Button powrot1 = (Button) findViewById(R.id.powrot1);
        >>>>>>>Stashed changes
        TextView tekst2=(TextView)findViewById(R.id.wynik2);
        TextView tekst=(TextView)findViewById(R.id.wynik);

       // tekst.addTextChangedListener(KEngine.pomocnicza);

        tekst2.setText("Najlepszy wynik: "+MainActivity.najlepszy);
        tekst.setText("Aktualny wynik: "+MainActivity.aktualny);

//        powrot1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent game = new Intent(getApplicationContext(), MainMenu.class);
//                int pid2 = android.os.Process.myPid();
//                android.os.Process.killProcess(pid2);
//
//                KScore.this.startActivity(game);
//
//
//            }

        <<<<<<<Updated upstream

        //});
        =======
        KScore.this.startActivity(game);
    }


});
        >>>>>>>Stashed changes

    }
}
