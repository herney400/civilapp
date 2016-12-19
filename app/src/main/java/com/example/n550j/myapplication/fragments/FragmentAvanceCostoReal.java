package com.example.n550j.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.n550j.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAvanceCostoReal extends Fragment {


    public FragmentAvanceCostoReal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_avance_costo_real, container, false);
    }

}
