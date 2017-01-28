package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.n550j.myapplication.R;

import java.util.List;

import Objetos.Project;
import Objetos.Vista_Infore_Periodo;

/**
 * Created by N550J on 27/01/2017.
 */

public class AdapterpInformporPeriodos  extends RecyclerView.Adapter<AdapterpInformporPeriodos.informeporPeriodoViewHolder>{


    Context context;
    public OnItemClickListener escucha;

    public interface OnItemClickListener{
        void onItemClick(Vista_Infore_Periodo vista_infore_periodo);

    }

    @Override
    public informeporPeriodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info_por_periodo, parent, false);

        return new informeporPeriodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(informeporPeriodoViewHolder holder, int position) {
        holder.bind(vista_infore_periodos.get(position),escucha, context);

    }

    @Override
    public int getItemCount() {
        return vista_infore_periodos.size();
    }


    public  class informeporPeriodoViewHolder extends RecyclerView.ViewHolder  {

        TextView nombreActividad;
        TextView txv_EV;
        TextView costoRealAC;
        TextView costoRealPV;
        TextView edtporcentajeAvance;
        TextView textVarianzaCV;
        TextView txtvarianzaSV;

        //  Button button2;

        public informeporPeriodoViewHolder(View itemview){
            super(itemview);
            nombreActividad= (TextView) itemView.findViewById(R.id.nombreActividad);
            txv_EV= (TextView) itemview.findViewById(R.id.txv_EV);
            costoRealAC= (TextView) itemview.findViewById(R.id.costoRealAC);
            costoRealPV= (TextView) itemview.findViewById(R.id.costoRealPV);
            edtporcentajeAvance= (TextView) itemview.findViewById(R.id.edtporcentajeAvance);
            textVarianzaCV= (TextView) itemview.findViewById(R.id.textVarianzaCV);
            txtvarianzaSV= (TextView) itemview.findViewById(R.id.txtvarianzaSV);
            //  button2= (Button) itemview.findViewById(R.id.actualizar);
            //  itemview.setOnClickListener(this);
        }
        /**   @Override
        public void onClick(View view){
        escucha.onClick(this,obtenerElID(getAdapterPosition()));
        }*/

        public  void bind(final Vista_Infore_Periodo vista_infore_periodo, final OnItemClickListener listener, Context context){

            nombreActividad.setText(vista_infore_periodo.getNombreActividad());
            txv_EV.setText(vista_infore_periodo.getEV()+"");
            costoRealAC.setText(vista_infore_periodo.getAC()+"");
            costoRealPV.setText(vista_infore_periodo.getPV());
            edtporcentajeAvance.setText(vista_infore_periodo.getPorcentajeAvance()+"");
            textVarianzaCV.setText(vista_infore_periodo.getCV());
            txtvarianzaSV.setText(vista_infore_periodo.getSV());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    escucha.onItemClick(vista_infore_periodo);

                }
            });

        }
    }

    List<Vista_Infore_Periodo> vista_infore_periodos;
    public  AdapterpInformporPeriodos(List<Vista_Infore_Periodo> vista_infore_periodos, OnItemClickListener listener, Context context){
        this.vista_infore_periodos=vista_infore_periodos;
        this.escucha=listener;
        this.context=context;
    }
}
