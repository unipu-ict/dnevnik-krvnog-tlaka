package com.example.esmeralda.sqlapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Chart extends AppCompatActivity {

    private static final String OPTIMALNI = "OPTIMALNI";
    private static final String NORMALNI = "NORMALNI";
    private static final String POVISENI = "POVISENI";
    private static final String VISOKI = "VISOKI";
    private static final String DOSTAVISOKI = "DOSTAVISOKI";
    private static final String HIPERTENZIJA = "HIPERTENZIJA";
    private static final String IZOLIRANI = "IZOLIRANI";

    public int optimalni;
    public int normalni;
    public int poviseni;
    public int visoki;
    public int dostavisoki;
    public int hipertenzija;
    public int izolirani;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        optimalni = getIntent().getIntExtra(OPTIMALNI, 1);
        normalni = getIntent().getIntExtra(NORMALNI,1);
        poviseni = getIntent().getIntExtra(POVISENI,1);
        visoki = getIntent().getIntExtra(VISOKI,1);
        dostavisoki = getIntent().getIntExtra(DOSTAVISOKI,1);
        hipertenzija = getIntent().getIntExtra(HIPERTENZIJA,1);
        izolirani = getIntent().getIntExtra(IZOLIRANI,1);


        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(optimalni, 0));
        entries.add(new Entry(normalni, 1));
        entries.add(new Entry(poviseni, 2));
        entries.add(new Entry(visoki, 4));
        entries.add(new Entry(dostavisoki, 5));
        entries.add(new Entry(hipertenzija, 6));
        entries.add(new Entry(izolirani, 7));

        PieDataSet dataset = new PieDataSet(entries, "|");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Opt");
        labels.add("Nor");
        labels.add("Pov");
        labels.add("Vis");
        labels.add("DostVis");
        labels.add("Hiper");
        labels.add("Izol");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        pieChart.setDescription("Description");
        pieChart.setData(data);

        pieChart.animateY(5000);

        pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image



    }
}