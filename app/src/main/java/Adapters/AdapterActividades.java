package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n550j.myapplication.R;
import com.example.n550j.myapplication.fragments.FragmentAvanceCostoReal;

import java.util.List;

import Objetos.Actividad;
import Objetos.Project;

import static com.example.n550j.myapplication.R.string.number_contract;

/**
 * Created by N550J on 18/01/2017.
 */

public class AdapterActividades extends RecyclerView.Adapter<AdapterActividades.ActividadesViewHolder> {

    Context context;
    public OnItemClickListener escucha;

    public interface OnItemClickListener{
        void onItemClick(Actividad actividad);

    }

    @Override
    public ActividadesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.actividades_card, parent, false);
        return new ActividadesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ActividadesViewHolder actividadesViewHolder, final int position) {
        actividadesViewHolder.bind(actividads.get(position),escucha,context);

        actividadesViewHolder.btn_actualizar.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {

                double porcentaje = Double.parseDouble(actividadesViewHolder.edtporcentaje.getText().toString());
                double costoReal= Double.parseDouble  (actividadesViewHolder.edtcostoReal.getText().toString());
                FragmentAvanceCostoReal fragmentAvanceCostoReal=new FragmentAvanceCostoReal();
                fragmentAvanceCostoReal.updateActivity(actividads.get(position).getIdActividad(),0,porcentaje,costoReal,context);

             //   actividadesViewHolder.createSimpleDialog(actividads.get(position).getNombreActividad()).show();

            }
        });
        actividadesViewHolder.btn_finalizar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Finalizada :"+actividads.get(position).getNombreActividad()+"",Toast.LENGTH_LONG).show();
            }
        });
    }
    List<Actividad> actividads;
    @Override
    public int getItemCount() {
        return actividads.size();
    }

    public  AdapterActividades(List<Actividad> actividads, OnItemClickListener listener, Context context){
        this.actividads=actividads;
        this.escucha=listener;
        this.context=context;
    }

    public  class ActividadesViewHolder extends RecyclerView.ViewHolder  {

        TextView txtmombreActivida;
        TextView txtduracionActivida;
        TextView txtcostoProgramado;
        EditText edtcostoReal;
        EditText edtporcentaje;
        Button btn_actualizar,btn_finalizar;

        public ActividadesViewHolder(View itemview){
            super(itemview);
             txtmombreActivida= (TextView) itemview.findViewById(R.id.nombreActividad);
             txtduracionActivida= (TextView) itemview.findViewById(R.id.txvduracion);
             txtcostoProgramado= (TextView) itemview.findViewById(R.id.costoProgramado);
             edtcostoReal= (EditText) itemview.findViewById(R.id.costoReal);
             edtporcentaje= (EditText) itemview.findViewById(R.id.edtporcentajeAvance);
             btn_actualizar= (Button) itemview.findViewById(R.id.btn_actualizar);
             btn_finalizar= (Button) itemview.findViewById(R.id.btn_finalizar);


            btn_actualizar= (Button) itemview.findViewById(R.id.btn_actualizar);
            btn_finalizar= (Button) itemview.findViewById(R.id.btn_finalizar);

            //  itemview.setOnClickListener(this);
        }
        /**   @Override
        public void onClick(View view){
        escucha.onClick(this,obtenerElID(getAdapterPosition()));
        }*/

        public  void bind(final Actividad actividad, OnItemClickListener listener, Context context){
            txtmombreActivida.setText(actividad.getNombreActividad());
            txtduracionActivida.setText(actividad.getDiasduracion()+"");
            txtcostoProgramado.setText(""+actividad.getCostoTotal());
            edtcostoReal.setText(actividad.getCostoReal()+"");
            edtporcentaje.setText(actividad.getPorcentaje()+"");

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    escucha.onItemClick(actividad);

                }
            });*/
        }

    }


}
