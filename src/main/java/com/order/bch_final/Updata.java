package com.order.bch_final;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class Updata extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;
        View v= LayoutInflater.from(getContext()).inflate(R.layout.updata_layout,null);
        builder = new AlertDialog.Builder(getContext());
        builder.setView(v);
        builder.setPositiveButton("خروج", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Do something here
            }
        });
        return builder.create();

    }
}
