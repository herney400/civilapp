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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Objetos.Periodo;
import Objetos.Project;
import costantes.Constantes;

/**
 * Created by N550J on 21/01/2017.
 */

public class ControladorPeriodos {

    public List<Periodo> getPeriodos(int idProyecto, Context c){
        JSONObject jsonObjperiodo = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(c);
        try {
            jsonObjperiodo.put("IDPROYECTO", idProyecto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Constantes.URL_TRAE_PERIODOS;

        final List<Periodo> periodoList=new ArrayList<>();
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObjperiodo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("status")){
                        JSONArray ja= response.getJSONArray("datos");
                        for(int i=0;i<ja.length();i++){
                            JSONObject jsonObjectPeriodos=ja.getJSONObject(i);
                            Periodo periodo=new Periodo();
                            periodo.setIdPerido(jsonObjectPeriodos.getInt("IDPERIODO"));
                            periodo.setIdActividad(jsonObjectPeriodos.getInt("IDACTIVIDAD"));
                            periodo.setDineroAsigado(jsonObjectPeriodos.getInt("DINEROASIGNADO"));
                           // periodo.setFechaInicial(jsonObjectPeriodos.getString("FECHA_INICIAL"));
                            periodoList.add(periodo);
                        }
                    }else{
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


}
