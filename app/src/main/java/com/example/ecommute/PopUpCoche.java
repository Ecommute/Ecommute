package com.example.ecommute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.ecommute.ui.perfil.PerfilFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PopUpCoche extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.pop_up_coche);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));

        String fuel = null;
        String consumption = null;
        final Response[] response = new Response[1];
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/cardetails?username=" + GlobalVariables.username + "&password=" + GlobalVariables.password)
                .method("GET", null)
                .build();
        try {
            response[0] = client.newCall(request).execute();
            JSONObject respuesta = new JSONObject(response[0].body().string());
            fuel =  respuesta.getString("fuelType");
            consumption = respuesta.getString("fuelConsumption");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if(fuel != null && consumption != null){
            EditText editFuel = findViewById(R.id.fuelType);
            EditText editConsumo = findViewById(R.id.consumo);
            editFuel.setText(fuel);
            editConsumo.setText(consumption);
        }


        Button guardar = findViewById(R.id.guardar_coche);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editFuel = findViewById(R.id.fuelType);
                EditText editConsumo = findViewById(R.id.consumo);
                //API

                final Response[] response2 = new Response[1];
                OkHttpClient client2 = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create("", mediaType);
                Request request2 = new Request.Builder()
                        .url("http://10.4.41.35:3000/users/cardetails?username="+ GlobalVariables.username +"&password="+ GlobalVariables.password +"&fuelType="+ editFuel.getText().toString() +"&fuelConsumption="+ editConsumo.getText().toString())
                        .method("PUT", body)
                        .build();

                try {
                    response2[0] = client2.newCall(request2).execute();
                    JSONObject respuesta2 = new JSONObject(response2[0].body().string());
                    JSONObject Jarray = new JSONObject(respuesta2.getString("result"));

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(PopUpCoche.this, PerfilFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });

    }
}
