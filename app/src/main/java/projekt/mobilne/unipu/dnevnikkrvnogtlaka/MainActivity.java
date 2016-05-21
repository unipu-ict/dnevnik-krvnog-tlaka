package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    /* TODO: nisam siguran dal mi je potreban
    public ArrayList<String> results = new ArrayList<>();
    */

    // region PRIVATNE VARIJABLE
    // TODO: to bi ja mislim trebalo samo u PovijestActivity-ju
    private static final String OPTIMALNI = "OPTIMALNI";
    private static final String NORMALNI = "NORMALNI";
    private static final String POVISENI = "POVISENI";
    private static final String VISOKI = "VISOKI";
    private static final String DOSTAVISOKI = "DOSTAVISOKI";
    private static final String HIPERTENZIJA = "HIPERTENZIJA";
    private static final String IZOLIRANI = "IZOLIRANI";

    private DbHelper myDb;
    private TextView etZadnjaTri;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciranje baze
        myDb = new DbHelper(this);

        /* TODO: nisam siguran kaj je to
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */


        /* TODO: nisam siguran koja je koja tipka
        final EditText etSistolicki = (EditText) findViewById(R.id.etSistolicki);
        final EditText etDijastolicki = (EditText) findViewById(R.id.etDijastolicki);
        final EditText etPuls = (EditText) findViewById(R.id.etPuls);
        etZadnjaTri = (EditText) findViewById(R.id.etZadnjaTri);

        Button buttonDodajTlak = (Button)findViewById(R.id.buttonDodajTlak);
        Button buttonPovijest = (Button)findViewById(R.id.buttonPovijest);
        Button buttonStanje = (Button)findViewById(R.id.buttonStanje);
        */

        // TODO: implementirati onClick metode na sve tri tipke



        ispisZadnjaTriUnosa();
    }

    private void ispisZadnjaTriUnosa() {
        Cursor res = myDb.getZadnjaTri();

        if(res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Tlak je unesen!", Toast.LENGTH_LONG).show();

            return;
        }

        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append("" + res.getString(4) + "  *  ");
            buffer.append("" + res.getString(1) + "/");
            buffer.append("" + res.getString(2) + "  ");
            buffer.append("(" + res.getString(3) + ")\n");
        }
        Toast.makeText(MainActivity.this, "datum | sist | dijast | puls" + buffer.toString(), Toast.LENGTH_LONG).show();
    }

    private void dodajNoviUnos(int sistolicki, int dijastolicki, int puls) {
        /* TODO: varijable moraju biti tipa string prilikom unosa u bazu
        boolean isInserted = myDb.insertData(sistolicki, dijastolicki, puls);

        if (isInserted = true)
            Toast.makeText(MainActivity.this, "Tlak unešen", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Dalje nećeš moći", Toast.LENGTH_LONG).show();
        */
    }

    private void ispisSvihUnosa() {
        Cursor cur = myDb.getAllData();
        if (cur.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Nema zapisa u bazi!", Toast.LENGTH_LONG).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while (cur.moveToNext()) {
            buffer.append(cur.getString(4) + " | ");
            buffer.append(cur.getString(1) + " | ");
            buffer.append(cur.getString(2) + " | ");
            buffer.append(cur.getString(3) + "\n");
        }
        Toast.makeText(MainActivity.this, "Povijest unosa:\n" + buffer, Toast.LENGTH_LONG).show();
    }

    /* TODO: to je u biti prelazak na PregledActivity, pogledati Antunov prototip
    private void viewStanje() {
        View v = getWindow().getDecorView().findFocus();
        pregledKrvnogTlaka(v);
    }
    */



    // region NAVIGACIJA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        View v = getWindow().getDecorView().findFocus();

        if (id == R.id.povijest_menu) {
            povijestKrvnogTlaka(v);
            return true;
        }
        else if (id == R.id.unos_menu) {
            unosNovogKrvnogTlaka(v);
            return true;
        }
        else if (id == R.id.pregled_menu) {
            pregledKrvnogTlaka(v);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void povijestKrvnogTlaka(View v) {
        Intent i  = new Intent(MainActivity.this, PovijestActivity.class);
        startActivity(i);
    }

    public void unosNovogKrvnogTlaka(View v) {
        Intent i  = new Intent(MainActivity.this, UnosActivity.class);
        startActivity(i);
    }

    public void pregledKrvnogTlaka(View v) {
        Intent i  = new Intent(MainActivity.this, PregledActivity.class);
        startActivity(i);
    }
// endregion
}
