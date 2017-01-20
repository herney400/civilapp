package Controlers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Objetos.Periodo;
import costantes.Constantes;

/**
 * Created by N550J on 19/01/2017.
 */

public class ControladorActividad {


    public boolean updateActivity(int idActividad , int finalizacionCompleta, double porcentaje_avance ,double costoReal,Context c){

        JSONObject jsonObjAcitivdad = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);

        try {
            jsonObjAcitivdad.put("idActividad", idActividad);
            jsonObjAcitivdad.put("finalizacion_completa", finalizacionCompleta);
            jsonObjAcitivdad.put("porcentaje_avance", porcentaje_avance);
            jsonObjAcitivdad.put("costo_real",costoReal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_START_SESSIOn;

        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObjAcitivdad, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("type")){
                        JSONObject jo= response.getJSONObject("data");

                    }else{
                        //  dialogAlertaP("No existe", context ,"Oops");
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
        return  true;
    }




}
