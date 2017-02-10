package com.example.n550j.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Objetos.Periodo;
import costantes.Constantes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentIndiceDesempeno.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentIndiceDesempeno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIndiceDesempeno extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String IDPROYECTO = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner spinner_periodos;
    int idPeriodo;
    Button botonBuscar;
    ArrayList entriesSPI= new ArrayList<String>();
    ArrayList entriesCPI = new ArrayList<String>();
    ArrayList entriesPCIB = new ArrayList<String>();
    ArrayList entriesRange = new ArrayList<String>();
    // TODO: Rename and change types of parameters
    int idProyecto;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentIndiceDesempeno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     ///* @param param2 Parameter 2.
     * @return A new instance of fragment FragmentIndiceDesempeno.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentIndiceDesempeno newInstance(int param1) {
        FragmentIndiceDesempeno fragment = new FragmentIndiceDesempeno();
        Bundle args = new Bundle();
        args.putInt(IDPROYECTO, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idProyecto = getArguments().getInt(IDPROYECTO);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      final   View view= inflater.inflate(R.layout.fragment_fragment_indice_desempeno, container, false);
        botonBuscar= (Button) view.findViewById(R.id.buttonBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graficar(view);
            }
        });

        getPeriodos(idProyecto, getContext(), view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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


    /*Metodo utilizado para obtener SPI CPI PCIB*/
    public List<Periodo> getSPI_CPI_PCIB(int idProyecto, int idPeriodo,final Context c){
        JSONObject jsonObjCurvaScostos = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        try {
            jsonObjCurvaScostos.put("IDPROYECTO", idProyecto);
            jsonObjCurvaScostos.put("IDPERIODO", idPeriodo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_SPI_CPI_PCIB+idProyecto+"&idperiod="+idPeriodo;
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
                        JSONArray jarraySPI=response.getJSONArray("SPI");
                        JSONArray jarrayCPI=response.getJSONArray("CPI");
                        JSONArray jarrayPCIB=response.getJSONArray("PCIB");
                        parseJsonSPI(jarraySPI);
                        parseJsonCPI(jarrayCPI);
                        parseJsonPCIB(jarrayPCIB);
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
    }//fin getSPI_CPI_PCIB


    public ArrayList<String> parseJsonSPI(JSONArray jsonArrayCV){
        entriesSPI.clear();
        for (int i=0;i<jsonArrayCV.length();i++){
            try {
                entriesSPI.add(new Entry(jsonArrayCV.getInt(i),i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesSPI;
    }
    public ArrayList<String> parseJsonCPI(JSONArray jsonArrayPV){
        entriesCPI.clear();
        for (int i=0;i<jsonArrayPV.length();i++){
            try {
                entriesCPI.add(new Entry(jsonArrayPV.getInt(i),i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesCPI;
    }

    public ArrayList<String> parseJsonPCIB(JSONArray jsonArrayEV){
        entriesPCIB.clear();
        for (int i=0;i<jsonArrayEV.length();i++){
            try {
                entriesPCIB.add(new Entry(jsonArrayEV.getInt(i),i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesPCIB;
    }

    public ArrayList<String> parseJsonRange(JSONArray jsonArrayrange){
        entriesRange.clear();
        for (int i=0;i<jsonArrayrange.length();i++){
            try {
                entriesRange.add(jsonArrayrange.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesRange;
    }

    public  void spinnerd(List<Periodo> periodos, View view){
        spinner_periodos= (Spinner) view.findViewById(R.id.spinner3);
        spinner_periodos.setOnItemSelectedListener(this);
        ArrayAdapter<Periodo> dataAdapter = new ArrayAdapter<Periodo>(getContext(), android.R.layout.simple_spinner_item, periodos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_periodos.setAdapter(dataAdapter);
    }
    public  void graficar(View view){

        LineChart barChart;
        barChart= (LineChart) view.findViewById(R.id.chart1);
        barChart.clear();

        barChart.animateY(3000);
        LineChart lineChart = (LineChart) view.findViewById(R.id.chart1);
        lineChart.clear();

        LineDataSet dataset=new LineDataSet(entriesCPI,"CPI");
        dataset.setColor(Color.BLUE);
        LineDataSet dataset2=new LineDataSet(entriesPCIB,"PCIB");
        dataset2.setColor(Color.GREEN);
        LineDataSet dataSet3=new LineDataSet(entriesSPI,"SPI");
        dataSet3.setColor(Color.RED);

        dataset.setDrawCubic(true);
        dataset2.setDrawCubic(true);
        dataSet3.setDrawCubic(true);

        ArrayList <ILineDataSet> lineDataSets=new ArrayList<>();

        lineDataSets.add(dataset);
        lineDataSets.add(dataset2);
       lineDataSets.add(dataSet3);

        LineData data=new LineData(entriesRange,dataset);
        LineData data2=new LineData(entriesRange,dataset2);
       LineData data3=new LineData(entriesRange, dataSet3);

        lineChart.setDoubleTapToZoomEnabled(true);
        lineChart.setDrawBorders(true);


        lineChart.setData(new LineData(entriesRange,lineDataSets));
        //lineChart.setData(data2);



        lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(getContext(),lineChart.getX()+"__"+lineChart.getX(),Toast.LENGTH_LONG).show();
            }
        });
    }


  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
        Periodo p= (Periodo) spinner_periodos.getSelectedItem();
        // getActivida(p.getIdPerido(),idProyecto,getContext());
        idPeriodo=p.getIdPerido();
      //  fechaPeriodo=p.getFechaFinal();
        getSPI_CPI_PCIB(idProyecto,idPeriodo,getContext());
        Toast.makeText(getContext(),"El periodo seleccionado es"+p.getIdPerido()+"Id proy"+idProyecto,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
