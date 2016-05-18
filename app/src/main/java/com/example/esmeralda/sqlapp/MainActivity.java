package com.example.esmeralda.sqlapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity{

    private ArrayList<String> results = new ArrayList<String>();

    private static final String OPTIMALNI = "OPTIMALNI";
    private static final String NORMALNI = "NORMALNI";
    private static final String POVISENI = "POVISENI";
    private static final String VISOKI = "VISOKI";
    private static final String DOSTAVISOKI = "DOSTAVISOKI";
    private static final String HIPERTENZIJA = "HIPERTENZIJA";
    private static final String IZOLIRANI = "IZOLIRANI";

    private DatabaseHelper myDb;
    private TextView editTextZadnjaTri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText editSistolicki = (EditText)findViewById(R.id.sistolicki);
        final EditText editDiastolicki = (EditText)findViewById(R.id.dijastolicki);
        final EditText editPuls = (EditText)findViewById(R.id.puls);
        editTextZadnjaTri = (TextView)findViewById(R.id.zadnja_tri_textView);

        Button btnDodajTlak = (Button)findViewById(R.id.button_add);
        Button btnAllView = (Button)findViewById(R.id.buttonwiev);
        Button btnStanje = (Button)findViewById(R.id.buttonsve);

        btnDodajTlak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editSistolicki.getText().toString().equals("") &&
                        !editDiastolicki.getText().toString().equals("") &&
                        !editPuls.getText().toString().equals("")) {

                    int sistolicki = Integer.parseInt(editSistolicki.getText().toString());
                    int diastolicki = Integer.parseInt(editDiastolicki.getText().toString());
                    int puls = Integer.parseInt(editPuls.getText().toString());

                    Validacija validacija = new Validacija(getApplicationContext());
                    if (validacija.validiraj(sistolicki, diastolicki, puls)) {
                        AddData(sistolicki, diastolicki, puls);
                        ZadnjaTri();
                    } else {
                        Toast.makeText(getApplicationContext(), "Poruka o grešci", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Prazni unosi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAllView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAll();
            }
        });

        btnStanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStanje();
            }
        });

        ZadnjaTri();
    }


    private void ZadnjaTri() {
        Cursor res = myDb.getZadnjaTri();
        if (res.getCount() == 0) {
            showMessage1("Problemi","Nema ničega");

            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("" + res.getString(4) + "  *  ");
            buffer.append("" + res.getString(1) + "/");
            buffer.append("" + res.getString(2) + "  ");
            buffer.append("(" + res.getString(3) + ")\n");


        }
        showMessage1("datum | sist | dijast | puls", buffer.toString());

    }

    private void AddData(int sistolicki, int diastolicki, int puls) {
        boolean isInserted = myDb.insertData(sistolicki, diastolicki, puls);

        if (isInserted = true)
            Toast.makeText(MainActivity.this, "Tlak unešen", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Dalje nećeš moći", Toast.LENGTH_LONG).show();
    }

    private void viewAll(){
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Problemi","Nema ničega");

            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {

            buffer.append(res.getString(4) + " | ");
            buffer.append(res.getString(1) + " | ");
            buffer.append(res.getString(2) + " | ");
            buffer.append(res.getString(3) + "\n");

        }
        showMessage("Povjest unosa", buffer.toString());
    }

    private void viewStanje() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Problemi", "Nema ničega");

            return;
        }
        int optimalni = 0;
        int normalni = 0;
        int poviseni = 0;
        int visoki = 0;
        int dostavisoki = 0;
        int hipertenzija = 0;
        int izolirani = 0;


        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            if (res.getInt(1) < 120 & res.getInt(2) < 80) {
                optimalni++;
            }else if (res.getInt(1) < 130 & res.getInt(2) < 85) {
                normalni++;
            }else if (res.getInt(1) < 139 & res.getInt(2) < 89) {
                poviseni++;
            }else if (res.getInt(1) < 159 & res.getInt(2) < 99) {
                visoki++;
            }else if (res.getInt(1) < 179 & res.getInt(2) < 109) {
                dostavisoki++;
            }else if (res.getInt(1) < 159 & res.getInt(2) < 95) {
                hipertenzija++;
            }else if (res.getInt(1) > 140 & res.getInt(2) > 90) {
                izolirani++;
            }
        }
        buffer.append("optimalni: "+ optimalni+ "\n");
        buffer.append("normalni: "+ normalni+ "\n");
        buffer.append("poviseni: "+ poviseni+ "\n");
        buffer.append("visoki: "+ visoki+ "\n");
        buffer.append("dosta visoki: "+ dostavisoki+ "\n");
        buffer.append("hipertenzija: "+ hipertenzija+ "\n");
        buffer.append("izolirani: " + izolirani + "\n");

        showMessage("Podaci za grafikon", buffer.toString());

        /*
        int br1 = Integer.parseInt(optimalni);
        int br2 = Integer.parseInt(normalni);
        int br3 = Integer.parseInt(poviseni);
        int br4 = Integer.parseInt(ostalo);
        */

        Intent i = new Intent(MainActivity.this, Chart.class);
        i.putExtra(OPTIMALNI, optimalni);
        i.putExtra(NORMALNI, normalni);
        i.putExtra(POVISENI, poviseni);
        i.putExtra(VISOKI, visoki);
        i.putExtra(DOSTAVISOKI, visoki);
        i.putExtra(HIPERTENZIJA, hipertenzija);
        i.putExtra(IZOLIRANI, izolirani);
        startActivity(i);
    }

    private void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void showMessage1(String title, String Message){
        editTextZadnjaTri.setText(Message);
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
