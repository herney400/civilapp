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

import Objetos.Project;
import costantes.Constantes;

/**
 * Created by N550J on 20/01/2017.
 */

public class ControladorProjects {

    public List<Project> getProyects(JSONArray jaa){
       // JSONObject jsonObjproyectos = new JSONObject();
      //  RequestQueue queue = Volley.newRequestQueue(c);

        List<Project> projectList=new ArrayList<>();
               // JSONArray ja= jaa.getJSONArray("Projects");
                for(int i=0;i<jaa.length();i++){
                    JSONObject jsonObjectProyecto= null;
                    try {
                        jsonObjectProyecto = jaa.getJSONObject(i);
                        Project project=new Project();
                        project.setIdProyecto(jsonObjectProyecto.getInt("IDPROYECTO"));
                        project.setName_project(jsonObjectProyecto.getString("NOMBRE_PROYECTO"));
                        project.setNumber_contract(jsonObjectProyecto.getString("NUMERO_CONTRATO"));
                        project.setCity(jsonObjectProyecto.getString("CIUDAD"));
                        project.setDirection(jsonObjectProyecto.getString("DIRECCION"));
                        projectList.add(project);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        return  projectList;
    }
}
