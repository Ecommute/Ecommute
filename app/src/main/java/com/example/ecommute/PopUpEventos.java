package com.example.ecommute;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.ui.ruta.RutaViewModel;

public class PopUpEventos extends Activity {

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

    public PopUpEventos(){

    }


    public void onCreate(@NonNull Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_eventos);

        Log.d("eventosPopup", "on create de popupeventos");
        Log.d("eventosPopup", String.valueOf(year));
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
