package com.example.n550j.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * {@link FragmentCausals.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCausals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCausals extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FloatingActionButton floatingActionButton;
    // TODO: Rename and change types of parameters
     int idProyecto;
    private String mParam2;


    ArrayList entriesCausale = new ArrayList<String>();
    ArrayList entriesCount = new ArrayList<String>();
    private OnFragmentInteractionListener mListener;

    public FragmentCausals() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment FragmentCausals.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCausals newInstance(int param1) {
        FragmentCausals fragment = new FragmentCausals();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
      //  args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idProyecto = getArguments().getInt(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View view=inflater.inflate(R.layout.fragment_fragment_causals, container, false);
        getCausales(idProyecto,getContext());


        floatingActionButton= (FloatingActionButton) view.findViewById(R.id.floatingActionButton4);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  graficar(view);
            }
        });

        return view;
    }
    /*Metodo utilizado para obtener los datos de causales*/
    public List<Periodo> getCausales(int idProyecto,final Context c){
        JSONObject jsonObjCurvaScostos = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        try {
            jsonObjCurvaScostos.put("IDPROYECTO", idProyecto);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_CAUSALES_SUM+idProyecto;
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
                        JSONArray jarrayCausale=response.getJSONArray("Causals");
                        JSONArray jarraycount=response.getJSONArray("Count");
                        ;
                       ;
                        graficar(getView(), parseJsonCounts(jarraycount),parseJsonCausale(jarrayCausale));

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

    private ArrayList<String> parseJsonCounts(JSONArray jarraycount) {
        entriesCount.clear();
        for (int i=0;i<jarraycount.length();i++){
            try {
                entriesCount.add(new BarEntry(jarraycount.getInt(i),i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  entriesCount;
    }

    private ArrayList<String> parseJsonCausale(JSONArray jarrayCausals) {

        entriesCausale.clear();
        for (int i=0;i<jarrayCausals.length();i++){
            try {
                entriesCausale.add(jarrayCausals.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entriesCausale;
    }

    public  void graficar(View view,ArrayList entriesCounts,ArrayList entriesCausales){

        BarChart barChart;
        barChart= (BarChart) view.findViewById(R.id.chartline);
        barChart.clear();
        barChart.animateY(3000);
        BarDataSet dataset= new BarDataSet(entriesCounts,"causales");
        dataset.setColor(Color.MAGENTA);
        BarData data= new BarData(entriesCausales,dataset);
        barChart.setData(data);
        //LineDataSet dataset=new LineDataSet(entriesCount,"Causales");
        dataset.setColor(Color.MAGENTA);
        barChart.setDoubleTapToZoomEnabled(true);
        barChart.setDrawBorders(true);
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(getContext(),lineChart.getX()+"__"+lineChart.getX(),Toast.LENGTH_LONG).show();
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
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
