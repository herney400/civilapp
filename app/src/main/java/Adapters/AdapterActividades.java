package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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

            }
        });
        actividadesViewHolder.btn_finalizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Finalizada :"+actividads.get(position).getNombreActividad()+"",Toast.LENGTH_LONG).show();
                actividadesViewHolder.createSimpleDialog(actividads.get(position).getIdActividad() ).show();
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
             txtduracionActivida= (TextView) itemview.findViewById(R.id.txv_EV);
             txtcostoProgramado= (TextView) itemview.findViewById(R.id.costoRealAC);
             edtcostoReal= (EditText) itemview.findViewById(R.id.costoRealPV);
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
        public AlertDialog createSimpleDialog(final int idActividad) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("Alerta")
                    .setMessage("Tèrmino la actividad en el tiempo planeado ?")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                 //   createSimple().show();
                                }
                            }).setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createSingleListDialog(idActividad).show();
                        }
                    });
            return builder.create();
        }

        public AlertDialog createSingleListDialog(final int idActividad) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            final CharSequence[] items = new CharSequence[14];

            items[0] = "Mala programaciòn";
            items[1] = "Falta de cancha (Prerrequisitos)";
            items[2] = "Planos defectuosos";
            items[3] = "Falta de M.O";
            items[4] = "Problema con contratistas";
            items[5] = "Problema con proveedores";
            items[6] = "Falta o falla de equipos";
            items[7] = "Bajo rendimiento M.O";
            items[8] = "Falta de materiales";
            items[9] = "Mala ejecucion del trabajo";
            items[10] = "Indefinicion o cambio del proyecto";
            items[11] = "Cambio en prioridades";
            items[12] = "Motivos climaticos";
            items[13] = "Falta de permisos";

            builder.setTitle("Elija una causal")
                    .setIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText( context,"Seleccionaste:"+edtcostoReal.getText()+"__Actividad:"+idActividad + items[which],  Toast.LENGTH_SHORT) .show();

                        }
                    });

            return builder.create();
        }
    }


}
