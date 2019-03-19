package com.a300.gi.a300.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.a300.gi.a300.R;
import com.a300.gi.a300.entidades.UserClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilUsuarioFragment extends Fragment {

    Button btnCerrar;
    TextView tvView, tvViewNombreUsuario;


    public PerfilUsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vista = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);

        btnCerrar = (Button) vista.findViewById(R.id.BtnCerrar);
        tvView = (TextView) vista.findViewById(R.id.TvTop);
        tvViewNombreUsuario = (TextView) vista.findViewById(R.id.TvNombreUsuario);

        SharedPreferences preferences = vista.getContext().getSharedPreferences(
                "Credenciales", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "");
        String nombre = preferences.getString("nombre", "");

        tvViewNombreUsuario.setText(nombre);


        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = vista.getContext().getSharedPreferences(
                        "Credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id", "");
                editor.putString("nombre", "");
                editor.putString("correo", "");
                editor.putString("sangre", "");

                editor.apply();

                Intent i = getContext().getPackageManager()
                        .getLaunchIntentForPackage( getContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        return vista;
    }

}
