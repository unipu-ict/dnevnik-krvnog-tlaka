package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UnosActivity extends AppCompatActivity {

    // region PRIVATNE METODE
    private DbHelper myDb;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unos);

        // Instanciranje baze
        myDb = new DbHelper(this);

        // Tipke za numeričku tipkovnicu
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        button0 = (Button)findViewById(R.id.button0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 1", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 2", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 3", Toast.LENGTH_SHORT).show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 4", Toast.LENGTH_SHORT).show();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 5", Toast.LENGTH_SHORT).show();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 6", Toast.LENGTH_SHORT).show();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 7", Toast.LENGTH_SHORT).show();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 8", Toast.LENGTH_SHORT).show();
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 9", Toast.LENGTH_SHORT).show();
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pritisnuta je tipka 0", Toast.LENGTH_SHORT).show();
            }
        });


        // kontrole za unos parametara nakon mjerenja krvnog tlaka
        final EditText etSistolicki = (EditText) findViewById(R.id.etSistolicki);
        final EditText etDijastolicki = (EditText) findViewById(R.id.etDijastolicki);
        final EditText etPuls = (EditText) findViewById(R.id.etPuls);

        // Tipka za potvrdu unosa krvnog tlaka
        Button unosKrvnogTlaka = (Button)findViewById(R.id.buttonUnos);

        unosKrvnogTlaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etSistolicki.getText().toString().equals("") &&
                        !etDijastolicki.getText().toString().equals("") &&
                        !etPuls.getText().toString().equals("")) {

                    int sistolicki = Integer.parseInt(etSistolicki.getText().toString());
                    int diastolicki = Integer.parseInt(etDijastolicki.getText().toString());
                    int puls = Integer.parseInt(etPuls.getText().toString());

                    Validacija validacija = new Validacija(getApplicationContext());
                    if (validacija.validiraj(sistolicki, diastolicki, puls)) {
                        dodajNoviUnos(sistolicki, diastolicki, puls);
                        View v = getWindow().getDecorView().findFocus();
                        pregledKrvnogTlaka(v);
                    } else {
                        Toast.makeText(getApplicationContext(), "Došlo je do pogreške", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Morate popuniti sva polja za unos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // region PRIVATNE METODE
    private void dodajNoviUnos(int sistolicki, int dijastolicki, int puls) {
        try {
            myDb.insertData(String.valueOf(sistolicki), String.valueOf(dijastolicki), String.valueOf(puls));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



    // endregion

    // region NAVIGACIJA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_unos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        View v = getWindow().getDecorView().findFocus();

        if (id == R.id.povijest_menu) {
            povijestKrvnogTlaka(v);
            return true;
        }
        else if (id == R.id.pocetna_menu) {
            pocetnaStranica(v);
            return true;
        }
        else if (id == R.id.pregled_menu) {
            pregledKrvnogTlaka(v);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void povijestKrvnogTlaka(View v) {
        Intent i  = new Intent(UnosActivity.this, PovijestActivity.class);
        startActivity(i);
    }

    public void pocetnaStranica(View v) {
        Intent i  = new Intent(UnosActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void pregledKrvnogTlaka(View v) {
        Intent i  = new Intent(UnosActivity.this, PregledActivity.class);
        startActivity(i);
    }
    // endregion
}
