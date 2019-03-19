package com.a300.gi.a300.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.a300.gi.a300.LoginUserActivity;
import com.a300.gi.a300.R;
import com.a300.gi.a300.RegisterUserActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Button btnregistrar = (Button) view.findViewById(R.id.btnRegistrar);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        Button btniniciar = (Button) view.findViewById(R.id.btnIngresar);
        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginUserActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
