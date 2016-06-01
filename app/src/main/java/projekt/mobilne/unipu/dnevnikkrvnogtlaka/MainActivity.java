package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String komentar;

    // region PRIVATNE VARIJABLE
    private ImageButton buttonNoviUnos;
    private ImageButton buttonPovijest;

    private TextView tvZadnjaTri;
    private TextView tvSistolicki;
    private TextView tvDijastolicki;
    private TextView tvPuls;
    private TextView tvZadnjeMjerenje;
    private DbHelper myDb;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Programski postavljen naziv screena
        this.setTitle("Krvni tlak");

        tvSistolicki = (TextView)findViewById(R.id.tvSistolicki);
        tvDijastolicki = (TextView)findViewById(R.id.tvDijastolicki);
        tvPuls = (TextView)findViewById(R.id.tvPuls);
        tvZadnjeMjerenje = (TextView) findViewById(R.id.tvZadnjeMjerenje);

        // Instanciranje baze
        myDb = new DbHelper(this);
        getZadnji();
        tvZadnjaTri = (TextView) findViewById(R.id.zadnja_tri_textView);
        ispisZadnjaTriUnosa();

        // ImageButtoni za prelazak na sljedeću aktivnost
        buttonNoviUnos = (ImageButton) findViewById(R.id.buttonDodajTlak);
        buttonPovijest = (ImageButton) findViewById(R.id.buttonPovijest);

        buttonNoviUnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UnosActivity.class);
                startActivity(i);
            }
        });

        buttonPovijest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MainActivity.this, PregledActivity.class);
                startActivity(i);
            }
        });
    }

    // region PRIVATNE METODE
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

    private void getZadnji() {
        Cursor res = myDb.getZadnji();

        String sist = "";
        String dist = "";
        String puls = "";

        while (res.moveToNext()) {
            sist = "" + res.getString(1);
            dist = "" + res.getString(2);
            puls = "" + res.getString(3);
            tvZadnjeMjerenje.append(" " + res.getString(4) + ")");
        }
        tvSistolicki.setText(sist);
        tvDijastolicki.setText(dist);
        tvPuls.setText(puls);
    }
    // endregion

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
