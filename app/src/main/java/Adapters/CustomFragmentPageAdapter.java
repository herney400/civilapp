package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.n550j.myapplication.fragments.FragmentAvanceCostoReal;
import com.example.n550j.myapplication.fragments.FragmentCurvaSdeCostos;

/**
 * Created by N550J on 23/01/2017.
 */

public class CustomFragmentPageAdapter extends FragmentPagerAdapter {
    private static final String TAG = CustomFragmentPageAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 2;
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
                return new FragmentCurvaSdeCostos();
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
                return "INDICE DE DESEMPEÑO";
            case 3:
                return "INDICE DE DESEMPEÑO";
        }
        return null;
    }
}
