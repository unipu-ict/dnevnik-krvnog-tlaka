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
    // TODO: to bi ja mislim trebalo samo u PovijestActivity-ju i UnosActivity-ju
    private static final String OPTIMALNI = "OPTIMALNI";
    private static final String NORMALNI = "NORMALNI";
    private static final String POVISENI = "POVISENI";
    private static final String VISOKI = "VISOKI";
    private static final String DOSTAVISOKI = "DOSTAVISOKI";
    private static final String HIPERTENZIJA = "HIPERTENZIJA";
    private static final String IZOLIRANI = "IZOLIRANI";

    private TextView tvZadnjaTri;
    private DbHelper myDb;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciranje baze
        myDb = new DbHelper(this);
        tvZadnjaTri = (TextView) findViewById(R.id.zadnja_tri_textView);
        ispisZadnjaTriUnosa();
    }


    private void ispisZadnjaTriUnosa() {
        Cursor res = myDb.getZadnjaTri();

        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append("" + res.getString(4) + "  *  ");
            buffer.append("" + res.getString(1) + "/");
            buffer.append("" + res.getString(2) + "  ");
            buffer.append("(" + res.getString(3) + ")\n");
        }
        tvZadnjaTri.setText("datum | sist | dijast | puls\n\n" + buffer.toString());
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
