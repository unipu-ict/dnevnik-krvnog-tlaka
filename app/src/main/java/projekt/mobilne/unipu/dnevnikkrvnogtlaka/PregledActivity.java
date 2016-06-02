package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class PregledActivity extends AppCompatActivity {

    // region PRIVATNE VARIJABLE
    private float optimalni = 0;
    private float normalni = 0;
    private float poviseni = 0;
    private float visoki = 0;
    private float dostavisoki = 0;
    private float hipertenzija = 0;
    private float izolirani = 0;

    private int brojac = 0;

    private DbHelper myDb;
    private Cursor res;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregled);

        // Programski postavljen naziv screena
        this.setTitle("Dijagnoza");

        myDb = new DbHelper(this);
        myDb.getAllData();


        inicijalizirajKursor();
        inicijalizirajGraf();
    }

    // region KURSOR
    private void inicijalizirajKursor() {
        // Inicijalizacija kursora koji prolazi sve podatke u bazi
        res = myDb.getAllData();
        while (res.moveToNext()) {
            if (res.getInt(1) < 120 & res.getInt(2) < 80) {
                optimalni++;
                brojac++;
            } else if (res.getInt(1) < 130 & res.getInt(2) < 85) {
                normalni++;
                brojac++;
            } else if (res.getInt(1) < 139 & res.getInt(2) < 89) {
                poviseni++;
                brojac++;
            } else if (res.getInt(1) < 159 & res.getInt(2) < 99) {
                visoki++;
                brojac++;
            } else if (res.getInt(1) < 179 & res.getInt(2) < 109) {
                dostavisoki++;
                brojac++;
            } else if (res.getInt(1) < 159 & res.getInt(2) < 95) {
                hipertenzija++;
                brojac++;
            } else if (res.getInt(1) > 140 & res.getInt(2) > 90) {
                izolirani++;
                brojac++;
            }
        }
        res.close();
    }
    // endregion

    // region GRAF
    private void inicijalizirajGraf() {
        // Inicijalizacija dijagrama
        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        // ArrayList koji sadrži nazive tlakova za legendu
        ArrayList<String> labels = new ArrayList<String>();
        // ArrayList koji sadrži instance pojedinih izmjerenih tlakova
        ArrayList<Entry> entries = new ArrayList<>();

        if (optimalni > 0) {
            optimalni = (optimalni / brojac) * 100;
            entries.add(new Entry(optimalni, 0));
            labels.add("Optimalni");
        }

        if (normalni > 0) {
            normalni = (normalni / brojac) * 100;
            entries.add(new Entry(normalni, 1));
            labels.add("Normalni");
        }

        if (poviseni > 0) {
            poviseni = (poviseni / brojac) * 100;
            entries.add(new Entry(poviseni, 2));
            labels.add("Povišeni");
        }

        if (visoki > 0) {
            visoki = (visoki / brojac) * 100;
            entries.add(new Entry(visoki, 3));
            labels.add("Visoki");
        }

        if (dostavisoki > 0) {
            dostavisoki = (dostavisoki / brojac) * 100;
            entries.add(new Entry(dostavisoki, 4));
            labels.add("Dosta visoki");
        }

        if (hipertenzija > 0) {
            hipertenzija = (hipertenzija / brojac) * 100;
            entries.add(new Entry(hipertenzija, 5));
            labels.add("Hipertenzija");
        }

        if (izolirani > 0) {
            izolirani = (izolirani / brojac) * 100;
            entries.add(new Entry(izolirani, 6));
            labels.add("Izolirani");
        }

        // Unos prikupljenih podataka u PieDataSet
        PieDataSet dataset = new PieDataSet(entries, "");

        // Skup labela i vrijednosti za PieChart
        PieData data = new PieData(labels, dataset);

        // Postavljanje seta boja za PieChart
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);

        // Dodavanje naziva legende
        //pieChart.setDescription("Legenda");
        pieChart.setData(data);

        // Postavljanje teksta u sredinu PieCharta
        pieChart.setCenterText("Prosjek krvnog tlaka (%)");
        //pieChart.setHoleRadius(20);

        // Brža animacija PieCharta
        pieChart.animateX(1000);

    }
    // endregion

    // region NAVIGACIJA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pregled, menu);
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
        else if (id == R.id.unos_menu) {
            unosNovogKrvnogTlaka(v);
            return true;
        }
        else if (id == R.id.pocetna_menu) {
            pocetnaStranica(v);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void povijestKrvnogTlaka(View v) {
        Intent i  = new Intent(PregledActivity.this, PovijestActivity.class);
        startActivity(i);
    }

    public void unosNovogKrvnogTlaka(View v) {
        Intent i  = new Intent(PregledActivity.this, UnosActivity.class);
        startActivity(i);
    }

    public void pocetnaStranica(View v) {
        Intent i  = new Intent(PregledActivity.this, MainActivity.class);
        startActivity(i);
    }
    // endregion
}
