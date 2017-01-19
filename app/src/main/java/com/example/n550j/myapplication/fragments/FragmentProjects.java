package com.example.n550j.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n550j.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.RecyclerAdapter;
import Objetos.Project;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerViewProject;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FragmentProjects() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProjects.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProjects newInstance(String param1, String param2) {
        FragmentProjects fragment = new FragmentProjects();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

         // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_projects, container, false);

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

       // mAdapter=new RecyclerAdapter(items);
       // recyclerViewProject.setAdapter(mAdapter);
        startRecyclerView(items, view);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public  void startRecyclerView(List<Project> listProjects, final View view){



        recyclerViewProject.setAdapter(new RecyclerAdapter(listProjects,new RecyclerAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Project item) {
            FragmentMisProyectos fragment=new FragmentMisProyectos();
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
    public void onStart() {
        super.onStart();
    }
}
