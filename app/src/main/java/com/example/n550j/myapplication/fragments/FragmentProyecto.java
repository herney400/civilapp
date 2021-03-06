package com.example.n550j.myapplication.fragments;


import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.n550j.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.CharArrayReader;
import java.io.File;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import costantes.Constantes;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProyecto extends Fragment {

    Button buttonCargarArchvo;
    FloatingActionButton floatingActionButton;
    public static String BASE_URL = "http://74.208.113.25/api/upload";
    static final String ARG_PARAM2_IDUSER = "param2";
    //"http://74.208.113.25/api/projects"
    EditText editTextNameProject, editTextNumbreProject, editTextCity,editTextDirection;
    String strNmaeProject,strNumberProject, strCity, strDirection;
    String  userName,  email;
    int idUser;
    int idUauario;

    static final int PICK_IMAGE_REQUEST = 1;
    private static final int FILE_SELECT_CODE = 0;

    String filePath;

    public FragmentProyecto() {
        // Required empty public constructor
    }

    public static FragmentProyecto newInstance( int idUser ) {
        FragmentProyecto fragment = new FragmentProyecto();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM2_IDUSER, idUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            idUauario = getArguments().getInt(ARG_PARAM2_IDUSER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_proyecto,container, false);
        loadGui(view);
        readFilePreferences();
        buttonCargarArchvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooserBrowse();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileUpload(filePath);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        //BarChart chart;
        // Inflate the layout for this fragment
        return view;
    }
    /*Metodo encargado de caragr la GUi*/
    public  void loadGui(View view){
        editTextNameProject= (EditText) view.findViewById(R.id.editProjectName);
        editTextNumbreProject= (EditText) view.findViewById(R.id.editContractNumber);
        editTextCity= (EditText) view.findViewById(R.id.editcityProject);
        editTextDirection= (EditText) view.findViewById(R.id.editdirectionProject);
        buttonCargarArchvo= (Button) view.findViewById(R.id.buttonCargarArchivo);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);


    }
    public  void getValue(String urlFile){
        strNmaeProject=editTextNameProject.getText().toString();
        strNumberProject=editTextNumbreProject.getText().toString();
        strCity =editTextCity.getText().toString();
        strDirection=editTextDirection.getText().toString();
        createProject(idUauario,strNmaeProject,strNumberProject,strCity,strDirection, urlFile);
    }

    public void createProject(int  idUauario,String strNmaeProject,String strNumberProject, String strCity,String strDirection, String url){

        JSONObject jsonObjCreateProject = new JSONObject();
        JSONObject jsonObjectID=new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.getCache().clear();
        Date today=new  Date();
        String URL = Constantes.URL_CREATE_PROJECT;
        try {
            jsonObjectID.put("idusuario", idUser);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjCreateProject.put("nombre_proyecto",strNmaeProject);
            jsonObjCreateProject.put("idusuario",idUauario);
            jsonObjCreateProject.put("numero_contrato", strNumberProject);
            jsonObjCreateProject.put("ciudad", strCity);
            jsonObjCreateProject.put("direccion", strDirection);
            jsonObjCreateProject.put("fecha_creacion", format(today));
            jsonObjCreateProject.put("ruta_archivo" , url);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObjCreateProject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                /*Aqui va la lectura del Json que retorne Lucho*/

            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",""+error);
            }
        });
        queue.add(request);
    }

    public  void readFilePreferences(){
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("archivoPreferences", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName","no hay user");
        idUser   = sharedPreferences.getInt("id",1);
        email    = sharedPreferences.getString("email", "email");
    }

    /*metodo encargado de abrir un File Chooser que permite elegir el archivo excel */
    private void ChooserBrowse() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(intent.createChooser(intent,"selecciona un archivo"), FILE_SELECT_CODE);
    }

    /*Metodo qeu utiliza volley para cargar el archivo excel al server*/
    private void FileUpload(final String imagePath) throws InterruptedException {
        final RequestFuture<SimpleMultiPartRequest> future=RequestFuture.newFuture();

        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.getCache().clear();
        JSONObject jsonObjStartSession = new JSONObject();
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, BASE_URL,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        String rs=response.toString();
                        String remplazo=rs.replace("\"","");
                        String[] sucee=remplazo.split(";");

                        if(sucee[0].equals("Sucess")){
                            getValue(sucee[1]);
                            Snackbar.make(getView(),"Archivo Enviado", Snackbar.LENGTH_LONG).show();
                        }else{
                            Snackbar.make(getView(),"No fue posible enviar su archivo", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",""+error);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        smr.addFile("image", imagePath);
        //smr.addMultipartParam("id", "identi", "dfd");
        queue.add(smr);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri picUri = data.getData();
            try {
                filePath = getPath(getContext(),picUri);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

    }

    // Get Path of selected image
  /*  private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getContext(),    contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }*/
    /*Metodo para obtener el path del archivo a enviar */
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String format(Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(fecha);
    }

}
