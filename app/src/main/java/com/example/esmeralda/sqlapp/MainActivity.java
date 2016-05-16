package com.example.esmeralda.sqlapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity{

    private ArrayList<String> results = new ArrayList<String>();

    private static final String EXTRA_KEY = "poviseni";
    private static final String EXTRA_KEY2 = "normalni";
    private static final String EXTRA_KEY3 = "optimalni";
    private static final String EXTRA_KEY4 = "ostalo";


    DatabaseHelper myDb;
    EditText editSistolicki,editDiastolicki,editPuls;
    Button btnDodajTlak;
    Button btnAllWiev;
    Button btnStanje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editSistolicki = (EditText)findViewById(R.id.sistolicki);
        editDiastolicki = (EditText)findViewById(R.id.dijastolicki);
        editPuls = (EditText)findViewById(R.id.puls);
        btnDodajTlak = (Button)findViewById(R.id.button_add);
        btnAllWiev = (Button)findViewById(R.id.buttonwiev);
        btnStanje = (Button)findViewById(R.id.buttonsve);
        AddData();
        ZadnjaTri();
        viewAll();
        viewStanje();
    }


    public  void ZadnjaTri() {
        Cursor res = myDb.getZadnjaTri();
        if (res.getCount() == 0) {
            showMessage1("Error", "Nothing found");

            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("" + res.getString(4) + " | ");
            buffer.append("" + res.getString(1) + " | ");
            buffer.append("" + res.getString(2) + " | ");
            buffer.append("" + res.getString(3) + "\n");


        }
        //showMessage1("datum | sist | dijast | puls", buffer.toString());




        if (res != null ) {
            if (res.moveToFirst()) {
                do {
                    String firstName = res.getString(4);
                    int age = res.getInt(3);
                    results.add("Name: " + firstName + ",Age: " + age);
                }while (res.moveToNext());
            }
        }








    }


    public  void AddData() {
        btnDodajTlak.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editSistolicki.getText().toString(),
                                editDiastolicki.getText().toString(),
                                editPuls.getText().toString());

                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Tlak unešen", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Dalje nećeš moći", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll(){
        btnAllWiev.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "Nothing found");

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
                }
        );
    }

    public void viewStanje(){



        btnStanje.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            showMessage("Error","Nothing found");

                            return;
                        }
                        int optimalni = 0;
                        int normalni = 0;
                        int poviseni = 0;
                        int ostalo = 0;
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            if(res.getInt(1) <= 120 & res.getInt(2)<85){
                                optimalni++;
                            }else if(res.getInt(1)<130 & res.getInt(2)<85){
                                normalni++;
                            }else {
                                if (res.getInt(1) < 159 & res.getInt(2) < 95) {
                                    poviseni++;
                                } else {
                                    ostalo++;
                                }
                            }

                        }
                        buffer.append("optimalni: "+ optimalni+ "\n");
                        buffer.append("normalni: "+ normalni+ "\n");
                        buffer.append("poviseni: "+ poviseni+ "\n");
                        buffer.append("ostalo: " + ostalo + "\n");

                        showMessage("Data", buffer.toString());

                        /*

                        int br1 = Integer.parseInt(optimalni);
                        int br2 = Integer.parseInt(normalni);
                        int br3 = Integer.parseInt(poviseni);
                        int br4 = Integer.parseInt(ostalo);
*/
                                Intent i = new Intent(MainActivity.this, Chart.class);
                                i.putExtra(EXTRA_KEY, optimalni);
                                i.putExtra(EXTRA_KEY2, normalni);
                                i.putExtra(EXTRA_KEY3, poviseni);
                                i.putExtra(EXTRA_KEY4, ostalo);
                                startActivity(i);

                    }
                }
        );

    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void showMessage1(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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
