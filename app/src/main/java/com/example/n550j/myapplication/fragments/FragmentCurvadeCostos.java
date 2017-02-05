package com.example.n550j.myapplication.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.Volley;
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
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import Objetos.Periodo;
import costantes.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCurvadeCostos extends Fragment implements AdapterView.OnItemSelectedListener {
    int idProyecto ;
    int idPeriodo;
    String fechaPeriodo;
    Spinner spinner_periodos;
    Button botonBuscar;

    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    private static final String ARG_IDPROYECTO = "ID_PROYECTO";
    public FragmentCurvadeCostos() {
        // Required empty public constructor
    }
    public static FragmentCurvadeCostos newInstance(int idProyecto) {
        FragmentCurvadeCostos fragment = new FragmentCurvadeCostos();
        Bundle args = new Bundle();
        args.putInt(ARG_IDPROYECTO, idProyecto);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idProyecto = getArguments().getInt(ARG_IDPROYECTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        LineChart barChart;
        View view= inflater.inflate(R.layout.fragment_fragment_curva_sde_costos, container, false);


//        final GraphView graph = (GraphView)view.findViewById(R.id.chart1);


        botonBuscar= (Button) view.findViewById(R.id.buttonBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

      /*  LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 100),
                new DataPoint(1, 500),
                new DataPoint(2, 300),
                new DataPoint(3, 200),
                new DataPoint(4, 600)
        });



        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        series.setAnimated(true);
        series.setTitle("CV CA");

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Series1: punto click: "+dataPoint, Toast.LENGTH_SHORT).show();

            }
        });

        series.setTitle("CV");

        graph.setClickable(true);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(com.jjoe64.graphview.LegendRenderer.LegendAlign.BOTTOM);
        graph.getLegendRenderer().setMargin(1);


        graph.addSeries(series); */
        getPeriodos(idProyecto, getContext(), view);
      //  graph.setTitle("dddd");




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
        LineData data=new LineData(labels,dataset);
        LineData data2=new LineData(labels,dataset2);


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

    }
    private static String convierteFecha(String stringFechaEntrada, String formatoEntrada, String formatoSalida){
        Log.i("TAG", "stringFechaEntrada :" +  stringFechaEntrada);
        //Definimos formato del string que ingresamos.
        SimpleDateFormat sdf = new SimpleDateFormat(formatoEntrada);
        try {
            Date date = sdf.parse(stringFechaEntrada);
            //Definimos formato del string que deseamos obtener.
            sdf = new SimpleDateFormat(formatoSalida);
            String stringFechaSalida = sdf.format(date);
            Log.i("TAG", "stringFechaSalida :" +  stringFechaSalida);
            Date dateSalida = sdf.parse(stringFechaSalida);
            //Log.i("TAG", "dateSalida :" +  dateSalida);
            return stringFechaSalida;
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public  void spinnerd(List<Periodo> periodos, View view){
        spinner_periodos= (Spinner) view.findViewById(R.id.spinner3);
        spinner_periodos.setOnItemSelectedListener(this);
        ArrayAdapter<Periodo> dataAdapter = new ArrayAdapter<Periodo>(getContext(), android.R.layout.simple_spinner_item, periodos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_periodos.setAdapter(dataAdapter);
    }


    public void AddValuesToBARENTRY(){
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

    public  void graphView(){


    }/*Metodo utilizado para obtener CV PV EV*/
    public List<Periodo> getCVPVEV(int idProyecto, int idPeriodo,final Context c, final View view){
        JSONObject jsonObjperiodo = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        try {
            jsonObjperiodo.put("IDPROYECTO", idProyecto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_TRAE_PERIODOS+idProyecto;
        queue.getCache().clear();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando");
        progressDialog.show();
        final List<Periodo> periodoList=new ArrayList<>();
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("Status")){
                        JSONArray ja= response.getJSONArray("Periodos");
                        for(int i=0;i<ja.length();i++){
                            JSONObject jsonObjectPeriodos=ja.getJSONObject(i);
                            Periodo periodo=new Periodo();
                            periodo.setIdPerido(jsonObjectPeriodos.getInt("IDPERIODO"));
                            periodo.setFechaInicial(jsonObjectPeriodos.getString("FECHA_INICIAL"));
                            periodo.setFechaFinal(jsonObjectPeriodos.getString("FECHA_FINAL"));
                            periodo.setFechaCreacion(jsonObjectPeriodos.getString("FECHA_CREACION"));
                            periodoList.add(periodo);
                        }
                        spinnerd(periodoList, view);
                        //  retornArray(periodoList);
                        //  getCausales(c);
                    }else{
                        Snackbar.make(getView(),"No tienes periodos", Snackbar.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.cancel();
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",""+error);
                progressDialog.cancel();
            }
        });
        queue.add(request);
        return   periodoList;
    }//fin getCVPVEV

    /*Metodo utilizado apra obtener los peridos pertenecientes al proyecto seleccionado *
     */
    public List<Periodo> getPeriodos(int idProyecto, final Context c, final View view){
        JSONObject jsonObjperiodo = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        try {
            jsonObjperiodo.put("IDPROYECTO", idProyecto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_TRAE_PERIODOS+idProyecto;
        queue.getCache().clear();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando");
        progressDialog.show();
        final List<Periodo> periodoList=new ArrayList<>();
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("Status")){
                        JSONArray ja= response.getJSONArray("Periodos");
                        for(int i=0;i<ja.length();i++){
                            JSONObject jsonObjectPeriodos=ja.getJSONObject(i);
                            Periodo periodo=new Periodo();
                            periodo.setIdPerido(jsonObjectPeriodos.getInt("IDPERIODO"));
                            periodo.setFechaInicial(jsonObjectPeriodos.getString("FECHA_INICIAL"));
                            periodo.setFechaFinal(jsonObjectPeriodos.getString("FECHA_FINAL"));
                            periodo.setFechaCreacion(jsonObjectPeriodos.getString("FECHA_CREACION"));
                            periodoList.add(periodo);
                        }
                        spinnerd(periodoList, view);
                      //  retornArray(periodoList);
                      //  getCausales(c);
                    }else{
                        Snackbar.make(getView(),"No tienes periodos", Snackbar.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.cancel();
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",""+error);
                progressDialog.cancel();
            }
        });
        queue.add(request);
        return   periodoList;
    }//fin getPeriodo


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
        Periodo p= (Periodo) spinner_periodos.getSelectedItem();
       // getActivida(p.getIdPerido(),idProyecto,getContext());
        idPeriodo=p.getIdPerido();
        fechaPeriodo=p.getFechaFinal();
       // Toast.makeText(getContext(),"El periodo seleccionado es"+p.getIdPerido()+"Id proy"+idProyecto,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
