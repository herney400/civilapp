package com.example.n550j.myapplication;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import costantes.Constantes;

public class CreateCountActivity extends AppCompatActivity {
      EditText editTextNameUser, editTextLastName,editTextUser,editTextEmail,editTextPassword,editTextRepetirpaswor;
      String    strNameUser, strLasName,strUser, strEmail,strPassword,strRepetirPassword;
      Button buttonCancelar, buttonAceptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_count);
        loadGui();
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVAlues();
            }
        });
    }

    public  void loadGui(){
        editTextNameUser = (EditText) findViewById(R.id.nameUser);
        editTextLastName = (EditText) findViewById(R.id.lastaName);
        editTextUser     = (EditText) findViewById(R.id.user);
        editTextEmail    = (EditText) findViewById(R.id.emailcre);
        editTextPassword = (EditText) findViewById(R.id.passwordcre);
        editTextRepetirpaswor= (EditText) findViewById(R.id.passwordrepe);
        buttonAceptar    = (Button) findViewById(R.id.buttonAceptar);
        buttonCancelar   = (Button) findViewById(R.id.buttonCancelar);
    }

    public  void getVAlues(){
        strNameUser = editTextNameUser.getText().toString();
        strLasName  = editTextLastName.getText().toString();
        strUser     = editTextUser.getText().toString();
        strEmail    = editTextEmail.getText().toString();
        strPassword = editTextPassword.getText().toString();
        strRepetirPassword=editTextPassword.getText().toString();

        if(strPassword.equals(strRepetirPassword)){
            createCount(strNameUser,strLasName,strUser,strEmail,strPassword);
        }else{
            editTextPassword.setText("");
            editTextRepetirpaswor.setText("");
        }
    }

    public  void createCount(String strNameUser, String strLasName,String strUser, String strEmail,String strPassword){
        JSONObject jsonObjCreateCount = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(this);
        Date today=new  Date();
        String URL = Constantes.URL_CREATE_COUNT;
        try {
            jsonObjCreateCount.put("nombre",strNameUser);
            jsonObjCreateCount.put("apellido",strLasName);
            jsonObjCreateCount.put("usuario",strUser);
            jsonObjCreateCount.put("email",strEmail);
            jsonObjCreateCount.put("contrasena",strPassword);
            jsonObjCreateCount.put("fecha_inscripcion",format(today));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObjCreateCount, new Response.Listener<JSONObject>() {
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

    public static String format(Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(fecha);
    }
}
