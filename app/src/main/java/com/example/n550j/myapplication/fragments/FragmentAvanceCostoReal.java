package com.example.n550j.myapplication.fragments;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.n550j.myapplication.R;
import com.google.android.gms.appdatasearch.GetRecentContextCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterActividades;
import Adapters.RecyclerAdapter;
import Objetos.Actividad;
import Objetos.Causal;
import Objetos.Periodo;
import Objetos.Project;
import costantes.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAvanceCostoReal extends Fragment implements AdapterView.OnItemSelectedListener {
    int     idProyecto ;
    private static final String ARG_IDPROYECTO = "ID_PROYECTO";
    Spinner spinner_periodos;
    Context context=getContext();
    protected ArrayAdapter<Periodo> adapter;
    SpinnerAdapter spinnerAdapter;
    List<Periodo> periodoListPublico=new ArrayList<Periodo>();
    private RecyclerView recyclerViewActividades;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Periodo> periodoList=new ArrayList<Periodo>();

    List<String> CausalList;
    CharSequence [] causa;


    public FragmentAvanceCostoReal() {
        // Required empty public constructor
    }

    public static FragmentAvanceCostoReal newInstance(int idProyecto) {
        FragmentAvanceCostoReal fragment = new FragmentAvanceCostoReal();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_avance_costo_real, container, false);

        //Llamar a consultarPeriodos() para llenar el spinner con los periodos
        recyclerViewActividades= (RecyclerView) view.findViewById(R.id.recycler);
        recyclerViewActividades.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewActividades.setLayoutManager(mLayoutManager);

        List<String> periodos = new ArrayList<String>();
        periodos.add("periodo 1"+idProyecto);

        Log.e("ID en avance", "--<"+idProyecto);

        List actividades= new ArrayList();

       periodoListPublico= getPeriodos(idProyecto, getContext(), view);
      //  startRecyclerView(actividades);
        return view;
    }


    public  void spinnerd( List<Periodo> periodos, View view){
        spinner_periodos= (Spinner) view.findViewById(R.id.spinner_periodos);
        spinner_periodos.setOnItemSelectedListener(this);
        ArrayAdapter<Periodo> dataAdapter = new ArrayAdapter<Periodo>(getContext(), android.R.layout.simple_spinner_item, periodos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_periodos.setAdapter(dataAdapter);
    }

    public  void startRecyclerView(List<Actividad> actividads){
        List<String> copia=new ArrayList<>();
        copia=CausalList;
        recyclerViewActividades.setAdapter(new AdapterActividades(actividads,causa,new AdapterActividades.OnItemClickListener(){
            @Override
            public void onItemClick(Actividad item) {
                FragmentMisProyectos fragment=new FragmentMisProyectos();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        },getContext() ));
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
                                retornArray(periodoList);
                                   getCausales(c);
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


    public List<Causal> getCausales( Context c){
        JSONObject jsonObjperiodo = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        try {
            jsonObjperiodo.put("IDPROYECTO", idProyecto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_CAUSALES;
        queue.getCache().clear();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando");
        progressDialog.show();

        final List<Causal> causals=new ArrayList<>();

        JsonRequest request = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("Status")){
                        JSONArray ja= response.getJSONArray("Causals");
                     //   final CharSequence[] items = new CharSequence[ja.length()];

                        causa = parseJSON(ja);
                       // AdapterActividades adapterActividades=new AdapterActividades(items);
                        Log.e("dd","dd");

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
        return   causals;
    }
public CharSequence[] parseJSON(JSONArray jsonArray) throws JSONException {
     List<String> causale=new ArrayList<>();
    CharSequence[]  causals = new CharSequence[jsonArray.length()];

    for(int i=0;i<jsonArray.length();i++){
        JSONObject jsonObjectCausals = jsonArray.getJSONObject(i);
        // Causal causal=new Causal();
        //  causal.setIDCAUSAL(jsonObjectCausals.getInt("IDCAUSAL"));
        causals[i]=jsonObjectCausals.getString("NOMBRE");
       // causale.add(  jsonObjectCausals.getString("NOMBRE"));
    }
    return causals;

}

/*Metodo utilizado para retornar las actividades pertenecientes al periodo seleccionado
* @parametro 1: idProyecto
* @parametro 2: idPeriodo
* */

    public List<Periodo> getActivida(int idPeriodo, int idProyecto, final Context c){

        RequestQueue queue = Volley.newRequestQueue(c);
        String URL = Constantes.URL_TRAE_ACTIVIDADES+"idproject="+idProyecto+"&idperiod="+idPeriodo;
        queue.getCache().clear();

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando");
        progressDialog.show();

        final List<Actividad> actividadList=new ArrayList<>();
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("Status")){
                        JSONArray ja= response.getJSONArray("Actividades");
                        for(int i=0;i<ja.length();i++){
                            JSONObject jsonObjectActividades=ja.getJSONObject(i);
                            Actividad actividad=new Actividad();
                            actividad.setIdActividad(jsonObjectActividades.getInt("IDACTIVIDAD"));
                            actividad.setIdPoyecto(jsonObjectActividades.getInt("IDPROYECTO"));
                            actividad.setNombreActividad(jsonObjectActividades.getString("NOMBRE_ACTIVIDAD"));
                            actividad.setDiasduracion(jsonObjectActividades.getInt("DIAS_DURACION"));
                            actividad.setIniciacion_primera(jsonObjectActividades.getString("INICIACION_PRIMERA"));
                            actividad.setFinalizacion_primera(jsonObjectActividades.getString("FINALIZACION_PRIMERA"));
                            actividad.setCosto_total(jsonObjectActividades.getInt("COSTO_TOTAL"));
                            actividad.setHolguralibre(jsonObjectActividades.getInt("HOLGURA_LIBRE"));
                            actividad.setPorcentaje(jsonObjectActividades.getDouble("PORCENTAJE_AVANCE"));
                            actividad.setFinalizacionCompleta(jsonObjectActividades.getBoolean("FINALIZACION_COMPLETA"));
                            actividad.setCostoReal(jsonObjectActividades.getDouble("COSTO_REAL"));
                            actividad.setFecha_inicial("2017-01-22T17:46:45.77");
                            actividad.setFecha_final("2017-01-22T17:46:45.77");
                            actividad.setFecha_creacion(jsonObjectActividades.getString("FECHA_CREACION"));
                            actividadList.add(actividad);
                        }
                        startRecyclerView(actividadList);

                    }else{
                        Snackbar.make(getView(),"No existen actividades para el periodo seleccionado", Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
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
        return  periodoList;
    }

    public boolean updateActivity(int idActividad , int finalizacionCompleta, double porcentaje_avance ,double costoReal, final Context c){

        JSONObject jsonObjAcitivdad = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        queue.getCache().clear();
        try {
            jsonObjAcitivdad.put("idActividad", idActividad);
            jsonObjAcitivdad.put("finalizacion_completa", finalizacionCompleta);
            jsonObjAcitivdad.put("porcentaje_avance", porcentaje_avance);
            jsonObjAcitivdad.put("costo_real",costoReal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_UPDATE_ACTIVIDADES;
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObjAcitivdad, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("Status")){
                        //JSONObject jo= response.getJSONObject("data");
                        createSimpleDialog("La actividad a sido actualizada",c).show();
                    }else{
                        createSimpleDialog("La actividad no pudo ser actualizada", c).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",""+error);
            }
        });
        queue.add(request);
        return  true;
    }//Fin updateActividad()

    public void finalizaActivida(int idActividad, ArrayList causalList, Context contextt ) throws JSONException {

        JSONArray jsonArray=new JSONArray();

        for (int i=0;i<causalList.size();i++){

            try {
                jsonArray.put(i,causalList.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Idcausals",jsonArray);
        jsonObject.put("Idactivity",idActividad);
        RequestQueue queue=Volley.newRequestQueue(contextt);

        String URL=Constantes.URL_FINALIZAR ;
        queue.getCache().clear();
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("Status")){
                        String resp=response.getString("Message");

                    }else{
                        String resp=response.getString("Message");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",""+error);
            }
        });
        queue.add(request);

    }

    public boolean finalizarActivity(int idActividad , int finalizacionCompleta, double porcentaje_avance ,double costoReal, int idCausal, final Context c){

        JSONObject jsonObjAcitivdad = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        queue.getCache().clear();
        try {

            jsonObjAcitivdad.put("idActividad", idActividad);
            jsonObjAcitivdad.put("finalizacion_completa", finalizacionCompleta);
            jsonObjAcitivdad.put("porcentaje_avance", porcentaje_avance);
            jsonObjAcitivdad.put("costo_real",costoReal);
            jsonObjAcitivdad.put("idCausal",idCausal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_UPDATE_ACTIVIDADES;
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObjAcitivdad, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("Status")){
                        //JSONObject jo= response.getJSONObject("data");
                        createSimpleDialog("La actividad a sido actualizada",c).show();
                    }else{
                        createSimpleDialog("La actividad no pudo ser actualizada", c).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",""+error);
            }
        });
        queue.add(request);
        return  true;
    }


    public List<Periodo> retornArray(List<Periodo> periodoList){
        this.periodoListPublico=periodoList;
        return periodoListPublico;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item=parent.getItemAtPosition(position).toString();
            Periodo p= (Periodo) spinner_periodos.getSelectedItem();
            getActivida(p.getIdPerido(),idProyecto,getContext());

            Toast.makeText(getContext(),"El periodo seleccionado es"+p.getIdPerido()+"Id proy"+idProyecto,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public  ArrayList<Periodo> consultarPeriodos(int idProyecto){
        ArrayList<Periodo > a = null;
        /*Aquie hay que crear el codigo para traer los periodos*/
        return  a;
    }
    public AlertDialog createSimpleDialog(String actividad, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Alerta")
                .setMessage(actividad )
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // listener.onPossitiveButtonClick();
                            }
                        });
        return builder.create();
    }
}
