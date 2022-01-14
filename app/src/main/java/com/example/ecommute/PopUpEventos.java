package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PopUpEventos extends AppCompatActivity {


    String[] arrayTitulos;
    String[] arrayLocalitzacions;
    RecyclerView ListaEventos;
    RecyclerView.LayoutManager mLayoutManager;

    Button añadir;


    public void onCreate(@NonNull Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_eventos);

        añadir = findViewById(R.id.button);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUpEventos.this, PopUpAnadirEvento.class);
                startActivity(intent);
            }
        });

        try {
            setUpRecycler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpRecycler() throws IOException, JSONException {

        if(GlobalVariables.authcode != null){

            final Response[] response = new Response[1];

            OkHttpClient client = new OkHttpClient().newBuilder().build();

            Request request = new Request.Builder()
                    .url("http://10.4.41.35:3000/calendar/getEvent?time=" + GlobalVariables.fecha + "&code=" + GlobalVariables.authcode
                            + "&username=" + GlobalVariables.username + "&password=" + GlobalVariables.password)
                    .method("GET", null)
                    .build();

            response[0] = client.newCall(request).execute();

            String jsonData = response[0].body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            JSONArray Jarray = Jobject.getJSONArray("events");

            int n = Jarray.length();
            String[] arrayTitulos = new String[n];
            String[] arrayLocalitzacions = new String[n];

            for (int i = 0; i < n; i++) {
                JSONObject object = Jarray.getJSONObject(i);
                if (object.has("summary")) {
                    arrayTitulos[i] = object.getString("summary");
                } else arrayTitulos[i] = "No title";
                if (object.has("location")) {
                    arrayLocalitzacions[i] = object.getString("location");
                } else arrayLocalitzacions[i] = "No location";
            }

        } else {
            Toast.makeText(this, "Debes logearte con Google para ver tus eventos!", Toast.LENGTH_LONG).show();
        }

        ListaEventos = findViewById(R.id.EventosView);
        AdapterEvents mAdapter = new AdapterEvents(this, arrayTitulos, arrayLocalitzacions);
        ListaEventos.setAdapter((mAdapter));

        mLayoutManager = new LinearLayoutManager(this);
        ListaEventos.setLayoutManager(mLayoutManager);
    }

}
