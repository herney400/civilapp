package Adapters;

import android.content.Context;
import android.content.DialogInterface;
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

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Objetos.Actividad;
import Objetos.Causal;

/**
 * Created by N550J on 18/01/2017.
 */

public class AdapterActividades extends RecyclerView.Adapter<AdapterActividades.ActividadesViewHolder> {
    final ArrayList itemsSeleccionados = new ArrayList();
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
                double costoReal  = Double.parseDouble(actividadesViewHolder.edtcostoReal.getText().toString());

                FragmentAvanceCostoReal fragmentAvanceCostoReal=new FragmentAvanceCostoReal();

                if(porcentaje==100.0){
                    fragmentAvanceCostoReal.updateActivity(idPeriodo,actividads.get(position).getIdActividad(),1,porcentaje,costoReal,context);
                }else {
                    fragmentAvanceCostoReal.updateActivity(idPeriodo,actividads.get(position).getIdActividad(),0,porcentaje,costoReal,context);
                }


            }
        });
        actividadesViewHolder.btn_finalizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                actividadesViewHolder.createSimpleDialog(actividads.get(position).getIdActividad() ).show();
                //Toast.makeText(context,"Finalizada :"+actividads.get(position).getNombreActividad()+""+itemsSeleccionados.size(),Toast.LENGTH_LONG).show();
                Log.e("---->",""+itemsSeleccionados.size());
            }
        });
    }
    List<Actividad> actividads;
    List<String> causales;
    CharSequence[]  causals;
    int idPeriodo;
    @Override
    public int getItemCount() {
        return actividads.size();
    }

    public  AdapterActividades(int idPeriodo,List<Actividad> actividads, CharSequence[] causal,OnItemClickListener listener, Context context){
        this.actividads=actividads;
        this.escucha=listener;
        this.context=context;
        this.causals=causal;
        this.idPeriodo=idPeriodo;
    }
    public  AdapterActividades(CharSequence[] items){
        //this.causals=new CharSequence[items.length];
        this.causals= Arrays.copyOf(items,items.length);

     /*   for(int i=0;i<items.length;i++){
            this.causals[i]=items[i];
        }*/
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
         //   causals=itemss;
         /*   builder.setTitle("Elija una causal")
                    .setIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setItems(causals, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText( context,"Seleccionaste:"+edtcostoReal.getText()+"__Actividad:"+idActividad +  causals[which],  Toast.LENGTH_SHORT) .show();
                        }
                    });*/
            builder.setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentAvanceCostoReal fragmentAvanceCostoReal=new FragmentAvanceCostoReal();
                            try {
                                fragmentAvanceCostoReal.finalizaActivida(idActividad,itemsSeleccionados, context);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                           // Toast.makeText( context, "Checks seleccionados:(" + itemsSeleccionados.size() + ")", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }). setTitle("Elija las causales de no cumplimiento")
                    .setMultiChoiceItems(causals, null, new DialogInterface.OnMultiChoiceClickListener()
                      {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                // Guardar indice seleccionado

                                itemsSeleccionados.add(which+1);
           //                     Toast.makeText( context, "Checks seleccionados:(" + itemsSeleccionados.get(which) + ")", Toast.LENGTH_SHORT).show();
                            } else if (itemsSeleccionados.contains(which)) {
                                // Remover indice sin selección
                                itemsSeleccionados.remove(Integer.valueOf(which));
                            }

                        }
                    } );
           // return builder.create();
         ///   itemsSeleccionados.clear();
            return builder.create();
        }
    }


}
