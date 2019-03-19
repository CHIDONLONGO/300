package com.a300.gi.a300.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a300.gi.a300.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequirementsFragment extends Fragment {


    public RequirementsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requirements, container, false);
    }

}
