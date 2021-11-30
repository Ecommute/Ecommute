package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivityLoginBinding;
import com.example.ecommute.databinding.ActivityRutasBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RutasActivity extends AppCompatActivity {

    private ActivityRutasBinding binding;
    private String iframe;
    private Button walking;
    private Button driving;
    private Button bicycling;
    private Button transit;
    private Button guardar;
    private String method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        binding = ActivityRutasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Get map iframe
        try {
            getMap();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        WebView googleMapWebView = binding.googlemapWebView;
        googleMapWebView.getSettings().setJavaScriptEnabled(true);
        googleMapWebView.loadData(iframe, "text/html", "utf-8");

        walking = binding.walking;
        driving = binding.driving;
        bicycling = binding.bicycling;
        transit = binding.transit;
        guardar = binding.guardar;

        try {
            getOptions();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickWalking();
            }
        });

        driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDriving();
            }
        });

        bicycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBicycling();
            }
        });

        transit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTransit();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    clickGuardar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getMap() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/map?origin="+GlobalVariables.origen+"&destination="+GlobalVariables.destino+"&mode=driving&username="+GlobalVariables.username+"&password="+GlobalVariables.password+"&width=390&height=400")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject respuesta = new JSONObject(response.body().string());
        iframe = respuesta.getString("iframe");
    }

    private void getOptions() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/options?username="+GlobalVariables.username+"&password="+GlobalVariables.password+"&origin="+GlobalVariables.origen+"&destination="+GlobalVariables.destino)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject respuesta = new JSONObject(response.body().string());
        JSONObject opciones = new JSONObject(respuesta.getString("options"));
        JSONObject conducir = new JSONObject(opciones.getString("driving"));
        JSONObject caminar = new JSONObject(opciones.getString("walking"));
        JSONObject bicicleta = new JSONObject(opciones.getString("bicycling"));
        JSONObject publico = new JSONObject(opciones.getString("transit"));

        walking.setText("      Walking            +" + caminar.getInt("points") + " puntos");
        driving.setText("      driving              +" + conducir.getInt("points") + " puntos");
        bicycling.setText("      bicycling          +" + bicicleta.getInt("points") + " puntos");
        transit.setText("      transit              +" + publico.getInt("points") + " puntos");

    }

    private void clickWalking(){
        method = "walking";
        walking.setBackgroundColor(getResources().getColor(R.color.medium_green));
        driving.setBackgroundColor(getResources().getColor(R.color.light_green));
        bicycling.setBackgroundColor(getResources().getColor(R.color.light_green));
        transit.setBackgroundColor(getResources().getColor(R.color.light_green));

    }

    private void clickDriving(){
        method = "driving";
        walking.setBackgroundColor(getResources().getColor(R.color.light_green));
        driving.setBackgroundColor(getResources().getColor(R.color.medium_green));
        bicycling.setBackgroundColor(getResources().getColor(R.color.light_green));
        transit.setBackgroundColor(getResources().getColor(R.color.light_green));
    }

    private void clickBicycling(){
        method = "bicycling";
        walking.setBackgroundColor(getResources().getColor(R.color.light_green));
        driving.setBackgroundColor(getResources().getColor(R.color.light_green));
        bicycling.setBackgroundColor(getResources().getColor(R.color.medium_green));
        transit.setBackgroundColor(getResources().getColor(R.color.light_green));
    }

    private void clickTransit(){
        method = "transit";
        walking.setBackgroundColor(getResources().getColor(R.color.light_green));
        driving.setBackgroundColor(getResources().getColor(R.color.light_green));
        bicycling.setBackgroundColor(getResources().getColor(R.color.light_green));
        transit.setBackgroundColor(getResources().getColor(R.color.medium_green));
    }

    private void clickGuardar() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/add?origin="+GlobalVariables.origen+"&destination="+GlobalVariables.destino+"&mode="+method+"&username="+GlobalVariables.username+"&password="+GlobalVariables.password)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        Intent intent = new Intent(RutasActivity.this, MainActivity.class);
        startActivity(intent);
    }

}