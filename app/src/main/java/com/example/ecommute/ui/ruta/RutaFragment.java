package com.example.ecommute.ui.ruta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommute.Ruta;
import com.example.ecommute.databinding.FragmentRutaBinding;

public class RutaFragment extends Fragment {

    private RutaViewModel rutaViewModel;
    private FragmentRutaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rutaViewModel =
                new ViewModelProvider(this).get(RutaViewModel.class);

        binding = FragmentRutaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView titulo = binding.textNotifications;
        rutaViewModel.getTitleText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                titulo.setText(s);
            }
        });

        Button buscar = binding.buscarRuta;

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearRuta();
            }
        });

        return root;
    }

    private void crearRuta(){
        TextView confirmacion = binding.confirmador;
        EditText origen = binding.editOrigen;
        EditText destino = binding.editDestino;
        Ruta nuevaRuta = new Ruta(origen.getText().toString(), destino.getText().toString());
        confirmacion.setText("Se ha creado la ruta:\n" + nuevaRuta.getOrigen() + " -> " + nuevaRuta.getDestino());

        //Intento fallido de pasar a un fragmento nuevo para mostrar el mapa
        //getFragmentManager().beginTransaction().replace(R.id.navigation_ruta, new PerfilFragment()).addToBackStack(null).commit();

        //afegir id al set de rutasRealizadas del currentUser
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}