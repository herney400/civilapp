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
import com.github.mikephil.charting.components.MarkerView;
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
import Objetos.Vista_Infore_Periodo;
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
    ArrayList entriesAC = new ArrayList<String>();
    ArrayList entriesPV = new ArrayList<String>();
    ArrayList entriesEV = new ArrayList<String>();
    ArrayList entriesRange = new ArrayList<String>();

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

        final View view= inflater.inflate(R.layout.fragment_fragment_curva_sde_costos, container, false);


//        final GraphView graph = (GraphView)view.findViewById(R.id.chart1);


        botonBuscar= (Button) view.findViewById(R.id.buttonBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               graficar(view);



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





        // Inflate the layout for this fragment
        return view;

    }
public  void graficar(View view){

    LineChart barChart;
    barChart= (LineChart) view.findViewById(R.id.chart1);
    barChart.clear();
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
    entries.add(new Entry(2f, 4));
    entries.add(new Entry(9f, 5));
    entries.add(new Entry(10f, 6));

    ArrayList entries2 = new ArrayList<String>();

    int i=1; int j=2;
    entries2.add(new Entry(i, j));
    entries2.add(new Entry(10, 1));
    entries2.add(new Entry(8, 2));
    entries2.add(new Entry(8, 3));
    entries2.add(new Entry(8, 4));
    entries2.add(new Entry(7, 5));
    entries2.add(new Entry(10, 6));

    //   ArrayList<int> entrada=new ArrayList<>();

    LineDataSet dataset=new LineDataSet(entriesAC,"AC");
    dataset.setColor(Color.BLUE);
    LineDataSet dataset2=new LineDataSet(entriesEV,"EV");
    dataset2.setColor(Color.RED);
    LineDataSet dataSet3=new LineDataSet(entriesPV,"PV");
    dataSet3.setColor(Color.GRAY);


    dataset.setDrawCubic(true);
    dataset2.setDrawCubic(true);
    dataSet3.setDrawCubic(true);

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
    lineDataSets.add(dataSet3);
    LineData data=new LineData(entriesRange,dataset);
    LineData data2=new LineData(entriesRange,dataset2);
    LineData data3=new LineData(entriesRange, dataSet3);

    lineChart.setDoubleTapToZoomEnabled(true);
    lineChart.setDrawBorders(true);


    lineChart.setData(new LineData(entriesRange,lineDataSets));
    //  lineChart.setData(data2);

    lineChart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            Toast.makeText(getContext(),lineChart.getX()+"__"+lineChart.getX(),Toast.LENGTH_LONG).show();
        }
    });



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


    /*Metodo utilizado para obtener CV PV EV*/
    public List<Periodo> getCVPVEV(int idProyecto, int idPeriodo,final Context c){
        JSONObject jsonObjCurvaScostos = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        try {
            jsonObjCurvaScostos.put("IDPROYECTO", idProyecto);
            jsonObjCurvaScostos.put("IDPERIODO", idPeriodo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_CV_PV+idProyecto+"&idperiod="+idPeriodo;
        queue.getCache().clear();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando");
        progressDialog.show();
        final List<Periodo> periodoList=new ArrayList<>();
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL,jsonObjCurvaScostos, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("Status")){
                        JSONArray jarrayRange= response.getJSONArray("Range");
                        JSONArray jarrayAC=response.getJSONArray("AC");
                        JSONArray jarrayPV=response.getJSONArray("PV");
                        JSONArray jarrayEV=response.getJSONArray("EV");

                        parseJsonAC(jarrayAC);
                        parseJsonPV(jarrayPV);
                        parseJsonEV(jarrayEV);
                        parseJsonRange(jarrayRange);

                    }else{
                        Snackbar.make(getView(),"No tienes datos", Snackbar.LENGTH_LONG).show();
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

    public ArrayList<String> parseJsonAC(JSONArray jsonArrayCV){
        for (int i=0;i<jsonArrayCV.length();i++){
            try {
                entriesAC.add(new Entry(i,jsonArrayCV.getInt(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesAC;
     }

    public ArrayList<String> parseJsonPV(JSONArray jsonArrayPV){
        for (int i=0;i<jsonArrayPV.length();i++){
            try {
                entriesPV.add(new Entry(i,jsonArrayPV.getInt(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesPV;
    }

    public ArrayList<String> parseJsonEV(JSONArray jsonArrayEV){
        for (int i=0;i<jsonArrayEV.length();i++){
            try {
                entriesEV.add(new Entry(i,jsonArrayEV.getInt(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesEV;
    }

    public ArrayList<String> parseJsonRange(JSONArray jsonArrayrange){
        for (int i=0;i<jsonArrayrange.length();i++){
            try {
                entriesRange.add(jsonArrayrange.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesRange;
    }



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
        getCVPVEV(idProyecto,idPeriodo,getContext());
        Toast.makeText(getContext(),"El periodo seleccionado es"+p.getIdPerido()+"Id proy"+idProyecto,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
