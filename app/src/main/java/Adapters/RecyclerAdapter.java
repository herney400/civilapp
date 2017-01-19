package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n550j.myapplication.MainActivity;
import com.example.n550j.myapplication.R;
import com.example.n550j.myapplication.fragments.FragmentMisProyectos;

import java.util.List;

import Objetos.Project;



/**
 * Created by N550J on 15/01/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProjectViewHolder> {

   // MainActivity mainActivity=new MainActivity();




    Context context;
    public   OnItemClickListener escucha;

   public interface OnItemClickListener{
        void onItemClick(Project project);

    }



    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.projectcardview, parent, false);

        return new ProjectViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder projectViewHolder, final int position) {
        projectViewHolder.bind(projects.get(position),escucha,context);
       // final String txt=projectViewHolder.name_project.getText().toString();
        /*projectViewHolder.button2.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Toast.makeText(context,projects.get(position).getCity().toString()+"_"+txt,Toast.LENGTH_LONG).show();
            }
        });*/

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
      //  Button button2;

     public ProjectViewHolder(View itemview){
          super(itemview);
          name_project= (TextView) itemView.findViewById(R.id.texvienameProject);
          number_contract= (TextView) itemview.findViewById(R.id.texviewnumbercontract);
          city= (TextView) itemview.findViewById(R.id.textViewCity);
          direction= (TextView) itemview.findViewById(R.id.direction);
       //  button2= (Button) itemview.findViewById(R.id.actualizar);
        //  itemview.setOnClickListener(this);
      }
     /**   @Override
        public void onClick(View view){
            escucha.onClick(this,obtenerElID(getAdapterPosition()));
        }*/

     public  void bind(final Project project,final  OnItemClickListener listener,Context context){
         name_project.setText(project.getName_project());
         number_contract.setText(project.getNumber_contract());
         city.setText(project.getCity());
         direction.setText(project.getDirection());

         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 escucha.onItemClick(project);

             }
         });
     }
  }
    public int obtenerElID(int posito){
        Log.e("Este es el ID ",""+projects.get(posito).getCity());


        return 1;
    }


    List<Project> projects;
    public  RecyclerAdapter(List<Project> projects,OnItemClickListener listener,Context context){
        this.projects=projects;
        this.escucha=listener;
        this.context=context;
    }


}
