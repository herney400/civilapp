package com.example.n550j.myapplication.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.n550j.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Adapters.CustomFragmentPageAdapter;
import Objetos.Project;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMisProyectos extends Fragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int     idProyecto ;
    Project project;
    private static final String ARG_IDPROYECTO = "ID_PROYECTO";
    private static final String ARG_PROYECTO = "PROYECTO";

    public FragmentMisProyectos() {
        // Required empty public constructor
    }
    public static FragmentMisProyectos newInstance(int idProyecto) {
        FragmentMisProyectos fragment = new FragmentMisProyectos();
        Bundle args = new Bundle();
        args.putInt(ARG_IDPROYECTO, idProyecto);
       // args.putSerializable(ARG_PROYECTO, (Serializable) project);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idProyecto = getArguments().getInt(ARG_IDPROYECTO);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_mis_proyectos, container, false);
      //  toolbar = (Toolbar)view.findViewById(R.id.toolbar);
       // ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        Log.e("id del proyecto","---->"+idProyecto);
        tabLayout.setupWithViewPager(viewPager);
        // Inflate the layout for this fragment
        viewPager.setAdapter(new CustomFragmentPageAdapter(getChildFragmentManager(),idProyecto));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        //FragmentProjects f=FragmentProjects.newInstance(userName,id,email);

        FragmentAvanceCostoReal fragmentAvanceCostoReal=FragmentAvanceCostoReal.newInstance(idProyecto);

       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
     /*
        adapter.addFragment(fragmentAvanceCostoReal, "AVANCE COSTO REAL");
        adapter.addFragment(new FragmentCurvadeCostos(), "INFORME POR PERIODO");
        adapter.addFragment(new FragmentCurvadeCostos(), "INDICE DE DESEMPEÑO");
       */
        viewPager.setAdapter(new CustomFragmentPageAdapter(getChildFragmentManager(),idProyecto));

    }

    public void leerArchivoPreferences(){


    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
