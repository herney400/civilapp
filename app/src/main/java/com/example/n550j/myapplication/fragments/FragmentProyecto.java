package com.example.n550j.myapplication.fragments;


import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.example.n550j.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProyecto extends Fragment {

    Button buttonCargarArchvo;
    FloatingActionButton floatingActionButton;

    public static String BASE_URL = "http://192.168.1.78:8081/civildocs/prueba.php";
    static final int PICK_IMAGE_REQUEST = 1;
    private static final int FILE_SELECT_CODE = 0;

    String filePath;

    public FragmentProyecto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_proyecto,container, false);
        buttonCargarArchvo= (Button) view.findViewById(R.id.buttonCargarArchivo);
        buttonCargarArchvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooserBrowse();
            }
        });
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUpload(filePath);
            }
        });
        // Inflate the layout for this fragment
        return view;
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
    private void FileUpload(final String imagePath) {
        Log.e("_URL",""+imagePath);
        RequestQueue queue= Volley.newRequestQueue(getContext());

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");
                          //  Log.e("Message",""+message);
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
        queue.add(smr);


        /*
        MyApplication.getInstance().addToRequestQueue(smr);*/

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Uri picUri = data.getData();
            Log.e("URL____",""+picUri);

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

}
