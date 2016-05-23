package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class PregledActivity extends AppCompatActivity {

    private int optimalni = 0;
    private int normalni = 0;
    private int poviseni = 0;
    private int visoki = 0;
    private int dostavisoki = 0;
    private int hipertenzija = 0;
    private int izolirani = 0;

    private DbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregled);

        myDb = new DbHelper(this);
        myDb.getAllData();

        Cursor res = myDb.getAllData();
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

        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        // ArrayList koji sadrži količinu pojedinih izmjerenih tlakova
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(optimalni, 0));
        entries.add(new Entry(normalni, 1));
        entries.add(new Entry(poviseni, 2));
        entries.add(new Entry(visoki, 4));
        entries.add(new Entry(dostavisoki, 5));
        entries.add(new Entry(hipertenzija, 6));
        entries.add(new Entry(izolirani, 7));

        // TODO: 22.5.2016. Šta je ovo brate, kakav |
        PieDataSet dataset = new PieDataSet(entries, "|");

        ArrayList<String> labels = new ArrayList<String>();
        if(optimalni>0)
        labels.add("Optimalni");
        labels.add("Normalni");
        labels.add("Povišeni");
        labels.add("Visoki");
        labels.add("Dosta visoki");
        labels.add("Hipertenzija");
        labels.add("Izolirani");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        pieChart.setDescription("Description");
        pieChart.setData(data);

        pieChart.animateY(5000);

        pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image
    }


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
