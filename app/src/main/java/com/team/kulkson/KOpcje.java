package com.team.kulkson;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioButton;


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








    }




    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        RadioButton radio1 = (RadioButton)findViewById(R.id.zolty);
    RadioButton radio2 = (RadioButton)findViewById(R.id.niebieski);
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.zolty:
                if (checked)
                {
                radio2.setChecked(false);
                    radio1.setChecked(true);

                    KEngine.BALL=R.drawable.kula;
                }


                    break;
            case R.id.niebieski:
                if (checked)
                {
                    radio1.setChecked(false);
                    radio2.setChecked(true);

                    KEngine.BALL=R.drawable.heliboy2;

                }


                    break;
        }

    }




}