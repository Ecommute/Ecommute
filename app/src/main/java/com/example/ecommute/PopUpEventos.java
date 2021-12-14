package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;

public class PopUpEventos extends AppCompatActivity {

    String[] titulos;
    String[] localizaciones;
    String[] horas;
    RecyclerView Eventos;
    RecyclerView.LayoutManager mLayoutManager;
    int year;
    int month;
    int dayOfMonth;

    public PopUpEventos(int year, int month, int dayOfMonth){
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    Button añadir;
    public PopUpEventos(){

    }


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

        Log.d("eventos", "on create de popupeventos");
        try {
            setUpItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO ESTO ES INSPO DE RUTA FRAGMEN PARA INICIALIZAR LOS ELEMENTOS DEL RECYCLER VIEW
        /*
        rutasFav = binding.recyclerRFavs;
        AdapterRutasFav mAdapter = new AdapterRutasFav(this.getActivity(), arrayOrigenes, arrayDestinos, arrayIds);
        rutasFav.setAdapter(mAdapter);

        mLayoutManager=new LinearLayoutManager(this.getActivity());
        rutasFav.setLayoutManager(mLayoutManager);


        return root;
        */
    }

    public void setUpItems() throws IOException, JSONException {
        //aqui inicializamos las tres listas

        /*

        final Response[] response = new Response[1];

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/list?username=" + GlobalVariables.username + "&password=" + GlobalVariables.password +)
                .method("GET", null)
                .build();

        response[0] = client.newCall(request).execute();

        String jsonData = response[0].body().string();
        JSONObject Jobject = new JSONObject(jsonData);
        JSONArray Jarray = Jobject.getJSONArray("events");

        int n = Jarray.length();
        ArrayEventos = new Events[n];

        for (int i = 0; i < n; i++) {
            JSONObject object = Jarray.getJSONObject(i);
            titulos[i] = Integer.valueOf(object.getString("title"));
            localizaciones[i] = object.getString("location");
            horas[i] = object.getString("startTime");
        }

        */
    }

}
