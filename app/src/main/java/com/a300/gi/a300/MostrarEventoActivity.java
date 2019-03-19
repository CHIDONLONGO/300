package com.a300.gi.a300;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

public class MostrarEventoActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    TextView txtId,txtNombre,txtFecha,txtDireccion,txtDescripcion;
    Button btnDonar;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_evento);

        txtId=(TextView) findViewById(R.id.TvMIdEvento);
        txtNombre=(TextView) findViewById(R.id.TvMNombreEvento);
        txtFecha=(TextView) findViewById(R.id.TvMFechaEvento);
        txtDireccion=(TextView) findViewById(R.id.TvMDireccionEvento);
        txtDescripcion=(TextView) findViewById(R.id.TvMDescripecionEvento);

        btnDonar=(Button) findViewById(R.id.BtnDonar);
        Bundle bundle=this.getIntent().getExtras();
        if (bundle!=null){
            txtId.setText(bundle.getString("id"));
            txtNombre.setText(bundle.getString("nombre"));
            txtFecha.setText(bundle.getString("fecha"));
            txtDireccion.setText(bundle.getString("direccion"));
            txtDescripcion.setText(bundle.getString("descripcion"));

            requestQueue = Volley.newRequestQueue(getApplicationContext());

            btnDonar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences(
                            "Credenciales", Context.MODE_PRIVATE);
                    String id = preferences.getString("id", "");
                    if (id.equals("")) {
                        MDToast mdToast = MDToast.makeText(getApplicationContext(), "Tienes que iniciar sesion",
                                MDToast.LENGTH_SHORT, MDToast.TYPE_INFO);
                        mdToast.show();
                    } else {
                        cargarWebService();
                        {
                        }
                    }
                }
            });
        }
    }

    private void cargarWebService() {
        Bundle bundle=this.getIntent().getExtras();
        progressDialog=new ProgressDialog(MostrarEventoActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        SharedPreferences preferences = getSharedPreferences(
                "Credenciales", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "");;
        String url = "http://gi-300.xyz/300/Guardar_Donante.php?idevento="+bundle.getString("id")+"&idusuario="+id;


        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        progressDialog.hide();
        MDToast mdToast = MDToast.makeText(getApplicationContext(), "Se a registrado",
                MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
        mdToast.show();
        finish();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        progressDialog.hide();
        MDToast mdToast = MDToast.makeText(getApplicationContext(), "Vaya, algo ha salido mal.\nIntenta de nuevo\n:(",
                MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
        mdToast.show();

    }

}
