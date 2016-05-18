package com.example.esmeralda.sqlapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Esmeralda on 17.5.2016..
 */
public class Validacija {

    private Context context;
    private final int SISTOLICKI_MAX = 155;

    public Validacija(Context context) {
        this.context = context;
    }

    public boolean validiraj(int sistolicki, int diastolicki, int puls) {
        // tu ces provjeravati, ako je nesto krivo
        Toast.makeText(context, "Poruka o grešci", Toast.LENGTH_SHORT).show();
        return true;
    }
}
