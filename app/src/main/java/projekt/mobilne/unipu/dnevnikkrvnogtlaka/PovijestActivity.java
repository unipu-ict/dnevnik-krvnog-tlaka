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
import android.widget.TextView;
import android.widget.Toast;


public class PovijestActivity extends AppCompatActivity {

    // region PRIVATNE VARIJABLE
    private DbHelper myDb;
    private TextView povijest;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_povijest);

        LstFragment lstFragment = (LstFragment) getSupportFragmentManager().findFragmentByTag("lstfragment");
        if (lstFragment == null) {
            lstFragment = new LstFragment();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(android.R.id.content, lstFragment, "lstfragment");
            transaction.commit();
        }

        //povijest = (TextView) findViewById(R.id.tbPovijest);
        //povijest.setText("Povijest unosa:\n");

        myDb = new DbHelper(this);
        myDb.getAllData();

        Cursor cur = myDb.getAllData();
        if (cur.getCount() == 0) {
            Toast.makeText(PovijestActivity.this, "Nema zapisa u bazi!", Toast.LENGTH_LONG).show();
            return;
        }

        // Prvi buffer u kojeg se zapisuju ključne vrijednosti koje ćemo ispisati na screenu
        StringBuffer buffer = new StringBuffer();

        while (cur.moveToNext()) {
            buffer.append(cur.getString(4) + " | ");
            buffer.append(cur.getString(1) + " | ");
            buffer.append(cur.getString(2) + " | ");
            buffer.append(cur.getString(3) + "\n");
        }

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(PovijestActivity.this, "Nema podataka.", Toast.LENGTH_LONG).show();
        }
        int optimalni = 0;
        int normalni = 0;
        int poviseni = 0;
        int visoki = 0;
        int dostavisoki = 0;
        int hipertenzija = 0;
        int izolirani = 0;

        // Drugi buffer u kojem brojimo koliko je kojih tipova tlaka evidentirano
        StringBuffer buffer2 = new StringBuffer();
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

        //povijest.append(buffer);
        //povijest.append(buffer2);
    }

    // region NAVIGACIJA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_povijest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        View v = getWindow().getDecorView().findFocus();

        //noinspection SimplifiableIfStatement
        if (id == R.id.pocetna_menu) {
            pocetnaStranica(v);
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

    public void pocetnaStranica(View v) {
        Intent i  = new Intent(PovijestActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void unosNovogKrvnogTlaka(View v) {
        Intent i  = new Intent(PovijestActivity.this, UnosActivity.class);
        startActivity(i);
    }

    public void pregledKrvnogTlaka(View v) {
        Intent i  = new Intent(PovijestActivity.this, PregledActivity.class);
        startActivity(i);
    }
    // endregion
}
