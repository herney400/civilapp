package Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n550j.myapplication.R;

import java.util.List;

import Objetos.Project;

/**
 * Created by N550J on 15/01/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProjectViewHolder> {


    Context context;
    public   OnItemClickListener escucha;

    interface OnItemClickListener{
        public void onClick(ProjectViewHolder holder, int idProject );

    }

    public RecyclerAdapter(Context context, OnItemClickListener escucha) {
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.projectcardview, parent, false);

        return new ProjectViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder projectViewHolder, final int position) {
        projectViewHolder.name_project.setText(projects.get(position).getName_project());
        projectViewHolder.number_contract.setText(projects.get(position).getNumber_contract());
        projectViewHolder.city.setText(projects.get(position).getCity());
        projectViewHolder.direction.setText(projects.get(position).getDirection());

        projectViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v,projects.get(position).getCity()+"",Snackbar.LENGTH_LONG ).show();
                Log.e("ID obtenido",projects.get(position).getCity()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }



    public  class ProjectViewHolder extends RecyclerView.ViewHolder  {

        TextView name_project;
        TextView number_contract;
        TextView city;
        TextView direction;
        TextView fechaCreacion;

     public ProjectViewHolder(View itemview){
          super(itemview);
          name_project= (TextView) itemView.findViewById(R.id.texvienameProject);
          number_contract= (TextView) itemview.findViewById(R.id.texviewnumbercontract);
          city= (TextView) itemview.findViewById(R.id.textViewCity);
          direction= (TextView) itemview.findViewById(R.id.direction);
        //  itemview.setOnClickListener(this);
      }
     /**   @Override
        public void onClick(View view){
            escucha.onClick(this,obtenerElID(getAdapterPosition()));
        }*/

  }
    public int obtenerElID(int posito){
        Log.e("Este es el ID ",""+projects.get(posito).getCity());

        return 1;
    }


    List<Project> projects;
    public  RecyclerAdapter(List<Project> projects){
        this.projects=projects;
    }


}
