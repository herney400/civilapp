package com.example.n550j.myapplication.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterpInformporPeriodos;
import Adapters.RecyclerAdapter;
import Objetos.Periodo;
import Objetos.Project;
import Objetos.Vista_Infore_Periodo;
import costantes.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInformePorPeriodo extends Fragment implements AdapterView.OnItemSelectedListener{

    private RecyclerView recyclerViewInforporPeriodo;
    private RecyclerView.LayoutManager mLayoutManager;
    Spinner spinner_periodos;
    int     idProyecto ;
    private static final String ARG_IDPROYECTO = "ID_PROYECTO";
    public FragmentInformePorPeriodo() {
        // Required empty public constructor
    }
    public static FragmentInformePorPeriodo newInstance(int idProyecto) {
        FragmentInformePorPeriodo fragment = new FragmentInformePorPeriodo();
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
        View view=inflater.inflate(R.layout.fragment_informe_por_periodo, container, false);
        recyclerViewInforporPeriodo= (RecyclerView) view.findViewById(R.id.recyclerInformeporPeriodos);
        recyclerViewInforporPeriodo.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewInforporPeriodo.setLayoutManager(mLayoutManager);

        List<Vista_Infore_Periodo> vista_infore_periodos=new ArrayList();


            getPeriodos(idProyecto,getContext(),view);

        // Inflate the layout for this fragment
        return view;
    }//Fin de onCreateView()

    public List<Project> getInformePeriodo(int idPeriodo,int idProyecto, Context c){
        JSONObject jsonObjproyectos = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);

        String URL = Constantes.URL_INFORME_PERIODO+"?idPeriodo="+idPeriodo+"?idProyecto="+idProyecto;

        List<Project> projectListe=new ArrayList<>();
        queue.getCache().clear();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando");
        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Vista_Infore_Periodo> vista_infore_periodos=new ArrayList<>();
                try {
                    if(response.getBoolean("Status")){
                        JSONArray ja= response.getJSONArray("Informe");
                        for(int i=0;i<ja.length();i++){
                            JSONObject jsonObjectInforPeriodos=ja.getJSONObject(i);

                            Vista_Infore_Periodo vista_infore_periodo=new Vista_Infore_Periodo();
                            vista_infore_periodo.setNombreActividad(jsonObjectInforPeriodos.getString("NOMBRE_ACTIVIDAD"));
                            vista_infore_periodo.setIdPeriodo(jsonObjectInforPeriodos.getInt("IDPERIODO"));
                            vista_infore_periodo.setIdProyecto(jsonObjectInforPeriodos.getInt("IDPROYECTO"));
                            vista_infore_periodo.setIdActividad(jsonObjectInforPeriodos.getInt("IDACTIVIDAD"));
                            vista_infore_periodo.setPorcentajeAvance(jsonObjectInforPeriodos.getDouble("PORCENTAJE_AVANCE"));
                            vista_infore_periodo.setEV(jsonObjectInforPeriodos.getInt("EV"));
                            vista_infore_periodo.setAC(jsonObjectInforPeriodos.getDouble("AC"));
                            vista_infore_periodo.setCV(jsonObjectInforPeriodos.getInt("CV"));
                            vista_infore_periodo.setSV(jsonObjectInforPeriodos.getInt("SV"));
                            vista_infore_periodo.setPV(jsonObjectInforPeriodos.getInt("PV"));

                            vista_infore_periodos.add(vista_infore_periodo);
                        }
                        startRecyclerView(vista_infore_periodos);
                    }else{
                        Snackbar.make(getView(),"No tienes proyectos", Snackbar.LENGTH_LONG).show();
                        //  dialogAlertaP("No hay proyectos ", context ,"Oops");
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
        return  projectListe;
    }

    /*Metodo utilizado apra obtener los peridos pertenecientes al proyecto seleccionado *
 */
    public List<Periodo> getPeriodos(int idProyecto, Context c, final View view){
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
    }

    public  void spinnerd(List<Periodo> periodos, View view){
        spinner_periodos= (Spinner) view.findViewById(R.id.spinner);
        spinner_periodos.setOnItemSelectedListener(this);
        ArrayAdapter<Periodo> dataAdapter = new ArrayAdapter<Periodo>(getContext(), android.R.layout.simple_spinner_item, periodos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_periodos.setAdapter(dataAdapter);
    }

    public  void startRecyclerView(List<Vista_Infore_Periodo> vista_infore_periodos){

        recyclerViewInforporPeriodo.setAdapter(new AdapterpInformporPeriodos(vista_infore_periodos, new AdapterpInformporPeriodos.OnItemClickListener(){
            @Override
            public void onItemClick(Vista_Infore_Periodo item) {
                // FragmentMisProyectos fragment=new FragmentMisProyectos();
                //FragmentProjects f=FragmentProjects.newInstance(userName,id,email);
              /*  FragmentMisProyectos fragment=FragmentMisProyectos.newInstance(item.getIdProyecto());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();*/
            }
        },getContext() ));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item=parent.getItemAtPosition(position).toString();
        Periodo p= (Periodo) spinner_periodos.getSelectedItem();
        //getActivida(p.getIdPerido(),idProyecto,getContext());
        getInformePeriodo(p.getIdPerido(),idProyecto,getContext());
        Toast.makeText(getContext(),"El periodo seleccionado es"+p.getIdPerido()+"Id proy"+idProyecto,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
