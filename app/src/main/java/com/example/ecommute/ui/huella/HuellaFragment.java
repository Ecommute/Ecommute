package com.example.ecommute.ui.huella;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.AdapterHistorial;
import com.example.ecommute.PopUpClass;
import com.example.ecommute.databinding.FragmentForoBinding;
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

public class HuellaFragment extends Fragment{

    private HuellaViewModel huellaViewModel;
    private FragmentHuellaBinding bindingH;
    private ItemHistorialBinding bindingI;
    RecyclerView historial;
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        View rootI = bindingI.getRoot();

        View iBox = bindingI.itemBox;
        iBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(v);
            }
        });

        return root;
    }

    private void setUpHistorial() throws Exception {
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

        URL url = new URL("10.4.41.35:3000/routes/list?username=marcelurpi&password=password");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
            //urlConnection.setRequestProperty("User-Agent", USER_AGENT);

        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader rd = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(rd);
            //String line = bufferedReader.readLine();

        JSONObject obj = new JSONObject(bufferedReader.toString());
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

        historial = bindingH.historial;
        AdapterHistorial mAdapter = new AdapterHistorial(this.getActivity(), arrayOrigenes, arrayDestinos, arrayPuntos);
        historial.setAdapter(mAdapter);

        mLayoutManager=new LinearLayoutManager(this.getActivity());
        historial.setLayoutManager(mLayoutManager);

        //urlConnection.disconnect();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bindingH = null;
        bindingI = null;
    }
}