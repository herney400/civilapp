package com.example.n550j.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ApplicationController;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.cache.DiskBasedCache;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.n550j.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.List;

import Adapters.RecyclerAdapter;
import Controlers.ControladorProjects;
import Objetos.Project;
import costantes.Constantes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProjects.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProjects#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProjects extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1_USERNAME = "param1";
    private static final String ARG_PARAM2_IDUSER = "param2";
    private static final String ARG_PARAM3_EMAIL = "param3";


    // String userName, String idUser,String email

    // TODO: Rename and change types of parameters
    private String userName;
    private int idUser;
    private String email;


    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerViewProject;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Project> projectListP=new ArrayList<>();
    public FragmentProjects() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param useName Parameter 1.
     * @param idUser Parameter 2.
     * @param email Parameter 2.
     * @return A new instance of fragment FragmentProjects.
     */
    // TODO: Rename and change types and number of parameters

    public static FragmentProjects newInstance(String useName, String idUser ,String email) {
        FragmentProjects fragment = new FragmentProjects();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1_USERNAME, useName);
        args.putString(ARG_PARAM2_IDUSER, idUser);
        args.putString(ARG_PARAM3_EMAIL, email);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_PARAM1_USERNAME);
            idUser= getArguments().getInt(ARG_PARAM2_IDUSER);
            email=getArguments().getString(ARG_PARAM3_EMAIL);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

         // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_projects, container, false);

        /*Crear consulta de los proyecto del usuario con el idUser que me llega del logeo*/

        ControladorProjects controladorProjects= new ControladorProjects();

        List<Project> proyectoItems=new ArrayList();
      //  proyectoItems = controladorProjects.getProyects(idUser,getContext());



        List items= new ArrayList();
        items.add(new Project(12,12,"proyecto 1", "1221","Cali","carre 94"));
        items.add(new Project(12,12,"proyecto 2", "1221","Palmira","carre 94"));
        items.add(new Project(12,12,"proyecto 3", "1221","Pradera","carre 94"));
        items.add(new Project(12,12,"proyecto 4", "1221","Candelaria","carre 94"));
        items.add(new Project(12,12,"proyecto 5", "1221","Florida","carre 94"));

        recyclerViewProject= (RecyclerView) view.findViewById(R.id.recycler_proyect);
        recyclerViewProject.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewProject.setLayoutManager(mLayoutManager);

        Log.e("Argumentos", userName+"_"+idUser+"_"+email);

       // mAdapter=new RecyclerAdapter(items);
       // recyclerViewProject.setAdapter(mAdapter);
        int usu=11;
        getProyects(usu, getContext());
        return view;

    }

    public List<Project> getProyects(int idUserc, Context c){
        JSONObject jsonObjproyectos = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);

        String URL = Constantes.URL_TRAE_PROYECTOS+"?user="+idUserc;

        List<Project> projectListe=new ArrayList<>();
        queue.getCache().clear();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                 List<Project> projectList=new ArrayList<>();
                try {
                    if(response.getBoolean("Status")){
                        JSONArray ja= response.getJSONArray("Projects");
                        for(int i=0;i<ja.length();i++){
                            JSONObject jsonObjectProyecto=ja.getJSONObject(i);
                            Project project=new Project();
                            project.setIdProyecto(jsonObjectProyecto.getInt("IDPROYECTO"));
                            project.setName_project(jsonObjectProyecto.getString("NOMBRE_PROYECTO"));
                            project.setNumber_contract(jsonObjectProyecto.getString("NUMERO_CONTRATO"));
                            project.setCity(jsonObjectProyecto.getString("CIUDAD"));
                            project.setDirection(jsonObjectProyecto.getString("DIRECCION"));
                            projectList.add(project);
                        }
                        startRecyclerView(projectList);
                    }else{
                        Snackbar.make(getView(),"No tienes proyectos", Snackbar.LENGTH_LONG).show();
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
        return  projectListe;
    }

    public  List<Project> llenarArrayProyectoo(List<Project> projectListP){

      return   this.projectListP=projectListP;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public  void startRecyclerView(List<Project> listProjects) {

        recyclerViewProject.setAdapter(new RecyclerAdapter(listProjects, new RecyclerAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Project item) {
             // FragmentMisProyectos fragment=new FragmentMisProyectos();
              //FragmentProjects f=FragmentProjects.newInstance(userName,id,email);
              FragmentMisProyectos fragment=FragmentMisProyectos.newInstance(item.getIdProyecto());
              getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        },getContext() ));
    }



   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
