package com.example.ecommute.ui.ruta;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.google.android.gms.common.util.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        TextView confirmacion = binding.confirmador;
        Button buscar = binding.buscarRuta;

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    crearRuta();
                } catch (IOException | JSONException e) {
                    confirmacion.setText(e.toString());
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    private void crearRuta() throws IOException, JSONException {
        TextView confirmacion = binding.confirmador;
        EditText origen = binding.editOrigen;
        EditText destino = binding.editDestino;

        final Response[] response = new Response[1];
        final Response[] response2 = new Response[1];
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder().url("http://10.4.41.35:3000/routes/stats?origin="+origen.getText().toString()+"&destination="+destino.getText().toString()+"&mode=driving&username=marcelurpi&password=password")
                .method("GET", null).build();
        response[0] = client.newCall(request).execute();
        JSONObject respuesta = new JSONObject(response[0].body().string());
        JSONObject stats = new JSONObject(respuesta.getString("stats"));
        String duracion = stats.getString("timeValue");
        confirmacion.setText("Duración: " + duracion);

        OkHttpClient client2 = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request2 = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/add?origin="+origen.getText().toString()+"&destination="+destino.getText().toString()+"&time="+duracion+"&mode=walking&username=marcelurpi&password=password")
                .method("POST", body)
                .build();
        response2[0] = client2.newCall(request2).execute();
        JSONObject respuesta2 = new JSONObject(response2[0].body().string());
        confirmacion.setText("Resultado: " + respuesta2.getString("result"));

        /*AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    response[0] = client.newCall(request).execute();
                    /*if (!response.isSuccessful()) {
                        return null;
                    }    aqui habia comment
                    confirmacion.setText(response[0].body().string());
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            /*@Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    destino.setText(s);
                }
            } aqui habia comment
        };

        asyncTask.execute();*/



        //Ruta nuevaRuta = new Ruta(origen.getText().toString(), destino.getText().toString());
        //confirmacion.setText(response.body().string());
        //confirmacion.setText("Se ha creado la ruta:\n" + origen.getText().toString() + " -> " + destino.getText().toString());

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