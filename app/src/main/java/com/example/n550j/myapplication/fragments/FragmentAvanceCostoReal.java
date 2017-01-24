package com.example.n550j.myapplication.fragments;


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
import android.widget.SpinnerAdapter;
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

import Adapters.AdapterActividades;
import Adapters.RecyclerAdapter;
import Objetos.Actividad;
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
    protected ArrayAdapter<Periodo> adapter;
    SpinnerAdapter spinnerAdapter;
    List<Periodo> periodoListPublico=new ArrayList<Periodo>();
    private RecyclerView recyclerViewActividades;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Periodo> periodoList=new ArrayList<Periodo>();

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
        periodos.add("periodo 2"+idProyecto);
        periodos.add("periodo 3"+idProyecto);
        periodos.add("periodo 4"+idProyecto);
        periodos.add("periodo 5"+idProyecto);
        periodos.add("periodo 6"+idProyecto);
        periodos.add("periodo 7"+idProyecto);
        periodos.add("periodo 8"+idProyecto);
        periodos.add("periodo 9"+idProyecto);
        Log.e("ID en avance", "--<"+idProyecto);

        List actividades= new ArrayList();
        actividades.add(new Actividad(1,2,"Crear columna",4,2000,1000,2,0,3.9));
        actividades.add(new Actividad(1,2,"Crear anden",4,2000,1000,2,0,3.9));
        actividades.add(new Actividad(1,2,"Crear palanca",4,2000,1000,2,0,3.9));
        actividades.add(new Actividad(1,2,"Crear mezclas",4,2000,1000,2,0,3.9));


        getPeriodos(idProyecto, getContext());

        spinner_periodos= (Spinner) view.findViewById(R.id.spinner_periodos);
        spinner_periodos.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, periodos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_periodos.setAdapter(dataAdapter);

      //
        startRecyclerView(actividades);
        return view;
    }
    public  void startRecyclerView(List<Actividad> actividads){
        recyclerViewActividades.setAdapter(new AdapterActividades(actividads,new AdapterActividades.OnItemClickListener(){
            @Override
            public void onItemClick(Actividad item) {
                FragmentMisProyectos fragment=new FragmentMisProyectos();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        },getContext() ));
    }

    public List<Periodo> getPeriodos(int idProyecto, Context c){
        JSONObject jsonObjperiodo = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        try {
            jsonObjperiodo.put("IDPROYECTO", idProyecto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_TRAE_PERIODOS+idProyecto;
        queue.getCache().clear();

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
                        retornArray(periodoList);

                    }else{
                        Snackbar.make(getView(),"No tienes periodos", Snackbar.LENGTH_LONG).show();
                        //  dialogAlertaP("No hay proyectos ", context ,"Oops");
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
        return  periodoList;
    }

    public List<Periodo> retornArray(List<Periodo> periodoList){
        this.periodoListPublico=periodoList;


        Log.e("Lengeth------>",""+periodoListPublico.size());
        return periodoListPublico;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(),"Select"+item+"-id"+idProyecto,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public  ArrayList<Periodo> consultarPeriodos(int idProyecto){
        ArrayList<Periodo > a = null;
        /*Aquie hay que crear el codigo para traer los periodos*/
        return  a;
    }

    public void getPeriods(){


    }



}
