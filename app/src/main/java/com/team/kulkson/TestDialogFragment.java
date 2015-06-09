package com.team.kulkson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by root on 27.05.15.
 */
public abstract class TestDialogFragment extends FragmentManager {
    //    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getFragment());
        builder.setMessage("he he heszki   ")
                .setPositiveButton("nowosc", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("starosc", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        /* Create the AlertDialog object and return it */
        return builder.create();
    }

    protected abstract Context getFragment();

}
