package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.n550j.myapplication.fragments.FragmentAvanceCostoReal;
import com.example.n550j.myapplication.fragments.FragmentCausals;
import com.example.n550j.myapplication.fragments.FragmentCurvadeCostos;
import com.example.n550j.myapplication.fragments.FragmentIndiceDesempeno;
import com.example.n550j.myapplication.fragments.FragmentInformePorPeriodo;

/**
 * Created by N550J on 23/01/2017.
 */

public class CustomFragmentPageAdapter extends FragmentPagerAdapter {
    private static final String TAG = CustomFragmentPageAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 5;
    int idProyecto;
    public CustomFragmentPageAdapter(FragmentManager fm, int idProeyecto) {
        super(fm);
        this.idProyecto=idProeyecto;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentAvanceCostoReal fragmentAvanceCostoReal=FragmentAvanceCostoReal.newInstance(idProyecto);
                return fragmentAvanceCostoReal;
            case 1:
                FragmentInformePorPeriodo fragmentInformePorPeriodo=FragmentInformePorPeriodo.newInstance(idProyecto);
                return  fragmentInformePorPeriodo;
            case 2:
                FragmentCurvadeCostos fragmentCurvadeCostos=FragmentCurvadeCostos.newInstance(idProyecto);

                return  fragmentCurvadeCostos;///
            case 3:
                FragmentIndiceDesempeno fragmentIndiceDesempeno= FragmentIndiceDesempeno.newInstance(idProyecto);
                return  fragmentIndiceDesempeno;
            case 4:
                FragmentCausals fragmentCausals=FragmentCausals.newInstance(idProyecto);
                return  fragmentCausals;
        }
        return null;
    }
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "AVANCE COSTO REAL";
            case 1:
                return "INFORME POR PERIODO";
            case 2:
                return "CURVA S DE COSTOS";
            case 3:
                return "INDICE DE DESEMPEÑO";
            case 4:
                return "GRÁFICA DE COSTO Y PROGRAMA";
        }
        return null;
    }
}
