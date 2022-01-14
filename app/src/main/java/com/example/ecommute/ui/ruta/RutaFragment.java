package com.example.ecommute.ui.ruta;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageHelper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.AdapterRutasFav;
import com.example.ecommute.GlobalVariables;
import com.example.ecommute.LoginActivity;
import com.example.ecommute.Ruta;
import com.example.ecommute.RutasActivity;

import com.example.ecommute.R;
import com.example.ecommute.databinding.FragmentRutaBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RutaFragment extends Fragment {

    private RutaViewModel rutaViewModel;
    private static FragmentRutaBinding binding;
    private LocationRequest locationRequest;


    String[] arrayOrigenes;
    String[] arrayDestinos;
    Integer[] arrayIds;
    RecyclerView rutasFav;
    RecyclerView.LayoutManager mLayoutManager;
    EditText origen;
    FusedLocationProviderClient client;

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

        Button export = binding.export;

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                export();
            }
        });

        client = LocationServices.getFusedLocationProviderClient(getActivity());
        ImageButton useLocation = binding.useLocation;
        useLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permissions
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    Toast toast = Toast.makeText(getActivity(), "Obteniendo dirección...", Toast.LENGTH_SHORT);
                    TextView vivi = (TextView) toast.getView().findViewById(android.R.id.message);
                    if( vivi != null)
                        toast.setGravity(Gravity.CENTER, 0, -200);
                    toast.getView().getBackground().setTint(ContextCompat.getColor(getContext(), R.color.light_green));
                    toast.show();
                    //When permission is granted
                    Task<Location> ubicacion = client.getCurrentLocation(100, null).addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(getContext(), Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String dire = addresses.get(0).getAddressLine(0).toString();
                            String direccion = dire.split(", ")[0] + ", " + dire.split(", ")[1];
                            origen.setText(direccion);
                        }
                    });
                }else{
                    Toast toast = Toast.makeText(getActivity(), "Activa el permiso de ubicación desde los ajustes, por favor", Toast.LENGTH_LONG);
                    TextView vivi = (TextView) toast.getView().findViewById(android.R.id.message);
                    if( vivi != null)
                        toast.setGravity(Gravity.CENTER, 0, 150);
                    toast.getView().getBackground().setTint(ContextCompat.getColor(getContext(), R.color.light_green));
                    toast.show();
                }
            }
        });

        TextView confirmacion = binding.confirmador;
        Button buscar = binding.buscarRuta;
        origen = binding.editOrigen;
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    crearRuta2();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        /*buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    crearRuta();
                } catch (IOException | JSONException e) {
                    confirmacion.setText(e.toString());
                    e.printStackTrace();
                }
            }
        });*/

        //START CODI RECYCLER RUTAS FAV
        try {
            setUpRutasFav();
        } catch (Exception e) {
            e.printStackTrace();
        }

        rutasFav = binding.recyclerRFavs;
        AdapterRutasFav mAdapter = new AdapterRutasFav(this.getActivity(), arrayOrigenes, arrayDestinos, arrayIds);
        rutasFav.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        rutasFav.setLayoutManager(mLayoutManager);
        //END CODI

        return root;
    }

    /*@SuppressLint("MissingPermission")
    private void getCurrentLocation(){
        //Initialize location manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Check Condition
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            //When location service is enabled, get last location
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @RequiresApi(api = Build.VERSION_CODES.S)
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Initialize location
                    Location location = task.getResult();
                    //Check condition
                    if(location != null){
                        //When Location exists
                        //Set latitude and longitude
                        EditText origen = binding.editOrigen;
                        origen.setText(String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude()));
                    }else{
                        //When location not ok
                        locationRequest.
                    }
                }
            })
        }
    }*/

    private void setUpRutasFav() throws Exception {
        String username = GlobalVariables.username;
        String password = GlobalVariables.password;

        final Response[] response = new Response[1];

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/favourites/list?username=" + username + "&password=" + password)
                .method("GET", null)
                .build();

        response[0] = client.newCall(request).execute();

        String jsonData = response[0].body().string();
        JSONObject Jobject = new JSONObject(jsonData);
        JSONArray Jarray = Jobject.getJSONArray("routes");

        int n;
        if (Jarray != null) n = Jarray.length();
        else n = 0;
        arrayOrigenes = new String[n];
        arrayDestinos = new String[n];
        arrayIds = new Integer[n];

        for (int i = 0; i < n; i++) {
            JSONObject object = Jarray.getJSONObject(i);
            arrayIds[i] = Integer.valueOf(object.getString("id"));
            arrayOrigenes[i] = object.getString("origin");
            arrayDestinos[i] = object.getString("destination");
        }
    }

    private void crearRuta() throws IOException, JSONException {
        TextView confirmacion = binding.confirmador;
        EditText origen = binding.editOrigen;
        EditText destino = binding.editDestino;
        String username = GlobalVariables.username;
        String password = GlobalVariables.password;

        final Response[] response = new Response[1];
        final Response[] response2 = new Response[1];
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder().url("http://10.4.41.35:3000/routes/stats?origin=" + origen.getText().toString() + "&destination=" + destino.getText().toString() + "&mode=driving&username=" + username + "&password=" + password)
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
                .url("http://10.4.41.35:3000/routes/add?origin=" + origen.getText().toString() + "&destination=" + destino.getText().toString() + "&time=" + duracion + "&mode=walking&username=" + username + "&password=" + password)
                .method("POST", body)
                .build();
        response2[0] = client2.newCall(request2).execute();
        JSONObject respuesta2 = new JSONObject(response2[0].body().string());
        confirmacion.setText("Resultado: " + respuesta2.getString("result"));
    }

    private void crearRuta2() throws IOException, JSONException {
        EditText origen = binding.editOrigen;
        EditText destino = binding.editDestino;
        String strOrigen = origen.getText().toString();
        String strDestino = destino.getText().toString();


        if(strOrigen.equals("") || strDestino.equals("")) {
            Toast.makeText(this.getActivity(), "Debes rellenar ambos campos", Toast.LENGTH_SHORT).show();
        }
        else {
            GlobalVariables.origen = strOrigen;
            GlobalVariables.destino = strDestino;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/cardetails?username=test&password=test")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject respuesta2 = new JSONObject(response.body().string());
        if(respuesta2.getString("fuelType") == "null" || respuesta2.getString("fuelConsumption") == "null"){
            Toast toast = Toast.makeText(getActivity(), "No has introducido detalles de tu coche", Toast.LENGTH_SHORT);
            TextView vivi = (TextView) toast.getView().findViewById(android.R.id.message);
            if( vivi != null)
                toast.setGravity(Gravity.CENTER, 0, -200);
            toast.getView().getBackground().setTint(ContextCompat.getColor(getContext(), R.color.light_green));
            toast.show();
        }else{
            Intent intent = new Intent(getActivity(), RutasActivity.class);
            startActivity(intent);
        }

    }


    }

    public void export() {
        //Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + binding.editDestino.getText().toString());
        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+binding.editOrigen.getText().toString()+"&destination="+binding.editDestino.getText().toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void setRutaFromFavs(String origen, String destino) {
        View root = binding.getRoot();

        TextView tvOrigen = root.findViewById(R.id.editOrigen);
        TextView tvDestino = root.findViewById(R.id.editDestino);
        tvOrigen.setText(origen);
        tvDestino.setText(destino);
    }

}