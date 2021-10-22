package com.example.ecommute.ui.huella;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.AdapterHistorial;
import com.example.ecommute.PopUpClass;
import com.example.ecommute.databinding.FragmentHuellaBinding;
import com.example.ecommute.databinding.ItemHistorialBinding;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HuellaFragment extends Fragment{


    private HuellaViewModel huellaViewModel;
    private FragmentHuellaBinding bindingH;
    private ItemHistorialBinding bindingI;
    RecyclerView historial;
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        huellaViewModel =
                new ViewModelProvider(this).get(HuellaViewModel.class);

        bindingH = FragmentHuellaBinding.inflate(inflater, container, false);
        View root = bindingH.getRoot();

        /*final TextView textView = binding.textDashboard;
        huellaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        //Recogemos origenes, destinos y puntos
        try {
            setUpHistorial();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //POP-UP VER DETALLES

        bindingI = ItemHistorialBinding.inflate(inflater, container, false);
        //View rootI = bindingI.getRoot();

        /*Button iButton = bindingI.itemButton;
        iButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(v);
            }
        });*/

        return root;
    }

    private void setUpHistorial() throws Exception {
        int n = 10;

        String[] arrayOrigenes = new String[n];
        String[] arrayDestinos = new String[n];
        Integer[] arrayPuntos = new Integer[n];
        Integer[] arrayIds = new Integer[n];


        /*CODI DE PROVES: omplim els 3 arrays amb filler només per provar el recycler

        Arrays.fill(arrayPuntos, 0);
        Arrays.fill(arrayIds, 1);

        for(int i = 0; i<n; ++i){
           arrayOrigenes[i] = "o" + i;
           arrayDestinos[i] = "d" + i;
        }*/

        /*CODI SEMI DEFINITIU*/

        /*URL url = new URL("10.4.41.35:3000/routes/list?username=marcelurpi&password=password");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
            //urlConnection.setRequestProperty("User-Agent", USER_AGENT);

        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader rd = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(rd);
            //String line = bufferedReader.readLine();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://10.4.41.35:3000/routes/list?username=marcelurpi&password=password")
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    int size = responseHeaders.size();

                    arrayOrigenes = new String[size];
                    arrayDestinos = new String[size];
                    arrayPuntos= new Integer[size];
                    arrayIds = new Integer[size];


                    for (int i = 0; i < size; i++) {
                        if(responseHeaders.name(i).equals("id")) arrayIds[i] = Integer.valueOf(responseHeaders.value(i));
                        else if(responseHeaders.name(i).equals("origin")) arrayOrigenes[i] = responseHeaders.value(i);
                        else if(responseHeaders.name(i).equals("destination")) arrayDestinos[i] = responseHeaders.value(i);
                        else if(responseHeaders.name(i).equals("points")) arrayPuntos[i] = Integer.valueOf(responseHeaders.value(i));
                    }
                }
            }
        });



        /*JSONObject obj = new JSONObject();
        JSONArray arr = obj.getJSONArray("value");

        int n = arr.length();

        arrayOrigenes = new String[n];
        arrayDestinos = new String[n];
        arrayPuntos = new Integer[n];

        for(int i = 0; i < n; i++) {
            arrayOrigenes[i] = arr.getJSONObject(i).getString("origin");
            arrayDestinos[i] = arr.getJSONObject(i).getString("destination");
            arrayPuntos[i] = Integer.valueOf(arr.getJSONObject(i).getString("points"));
        }*/

        TextView textView4 = bindingH.textView4;

        final Response[] response = new Response[1];

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/list?username=marcelurpi&password=password")
                .method("GET", null)
                .build();

        response[0] = client.newCall(request).execute();
        textView4.setText(response[0].body().string());

        /*JSONObject obj = response[0];
        JSONArray arr = obj.getJSONArray("value");

        int size = arr.length();

        String[] arrayOrigenes = new String[size];
        String[] arrayDestinos = new String[size];
        Integer[] arrayPuntos= new Integer[size];
        Integer[] arrayIds = new Integer[size];

        for(int i = 0; i < size; i++) {
            arrayIds[i] = Integer.valueOf(arr.getJSONObject(i).getString("id"));
            arrayOrigenes[i] = arr.getJSONObject(i).getString("origin");
            arrayDestinos[i] = arr.getJSONObject(i).getString("destination");
            arrayPuntos[i] = Integer.valueOf(arr.getJSONObject(i).getString("points"));
        }

        /*Headers responseHeaders = response[0].headers();
        int size = responseHeaders.size();

        String[] arrayOrigenes = new String[size];
        String[] arrayDestinos = new String[size];
        Integer[] arrayPuntos= new Integer[size];
        Integer[] arrayIds = new Integer[size];

        for (int i = 0; i < size; i++) {
            if(responseHeaders.name(i).equals("id")) arrayIds[i] = Integer.valueOf(responseHeaders.value(i));
            else if(responseHeaders.name(i).equals("origin")) arrayOrigenes[i] = responseHeaders.value(i);
            else if(responseHeaders.name(i).equals("destination")) arrayDestinos[i] = responseHeaders.value(i);
            else if(responseHeaders.name(i).equals("points")) arrayPuntos[i] = Integer.valueOf(responseHeaders.value(i));
        }*/


        historial = bindingH.historial;
        AdapterHistorial mAdapter = new AdapterHistorial(this.getActivity(), arrayOrigenes, arrayDestinos, arrayPuntos, arrayIds);
        historial.setAdapter(mAdapter);

        mLayoutManager=new LinearLayoutManager(this.getActivity());
        historial.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bindingH = null;
        bindingI = null;
    }
}