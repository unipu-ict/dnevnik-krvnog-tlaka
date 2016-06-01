package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UnosActivity extends AppCompatActivity {

    // region PRIVATNE VARIJABLE
    private DbHelper myDb;
    private boolean val = false;
    private EditText etSistolicki;
    private EditText etDijastolicki;
    private EditText etPuls;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unos);

        // kontrole za unos parametara nakon mjerenja krvnog tlaka
        etSistolicki = (EditText)findViewById(R.id.etSistolicki);
        etDijastolicki = (EditText)findViewById(R.id.etDijastolicki);
        etPuls = (EditText)findViewById(R.id.etPuls);

        etSistolicki.setInputType(InputType.TYPE_NULL);
        etDijastolicki.setInputType(InputType.TYPE_NULL);
        etPuls.setInputType(InputType.TYPE_NULL);

        // Programski postavljen naziv screena
        this.setTitle("Unos krvnog tlaka");

        // Instanciranje baze
        myDb = new DbHelper(this);

        // Tipka za potvrdu unosa krvnog tlaka
        Button unosKrvnogTlaka = (Button)findViewById(R.id.buttonUnos);

        Button buttonBrisanje = (Button)findViewById(R.id.buttonBrisanje);

        buttonBrisanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSistolicki.isFocused()) {
                    if(etSistolicki.length() > 0 )
                        etSistolicki.setText(etSistolicki.getText().toString().substring(0, etSistolicki.length()-1));
                } else if (etDijastolicki.isFocused()) {
                    if(etDijastolicki.length() > 0 ) {
                        etDijastolicki.setText(etDijastolicki.getText().toString().substring(0, etDijastolicki.length() - 1));
                        if (etDijastolicki.length() == 0)
                            etSistolicki.requestFocus();
                    }
                } else if (etPuls.isFocused()) {
                    if(etPuls.length() > 0 ) {
                        etPuls.setText(etPuls.getText().toString().substring(0, etPuls.length() - 1));
                        if(etPuls.length() == 0)
                            etDijastolicki.requestFocus();
                    }
                }
            }
        });

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
                    val = validacija.validiraj(sistolicki, diastolicki, puls);
                    if (val) {
                        /* TODO: vrijeme
                        Long vrijeme = System.currentTimeMillis()/1000;
                        String temp = myDb.getDatumIVrijeme(vrijeme);
                        */
                        dodajNoviUnos(sistolicki, diastolicki, puls);
                        View v = getWindow().getDecorView().findFocus();
                        Toast.makeText(getApplicationContext(), "Tlak je uspješno unesen", Toast.LENGTH_SHORT).show();
                        pocetnaStranica(v);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Došlo je do pogreške", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Morate popuniti sva polja!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void onClickNumberButton(View v) {
        CharSequence broj = ((Button)v).getText();
        if (etSistolicki.isFocused()) {
            etSistolicki.append(broj);
            if (etSistolicki.length() == 3)
                etDijastolicki.requestFocus();
        } else if (etDijastolicki.isFocused()) {
            etDijastolicki.append(broj);
            if (etDijastolicki.length() == 3)
                etPuls.requestFocus();
        } else if (etPuls.isFocused()) {
            etPuls.append(broj);
        }
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
