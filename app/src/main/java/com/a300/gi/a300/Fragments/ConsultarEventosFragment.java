package com.a300.gi.a300.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a300.gi.a300.Adaptadores.EventosAdapter;
import com.a300.gi.a300.LoginUserActivity;
import com.a300.gi.a300.MostrarEventoActivity;
import com.a300.gi.a300.R;
import com.a300.gi.a300.entidades.EventClass;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultarEventosFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    RecyclerView recyclerViewEventos;
    ArrayList<EventClass> listaEventos;

    ProgressDialog progressDialog;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;



    public ConsultarEventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_consultar_eventos, container, false);

        listaEventos = new ArrayList<>();

        recyclerViewEventos = (RecyclerView) vista.findViewById(R.id.IdRecyclerViewEventos);
        recyclerViewEventos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewEventos.setHasFixedSize(true);

        requestQueue = Volley.newRequestQueue(getContext());

        cargarWebService();

        return vista;
    }

    private void cargarWebService() {
        SharedPreferences preferences = getContext().getSharedPreferences(
                "Credenciales", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String url = "http://gi-300.xyz/300/Consultar_Eventos.php?idusuario="+preferences.getString("id","0");
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {

        EventClass eventClass = null;
        JSONArray jsonArray = response.optJSONArray("datos");
        try {
            SharedPreferences preferences = getContext().getSharedPreferences(
                    "Credenciales", Context.MODE_PRIVATE);
            for (int i = 0; i < jsonArray.length(); i++) {
                eventClass = new EventClass();
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);

                    eventClass.setId(jsonObject.optString("id"));
                    eventClass.setName(jsonObject.optString("nombre"));
                    eventClass.setAdress(jsonObject.optString("direccion"));
                    eventClass.setDescription(jsonObject.optString("descripcion"));
                    eventClass.setDate(jsonObject.optString("fecha"));
                    listaEventos.add(eventClass);





            }
            EventosAdapter adapter = new EventosAdapter(listaEventos);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), MostrarEventoActivity.class);

                    Bundle bundle=new Bundle();
                    bundle.putString("id",listaEventos.get(recyclerViewEventos.getChildAdapterPosition(view)).getId().toString());
                    bundle.putString("nombre",listaEventos.get(recyclerViewEventos.getChildAdapterPosition(view)).getName().toString());
                    bundle.putString("direccion",listaEventos.get(recyclerViewEventos.getChildAdapterPosition(view)).getAdress().toString());
                    bundle.putString("descripcion",listaEventos.get(recyclerViewEventos.getChildAdapterPosition(view)).getDescription().toString());
                    bundle.putString("fecha",listaEventos.get(recyclerViewEventos.getChildAdapterPosition(view)).getDate().toString());

                    intent.putExtras(bundle);
                    startActivity(intent);


                }
            });
            recyclerViewEventos.setAdapter(adapter);

            progressDialog.hide();
            MDToast mdToast = MDToast.makeText(getContext(), "",
                    MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
            mdToast.show();


        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.hide();
            MDToast mdToast = MDToast.makeText(getContext(), "Fallo en BD.\nIntenta de nuevo\n:(",
                    MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
            mdToast.show();

        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        progressDialog.hide();
        MDToast mdToast = MDToast.makeText(getContext(), "Vaya, algo ha salido mal.\nIntenta de nuevo\n:(",
                MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
        mdToast.show();

    }
}
