package com.example.n550j.myapplication.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.n550j.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCurvaSdeCostos extends Fragment {

  LineChart barChart;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    public FragmentCurvaSdeCostos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_curva_sde_costos, container, false);
        barChart= (LineChart) view.findViewById(R.id.chart1);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        //barChart.setData(BARDATA);

        barChart.animateY(3000);
                final LineChart lineChart = (LineChart) view.findViewById(R.id.chart1);
                // creating list of entry<br />
                ArrayList entries = new ArrayList<String>();
                entries.add(new Entry(0, 0));
                entries.add(new Entry(16f, 1));
                entries.add(new Entry(6f, 2));
                entries.add(new Entry(2f, 3));
                entries.add(new Entry(18f, 4));
                entries.add(new Entry(9f, 5));
                entries.add(new Entry(10f, 6));

        ArrayList entries2 = new ArrayList<String>();
        entries2.add(new Entry(0, 0));
        entries2.add(new Entry(10f, 1));
        entries2.add(new Entry(8f, 2));
        entries2.add(new Entry(6f, 3));
        entries2.add(new Entry(8f, 4));
        entries2.add(new Entry(7f, 5));
        entries2.add(new Entry(10f, 6));

        LineDataSet dataset=new LineDataSet(entries,"dd");
        dataset.setColor(Color.BLUE);
        LineDataSet dataset2=new LineDataSet(entries2,"jj");


        dataset.setDrawCubic(true);
        dataset2.setDrawCubic(true);
        ArrayList <ILineDataSet> lineDataSets=new ArrayList<>();
        ArrayList labels = new ArrayList<String>();

                labels.add("Periodo 0");
                labels.add("Periodo 1");
                labels.add("Periodo 2");
                labels.add("Periodo 3");
                labels.add("Periodo 4");
                labels.add("Periodo 5");
                labels.add("Periodo 6");
        lineDataSets.add(dataset);
        lineDataSets.add(dataset2);
       /* LineData data=new LineData(labels,dataset);
        LineData data2=new LineData(labels,dataset2);
*/

        lineChart.setDoubleTapToZoomEnabled(true);
        lineChart.setDrawBorders(true);
        

        lineChart.setData(new LineData(labels,lineDataSets));
      //  lineChart.setData(data2);

        lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Toast.makeText(getContext(),lineChart.getX()+"__"+lineChart.getX(),Toast.LENGTH_LONG).show();
            }
        });

        // Inflate the layout for this fragment
        return view;

    }public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(2f, 0));
        BARENTRY.add(new BarEntry(4f, 1));
        BARENTRY.add(new BarEntry(6f, 2));
        BARENTRY.add(new BarEntry(8f, 3));
        BARENTRY.add(new BarEntry(7f, 4));
        BARENTRY.add(new BarEntry(3f, 5));

    }

    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("January");
        BarEntryLabels.add("February");
        BarEntryLabels.add("March");
        BarEntryLabels.add("April");
        BarEntryLabels.add("May");
        BarEntryLabels.add("June");

    }


}
