package com.example.n550j.myapplication;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import costantes.Constantes;

public class homeActivity extends AppCompatActivity {
     Context context=this;
    @BindView(R.id.Editexemail)
    EditText editexemail;

    @BindView(R.id.Editexpassword)
    EditText editexpassword;

    @BindView(R.id.buttonStartSssion)
    Button buttonStartSesion;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    TextView textViewCreateCount;
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        buttonStartSesion = (Button) findViewById(R.id.buttonStartSssion);
        editexemail = (EditText) findViewById(R.id.Editexemail);
        editexpassword = (EditText) findViewById(R.id.Editexpassword);
        textViewCreateCount= (TextView) findViewById(R.id.CrearCuenta);

        buttonStartSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                startAcitivity("nada", "nada", "nada");
            }
        });
        textViewCreateCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starActivityCreateUser();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @OnClick(R.id.buttonStartSssion)
    public void login() {
        JSONObject jsonObjStartSession = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(this);
        String stringemail = editexemail.getText().toString();
        String stringPassword = editexpassword.getText().toString();
        try {
            jsonObjStartSession.put("email", stringemail);
            jsonObjStartSession.put("password", stringPassword);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_START_SESSIOn;
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObjStartSession, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("type")){

                       JSONObject jo= response.getJSONObject("data");
                       String name= jo.getString("nombre");
                       String idUser=  jo.getString("idusuario");
                       String email= jo.getString("email");
                       startAcitivity(name,idUser,email);
                    }else{
                        dialogAlertaP("No existe", context ,"Oops");
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

     public void startAcitivity(String userName, String idUser,String email){
         SharedPreferences sharedPreferences= getSharedPreferences("archivoPreferences",Context.MODE_PRIVATE);
         SharedPreferences.Editor editor=sharedPreferences.edit();

         editor.putString("userName",userName);
         editor.putString("id",idUser);
         editor.putString("email", email);
         editor.commit();


         Intent intent = new Intent(this, MainActivity.class);
         //intent.putExtra("emailuser", stringemail);
         startActivity(intent);
     }

     public  void starActivityCreateUser(){
         Intent intent = new Intent(this, CreateCountActivity.class);
         //intent.putExtra("emailuser", stringemail);
         startActivity(intent);

     }

    public void dialogAlertaP(String error, Context context, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(error);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("home Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
