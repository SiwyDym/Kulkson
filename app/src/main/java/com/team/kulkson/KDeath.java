package com.team.kulkson;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class KDeath extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kdeath);

        final KEngine silnik = new KEngine();

        TextView tekst2 = (TextView) findViewById(R.id.wynik2);
        TextView tekst = (TextView) findViewById(R.id.wynik);


        tekst2.setText("Najlepszy wynik: " + KEngine.najlepszy);
        tekst.setText("Twój wynik: " + KEngine.aktualny);


    }
}