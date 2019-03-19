package com.a300.gi.a300;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity{


    private Spinner spinnerBT;
    private TextView txtnombre, txtcorreo, txtcontra, txtcontra2;
    private Button btnregistrar,btnCargarImagen;
    private ImageView imageView;

    private Bitmap bitmap;

    ProgressDialog progressDialog;


    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        spinnerBT = (Spinner) findViewById(R.id.TxtBT);
        txtnombre = (EditText) findViewById(R.id.TxtNombre);
        txtcorreo = (EditText) findViewById(R.id.TxtCorreo);
        txtcontra = (EditText) findViewById(R.id.TxtContra);
        txtcontra2 = (EditText) findViewById(R.id.TxtContra2);
        btnregistrar = (Button) findViewById(R.id.BtnCrearUsuario);
        imageView=(ImageView)findViewById(R.id.IdImageView);
        btnCargarImagen=(Button)findViewById(R.id.BtnCargarImagen);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_type_options, android.R.layout.simple_spinner_item);

        spinnerBT.setAdapter(adapter);
    }


    private void cargarWebService() {

        progressDialog=new ProgressDialog(RegisterUserActivity.this);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Espere por favor...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url="http://gi-300.xyz/300/Guardar_usuario_img.php";

        stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();

                if(response.trim().equalsIgnoreCase("registra")){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "Su usuario ha sido registrado",
                            MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                    finish();

                }else {
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "No Registra.\nIntenta de nuevo\n"+response,
                            MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                MDToast mdToast = MDToast.makeText(getApplicationContext(), "Vaya, algo ha salido mal.\nIntenta de nuevo\n:(",
                        MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                mdToast.show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String nombre=txtnombre.getText().toString();
                String correo=txtcorreo.getText().toString();
                String sangre=spinnerBT.getSelectedItem().toString();
                String password=txtcontra.getText().toString();

                String imagen=convertirImagen(bitmap);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("nombre",nombre);
                parametros.put("correo",correo);
                parametros.put("sangre",sangre);
                parametros.put("password",password);
                parametros.put("imagen",imagen);


                return parametros;
            }
        };

        requestQueue.add(stringRequest);



        /*String url = "http://gi-300.xyz/300/Save_user.php?nombre=" + txtnombre.getText().toString() +
                "&correo=" + txtcorreo.getText().toString() + "&sangre=" + spinnerBT.getSelectedItem().toString() +
                "&password=" + txtcontra.getText().toString();

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);*/
    }

    private String convertirImagen(Bitmap bitmap) {
        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,arrayOutputStream);
        byte[] imagenByte=arrayOutputStream.toByteArray();
        String imagenString=Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }


    public void oneclick(View view) {

        cargarImagen();
    }

    private void cargarImagen() {

        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            imageView.setImageURI(path);

            try {
                bitmap=MediaStore.Images.Media.getBitmap(RegisterUserActivity.this.getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bitmap=redimensionarImagen(bitmap,600,800);
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {
        int ancho=bitmap.getWidth();
        int alto= bitmap.getHeight();

        if(ancho>anchoNuevo||alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix= new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);
        }else {
            return bitmap;
        }
    }


}
