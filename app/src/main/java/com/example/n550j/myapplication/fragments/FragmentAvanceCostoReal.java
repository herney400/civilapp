package com.example.n550j.myapplication.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.n550j.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterActividades;
import Adapters.RecyclerAdapter;
import Objetos.Actividad;
import Objetos.Periodo;
import Objetos.Project;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAvanceCostoReal extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinner_periodos;
    protected ArrayAdapter<Periodo> adapter;
    SpinnerAdapter spinnerAdapter;

    private RecyclerView recyclerViewActividades;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FragmentAvanceCostoReal() {
        // Required empty public constructor
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
        periodos.add("periodo 1");
        periodos.add("periodo 2");
        periodos.add("periodo 3");
        periodos.add("periodo 4");
        periodos.add("periodo 5");
        periodos.add("periodo 6");
        periodos.add("periodo 7");
        periodos.add("periodo 8");
        periodos.add("periodo 9");

        List actividades= new ArrayList();
        actividades.add(new Actividad(1,2,"Crear columna",4,2000,1000,2,0,3.9));
        actividades.add(new Actividad(1,2,"Crear anden",4,2000,1000,2,0,3.9));
        actividades.add(new Actividad(1,2,"Crear palanca",4,2000,1000,2,0,3.9));
        actividades.add(new Actividad(1,2,"Crear mezclas",4,2000,1000,2,0,3.9));



        spinner_periodos= (Spinner) view.findViewById(R.id.spinner_periodos);
        spinner_periodos.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, periodos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_periodos.setAdapter(dataAdapter);

        startRecyclerView(actividades,view);
        return view;
    }
    public  void startRecyclerView(List<Actividad> actividads, final View view){
        recyclerViewActividades.setAdapter(new AdapterActividades(actividads,new AdapterActividades.OnItemClickListener(){
            @Override
            public void onItemClick(Actividad item) {
                FragmentMisProyectos fragment=new FragmentMisProyectos();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        },getContext() ));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(),"Select"+item,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public  ArrayList<Periodo> consultarPeriodos(){
        ArrayList<Periodo > a = null;
        /*Aquie hay que crear el codigo para traer los periodos*/
        return  a;
    }

    public void getPeriods(){


    }



}
