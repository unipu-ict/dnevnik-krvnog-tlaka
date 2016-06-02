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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private String komentar;

    // region PRIVATNE VARIJABLE
    private ImageButton buttonNoviUnos;
    private ImageButton buttonPovijest;

    private TextView tvSistolicki;
    private TextView tvDijastolicki;
    private TextView tvPuls;
    private TextView tvZadnjeMjerenje;
    private DbHelper myDb;


    // datum, vrijeme, vrijednost
    private TextView tvDatum1;
    private TextView tvDatum2;
    private TextView tvDatum3;
    private TextView tvVrijeme1;
    private TextView tvVrijeme2;
    private TextView tvVrijeme3;
    private TextView tvVrijednost1;
    private TextView tvVrijednost2;
    private TextView tvVrijednost3;
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

        tvDatum1 = (TextView) findViewById(R.id.tvTableA1);
        tvDatum2 = (TextView) findViewById(R.id.tvTableB1);
        tvDatum3 = (TextView) findViewById(R.id.tvTableC1);

        tvVrijeme1 = (TextView) findViewById(R.id.tvTableA2);
        tvVrijeme2 = (TextView) findViewById(R.id.tvTableB2);
        tvVrijeme3 = (TextView) findViewById(R.id.tvTableC2);

        tvVrijednost1 = (TextView) findViewById(R.id.tvTableA3);
        tvVrijednost2 = (TextView) findViewById(R.id.tvTableB3);
        tvVrijednost3 = (TextView) findViewById(R.id.tvTableC3);

        // Instanciranje baze
        myDb = new DbHelper(this);
        getZadnji();
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

        int br = 0;
        while (res.moveToNext()) {


//
//            // datum, vrijeme, vrijednost
//            tvDatum1.setText(myDatum);
//            tvVrijeme1.setText(mySat);
//            tvVrijednost1.setText(res.getString(3));

            br++;
            if (br == 1) {
                tvDatum1.setText(res.getString(4));
                tvVrijeme1.setText(res.getString(1) + "/" + res.getString(2));
                tvVrijednost1.setText(res.getString(3));
            }
            else if (br == 2) {
                tvDatum2.setText(res.getString(4));
                tvVrijeme2.setText(res.getString(1) + "/" + res.getString(2));
                tvVrijednost2.setText(res.getString(3));
            }
            else {
                tvDatum3.setText(res.getString(4));
                tvVrijeme3.setText(res.getString(1) + "/" + res.getString(2));
                tvVrijednost3.setText(res.getString(3));
            }
        }
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
            tvZadnjeMjerenje.append(" " + res.getString(4) + " )");
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
