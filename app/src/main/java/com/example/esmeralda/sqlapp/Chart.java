package com.example.esmeralda.sqlapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Chart extends AppCompatActivity {

    private static final String EXTRA_KEY = "poviseni";
    private static final String EXTRA_KEY2 = "normalni";
    private static final String EXTRA_KEY3 = "optimalni";
    private static final String EXTRA_KEY4 = "ostalo";
    public int optimalni;
    public int normalni;
    public int poviseni;
    public int ostalo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        optimalni = getIntent().getIntExtra(EXTRA_KEY, 1);
        normalni = getIntent().getIntExtra(EXTRA_KEY2,1);
        poviseni = getIntent().getIntExtra(EXTRA_KEY3,1);
        ostalo = getIntent().getIntExtra(EXTRA_KEY4,1);


        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(optimalni, 0));
        entries.add(new Entry(normalni, 1));
        entries.add(new Entry(poviseni, 2));
        entries.add(new Entry(ostalo, 3));

        PieDataSet dataset = new PieDataSet(entries, "Jos mogu dodati");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Poviseni");
        labels.add("Normalni");
        labels.add("Optimalni");
        labels.add("Ostalo");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        pieChart.setDescription("Description");
        pieChart.setData(data);

        pieChart.animateY(5000);

        pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image



    }
}