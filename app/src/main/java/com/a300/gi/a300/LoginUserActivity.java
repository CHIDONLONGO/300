package com.a300.gi.a300;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.a300.gi.a300.entidades.UserClass;
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

public class LoginUserActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    private Button btnIniciarSesion;
    private EditText txtCorreo, txtPassword;
    private TextView textView;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        btnIniciarSesion = (Button) findViewById(R.id.BtnIS);
        txtCorreo = (EditText) findViewById(R.id.TxtCorreo);
        txtPassword = (EditText) findViewById(R.id.TxtPassword);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSesion();
            }
        });

    }

    private void IniciarSesion() {

        progressDialog=new ProgressDialog(LoginUserActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String url = "http://gi-300.xyz/300/Login.php?correo=" + txtCorreo.getText().toString() +
                "&password=" + txtPassword.getText().toString();

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {

        UserClass miusuario = new UserClass();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            miusuario.setId(jsonObject.optString("id"));
            miusuario.setName(jsonObject.optString("nombre"));
            miusuario.setEmail(jsonObject.optString("correo"));
            miusuario.setBlood(jsonObject.optString("sangre"));

/*Guardar usuario en sjared preferences*/
            SharedPreferences preferences=getSharedPreferences(
                    "Credenciales",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id",miusuario.getId());
            editor.putString("nombre",miusuario.getName());
            editor.putString("correo",miusuario.getEmail());
            editor.putString("sangre",miusuario.getBlood());

            editor.apply();



        } catch (JSONException e) {
            e.printStackTrace();
        }



        progressDialog.hide();

        MDToast mdToast = MDToast.makeText(getApplicationContext(), "Hola " + miusuario.getName() + "!! :)",
                MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
        mdToast.show(); 
        finish();

        Intent i = getApplicationContext().getPackageManager()
                .getLaunchIntentForPackage( getApplicationContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);



    }



    @Override
    public void onErrorResponse(VolleyError error) {

        progressDialog.hide();

        MDToast mdToast = MDToast.makeText(getApplicationContext(), "Vaya, algo ha salido mal.\nIntenta de nuevo\n:(",
                MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
        mdToast.show();

    }
}
