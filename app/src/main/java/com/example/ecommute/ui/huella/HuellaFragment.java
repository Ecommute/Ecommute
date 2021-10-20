package com.example.ecommute.ui.huella;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.AdapterHistorial;
import com.example.ecommute.MainActivity;
import com.example.ecommute.R;
import com.example.ecommute.Ruta;
import com.example.ecommute.Usuario;
import com.example.ecommute.databinding.FragmentHuellaBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Arrays;
import java.util.Vector;

public class HuellaFragment extends Fragment{

    private HuellaViewModel huellaViewModel;
    private FragmentHuellaBinding binding;
    RecyclerView historial;
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        huellaViewModel =
                new ViewModelProvider(this).get(HuellaViewModel.class);

        binding = FragmentHuellaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textDashboard;
        huellaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        //Recogemos origenes, destinos y puntos
        setUpHistorial();

        return root;
    }

    private void setUpHistorial() {
        //Per instanciar els arrays mirar el size de rutasRealizadas de l'usuari
        int n = 10;

        String[] arrayOrigenes = new String[n];
        String[] arrayDestinos = new String[n];
        Integer[] arrayPuntos = new Integer[n];

        //CODI DE PROVES: omplim els 3 arrays amb filler només per provar el recycler

        Arrays.fill(arrayPuntos, 0);

        for(int i = 0; i<n; ++i){
           arrayOrigenes[i] = "o" + i;
           arrayDestinos[i] = "d" + i;
        }

        /*CODI SEMI DEFINITIU
        //Comptant que la resta està implementada--
        Vector<Integer> rutasRealizadas = usuarioActivo.getRutasRealizadas();
        for(int i = 0; i < rutasRealizadas.size(); ++i){
            ruta = getRutaById(rutasRealizadas[i]) -> algo així, not yet implemented
            origen = ruta.getOrigen();
            destino = ruta.getDestino();
            puntos = ruta.getPuntos();

            arrayOrigenes[i] = origen;
            arrayDestinos[i] = destino;
            arrayPuntos[i] = puntos;
        }*/

        historial = binding.historial;
        AdapterHistorial mAdapter = new AdapterHistorial(this.getActivity(), arrayOrigenes, arrayDestinos, arrayPuntos);
        historial.setAdapter(mAdapter);

        mLayoutManager=new LinearLayoutManager(this.getActivity());
        historial.setLayoutManager(mLayoutManager);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}