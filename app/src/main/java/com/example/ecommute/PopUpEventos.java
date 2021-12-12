package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class PopUpEventos extends AppCompatActivity {

    String[] titulos;
    String[] localizaciones;
    String[] horas;
    RecyclerView Eventos;
    RecyclerView.LayoutManager mLayoutManager;

    ImageButton añadir;

    public void onCreate(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_eventos);

        añadir = findViewById(R.id.añadirEvento);

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

    public void setUpItems(){
        //aqui inicializamos las tres listas
    }

}
